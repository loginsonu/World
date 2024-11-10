package com.example.world.core.data.remote

import com.example.world.country.data.remote.dto.CountryListResponseDto
import com.example.world.detail.data.remote.CountryDetailsResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    // this api is free version it is giving list of 10 data only so kept default
    // value as 10, if we pass limit as more than 10 it will give access denied
    @GET("v1/geo/countries")
    suspend fun getCountryList(
        @Query("limit") limit: Int= 10
    ) :CountryListResponseDto

    @GET("v1/geo/countries/{countryCode}")
    suspend fun getCountryDetails(
        @Path("countryCode") countryCode: String
    ) :CountryDetailsResponseDto
}