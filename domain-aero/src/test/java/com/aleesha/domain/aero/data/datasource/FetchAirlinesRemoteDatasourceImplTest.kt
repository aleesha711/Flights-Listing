package com.aleesha.domain.aero.data.datasource

import app.cash.turbine.test
import com.aleesha.domain.aero.data.model.AirlinesItem
import com.aleesha.domain.aero.data.remote.AirlinesApi
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FetchAirlinesRemoteDatasourceImplTest {

    private val airlinesApi: AirlinesApi = mockk(relaxed = true)

    private val fetchAirlinesRemoteDatasourceImpl = FetchAirlinesRemoteDatasourceImpl(
        airlinesApi
    )

    private val airline = AirlinesItem("class", "alliance", "code", "defaultname", "logourl", "name", "phone", "site", "usname", true)

    @Test
    fun `test getAirlines valid data emission`() = runTest {
        coEvery {
            airlinesApi.getAirlines()
        } returns listOf(airline)

        fetchAirlinesRemoteDatasourceImpl.getAirlines().test {
            assertEquals(listOf(airline), awaitItem())
            awaitComplete()
        }
    }
}
