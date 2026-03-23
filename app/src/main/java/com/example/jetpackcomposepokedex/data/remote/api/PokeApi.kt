package com.example.jetpackcomposepokedex.data.remote.api

import com.example.jetpackcomposepokedex.data.remote.dto.PokemonDto
import com.example.jetpackcomposepokedex.data.remote.dto.PokemonListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API interface for PokeAPI.
 */
interface PokeApi {
    
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListDto
    
    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): PokemonDto
}