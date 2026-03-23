package com.example.jetpackcomposepokedex.domain.model

/**
 * Enum representing Pokémon types.
 * Contains only business logic data, no UI colors.
 */
enum class PokemonType(
    val displayName: String,
    val apiName: String
) {
    NORMAL("Normal", "normal"),
    FIRE("Fire", "fire"),
    WATER("Water", "water"),
    ELECTRIC("Electric", "electric"),
    GRASS("Grass", "grass"),
    ICE("Ice", "ice"),
    FIGHTING("Fighting", "fighting"),
    POISON("Poison", "poison"),
    GROUND("Ground", "ground"),
    FLYING("Flying", "flying"),
    PSYCHIC("Psychic", "psychic"),
    BUG("Bug", "bug"),
    ROCK("Rock", "rock"),
    GHOST("Ghost", "ghost"),
    DRAGON("Dragon", "dragon"),
    DARK("Dark", "dark"),
    STEEL("Steel", "steel"),
    FAIRY("Fairy", "fairy"),
    UNKNOWN("Unknown", "");

    companion object {
        fun fromApiName(name: String?): PokemonType {
            return entries.find { it.apiName == name?.lowercase() } ?: UNKNOWN
        }
    }
}