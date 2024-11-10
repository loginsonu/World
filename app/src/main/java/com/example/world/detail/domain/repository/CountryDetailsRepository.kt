package com.example.world.detail.domain.repository

import com.example.world.core.domain.DataError
import com.example.world.core.domain.Result
import com.example.world.detail.domain.model.CountryDetailsResponse
import kotlinx.coroutines.flow.Flow

interface CountryDetailsRepository {
  suspend fun countryDetails(
      countryCode:String
  ): Flow<Result<CountryDetailsResponse, DataError.Network>>
}