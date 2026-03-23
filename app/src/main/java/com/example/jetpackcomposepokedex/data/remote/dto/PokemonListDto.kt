package com.example.jetpackcomposepokedex.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for Pokemon list response.
 */
@Serializable
data class PokemonListDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResultDto>
)

/**
 * DTO for individual Pokemon result in list.
 */
@Serializable
data class PokemonResultDto(
    val name: String,
    val url: String
)