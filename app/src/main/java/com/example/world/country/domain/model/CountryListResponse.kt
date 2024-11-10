package com.example.world.country.domain.model



data class CountryListResponse(
    val listCountry: List<CountryItem>
)

data class CountryItem(
    val countryName: String,
    val countryCode: String
)
