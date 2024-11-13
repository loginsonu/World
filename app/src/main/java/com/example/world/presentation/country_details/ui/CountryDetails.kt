package com.example.world.presentation.country_details.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.example.world.R
import com.example.world.presentation.country_details.model.CountryDetailsState
import com.example.world.presentation.country_details.viewmodel.CountryDetailsViewModel
import com.example.world.presentation.common.component.AppBar
import com.example.world.presentation.common.component.ErrorView
import com.example.world.domain.model.CountryDetailsResponse
import com.example.world.presentation.country_details.ui.components.ImageComponent

/**
 * Country details
 *
 * @param countryCode THis to use in country details api call
 * @param viewModel HiltViewModel Injection
 */
@Composable
fun CountryDetails(
    countryCode : String,
    viewModel: CountryDetailsViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setCountryCode(countryCode)
        viewModel.getCountryDetails(countryCode)
    }
        CountryDetailsLocal(
            state = state,
            resetState = {viewModel.resetState()},
            retry = {
                viewModel.getCountryDetails(state.countryCode)
            }
        )



}

/**
 * Country details local
 *
 * @param state Screen State
 * @param resetState After any Error, Call back event to reset the error state
 * @param retry After any Error, Call back event to Retry api call
 * @receiver
 * @receiver
 */
@Composable
fun CountryDetailsLocal(
    state: CountryDetailsState,
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
            state.countryDetailsResponse?.let { details->
                //here imageLoader is created for decoding svg image
                val imageLoader = ImageLoader.Builder(context)
                    .components {
                        add(SvgDecoder.Factory())
                    }
                    .build()

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppBar(
                        appTitle = stringResource(R.string.country_details)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                       ImageComponent(
                           modifier = Modifier.size(200.dp),
                           imageUrl = details.countryFlag.replace("http://", "https://"),
                           imageLoader = imageLoader,
                           contentDescription = "flag",
                       )
                        Text(
                            modifier = Modifier.padding(top = 5.dp),
                            text = details.countryName,
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ){
                        Text(
                            text = stringResource(R.string.capital),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            modifier = Modifier.padding(start = 5.dp),
                            text = details.capitalName
                        )

                    }

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = stringResource(R.string.calling_code),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            modifier = Modifier.padding(start = 5.dp),
                            text = details.callingCode
                        )

                    }

                }
            }?: ErrorView(
                message = errorMessage
            ){
                retry()
            }

        }
    }

}


@Preview(showBackground = true)
@Composable
fun CountryDetailsErrorPreview(){
    CountryDetailsLocal(CountryDetailsState())
}

@Preview(showBackground = true)
@Composable
fun CountryDetailsPreview(){
    CountryDetailsLocal(
        CountryDetailsState(
            countryDetailsResponse = CountryDetailsResponse(
                countryName = "XYZ",
                capitalName = "abc",
                callingCode = "+91",
                countryFlag = ""
            )
        )
    )
}