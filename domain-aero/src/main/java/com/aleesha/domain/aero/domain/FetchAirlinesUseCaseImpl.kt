package com.aleesha.domain.aero.domain

import com.aleesha.core.coroutine.SuspendDispatcher
import com.aleesha.domain.aero.data.repository.FetchAirlinesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class FetchAirlinesUseCaseImpl @Inject constructor(
    private val repository: FetchAirlinesRepository,
    private val dispatcherProvider: SuspendDispatcher
) : FetchAirlinesUseCase {
    override suspend fun execute(): Flow<FetchAirlinesUseCase.Output> {
        return repository.getAirlines().map {
            FetchAirlinesUseCase.Output.Success(it) as FetchAirlinesUseCase.Output
        }.catch { exception ->
            if (exception.isNetworkException()) {
                emit(FetchAirlinesUseCase.Output.ErrorNetwork)
            } else {
                emit(FetchAirlinesUseCase.Output.UnknownError(exception.message.orEmpty()))
            }
        }.flowOn(dispatcherProvider.io())
    }

    private fun Throwable.isNetworkException() =
        this is IOException || this is UnknownHostException
}
