package com.example.world.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.world.presentation.country_list.ui.CountryList
import com.example.world.presentation.country_details.ui.CountryDetails


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