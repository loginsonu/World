package com.example.world.presentation.country_details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.world.domain.Result
import com.example.world.presentation.common.mapper.toUiNetworkErrorMessage
import com.example.world.presentation.common.utils.UiMessage
import com.example.world.domain.use_case.GetCountryDetailUseCase
import com.example.world.presentation.country_details.model.CountryDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val getCountryDetailUseCase: GetCountryDetailUseCase
):ViewModel() {
    private val _state = MutableStateFlow(CountryDetailsState())
    val state = _state.asStateFlow()


    fun getCountryDetails(countryCode:String){
        viewModelScope.launch(Dispatchers.IO) {
            getCountryDetailUseCase(countryCode)
                .collect{ result->
                    when(result){
                        is Result.Loading -> {
                            _state.update {
                                it.copy(isLoading = true)
                            }

                        }

                        is Result.Success ->{
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    countryDetailsResponse = result.data
                                )
                            }

                        }

                        is Result.Error ->{
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.error.toUiNetworkErrorMessage()
                                )
                            }

                        }
                    }
                }
        }
    }

    fun setCountryCode(countryCode:String){
        _state.update {
            it.copy(countryCode = countryCode)
        }
    }

    fun resetState(){
        _state.update {
            it.copy(
                error = UiMessage.DynamicString("")
            )
        }
    }
}