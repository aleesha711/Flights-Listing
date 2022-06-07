package com.aleesha.datastore.datastoremanager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DataStoreManagerImpl @Inject constructor(
    private val userPreferencesDataStore: DataStore<Preferences>
) : DataStoreManager {

    override suspend fun saveAirlinesToPreferencesStore(key: String) {
        userPreferencesDataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = key
        }
    }

    override fun getAirlinesFromPreferencesStore(key: String): Flow<String?> {
        return userPreferencesDataStore.data
            .map { preferences -> preferences[stringPreferencesKey(key)] }
    }

    override suspend fun removeAirline(key: String) {
        userPreferencesDataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(key))
        }
    }
}
