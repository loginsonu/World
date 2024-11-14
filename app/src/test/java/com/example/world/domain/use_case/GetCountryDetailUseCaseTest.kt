package com.example.world.domain.use_case

import com.example.world.domain.model.CountryDetailsResponse
import com.example.world.domain.model.DataError
import com.example.world.domain.model.Result
import com.example.world.domain.repository.CountryDetailsRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetCountryDetailUseCaseTest {

    private lateinit var getCountryDetailsUseCase: GetCountryDetailUseCase
    private val repository : CountryDetailsRepository = mock()
    private lateinit var countryCode: String

    @Before
    fun setup() {
        countryCode = "IN"
        getCountryDetailsUseCase = GetCountryDetailUseCase(repository)
    }

    @Test
    fun `should return loading state initially`() = runTest {
        //Arrange
        val flowResult = flow {
            emit(Result.Loading)
        }

        whenever(repository.countryDetails(countryCode)).thenReturn(flowResult)
        //Act
        val resultFlow = getCountryDetailsUseCase(countryCode)

        //Assert
        resultFlow.collect { result ->
            assertTrue(result is Result.Loading)
            val data = (result as Result.Loading)
            assertEquals(Result.Loading, data)
        }

        // Verify that the repository method was called exactly once
        verify(repository, times(1)).countryDetails(countryCode)
    }

    @Test
    fun `should return country details successfully`() = runTest {
        //Arrange
        val fakeCountryDetails = CountryDetailsResponse(
            countryName = "India",
            capitalName = "New Delhi",
            callingCode = "+91",
            countryFlag = "https://flagcdn.com/w320/in.png"
        )

        val flowResult = flow {
            emit(Result.Success(fakeCountryDetails))
        }
        whenever(repository.countryDetails(countryCode)).thenReturn(flowResult)

        //Act
        val resultFlow = getCountryDetailsUseCase(countryCode)

        //Assert
        resultFlow.collect { result ->
            assertTrue(result is Result.Success)
            val data = (result as Result.Success).data
            assertEquals(fakeCountryDetails, data)
        }

        // Verify that the repository method was called exactly once
        verify(repository, times(1)).countryDetails(countryCode)
    }

    @Test
    fun `should return error when repository fails`() = runTest {
        //Arrange
        val fakeError = DataError.Network.SERVER_ERROR
        val flowResult = flow {
            emit(Result.Error<CountryDetailsResponse, DataError.Network>(fakeError)) // Emitting a failure result
        }

        whenever(repository.countryDetails(countryCode)).thenReturn(flowResult)

        //Act
        val resultFlow = getCountryDetailsUseCase(countryCode)

        // Assert
        resultFlow.collect { result ->
            assertTrue(result is Result.Error)
            val error = (result as Result.Error<CountryDetailsResponse, DataError.Network>).error
            assertEquals(fakeError,error)
        }

        // Verify that the repository method was called exactly once
        verify(repository, times(1)).countryDetails(countryCode)
    }
}