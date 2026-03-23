package com.example.jetpackcomposepokedex.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for detailed Pokemon response.
 */
@Serializable
data class PokemonDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    @SerialName("base_experience")
    val baseExperience: Int,
    val sprites: SpritesDto,
    val stats: List<PokemonStatDto>,
    val types: List<PokemonTypeDto>
)

@Serializable
data class SpritesDto(
    val other: OtherSpritesDto? = null
)

@Serializable
data class OtherSpritesDto(
    @SerialName("official-artwork")
    val officialArtwork: OfficialArtworkDto? = null
)

@Serializable
data class OfficialArtworkDto(
    @SerialName("front_default")
    val frontDefault: String? = null
)