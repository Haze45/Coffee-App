package com.example.coffee.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.coffee.presentation.screens.cartscreen.CartScreen
import com.example.coffee.presentation.screens.detailscreen.DetailScreen
import com.example.coffee.presentation.screens.favouriteScreen.FavouriteScreen
import com.example.coffee.presentation.screens.homescreen.HomeScreen
import com.example.coffee.presentation.screens.profilescreen.ProfileScreen
import com.example.coffee.presentation.screens.welcomescreens.WelcomeScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.WelcomeScreen) {
        composable<Routes.WelcomeScreen> {
            WelcomeScreen(navController)
        }

        composable<Routes.HomeScreen> {
            HomeScreen(navController)
        }

        composable<Routes.DetailScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<Routes.DetailScreen>()
            DetailScreen(productId = args.productId, navController)
        }

        composable<Routes.CartScreen> {
            CartScreen(navController)
        }

        composable<Routes.FavouriteScreen> {
            FavouriteScreen(navController)
        }

        composable<Routes.ProfileScreen> {
            ProfileScreen(navController)
        }
    }

}