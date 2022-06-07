package com.aleesha.domain.aero.data.repository

import com.aleesha.domain.aero.data.datasource.FetchAirlinesRemoteDatasource
import com.aleesha.domain.aero.data.model.AirlinesItem
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAirlinesRepositoryImpl @Inject constructor(private val fetchAirlinesRemoteDatasource: Lazy<FetchAirlinesRemoteDatasource>) : FetchAirlinesRepository {
    override suspend fun getAirlines(): Flow<List<AirlinesItem>> {
        return fetchAirlinesRemoteDatasource.get().getAirlines()
    }
}