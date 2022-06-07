package com.aleesha.domain.aero.domain

import com.aleesha.core.usecase.BaseInputLessUseCase
import com.aleesha.domain.aero.data.model.AirlinesItem

interface FetchAirlinesUseCase : BaseInputLessUseCase<FetchAirlinesUseCase.Output> {
    sealed class Output : BaseInputLessUseCase.Output {
        data class Success(val airlines: List<AirlinesItem>) : Output()
        object ErrorNetwork : Output()
        data class UnknownError(val message: String) : Output()
    }
}
