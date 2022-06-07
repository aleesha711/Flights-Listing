package com.aleesha.domain.aero.data.repository

import com.aleesha.domain.aero.data.model.AirlinesItem
import kotlinx.coroutines.flow.Flow

interface FetchAirlinesRepository {
    suspend fun getAirlines(): Flow<List<AirlinesItem>>
}