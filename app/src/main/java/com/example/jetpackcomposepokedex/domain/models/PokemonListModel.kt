package com.example.jetpackcomposepokedex.domain.models

data class PokemonListModel(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)
