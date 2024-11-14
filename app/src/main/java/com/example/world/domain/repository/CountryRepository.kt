package com.example.world.domain.repository


import com.example.world.domain.model.DataError
import com.example.world.domain.model.Result
import com.example.world.domain.model.CountryListResponse
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    suspend fun countryList(): Flow<Result<CountryListResponse, DataError.Network>>
}