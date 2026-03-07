package com.plcoding.jetpackcomposepokedex.domain.models

import com.plcoding.jetpackcomposepokedex.data.remote.responses.Result

data class PokemonListModel(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)
