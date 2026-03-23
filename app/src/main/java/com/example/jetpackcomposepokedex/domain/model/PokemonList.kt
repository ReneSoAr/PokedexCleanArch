package com.example.jetpackcomposepokedex.domain.model

/**
 * Domain model representing a paginated list of Pokémon.
 */
data class PokemonList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonListItem>
)

/**
 * Simplified model for list items.
 */
data class PokemonListItem(
    val id: Int,
    val name: String,
    val imageUrl: String?
) {
    val displayName: String get() = name.replaceFirstChar { it.uppercase() }
}