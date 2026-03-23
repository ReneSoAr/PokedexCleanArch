package com.example.jetpackcomposepokedex.data.remote.mapper

import com.example.jetpackcomposepokedex.data.remote.dto.PokemonListDto
import com.example.jetpackcomposepokedex.data.remote.dto.PokemonResultDto
import com.example.jetpackcomposepokedex.domain.model.PokemonList
import com.example.jetpackcomposepokedex.domain.model.PokemonListItem

/**
 * Mapper to convert PokemonListDto to domain model.
 */
object PokemonListMapper {
    
    fun mapToDomain(dto: PokemonListDto): PokemonList {
        return PokemonList(
            count = dto.count,
            next = dto.next,
            previous = dto.previous,
            results = dto.results.map { mapResultToDomain(it) }
        )
    }
    
    private fun mapResultToDomain(dto: PokemonResultDto): PokemonListItem {
        // Extract ID from URL: "https://pokeapi.co/api/v2/pokemon/1/" → 1
        val id = dto.url.trimEnd('/').split("/").lastOrNull()?.toIntOrNull() ?: 0
        
        return PokemonListItem(
            id = id,
            name = dto.name,
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
        )
    }
}