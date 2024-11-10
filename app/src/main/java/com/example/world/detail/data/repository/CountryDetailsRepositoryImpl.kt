package com.example.world.detail.data.repository

import com.example.world.core.data.remote.Api
import com.example.world.core.domain.DataError
import com.example.world.core.domain.Result
import com.example.world.core.domain.mapper.toNetworkError
import com.example.world.detail.data.remote.toCountryDetails
import com.example.world.detail.domain.model.CountryDetailsResponse
import com.example.world.detail.domain.repository.CountryDetailsRepository
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