package com.example.world.data.mapper

import com.example.world.data.remote.model.CountryDetailsResponseDto
import com.example.world.domain.model.CountryDetailsResponse

/**
 * To country Details
 *
 * This function maps CountryDetailsResponseDto to CountryDetailsResponse
 */
fun CountryDetailsResponseDto.toCountryDetails(): CountryDetailsResponse {
    return CountryDetailsResponse(
        countryName = data?.name?:"",
        capitalName = data?.capital?:"",
        callingCode = data?.callingCode?:"",
        countryFlag = data?.flagImageUri?:""
    )
}