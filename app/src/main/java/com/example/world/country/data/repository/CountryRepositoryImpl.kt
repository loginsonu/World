package com.example.world.country.data.repository

import com.example.world.core.data.remote.Api
import com.example.world.core.domain.DataError
import com.example.world.core.domain.Result
import com.example.world.core.domain.mapper.toNetworkError
import com.example.world.country.data.remote.dto.toCountryList
import com.example.world.country.domain.model.CountryListResponse
import com.example.world.country.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
    private val api: Api
) : CountryRepository {
    override suspend fun countryList(): Flow<Result<CountryListResponse, DataError.Network>>  = flow{
       try {
           emit(Result.Loading)
           val response = api.getCountryList().toCountryList()
           emit(Result.Success(response))
       }catch(exception: Throwable) {
           emit(Result.Error(exception.toNetworkError()))
       }


    }
}