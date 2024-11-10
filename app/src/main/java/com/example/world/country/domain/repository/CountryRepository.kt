package com.example.world.country.domain.repository


import com.example.world.core.domain.DataError
import com.example.world.core.domain.Result
import com.example.world.country.domain.model.CountryListResponse
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    suspend fun countryList(): Flow<Result<CountryListResponse,DataError.Network>>
}