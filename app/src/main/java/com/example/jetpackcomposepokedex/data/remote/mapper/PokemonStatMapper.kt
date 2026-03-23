package com.example.jetpackcomposepokedex.data.remote.mapper

import com.example.jetpackcomposepokedex.data.remote.dto.PokemonStatDto
import com.example.jetpackcomposepokedex.domain.model.PokemonStat
import com.example.jetpackcomposepokedex.domain.model.StatType

/**
 * Mapper to convert PokemonStatDto to domain model.
 */
object PokemonStatMapper {
    
    fun mapToDomain(dto: PokemonStatDto): PokemonStat {
        return PokemonStat(
            type = StatType.fromApiName(dto.stat.name),
            value = dto.baseStat,
            effort = dto.effort
        )
    }
}