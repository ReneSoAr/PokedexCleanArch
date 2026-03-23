package com.example.jetpackcomposepokedex.domain.usecase

import com.example.jetpackcomposepokedex.domain.error.DomainError
import com.example.jetpackcomposepokedex.domain.model.Pokemon
import com.example.jetpackcomposepokedex.domain.repository.PokemonRepository
import javax.inject.Inject

/**
 * Use case for searching Pokémon by name.
 */
class SearchPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    /**
     * Executes the use case.
     * Note: PokeAPI doesn't support partial search, so this performs an exact match.
     * @param query Name or partial name of the Pokémon
     * @return Result with Pokemon or error
     */
    suspend operator fun invoke(query: String): Result<Pokemon> {
        val normalizedQuery = query.trim().lowercase()
        
        if (normalizedQuery.isEmpty()) {
            return Result.failure(DomainError.Unknown("Search query cannot be empty"))
        }
        
        return repository.getPokemonInfo(normalizedQuery)
    }
}