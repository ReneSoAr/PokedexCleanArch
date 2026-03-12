package com.example.jetpackcomposepokedex.presentation.navigation

/**
 * Rutas de navegación type-safe.
 * 
 * Uso:
 * - Screen.PokemonList.route → "pokemon_list"
 * - Screen.PokemonDetail.createRoute(0xFF0000, "Pikachu") → "pokemon_detail/0/16711680/Pikachu"
 */
sealed class Screen(val route: String) {
    
    object PokemonList : Screen("pokemon_list")
    
    object PokemonDetail : Screen("pokemon_detail/{dominantColor}/{pokemonName}") {
        fun createRoute(dominantColor: Int, pokemonName: String): String {
            return "pokemon_detail/$dominantColor/$pokemonName"
        }
    }
}
