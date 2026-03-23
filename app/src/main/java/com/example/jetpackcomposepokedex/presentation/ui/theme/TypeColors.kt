package com.example.jetpackcomposepokedex.presentation.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.jetpackcomposepokedex.domain.model.PokemonType

/**
 * Colors for Pokemon types.
 */
object TypeColors {
    val Normal = Color(0xFFA8A878)
    val Fire = Color(0xFFF08030)
    val Water = Color(0xFF6890F0)
    val Electric = Color(0xFFF8D030)
    val Grass = Color(0xFF78C850)
    val Ice = Color(0xFF98D8D8)
    val Fighting = Color(0xFFC03028)
    val Poison = Color(0xFFA040A0)
    val Ground = Color(0xFFE0C068)
    val Flying = Color(0xFFA890F0)
    val Psychic = Color(0xFFF85888)
    val Bug = Color(0xFFA8B820)
    val Rock = Color(0xFFB8A038)
    val Ghost = Color(0xFF705898)
    val Dragon = Color(0xFF7038F8)
    val Dark = Color(0xFF705848)
    val Steel = Color(0xFFB8B8D0)
    val Fairy = Color(0xFFEE99AC)
    val Unknown = Color(0xFF68A090)

    fun getColor(type: PokemonType): Color {
        return when (type) {
            PokemonType.NORMAL -> Normal
            PokemonType.FIRE -> Fire
            PokemonType.WATER -> Water
            PokemonType.ELECTRIC -> Electric
            PokemonType.GRASS -> Grass
            PokemonType.ICE -> Ice
            PokemonType.FIGHTING -> Fighting
            PokemonType.POISON -> Poison
            PokemonType.GROUND -> Ground
            PokemonType.FLYING -> Flying
            PokemonType.PSYCHIC -> Psychic
            PokemonType.BUG -> Bug
            PokemonType.ROCK -> Rock
            PokemonType.GHOST -> Ghost
            PokemonType.DRAGON -> Dragon
            PokemonType.DARK -> Dark
            PokemonType.STEEL -> Steel
            PokemonType.FAIRY -> Fairy
            PokemonType.UNKNOWN -> Unknown
        }
    }
}