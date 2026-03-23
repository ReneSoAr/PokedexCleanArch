package com.example.jetpackcomposepokedex.data.remote.mapper

import com.example.jetpackcomposepokedex.data.remote.dto.PokemonDto
import com.example.jetpackcomposepokedex.domain.model.Pokemon

/**
 * Mapper to convert PokemonDto to domain model.
 */
object PokemonMapper {
    
    fun mapToDomain(dto: PokemonDto): Pokemon {
        return Pokemon(
            id = dto.id,
            name = dto.name,
            height = dto.height,
            weight = dto.weight,
            baseExperience = dto.baseExperience,
            imageUrl = dto.sprites.other?.officialArtwork?.frontDefault
                ?: dto.sprites.other?.let { "" },
            types = dto.types
                .sortedBy { it.slot }
                .map { PokemonTypeMapper.mapToDomain(it) },
            stats = dto.stats.map { PokemonStatMapper.mapToDomain(it) }
        )
    }
}