package com.example.jetpackcomposepokedex.presentation.mapper

import androidx.compose.ui.graphics.Color
import com.example.jetpackcomposepokedex.domain.model.Pokemon
import com.example.jetpackcomposepokedex.domain.model.PokemonListItem
import com.example.jetpackcomposepokedex.domain.model.PokemonStat
import com.example.jetpackcomposepokedex.domain.model.PokemonType
import com.example.jetpackcomposepokedex.domain.model.StatType
import com.example.jetpackcomposepokedex.presentation.model.PokemonDetailUi
import com.example.jetpackcomposepokedex.presentation.model.PokemonListItemUi
import com.example.jetpackcomposepokedex.presentation.model.StatUi
import com.example.jetpackcomposepokedex.presentation.model.TypeUi
import com.example.jetpackcomposepokedex.presentation.ui.theme.StatColors
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeColors

/**
 * Maps domain PokemonListItem to UI model.
 */
fun PokemonListItem.toUiModel(dominantColor: Color): PokemonListItemUi {
    return PokemonListItemUi(
        id = id,
        name = displayName,
        imageUrl = imageUrl ?: "",
        dominantColor = dominantColor
    )
}

/**
 * Maps domain Pokemon to UI model.
 */
fun Pokemon.toUiModel(): PokemonDetailUi {
    return PokemonDetailUi(
        id = id,
        name = displayName,
        heightInMeters = heightInMeters,
        weightInKg = weightInKg,
        baseExperience = baseExperience,
        imageUrl = imageUrl,
        types = types.map { it.toUiModel() },
        stats = stats.map { it.toUiModel() }
    )
}

/**
 * Maps domain PokemonType to UI model.
 */
fun PokemonType.toUiModel(): TypeUi {
    return TypeUi(
        name = displayName,
        color = TypeColors.getColor(this)
    )
}

/**
 * Maps domain PokemonStat to UI model.
 */
fun PokemonStat.toUiModel(): StatUi {
    return StatUi(
        name = type.displayName,
        abbreviation = type.abbreviation,
        value = value,
        color = StatColors.getColor(type)
    )
}