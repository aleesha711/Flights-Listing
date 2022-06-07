package com.aleesha.datastore.datastoremanager

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    suspend fun saveAirlinesToPreferencesStore(key: String)
    suspend fun removeAirline(key: String)
    fun getAirlinesFromPreferencesStore(key: String): Flow<String?>
}