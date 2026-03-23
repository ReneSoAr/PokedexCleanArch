package com.example.jetpackcomposepokedex.domain.model

/**
 * Domain model representing a Pokémon stat.
 * Pure data class - no UI dependencies.
 */
data class PokemonStat(
    val type: StatType,
    val value: Int,
    val effort: Int
)

/**
 * Enum representing stat types.
 * Contains only business logic data, no UI colors.
 */
enum class StatType(
    val displayName: String,
    val apiName: String,
    val abbreviation: String
) {
    HP("HP", "hp", "HP"),
    ATTACK("Attack", "attack", "Atk"),
    DEFENSE("Defense", "defense", "Def"),
    SPECIAL_ATTACK("Sp. Atk", "special-attack", "SpAtk"),
    SPECIAL_DEFENSE("Sp. Def", "special-defense", "SpDef"),
    SPEED("Speed", "speed", "Spd"),
    UNKNOWN("Unknown", "", "??");

    companion object {
        fun fromApiName(name: String?): StatType {
            return entries.find { it.apiName == name?.lowercase() } ?: UNKNOWN
        }
    }
}