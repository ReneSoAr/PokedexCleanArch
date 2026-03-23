package com.example.jetpackcomposepokedex.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for Pokemon stat.
 */
@Serializable
data class PokemonStatDto(
    @SerialName("base_stat")
    val baseStat: Int,
    val effort: Int,
    val stat: StatInfoDto
)

@Serializable
data class StatInfoDto(
    val name: String,
    val url: String
)