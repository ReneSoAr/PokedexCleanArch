package com.example.jetpackcomposepokedex.domain.models

/**
 * Modelo de dominio para un resultado de Pokémon.
 * 
 * Representa un Pokémon en la lista sin todos sus detalles.
 * Solo contiene nombre y URL para obtener más información.
 */
data class PokemonResultModel(
    val name: String,
    val url: String
)
