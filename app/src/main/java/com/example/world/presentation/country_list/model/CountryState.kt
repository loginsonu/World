package com.example.world.presentation.country_list.model

import com.example.world.presentation.common.utils.UiMessage
import com.example.world.domain.model.CountryListResponse

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
    val countryListResponse: CountryListResponse?= null,
    val error: UiMessage = UiMessage.DynamicString("")
)
