package com.example.world.domain.repository

import com.example.world.domain.DataError
import com.example.world.domain.Result
import com.example.world.domain.model.CountryDetailsResponse
import kotlinx.coroutines.flow.Flow

interface CountryDetailsRepository {
  suspend fun countryDetails(
      countryCode:String
  ): Flow<Result<CountryDetailsResponse, DataError.Network>>
}