package com.example.world.country.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.world.R
import com.example.world.core.presentation.component.AppBar
import com.example.world.core.presentation.component.ErrorView
import com.example.world.core.presentation.navigation.Screens
import com.example.world.core.presentation.ui.theme.WorldTheme
import com.example.world.country.presentation.component.CountryItem


/**
 * Country list
 *
 * @param navController This is for navigation
 * @param viewModel HiltViewModel injected
 */
@Composable
fun CountryList(
    navController: NavHostController,
    viewModel: CountryViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    CountryListLocal(
        navController = navController,
        state = state,
        resetState = {viewModel.resetState()},
        retry = {viewModel.getCountryList()}
    )


}

/**
 * Country list local
 *
 * @param navController for navigation
 * @param state Screen State
 * @param resetState After any Error, Call back event to reset the error state
 * @param retry After any Error, Call back event to Retry api call
 * @receiver
 * @receiver
 */
@Composable
private fun CountryListLocal(
    navController: NavHostController,
    state: CountryState,
    resetState:()->Unit={},
    retry:()->Unit={}
){
    val context = LocalContext.current
    var errorMessage by remember {
        mutableStateOf("")
    }

    state.error.asString().takeIf { it.isNotEmpty() }?.let { message ->
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        errorMessage = message
        resetState()
    }

    Box(modifier = Modifier.fillMaxSize()){
        if(state.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }else{
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                AppBar(stringResource(R.string.country_list))
                state.countryListResponse?.let {
                    LazyColumn(
                    ) {
                        items(it.listCountry){ item ->
                            CountryItem(
                                item.countryName,
                                item.countryCode
                            ){
                                navController.navigate(
                                    Screens.CountryDetail(item.countryCode)
                                )
                            }
                        }
                    }
                }?: ErrorView(errorMessage){
                    retry()
                }


            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CountryListPreview(){
    WorldTheme {
        CountryListLocal(state = CountryState(), navController = rememberNavController())
    }
}