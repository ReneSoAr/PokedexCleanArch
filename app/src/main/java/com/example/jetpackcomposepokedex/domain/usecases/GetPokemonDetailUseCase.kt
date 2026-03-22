package com.example.jetpackcomposepokedex.domain.usecases

import com.example.jetpackcomposepokedex.domain.models.PokemonModel
import com.example.jetpackcomposepokedex.domain.repository.PokemonRepository
import com.example.jetpackcomposepokedex.utils.Resource
import javax.inject.Inject

/**
 * Caso de uso para obtener los detalles de un Pokémon específico.
 *
 * Este caso de uso encapsula la lógica de negocio para recuperar
 * la información detallada de un Pokémon por su nombre.
 */
class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    /**
     * Ejecuta el caso de uso para obtener los detalles de un Pokémon.
     *
     * @param pokemonName Nombre del Pokémon a buscar
     * @return Resource con los detalles del Pokémon o un error
     */
    suspend operator fun invoke(pokemonName: String): Resource<PokemonModel> {
        return repository.getPokemonInfo(pokemonName)
    }
}
