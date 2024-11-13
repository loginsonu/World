package com.example.world.presentation.country_list.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.world.domain.Result
import com.example.world.presentation.common.mapper.toUiNetworkErrorMessage
import com.example.world.presentation.common.utils.UiMessage
import com.example.world.domain.use_case.GetCountryUseCase
import com.example.world.presentation.country_list.model.CountryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val getCountryUseCase: GetCountryUseCase
): ViewModel(){
    private val _state = MutableStateFlow(CountryState())
    val state = _state.asStateFlow()

    init {
        getCountryList()
    }


    fun getCountryList(){
        viewModelScope.launch(Dispatchers.IO) {
            getCountryUseCase()
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
                                countryListResponse = result.data
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

    fun resetState(){
        _state.update {
            it.copy(
                error = UiMessage.DynamicString("")
            )
        }
    }


}