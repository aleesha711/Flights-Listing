package com.aleesha.core.usecase

import kotlinx.coroutines.flow.Flow

interface BaseInputLessUseCase<Output : BaseInputLessUseCase.Output> {

    suspend fun execute(): Flow<Output>

    interface Output
}
