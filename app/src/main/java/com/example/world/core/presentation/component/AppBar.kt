package com.example.world.core.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * App bar
 *
 * @param appTitle the title to show
 */
@Composable
fun AppBar(
    appTitle:String
){
    Card (
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = appTitle,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AppBarPreview(){
    AppBar(
        appTitle = "Default"
    )
}