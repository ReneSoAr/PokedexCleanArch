package com.example.jetpackcomposepokedex.data.repository

import com.example.jetpackcomposepokedex.data.remote.PokeApi
import com.example.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.example.jetpackcomposepokedex.data.remote.responses.PokemonList
import com.example.jetpackcomposepokedex.data.remote.responses.Stat
import com.example.jetpackcomposepokedex.data.remote.responses.Type
import com.example.jetpackcomposepokedex.domain.models.PokemonListItem
import com.example.jetpackcomposepokedex.domain.models.PokemonListModel
import com.example.jetpackcomposepokedex.domain.models.PokemonModel
import com.example.jetpackcomposepokedex.domain.models.PokemonStat
import com.example.jetpackcomposepokedex.domain.models.PokemonType
import com.example.jetpackcomposepokedex.domain.models.StatType
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
private fun PokemonList.toDomain(): PokemonListModel {
    return PokemonListModel(
        count = count,
        next = next,
        previous = previous as? String,
        results = results.map { it.toDomain() }
    )
}

/**
 * Extensión para convertir Result (Data) → PokemonListItem (Domain)
 */
private fun com.example.jetpackcomposepokedex.data.remote.responses.Result.toDomain(): PokemonListItem {
    // Extraer ID de la URL: "https://pokeapi.co/api/v2/pokemon/1/" → 1
    val id = url.trimEnd('/').split("/").lastOrNull()?.toIntOrNull() ?: 0

    return PokemonListItem(
        id = id,
        name = name,
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    )
}

/**
 * Extensión para convertir Pokemon (Data) → PokemonModel (Domain)
 */
private fun Pokemon.toDomain(): PokemonModel {
    val sortedTypes = types
        .map { it.toDomain() }
        .sortedBy { it.slot }
        .map { it.type }

    return PokemonModel(
        id = id,
        name = name,
        height = height,
        weight = weight,
        baseExperience = base_experience,
        imageUrl = extractOfficialArtworkUrl(sprites),
        types = sortedTypes,
        stats = stats.map { it.toDomain() }
    )
}

/**
 * Extrae la URL del artwork oficial de mejor calidad.
 */
private fun extractOfficialArtworkUrl(sprites: com.example.jetpackcomposepokedex.data.remote.responses.Sprites): String? {
    return sprites.other?.officialArtwork?.front_default
        ?: sprites.front_default
}

/**
 * Wrapper interno para mantener el slot del tipo.
 */
private data class PokemonTypeWithSlot(
    val type: PokemonType,
    val slot: Int
)

/**
 * Extensión para convertir Type (Data) → PokemonTypeWithSlot (Wrapper interno)
 */
private fun Type.toDomain(): PokemonTypeWithSlot {
    return PokemonTypeWithSlot(
        type = PokemonType.fromApiName(this.type.name),
        slot = slot
    )
}

/**
 * Extensión para convertir Stat (Data) → PokemonStat (Domain)
 */
private fun Stat.toDomain(): PokemonStat {
    return PokemonStat(
        type = StatType.fromApiName(stat.name),
        value = base_stat,
        effort = effort
    )
}