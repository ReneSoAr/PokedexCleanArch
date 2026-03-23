package com.example.jetpackcomposepokedex.data.remote.mapper

import com.example.jetpackcomposepokedex.data.remote.dto.PokemonTypeDto
import com.example.jetpackcomposepokedex.domain.model.PokemonType

/**
 * Mapper to convert PokemonTypeDto to domain model.
 */
object PokemonTypeMapper {
    
    fun mapToDomain(dto: PokemonTypeDto): PokemonType {
        return PokemonType.fromApiName(dto.type.name)
    }
}