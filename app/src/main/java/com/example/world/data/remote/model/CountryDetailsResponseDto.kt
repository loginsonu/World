package com.example.world.data.remote.model


data class CountryDetailsResponseDto(
	val data: Data? = null
)

data class Data(
	val callingCode: String? = null,
	val flagImageUri: String? = null,
	val capital: String? = null,
	val wikiDataId: String? = null,
	val code: String? = null,
	val numRegions: Int? = null,
	val currencyCodes: List<String?>? = null,
	val name: String? = null
)


