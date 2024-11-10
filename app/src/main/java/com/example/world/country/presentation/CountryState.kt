package com.example.world.country.presentation

import com.example.world.core.presentation.util.UiMessage
import com.example.world.country.domain.model.CountryListResponse

/**
 * Country state
 *
 * @property isLoading for showing loader status
 * @property countryListResponse for showing country list
 * @property error for showing error message
 * @constructor Create empty Country state
 */
data class CountryState(
    val isLoading : Boolean = false,
    val countryListResponse: CountryListResponse ?= null,
    val error: UiMessage = UiMessage.DynamicString("")
)
