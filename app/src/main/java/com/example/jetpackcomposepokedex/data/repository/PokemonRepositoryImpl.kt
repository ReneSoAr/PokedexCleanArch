package com.example.jetpackcomposepokedex.data.repository

import com.example.jetpackcomposepokedex.data.remote.PokeApi
import com.example.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.example.jetpackcomposepokedex.data.remote.responses.PokemonList
import com.example.jetpackcomposepokedex.data.remote.responses.Result
import com.example.jetpackcomposepokedex.domain.models.PokemonListModel
import com.example.jetpackcomposepokedex.domain.models.PokemonModel
import com.example.jetpackcomposepokedex.domain.models.PokemonResultModel
import com.example.jetpackcomposepokedex.domain.repository.PokemonRepository
import com.example.jetpackcomposepokedex.utils.Resource
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Implementación del repositorio de Pokémon.
 * 
 * Conecta la API de PokeAPI con los modelos de dominio.
 * Maneja errores de red y convierte respuestas de data a domain.
 */
class PokemonRepositoryImpl @Inject constructor(
    private val pokeApi: PokeApi
) : PokemonRepository {

    override suspend fun getPokemonList(
        limit: Int,
        offset: Int
    ): Resource<PokemonListModel> {
        return try {
            val response = pokeApi.getPokemonList(limit, offset).toDomain()
            Resource.Success(response)
        } catch (e: IOException) {
            return Resource.Error("Error de red. Verifica tu conexión a internet.")
        } catch (e: HttpException) {
            return Resource.Error("Error del servidor: ${e.code()}")
        } catch (e: CancellationException) {
            throw e  // No capturar CancellationException
        } catch (e: Exception) {
            return Resource.Error("Error desconocido: ${e.message}")
        }
    }

    override suspend fun getPokemonInfo(pokemonName: String): Resource<PokemonModel> {
        return try {
            val response = pokeApi.getPokemonInfo(pokemonName).toDomain()
            Resource.Success(response)
        } catch (e: IOException) {
            return Resource.Error("Error de red. Verifica tu conexión a internet.")
        } catch (e: HttpException) {
            return Resource.Error("Error del servidor: ${e.code()}")
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return Resource.Error("Error desconocido: ${e.message}")
        }
    }
}

/**
 * Extensión para convertir PokemonList (Data) → PokemonListModel (Domain)
 */
fun PokemonList.toDomain(): PokemonListModel {
    return PokemonListModel(
        count = count,
        next = next,
        previous = previous as? String,  // Cast seguro de Any? a String?
        results = results.map { it.toDomain() }  // Mapear cada resultado
    )
}

/**
 * Extensión para convertir Result (Data) → PokemonResultModel (Domain)
 */
fun Result.toDomain(): PokemonResultModel {
    return PokemonResultModel(
        name = name,
        url = url
    )
}

/**
 * Extensión para convertir Pokemon (Data) → PokemonModel (Domain)
 */
fun Pokemon.toDomain(): PokemonModel {
    return PokemonModel(
        abilities = abilities,
        base_experience = base_experience,
        cries = cries,
        forms = forms,
        game_indices = game_indices,
        height = height,
        held_items = held_items,
        id = id,
        is_default = is_default,
        location_area_encounters = location_area_encounters,
        moves = moves,
        name = name,
        order = order,
        past_abilities = past_abilities,
        past_stats = past_stats,
        past_types = past_types,
        species = species,
        sprites = sprites,
        stats = stats,
        types = types,
        weight = weight
    )
}
