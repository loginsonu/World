package com.example.world.data.repository

import app.cash.turbine.test
import com.example.world.data.remote.api.Api
import com.example.world.data.remote.model.CountryListResponseDto
import com.example.world.data.remote.model.DataItem
import com.example.world.domain.model.CountryItem
import com.example.world.domain.model.CountryListResponse
import com.example.world.domain.model.DataError
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import com.example.world.domain.model.Result
import org.mockito.kotlin.doAnswer
import java.io.IOException


@ExperimentalCoroutinesApi
class CountryRepositoryImplTest {

    private val api : Api = mock()
    private lateinit var repository: CountryRepositoryImpl

    @Before
    fun setup() {
        repository = CountryRepositoryImpl(api)
    }

    @Test
    fun `countryList should emit Loading then Success`() = runTest{
        val mockDto = CountryListResponseDto(
            data = listOf(
                DataItem(name = "India", code = "IN"),
                DataItem(name = "Canada", code = "CA")
            )
        )

        whenever(api.getCountryList()).thenReturn(mockDto)

        // Expected mapped response
        val expectedResponse = CountryListResponse(
            listCountry = listOf(
                CountryItem(countryName = "India", countryCode = "IN"),
                CountryItem(countryName = "Canada", countryCode = "CA")
            )
        )


        repository.countryList().test {

            assertEquals(Result.Loading, awaitItem())

            assertEquals(Result.Success(expectedResponse), awaitItem())

            awaitComplete()
        }
    }

    @Test
    fun `countryList should emit Loading then Error on any exception`() = runTest{

        val errorList = listOf(
            IOException("No Internet") to DataError.Network.NO_INTERNET
            )

        errorList.forEach { (exception, expectedError) ->

            whenever(api.getCountryList()).doAnswer{ throw exception }

            repository.countryList().test {
                assertEquals(Result.Loading, awaitItem())

                val errorResult = awaitItem()
                assert(errorResult is Result.Error && errorResult.error == expectedError)

                awaitComplete()
            }
        }
    }
}