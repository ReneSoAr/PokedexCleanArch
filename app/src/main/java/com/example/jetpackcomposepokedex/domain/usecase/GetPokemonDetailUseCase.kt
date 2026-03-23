package com.example.jetpackcomposepokedex.domain.usecase

import com.example.jetpackcomposepokedex.domain.model.Pokemon
import com.example.jetpackcomposepokedex.domain.repository.PokemonRepository
import javax.inject.Inject

/**
 * Use case for getting detailed information about a specific Pokémon.
 */
class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    /**
     * Executes the use case.
     * @param name Name of the Pokémon
     * @return Result with Pokemon
     */
    suspend operator fun invoke(name: String): Result<Pokemon> = 
        repository.getPokemonInfo(name)
}