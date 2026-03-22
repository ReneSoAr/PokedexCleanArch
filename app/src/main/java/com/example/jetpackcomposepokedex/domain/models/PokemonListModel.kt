package com.example.jetpackcomposepokedex.domain.models

/**
 * Modelo de dominio para la lista de Pokémon.
 * 
 * Representa la respuesta de la API con la lista de Pokémon.
 * Nota: previous puede ser null (String?) para la primera página.
 */
data class PokemonListModel(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonListItem>
)