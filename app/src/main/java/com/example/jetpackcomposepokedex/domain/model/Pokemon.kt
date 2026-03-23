package com.example.jetpackcomposepokedex.domain.model

/**
 * Domain model representing a Pokémon.
 * Pure Kotlin data class with no Android dependencies.
 */
data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,           // in decimeters
    val weight: Int,           // in hectograms
    val baseExperience: Int,
    val imageUrl: String?,     // URL of the official artwork
    val types: List<PokemonType>,
    val stats: List<PokemonStat>
) {
    val displayName: String get() = name.replaceFirstChar { it.uppercase() }
    val heightInMeters: Float get() = height / 10f
    val weightInKg: Float get() = weight / 10f
    val primaryType: PokemonType get() = types.firstOrNull() ?: PokemonType.UNKNOWN
    val totalStats: Int get() = stats.sumOf { it.value }
    val maxStat: Int get() = stats.maxOfOrNull { it.value } ?: 0
}