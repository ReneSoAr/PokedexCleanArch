package com.example.jetpackcomposepokedex.data.remote.responses

/**
 * Sprites de un Pokémon.
 * 
 * Nota: Los sprites female pueden ser null para Pokémon sin diferencias de género.
 */
data class Sprites(
    val back_default: String,
    val back_female: String?,  // Nullable - no todos los Pokémon tienen versión femenina
    val back_shiny: String,
    val back_shiny_female: String?,  // Nullable
    val front_default: String,
    val front_female: String?,  // Nullable
    val front_shiny: String,
    val front_shiny_female: String?,  // Nullable
    val other: Other,
    val versions: Versions
)
