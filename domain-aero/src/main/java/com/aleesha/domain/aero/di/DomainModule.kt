package com.aleesha.domain.aero.di

import com.aleesha.core.coroutine.AppCoroutineDispatchers
import com.aleesha.core.coroutine.SuspendDispatcher
import com.aleesha.domain.aero.domain.FetchAirlinesUseCase
import com.aleesha.domain.aero.domain.FetchAirlinesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
internal interface DomainModule {
    @Binds
    fun bindFetchAirlinesUseCase(fetchAirlinesUseCaseImpl: FetchAirlinesUseCaseImpl): FetchAirlinesUseCase

    companion object {
        @ViewModelScoped
        @Provides
        fun provideDispatcher(): SuspendDispatcher {
            return AppCoroutineDispatchers()
        }
    }
}
