package com.example.world.presentation.common.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.world.presentation.common.navigation.MainNavGraph
import com.example.world.presentation.common.ui.theme.WorldTheme
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
                    val navController = rememberNavController()
                    MainNavGraph(navController)
                }

            }
        }
    }
}