package com.example.world.presentation.country_list.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.world.domain.DataError
import com.example.world.domain.Result
import com.example.world.domain.model.CountryItem
import com.example.world.domain.model.CountryListResponse
import com.example.world.domain.repository.CountryRepository
import com.example.world.domain.use_case.GetCountryUseCase
import com.example.world.presentation.common.utils.UiMessage
import com.example.world.presentation.country_list.model.CountryState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
class CountryViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CountryViewModel
    private lateinit var getCountryUseCase: GetCountryUseCase
    private val repository : CountryRepository = mock()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getCountryUseCase = GetCountryUseCase(repository)
        viewModel = CountryViewModel(getCountryUseCase)
    }

    @Test
    fun `getCountryList - should set loading state`() = runBlocking {
        whenever(getCountryUseCase()).thenReturn(flowOf(Result.Loading))

        viewModel.getCountryList()

        viewModel.state.test {
            val item = awaitItem()
            assertEquals(CountryState(isLoading = true), item)
        }
    }

    @Test
    fun `getCountryList - should set success state`() = runBlocking {
        val fakeCountryList = CountryListResponse(
            listCountry = listOf(CountryItem("India", "IN"))
        )
        whenever(getCountryUseCase()).thenReturn(flowOf(Result.Success(fakeCountryList)))

        viewModel.getCountryList()

        viewModel.state.test {
            val item = awaitItem()
            assertEquals(CountryState(isLoading = false, countryListResponse = fakeCountryList),item)
        }
    }

    @Test
    fun `getCountryList - should set error state`() = runBlocking {
        val fakeError = DataError.Network.SERVER_ERROR
        whenever(getCountryUseCase()).thenReturn(flowOf(Result.Error<CountryListResponse, DataError.Network>(fakeError)))

        viewModel.getCountryList()

        viewModel.state.test {
            val item = awaitItem().copy(error = UiMessage.DynamicString(fakeError.toString()))
            assertEquals(CountryState(isLoading = false, error = UiMessage.DynamicString(fakeError.toString())),
                item)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}