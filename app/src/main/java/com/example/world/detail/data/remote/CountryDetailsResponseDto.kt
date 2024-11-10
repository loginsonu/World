package com.example.world.detail.data.remote

import com.example.world.detail.domain.model.CountryDetailsResponse
import com.google.gson.annotations.SerializedName

data class CountryDetailsResponseDto(

	@field:SerializedName("data")
	val data: Data? = null
)

data class Data(

	@field:SerializedName("callingCode")
	val callingCode: String? = null,

	@field:SerializedName("flagImageUri")
	val flagImageUri: String? = null,

	@field:SerializedName("capital")
	val capital: String? = null,

	@field:SerializedName("wikiDataId")
	val wikiDataId: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("numRegions")
	val numRegions: Int? = null,

	@field:SerializedName("currencyCodes")
	val currencyCodes: List<String?>? = null,

	@field:SerializedName("name")
	val name: String? = null
)

/**
 * To country Details
 *
 * This function maps CountryDetailsResponseDto to CountryDetailsResponse
 */
fun CountryDetailsResponseDto.toCountryDetails():CountryDetailsResponse{
	return CountryDetailsResponse(
		countryName = data?.name?:"",
		capitalName = data?.capital?:"",
		callingCode = data?.callingCode?:"",
		countryFlag = data?.flagImageUri?:""
	)
}