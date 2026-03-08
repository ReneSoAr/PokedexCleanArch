package com.example.jetpackcomposepokedex.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavManager() {
    val navContoller = rememberNavController()

    NavHost(
        navContoller,
        startDestination = "pokemon_list-Screen"
    ) {
        composable(
            route = "pokemon_list-Screen/{dominantColor}/{pokemonName}",
            arguments = listOf(
                navArgument(name = "dominantColor") {
                    type = NavType.IntType
                },

                navArgument("pokemonName") {
                    type = NavType.StringType
                }
            )
        ) {navBackStackEntry ->
            val dominantColor = remember {
                val color = navBackStackEntry.arguments?.getInt("dominantColor")
                color?.let{ Color(it) }?: Color.White
            }

            val pokemonName = remember{
                navBackStackEntry.arguments?.getString("pokemonName")
            }

        }
    }
}