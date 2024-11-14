package com.example.world.data.repository

import app.cash.turbine.test
import com.example.world.data.remote.api.Api
import com.example.world.data.remote.model.CountryDetailsResponseDto
import com.example.world.data.remote.model.Data
import com.example.world.domain.model.CountryDetailsResponse
import com.example.world.domain.model.DataError
import com.example.world.domain.model.Result
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException

@ExperimentalCoroutinesApi
class CountryDetailsRepositoryImplTest {
    private val api : Api = mock()
    private lateinit var repository: CountryDetailsRepositoryImpl
    private lateinit var countryCode: String

    @Before
    fun setup() {
        countryCode = "IN"
        repository = CountryDetailsRepositoryImpl(api)
    }

    @Test
    fun `countryDetails should emit Loading then Success`() = runTest{
        val mockDto = CountryDetailsResponseDto(
            data = Data(
                callingCode = "+91",
                capital = "New Delhi",
                flagImageUri = "https://www.countryflags.io/IN/flat/64.png",
                name = "India")
            )

        whenever(api.getCountryDetails(countryCode)).thenReturn(mockDto)

        val expectedResponse = CountryDetailsResponse(
            callingCode = "+91",
            countryName = "India",
            capitalName = "New Delhi",
            countryFlag = "https://www.countryflags.io/IN/flat/64.png"
        )

        repository.countryDetails(countryCode).test {

            assertEquals(Result.Loading, awaitItem())

            assertEquals(Result.Success(expectedResponse), awaitItem())

            awaitComplete()
        }
    }

    @Test
    fun `countryDetails should emit Loading then Error on any exception`() = runTest{

        val errorList = listOf(
            IOException("No Internet") to DataError.Network.NO_INTERNET
        )

        errorList.forEach { (exception, expectedError) ->

            whenever(api.getCountryDetails(countryCode)).doAnswer{ throw exception }

            repository.countryDetails(countryCode).test {
                assertEquals(Result.Loading, awaitItem())

                val errorResult = awaitItem()
                assert(errorResult is Result.Error && errorResult.error == expectedError)

                awaitComplete()
            }
        }
    }
}