package com.example.world.domain.use_case

import com.example.world.domain.repository.CountryRepository
import javax.inject.Inject

class GetCountryUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    suspend operator fun invoke() = repository.countryList()
}