package com.example.jetpackcomposepokedex.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpackcomposepokedex.presentation.pokemonList.PokemonListScreen

@Composable
fun NavManager() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.PokemonList.route
    ) {
        composable(Screen.PokemonList.route) {
            PokemonListScreen(navController = navController)
        }

        composable(
            route = Screen.PokemonDetail.route,
            arguments = listOf(
                navArgument("dominantColor") {
                    type = NavType.IntType
                },
                navArgument("pokemonName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val dominantColor = remember {
                val color = backStackEntry.arguments?.getInt("dominantColor")
                color?.let { Color(it) } ?: Color.White
            }

            val pokemonName = remember {
                backStackEntry.arguments?.getString("pokemonName") ?: ""
            }

            // TODO: Implement PokemonDetailScreen
        }
    }
}
