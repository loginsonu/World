package com.example.world.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.world.core.domain.Result
import com.example.world.core.presentation.mapper.toUiNetworkErrorMessage
import com.example.world.core.presentation.util.UiMessage
import com.example.world.detail.domain.use_case.GetCountryDetailUseCase
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