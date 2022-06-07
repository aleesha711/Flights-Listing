package com.aleesha.domain.aero.data.datasource

import com.aleesha.domain.aero.data.model.AirlinesItem
import com.aleesha.domain.aero.data.remote.AirlinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchAirlinesRemoteDatasourceImpl @Inject constructor(private val airlinesApi: AirlinesApi) : FetchAirlinesRemoteDatasource {
    override suspend fun getAirlines(): Flow<List<AirlinesItem>> = flow {
        emit(airlinesApi.getAirlines())
    }
}
