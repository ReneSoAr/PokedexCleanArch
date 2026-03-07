package com.plcoding.jetpackcomposepokedex.domain.repository

import com.plcoding.jetpackcomposepokedex.domain.models.PokemonListModel
import com.plcoding.jetpackcomposepokedex.domain.models.PokemonModel
import com.plcoding.jetpackcomposepokedex.utils.Resource

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonListModel>
    suspend fun getPokemonInfo(pokemonName: String): Resource<PokemonModel>
}