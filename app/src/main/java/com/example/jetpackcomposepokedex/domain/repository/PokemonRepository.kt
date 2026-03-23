package com.example.jetpackcomposepokedex.domain.repository

import com.example.jetpackcomposepokedex.domain.error.DomainError
import com.example.jetpackcomposepokedex.domain.model.Pokemon
import com.example.jetpackcomposepokedex.domain.model.PokemonList

/**
 * Repository interface for Pokémon data.
 * Returns Result type with domain models or domain errors.
 */
interface PokemonRepository {
    /**
     * Gets a paginated list of Pokémon.
     * @param limit Number of items per page
     * @param offset Offset from the start
     * @return Result with PokemonList or DomainError
     */
    suspend fun getPokemonList(limit: Int, offset: Int): Result<PokemonList>

    /**
     * Gets detailed information about a specific Pokémon.
     * @param name Name of the Pokémon
     * @return Result with Pokemon or DomainError
     */
    suspend fun getPokemonInfo(name: String): Result<Pokemon>
}