package com.aleesha.feature_aero.ui.viewmodel

import com.aleesha.BaseTest
import com.aleesha.datastore.datastoremanager.DataStoreManager
import com.aleesha.domain.aero.data.model.AirlinesItem
import com.aleesha.domain.aero.domain.FetchAirlinesUseCase
import com.aleesha.domain.aero.domain.FetchAirlinesUseCase.Output.*
import com.aleesha.feature_aero.viewmodel.AirlineViewModel
import io.mockk.*
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Test

class AirlineViewModelTest : BaseTest() {

    private lateinit var viewModel: AirlineViewModel
    private val fetchAirlinesUseCase: FetchAirlinesUseCase = mockk(relaxed = true)
    private val dataStoreManager: DataStoreManager = mockk(relaxed = true)
    private val airline = AirlinesItem("class", "alliance", "code", "defaultname", "logourl", "name", "phone", "site", "usname", true)

    private fun getViewModel() {
        viewModel = AirlineViewModel(fetchAirlinesUseCase, dataStoreManager)
    }

    @Test
    fun `test fetch airlines shows success and providing list`() = runTest {
        coEvery {
            fetchAirlinesUseCase.execute()
        } returns flow { emit(Success(listOf(airline))) }

        getViewModel()
        Assert.assertTrue(viewModel.airlines.value!!.isNotEmpty())
        TestCase.assertEquals(arrayListOf(airline), viewModel.airlines.value)
    }

    @Test
    fun `test fetch airlines shows network error`() = runTest {
        coEvery {
            fetchAirlinesUseCase.execute()
        } returns flow { emit(ErrorNetwork) }

        getViewModel()
        TestCase.assertEquals(Unit, viewModel.isNetworkFailure.value)
    }

    @Test
    fun `test fetch airlines shows unknown error`() = runTest {
        coEvery {
            fetchAirlinesUseCase.execute()
        } returns flow { emit(UnknownError("error message")) }

        getViewModel()
        Assert.assertTrue(viewModel.unknownError.value!!.isNotEmpty())
        TestCase.assertEquals("error message", viewModel.unknownError.value)
    }
}
