package com.aleesha.domain.aero.data.repository

import app.cash.turbine.test
import com.aleesha.domain.aero.data.datasource.FetchAirlinesRemoteDatasource
import com.aleesha.domain.aero.data.model.AirlinesItem
import dagger.Lazy
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FetchAirlinesRepositoryImplTest {
    private val fetchAirlinesRemoteDatasource: Lazy<FetchAirlinesRemoteDatasource> = mockk()
    private val repository = FetchAirlinesRepositoryImpl(fetchAirlinesRemoteDatasource)
    private val airline = AirlinesItem("class", "alliance", "code", "defaultname", "logourl", "name", "phone", "site", "usname", true)

    @Test
    fun `test getAirlines return valid list`() = runTest {
        coEvery {
            fetchAirlinesRemoteDatasource.get().getAirlines()
        } returns flow { emit(listOf(airline)) }

        repository.getAirlines().test {
            assertEquals(listOf(airline), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `test getAirlines return empty list`() = runTest {
        coEvery {
            fetchAirlinesRemoteDatasource.get().getAirlines()
        } returns flow { emit(emptyList<AirlinesItem>()) }

        repository.getAirlines().test {
            assertEquals(emptyList<AirlinesItem>(), awaitItem())
            awaitComplete()
        }
    }
}