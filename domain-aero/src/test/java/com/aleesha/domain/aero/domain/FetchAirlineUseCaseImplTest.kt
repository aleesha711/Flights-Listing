package com.aleesha.domain.aero.domain

import app.cash.turbine.test
import com.aleesha.domain.aero.data.model.AirlinesItem
import com.aleesha.domain.aero.data.repository.FetchAirlinesRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

class FetchAirlineUseCaseImplTest {
    private val repository: FetchAirlinesRepository = mockk()
    private val useCase = FetchAirlinesUseCaseImpl(repository, TestDispatcherProvider())

    private val airline = AirlinesItem("class", "alliance", "code", "defaultname", "logourl", "name", "phone", "site", "usname", true)

    @Test
    fun `test getAirlines provide valid list`() = runTest {
        coEvery {
            repository.getAirlines()
        } returns flow { emit(listOf(airline)) }

        useCase.execute().test {
            val item = awaitItem()
            assert(item is FetchAirlinesUseCase.Output.Success)
            assert((item as FetchAirlinesUseCase.Output.Success).airlines.size == listOf(airline).size)
            awaitComplete()
        }
    }

    @Test
    fun `test getAirlines throw unknown error`() = runTest {
        val expected = FetchAirlinesUseCase.Output.UnknownError("test")
        coEvery {
            repository.getAirlines()
        } returns flow { emit(throw RuntimeException("test")) }

        useCase.execute().test {
            assertEquals(expected, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `test getAirlines throw network error`() = runTest {
        val expected = FetchAirlinesUseCase.Output.ErrorNetwork
        coEvery {
            repository.getAirlines()
        } returns flow { emit(throw IOException("test")) }

        useCase.execute().test {
            assertEquals(expected, awaitItem())
            awaitComplete()
        }
    }
}
