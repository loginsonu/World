package com.example.world.detail.presentation

import com.example.world.core.presentation.util.UiMessage
import com.example.world.detail.domain.model.CountryDetailsResponse

/**
 * Country details state
 *
 * @property isLoading used for loader status
 * @property countryDetailsResponse used for showing country details
 * @property error used for showing error message
 * @property countryCode used for country code
 * @constructor Create empty Country details state
 */
data class CountryDetailsState(
    val isLoading : Boolean = false,
    val countryDetailsResponse: CountryDetailsResponse?=null,
    val error: UiMessage = UiMessage.DynamicString(""),
    val countryCode:String = ""
)
