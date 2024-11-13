package com.example.world.presentation.common.navigation

import kotlinx.serialization.Serializable


sealed class Screens {
    @Serializable
    object Country

    @Serializable
    data class CountryDetail(
        val countryCode: String = ""
    )
}