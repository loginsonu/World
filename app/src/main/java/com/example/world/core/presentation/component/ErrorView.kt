package com.example.world.core.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.world.R

@Composable
fun ErrorView(
    message: String,
    onTryAgain : ()->Unit = {}
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(message)
        Button(
            modifier = Modifier.padding(top = 5.dp),
            onClick = {
                onTryAgain()
            }
        ) {
            Text(stringResource(R.string.try_again))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorPagePreview(){
    ErrorView(
        message = "Something went wrong"
    )
}