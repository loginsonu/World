package com.example.world.data.remote.api


import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api : Api


    @Before
    fun setUp(){

        mockWebServer = MockWebServer()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url(path = "/"))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Test
    fun `getCountryList expected data`() = runTest{
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(
                """{
                    "metadata": {"currentOffset": 0, "totalCount": 10},
                    "data": [{"wikiDataId": "Q30", "code": "US", "currencyCodes": ["USD"], "name": "United States"}],
                    "links": []
                }"""
            )
        mockWebServer.enqueue(mockResponse)

        val response = api.getCountryList()
        mockWebServer.takeRequest()

        assertEquals(1, response.data?.size)
        assertEquals("United States", response.data?.first()?.name)
        assertEquals("US", response.data?.first()?.code)
    }

    @Test
    fun `getCountryList should return error`() = runTest {
        // Arrange
        val mockResponse = MockResponse().setResponseCode(403).setBody("{\"error\": \"Access Denied\"}")
        mockWebServer.enqueue(mockResponse)

        val exception = runCatching { api.getCountryList(limit = 15) }.exceptionOrNull()

        assert(exception is retrofit2.HttpException)
        assertEquals(403, (exception as retrofit2.HttpException).code())
    }


    @Test
    fun `getCountryDetails should return data`() = runTest {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(
                """{
                   "data": {"wikiDataId": "Q30", "code": "US", "currencyCodes": ["USD"], "name": "United States","flagImageUri": "https://images.png"}
                }"""
            )
        mockWebServer.enqueue(mockResponse)

        // Act
        val response = api.getCountryDetails("US")

        // Assert
        assertEquals("United States", response.data?.name)
        assertEquals("US", response.data?.code)
    }
}