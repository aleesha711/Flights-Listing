package com.aleesha.domain.aero.di

import com.aleesha.domain.aero.data.datasource.FetchAirlinesRemoteDatasource
import com.aleesha.domain.aero.data.datasource.FetchAirlinesRemoteDatasourceImpl
import com.aleesha.domain.aero.data.repository.FetchAirlinesRepository
import com.aleesha.domain.aero.data.repository.FetchAirlinesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal interface DataModule {
    @Binds
    @Singleton
    fun bindFetchAirlinesRemoteDatasource(fetchAirlinesRemoteDatasourceImpl: FetchAirlinesRemoteDatasourceImpl): FetchAirlinesRemoteDatasource

    @Binds
    @Singleton
    fun bindFetchAirlinesRepository(fetchAirlinesRepositoryImpl: FetchAirlinesRepositoryImpl): FetchAirlinesRepository
}
