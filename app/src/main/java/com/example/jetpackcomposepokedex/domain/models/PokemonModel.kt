package com.example.jetpackcomposepokedex.domain.models

/**
 * Modelo de dominio para un Pokémon.
 * Representa los datos necesarios para mostrar un Pokémon en la UI.
 * Este modelo es agnóstico de la fuente de datos (API, base de datos, etc).
 */
data class PokemonModel(
    val id: Int,
    val name: String,
    val height: Int,           // en decímetros
    val weight: Int,           // en hectogramos
    val baseExperience: Int,
    val imageUrl: String?,     // URL de la imagen oficial
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

/**
 * Modelo simplificado para mostrar en la lista.
 */
data class PokemonListItem(
    val id: Int,
    val name: String,
    val imageUrl: String?
) {
    val displayName: String get() = name.replaceFirstChar { it.uppercase() }
}
