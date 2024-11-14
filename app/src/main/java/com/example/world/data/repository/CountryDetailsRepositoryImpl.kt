package com.example.world.data.repository

import com.example.world.data.remote.api.Api
import com.example.world.domain.model.DataError
import com.example.world.domain.model.Result
import com.example.world.data.mapper.toNetworkError
import com.example.world.data.mapper.toCountryDetails
import com.example.world.domain.model.CountryDetailsResponse
import com.example.world.domain.repository.CountryDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountryDetailsRepositoryImpl @Inject constructor(
    private val api: Api
) : CountryDetailsRepository {
    override suspend fun countryDetails(countryCode:String
    ): Flow<Result<CountryDetailsResponse, DataError.Network>>  = flow{
       try {
           emit(Result.Loading)
           val response = api.getCountryDetails(countryCode).toCountryDetails()
           emit(Result.Success(response))
       }catch(exception: Throwable) {
           emit(Result.Error(exception.toNetworkError()))
       }
    }
}