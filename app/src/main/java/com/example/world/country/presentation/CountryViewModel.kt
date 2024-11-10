package com.example.world.country.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.world.core.domain.Result
import com.example.world.core.presentation.mapper.toUiNetworkErrorMessage
import com.example.world.core.presentation.util.UiMessage
import com.example.world.country.domain.use_case.GetCountryUseCase
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