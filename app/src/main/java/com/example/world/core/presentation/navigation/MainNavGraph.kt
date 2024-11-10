package com.example.world.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.world.country.presentation.CountryList
import com.example.world.detail.presentation.CountryDetails


/**
 * Main nav graph
 *
 * @param navController this is required for navigation from one composable to another
 */
@Composable
fun MainNavGraph(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screens.Country
    ) {
        composable<Screens.Country> {
            CountryList(navController=navController)
        }

        composable<Screens.CountryDetail> {
            val countryCode = it.toRoute<Screens.CountryDetail>().countryCode
            CountryDetails(countryCode=countryCode)
        }
    }
}