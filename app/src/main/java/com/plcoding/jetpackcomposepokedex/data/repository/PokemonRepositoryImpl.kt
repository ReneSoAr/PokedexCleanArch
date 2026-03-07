package com.plcoding.jetpackcomposepokedex.data.repository

import com.plcoding.jetpackcomposepokedex.data.remote.PokeApi
import com.plcoding.jetpackcomposepokedex.domain.models.PokemonListModel
import com.plcoding.jetpackcomposepokedex.domain.models.PokemonModel
import com.plcoding.jetpackcomposepokedex.domain.repository.PokemonRepository
import com.plcoding.jetpackcomposepokedex.utils.Resource
import javax.inject.Inject

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
        } catch (e: Exception) {
            return Resource.Error("An unknown error ocurred")
        }
        /*catch (e: IOException) {
            return Resource.Error("Network error")
        }
        catch (e: HttpException) {
            return Resource.Error("Server error")
        }
        catch (e: Exception) {
            if (e is CancellationException) throw e
            return Resource.Error("Unknown error")
        }
        */
    }

    override suspend fun getPokemonInfo(pokemonName: String): Resource<PokemonModel> {
        return try {
            val response = pokeApi.getPokemonInfo(pokemonName).toDomain()
            Resource.Success(response)
        } catch (e: Exception) {
            return Resource.Error("An unknown error ocurred")
        }
    }

}


fun PokemonList.toDomain(): PokemonListModel {
    return PokemonListModel(
        count = count,
        next = next,
        previous = previous,
        results = results
    )
}

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
