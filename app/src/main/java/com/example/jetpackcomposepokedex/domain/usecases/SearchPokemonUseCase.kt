package com.example.jetpackcomposepokedex.domain.usecases

import com.example.jetpackcomposepokedex.domain.models.PokemonModel
import com.example.jetpackcomposepokedex.domain.repository.PokemonRepository
import com.example.jetpackcomposepokedex.utils.Resource
import javax.inject.Inject

/**
 * Caso de uso para buscar Pokémon por nombre.
 *
 * Este caso de uso encapsula la lógica de búsqueda de Pokémon,
 * permitiendo buscar coincidencias parciales o exactas en el nombre.
 */
class SearchPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    /**
     * Ejecuta el caso de uso para buscar un Pokémon.
     *
     * Nota: La API de PokeAPI no soporta búsqueda parcial directamente,
     * por lo que este caso de uso intenta una búsqueda exacta.
     * Para búsqueda parcial, considerar implementar caché local.
     *
     * @param query Nombre o parte del nombre del Pokémon a buscar
     * @return Resource con el Pokémon encontrado o un error
     */
    suspend operator fun invoke(query: String): Resource<PokemonModel> {
        // Normalizar el query: minúsculas y sin espacios extras
        val normalizedQuery = query.trim().lowercase()
        
        if (normalizedQuery.isEmpty()) {
            return Resource.Error("El término de búsqueda no puede estar vacío")
        }
        
        return repository.getPokemonInfo(normalizedQuery)
    }
}
