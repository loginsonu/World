package com.example.world.presentation.country_list.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.world.domain.model.CountryDetailsResponse
import com.example.world.domain.model.DataError
import com.example.world.domain.model.Result
import com.example.world.domain.repository.CountryDetailsRepository
import com.example.world.domain.use_case.GetCountryDetailUseCase
import com.example.world.presentation.common.utils.UiMessage
import com.example.world.presentation.country_details.model.CountryDetailsState
import com.example.world.presentation.country_details.viewmodel.CountryDetailsViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CountryDetailsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CountryDetailsViewModel
    private lateinit var getCountryDetailUseCase: GetCountryDetailUseCase
    private lateinit var countryCode: String
    private val repository : CountryDetailsRepository = mock()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        countryCode = "IN"
        getCountryDetailUseCase = GetCountryDetailUseCase(repository)
        viewModel = CountryDetailsViewModel(getCountryDetailUseCase)
    }

    @Test
    fun `getCountryDetails - should set loading state`() = runBlocking{
        whenever(getCountryDetailUseCase(countryCode)).thenReturn(flowOf(Result.Loading))

        viewModel.getCountryDetails(countryCode)

        viewModel.state.test {
            val item = awaitItem()
            assertEquals(CountryDetailsState(isLoading = true), item)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getCountryDetails - should set success state`() = runBlocking {
        val fakeCountryDetails = CountryDetailsResponse(
            countryName = "India",
            capitalName = "New Delhi",
            callingCode = "+91",
            countryFlag = "https://flagcdn.com/w320/in.png"
        )
        whenever(getCountryDetailUseCase(countryCode)).thenReturn(flowOf(Result.Success(fakeCountryDetails)))

        viewModel.getCountryDetails(countryCode)

        viewModel.state.test {
            val item = awaitItem()
            assertEquals(CountryDetailsState(isLoading = false, countryDetailsResponse = fakeCountryDetails),item)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getCountryDetails - should set error state`() = runBlocking {
        val fakeError = DataError.Network.SERVER_ERROR
        whenever(getCountryDetailUseCase(countryCode)).thenReturn(flowOf(Result.Error<CountryDetailsResponse, DataError.Network>(fakeError)))

        viewModel.getCountryDetails(countryCode)

        viewModel.state.test {

            val item = awaitItem().copy(
                error = UiMessage.DynamicString(fakeError.toString())
            )
            assertEquals(CountryDetailsState(isLoading = false,
                error = UiMessage.DynamicString(fakeError.toString())
            ),item)
            cancelAndConsumeRemainingEvents()
        }

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}