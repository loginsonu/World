package com.example.world.data.repository

import com.example.world.data.remote.api.Api
import com.example.world.domain.model.DataError
import com.example.world.domain.model.Result
import com.example.world.data.mapper.toNetworkError
import com.example.world.domain.model.CountryListResponse
import com.example.world.domain.repository.CountryRepository
import com.example.world.data.mapper.toCountryList
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