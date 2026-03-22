package com.example.jetpackcomposepokedex.domain.models

import androidx.compose.ui.graphics.Color

/**
 * Modelo de dominio para los tipos de Pokémon.
 */
enum class PokemonType(
    val displayName: String,
    val apiName: String,
    val color: Color
) {
    NORMAL("Normal", "normal", Color(0xFFA8A878)),
    FIRE("Fire", "fire", Color(0xFFF08030)),
    WATER("Water", "water", Color(0xFF6890F0)),
    ELECTRIC("Electric", "electric", Color(0xFFF8D030)),
    GRASS("Grass", "grass", Color(0xFF78C850)),
    ICE("Ice", "ice", Color(0xFF98D8D8)),
    FIGHTING("Fighting", "fighting", Color(0xFFC03028)),
    POISON("Poison", "poison", Color(0xFFA040A0)),
    GROUND("Ground", "ground", Color(0xFFE0C068)),
    FLYING("Flying", "flying", Color(0xFFA890F0)),
    PSYCHIC("Psychic", "psychic", Color(0xFFF85888)),
    BUG("Bug", "bug", Color(0xFFA8B820)),
    ROCK("Rock", "rock", Color(0xFFB8A038)),
    GHOST("Ghost", "ghost", Color(0xFF705898)),
    DRAGON("Dragon", "dragon", Color(0xFF7038F8)),
    DARK("Dark", "dark", Color(0xFF705848)),
    STEEL("Steel", "steel", Color(0xFFB8B8D0)),
    FAIRY("Fairy", "fairy", Color(0xFFEE99AC)),
    UNKNOWN("Unknown", "", Color.Black);

    companion object {
        fun fromApiName(name: String?): PokemonType {
            return entries.find { it.apiName == name?.lowercase() } ?: UNKNOWN
        }
    }
}
