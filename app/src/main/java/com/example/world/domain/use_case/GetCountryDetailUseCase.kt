package com.example.world.domain.use_case

import com.example.world.domain.repository.CountryDetailsRepository
import javax.inject.Inject

class GetCountryDetailUseCase @Inject constructor(
    private val repository: CountryDetailsRepository
) {
    suspend operator fun invoke(
        countryCode:String
    ) = repository.countryDetails(countryCode)
}