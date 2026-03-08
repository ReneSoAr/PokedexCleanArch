package com.example.jetpackcomposepokedex.domain.repository

import com.example.jetpackcomposepokedex.domain.models.PokemonListModel
import com.example.jetpackcomposepokedex.domain.models.PokemonModel
import com.example.jetpackcomposepokedex.utils.Resource

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonListModel>
    suspend fun getPokemonInfo(pokemonName: String): Resource<PokemonModel>
}