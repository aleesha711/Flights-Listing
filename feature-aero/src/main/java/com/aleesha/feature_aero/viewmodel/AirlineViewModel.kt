package com.aleesha.feature_aero.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleesha.core.extension.exhaustive
import com.aleesha.datastore.datastoremanager.DataStoreManager
import com.aleesha.domain.aero.data.model.AirlinesItem
import com.aleesha.domain.aero.domain.FetchAirlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirlineViewModel @Inject constructor(private val fetchAirlinesUseCase: FetchAirlinesUseCase, private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val _airlines: MutableLiveData<List<AirlinesItem>> = MutableLiveData()
    private val _isNetworkFailure = MutableLiveData<Unit>()
    private val _unknownError = MutableLiveData<String>()

    val airlines: LiveData<List<AirlinesItem>>
        get() = _airlines

    val isNetworkFailure: LiveData<Unit>
        get() = _isNetworkFailure

    val unknownError: LiveData<String>
        get() = _unknownError

    init {
        fetchAirlines()
    }

    fun fetchAirlines() {
        viewModelScope.launch {
            fetchAirlinesUseCase.execute().collect { output ->
                when (output) {
                    is FetchAirlinesUseCase.Output.Success -> {
                        val remoteAirLines = output.airlines
                        remoteAirLines.forEach {
                            retrieveFavoriteAirline(it)
                        }
                        _airlines.postValue(remoteAirLines)
                    }
                    is FetchAirlinesUseCase.Output.ErrorNetwork -> {
                        _isNetworkFailure.postValue(Unit)
                    }
                    is FetchAirlinesUseCase.Output.UnknownError -> {
                        _unknownError.postValue(output.message)
                    }
                }.exhaustive
            }
        }
    }

    fun markFavoriteAirline(key: String) {
        viewModelScope.launch {
            dataStoreManager.saveAirlinesToPreferencesStore(key)
        }
    }

    fun removeFavoriteAirline(key: String) {
        viewModelScope.launch {
            dataStoreManager.removeAirline(key)
        }
    }

    private fun retrieveFavoriteAirline(airlinesItem: AirlinesItem) {
        viewModelScope.launch {
            dataStoreManager.getAirlinesFromPreferencesStore(airlinesItem.name).collect { name ->
                name?.let {
                    airlinesItem.isFavorite = true
                }
            }
        }
    }

    fun isFavoriteAirline(name: String): Boolean {
        var isFavorite = false
        viewModelScope.launch {
            dataStoreManager.getAirlinesFromPreferencesStore(name).collect { name ->
                name?.let {
                    isFavorite = true
                }
            }
        }
        return isFavorite
    }
}
