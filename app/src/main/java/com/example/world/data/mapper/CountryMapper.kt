package com.example.world.data.mapper

import com.example.world.domain.model.CountryItem
import com.example.world.domain.model.CountryListResponse
import com.example.world.data.remote.model.CountryListResponseDto

/**
 * To country list
 *
 * This function maps CountryListResponseDto to CountryListResponse
 */
fun CountryListResponseDto.toCountryList(): CountryListResponse {
    return CountryListResponse(
        listCountry = data?.mapNotNull {
            CountryItem(
                countryName = it?.name?:"",
                countryCode = it?.code?:""
            )
        }?: emptyList()
    )
}