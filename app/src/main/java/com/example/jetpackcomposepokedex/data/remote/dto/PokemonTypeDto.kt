package com.example.jetpackcomposepokedex.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * DTO for Pokemon type.
 */
@Serializable
data class PokemonTypeDto(
    val slot: Int,
    val type: TypeInfoDto
)

@Serializable
data class TypeInfoDto(
    val name: String,
    val url: String
)