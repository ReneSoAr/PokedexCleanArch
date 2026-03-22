package com.example.jetpackcomposepokedex.domain.usecases

import com.example.jetpackcomposepokedex.domain.models.PokemonListModel
import com.example.jetpackcomposepokedex.domain.repository.PokemonRepository
import com.example.jetpackcomposepokedex.utils.Resource
import javax.inject.Inject

/**
 * Caso de uso para obtener la lista paginada de Pokémon.
 *
 * Este caso de uso encapsula la lógica de negocio para recuperar
 * Pokémon de forma paginada desde el repositorio.
 */
class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    /**
     * Ejecuta el caso de uso para obtener la lista de Pokémon.
     *
     * @param limit Cantidad de Pokémon a obtener por página
     * @param offset Desplazamiento desde el inicio de la lista
     * @return Resource con la lista de Pokémon o un error
     */
    suspend operator fun invoke(limit: Int, offset: Int): Resource<PokemonListModel> {
        return repository.getPokemonList(limit, offset)
    }
}
