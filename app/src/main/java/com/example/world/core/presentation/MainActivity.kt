package com.example.world.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.world.core.presentation.navigation.MainNavGraph
import com.example.world.core.presentation.ui.theme.WorldTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorldTheme {
                Box(
                    modifier = Modifier.safeDrawingPadding()
                ){
                    MainNavGraph(rememberNavController())
                }

            }
        }
    }
}