package com.example.world.domain.repository


import com.example.world.domain.DataError
import com.example.world.domain.Result
import com.example.world.domain.model.CountryListResponse
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    suspend fun countryList(): Flow<Result<CountryListResponse, DataError.Network>>
}