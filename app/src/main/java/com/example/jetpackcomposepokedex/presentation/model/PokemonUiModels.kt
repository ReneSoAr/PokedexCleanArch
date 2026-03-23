package com.example.jetpackcomposepokedex.presentation.model

import androidx.compose.ui.graphics.Color

/**
 * UI model for Pokemon list item.
 */
data class PokemonListItemUi(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val dominantColor: Color
)

/**
 * UI model for detailed Pokemon.
 */
data class PokemonDetailUi(
    val id: Int,
    val name: String,
    val heightInMeters: Float,
    val weightInKg: Float,
    val baseExperience: Int,
    val imageUrl: String?,
    val types: List<TypeUi>,
    val stats: List<StatUi>
)

/**
 * UI model for Pokemon type with color.
 */
data class TypeUi(
    val name: String,
    val color: Color
)

/**
 * UI model for stat with color.
 */
data class StatUi(
    val name: String,
    val abbreviation: String,
    val value: Int,
    val color: Color
)