package com.example.jetpackcomposepokedex.domain.models

import androidx.compose.ui.graphics.Color
import com.example.jetpackcomposepokedex.presentation.ui.theme.AtkColor
import com.example.jetpackcomposepokedex.presentation.ui.theme.DefColor
import com.example.jetpackcomposepokedex.presentation.ui.theme.HPColor
import com.example.jetpackcomposepokedex.presentation.ui.theme.SpAtkColor
import com.example.jetpackcomposepokedex.presentation.ui.theme.SpDefColor
import com.example.jetpackcomposepokedex.presentation.ui.theme.SpdColor

/**
 * Modelo de dominio para las estadísticas (stats) de un Pokémon.
 * Este es un modelo puro sin dependencias de la capa de datos.
 */
data class PokemonStat(
    val type: StatType,
    val value: Int,
    val effort: Int
) {
    val abbreviation: String get() = type.abbreviation
    val displayName: String get() = type.displayName
    val color: Color get() = type.color
}

/**
 * Enum que representa los tipos de estadísticas de Pokémon.
 * Incluye colores para UI y abreviaciones para mostrar.
 */
enum class StatType(
    val displayName: String,
    val apiName: String,
    val abbreviation: String,
    val color: Color
) {
    HP(
        displayName = "HP",
        apiName = "hp",
        abbreviation = "HP",
        color = HPColor
    ),
    ATTACK(
        displayName = "Attack",
        apiName = "attack",
        abbreviation = "Atk",
        color = AtkColor
    ),
    DEFENSE(
        displayName = "Defense",
        apiName = "defense",
        abbreviation = "Def",
        color = DefColor
    ),
    SPECIAL_ATTACK(
        displayName = "Sp. Atk",
        apiName = "special-attack",
        abbreviation = "SpAtk",
        color = SpAtkColor
    ),
    SPECIAL_DEFENSE(
        displayName = "Sp. Def",
        apiName = "special-defense",
        abbreviation = "SpDef",
        color = SpDefColor
    ),
    SPEED(
        displayName = "Speed",
        apiName = "speed",
        abbreviation = "Spd",
        color = SpdColor
    ),
    UNKNOWN(
        displayName = "Unknown",
        apiName = "",
        abbreviation = "??",
        color = Color.Gray
    );

    companion object {
        fun fromApiName(name: String?): StatType {
            return entries.find { it.apiName == name?.lowercase() } ?: UNKNOWN
        }
    }
}
