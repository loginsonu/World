package com.example.world.domain.use_case

import com.example.world.domain.DataError
import com.example.world.domain.Result
import com.example.world.domain.model.CountryItem
import com.example.world.domain.model.CountryListResponse
import com.example.world.domain.repository.CountryRepository
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
class GetCountryUseCaseTest {

    private lateinit var getCountryUseCase: GetCountryUseCase
    private val repository : CountryRepository = mock()

    @Before
    fun setup() {
        getCountryUseCase = GetCountryUseCase(repository)
    }


    @Test
    fun `should return loading state initially`() = runTest {
        //Arrange
        val flowResult = flow {
            emit(Result.Loading)
        }

        whenever(repository.countryList()).thenReturn(flowResult)

        //Act
        val resultFlow = getCountryUseCase()

        //Assert
        resultFlow.collect { result ->
            assertTrue(result is Result.Loading)
            val data = (result as Result.Loading)
            assertEquals(Result.Loading, data)
        }

        // Verify that the repository method was called exactly once
        verify(repository, times(1)).countryList()
    }

    @Test
    fun `should return country list successfully`() = runTest {
        //Arrange
        val fakeCountryList = CountryListResponse(
            listCountry = listOf(CountryItem("India", "IN"))
        )

        val flowResult = flow {
            emit(Result.Success(fakeCountryList))
        }


        whenever(repository.countryList()).thenReturn(flowResult)

        //Act
        val resultFlow = getCountryUseCase()


        //Assert
        resultFlow.collect { result ->
            assertTrue(result is Result.Success)
            val data = (result as Result.Success).data
            assertEquals(fakeCountryList, data)
        }

        // Verify that the repository method was called exactly once
        verify(repository, times(1)).countryList()
    }

    @Test
    fun `should return error when repository fails`() = runTest {
        //Arrange
        val fakeError = DataError.Network.SERVER_ERROR
        val flowResult = flow {
            emit(Result.Error<CountryListResponse,DataError.Network>(fakeError)) // Emitting a failure result
        }


        whenever(repository.countryList()).thenReturn(flowResult)

        // Act
        val resultFlow = getCountryUseCase()

        // Assert
        resultFlow.collect { result ->
            assertTrue(result is Result.Error)
            val error = (result as Result.Error<CountryListResponse, DataError.Network>).error
            assertEquals(fakeError,error)
        }

        // Verify that the repository method was called exactly once
        verify(repository, times(1)).countryList()
    }
}