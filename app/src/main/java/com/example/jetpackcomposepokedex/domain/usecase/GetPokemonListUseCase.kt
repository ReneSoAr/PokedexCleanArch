package com.example.jetpackcomposepokedex.domain.usecase

import com.example.jetpackcomposepokedex.domain.model.PokemonList
import com.example.jetpackcomposepokedex.domain.repository.PokemonRepository
import javax.inject.Inject

/**
 * Use case for getting a paginated list of Pokémon.
 */
class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    /**
     * Executes the use case.
     * @param limit Number of items per page
     * @param offset Offset from the start
     * @return Result with PokemonList
     */
    suspend operator fun invoke(limit: Int, offset: Int) = 
        repository.getPokemonList(limit, offset)
}