package com.aleesha.domain.aero.di

import com.aleesha.domain.aero.data.remote.AirlinesApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
    }

    @Provides
    @Singleton
    fun provideAirlinesService(
        retrofitBuilder: Retrofit.Builder
    ): AirlinesApi {

        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        val retrofit = retrofitBuilder
            .baseUrl(AirlinesApi.BASE_URL)
            .client(client)
            .build()

        return retrofit.create(AirlinesApi::class.java)
    }
}
