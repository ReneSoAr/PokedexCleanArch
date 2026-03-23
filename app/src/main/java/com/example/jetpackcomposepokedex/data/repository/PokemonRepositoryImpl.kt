package com.example.jetpackcomposepokedex.data.repository

import com.example.jetpackcomposepokedex.data.remote.api.PokeApi
import com.example.jetpackcomposepokedex.data.remote.mapper.PokemonListMapper
import com.example.jetpackcomposepokedex.data.remote.mapper.PokemonMapper
import com.example.jetpackcomposepokedex.domain.error.DomainError
import com.example.jetpackcomposepokedex.domain.model.Pokemon
import com.example.jetpackcomposepokedex.domain.model.PokemonList
import com.example.jetpackcomposepokedex.domain.model.PokemonListItem
import com.example.jetpackcomposepokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of PokemonRepository.
 * Handles API calls and error mapping.
 */
@Singleton
class PokemonRepositoryImpl @Inject constructor(
    private val pokeApi: PokeApi
) : PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): Result<PokemonList> {
        return try {
            val dto = pokeApi.getPokemonList(limit, offset)
            val domain = PokemonListMapper.mapToDomain(dto)
            Result.success(domain)
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            Result.failure(DomainError.NetworkError("Network error. Please check your internet connection."))
        } catch (e: HttpException) {
            Result.failure(DomainError.NetworkError("Server error: ${e.code()}"))
        } catch (e: Exception) {
            Result.failure(DomainError.Unknown("Unknown error: ${e.message}"))
        }
    }

    override suspend fun getPokemonInfo(name: String): Result<Pokemon> {
        return try {
            val dto = pokeApi.getPokemonInfo(name)
            val domain = PokemonMapper.mapToDomain(dto)
            Result.success(domain)
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            Result.failure(DomainError.NetworkError("Network error. Please check your internet connection."))
        } catch (e: HttpException) {
            when (e.code()) {
                404 -> Result.failure(DomainError.NotFound(name))
                else -> Result.failure(DomainError.NetworkError("Server error: ${e.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(DomainError.Unknown("Unknown error: ${e.message}"))
        }
    }

    override suspend fun searchPokemonByName(query: String): Result<List<PokemonListItem>> {
        return try {
            val allPokemon = pokeApi.getPokemonList(limit = 10000, offset = 0)
            val normalizedQuery = query.trim().lowercase()
            
            val filtered = allPokemon.results
                .filter { it.name.contains(normalizedQuery, ignoreCase = true) }
                .map { result ->
                    val id = extractIdFromUrl(result.url)
                    PokemonListItem(
                        id = id,
                        name = result.name,
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
                    )
                }
            
            Result.success(filtered)
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            Result.failure(DomainError.NetworkError("Network error. Please check your internet connection."))
        } catch (e: HttpException) {
            Result.failure(DomainError.NetworkError("Server error: ${e.code()}"))
        } catch (e: Exception) {
            Result.failure(DomainError.Unknown("Unknown error: ${e.message}"))
        }
    }

    private fun extractIdFromUrl(url: String): Int {
        return url.trimEnd('/').split("/").last().toIntOrNull() ?: 0
    }
}