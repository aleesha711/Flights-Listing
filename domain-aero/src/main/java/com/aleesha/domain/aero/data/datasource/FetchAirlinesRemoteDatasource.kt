package com.aleesha.domain.aero.data.datasource

import com.aleesha.domain.aero.data.model.AirlinesItem
import kotlinx.coroutines.flow.Flow

interface FetchAirlinesRemoteDatasource {
    suspend fun getAirlines(): Flow<List<AirlinesItem>>
}