package com.example.world.detail.domain.use_case

import com.example.world.detail.domain.repository.CountryDetailsRepository
import javax.inject.Inject

class GetCountryDetailUseCase @Inject constructor(
    private val repository: CountryDetailsRepository
) {
    suspend operator fun invoke(
        countryCode:String
    ) = repository.countryDetails(countryCode)
}