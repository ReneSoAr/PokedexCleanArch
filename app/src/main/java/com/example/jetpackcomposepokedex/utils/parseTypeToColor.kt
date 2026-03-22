package com.example.jetpackcomposepokedex.utils

import androidx.compose.ui.graphics.Color
import com.example.jetpackcomposepokedex.data.remote.responses.Stat
import com.example.jetpackcomposepokedex.presentation.ui.theme.AtkColor
import com.example.jetpackcomposepokedex.presentation.ui.theme.DefColor
import com.example.jetpackcomposepokedex.presentation.ui.theme.HPColor
import com.example.jetpackcomposepokedex.presentation.ui.theme.SpAtkColor
import com.example.jetpackcomposepokedex.presentation.ui.theme.SpDefColor
import com.example.jetpackcomposepokedex.presentation.ui.theme.SpdColor
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeBug
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeDark
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeDragon
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeElectric
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeFairy
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeFighting
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeFire
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeFlying
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeGhost
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeGrass
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeGround
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeIce
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeNormal
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypePoison
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypePsychic
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeRock
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeSteel
import com.example.jetpackcomposepokedex.presentation.ui.theme.TypeWater
import com.example.jetpackcomposepokedex.data.remote.responses.Type

fun parseTypeToColor(type: Type): Color {
    return when (type.type.name?.lowercase() ?: "") {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}

fun parseStatToColor(stat: Stat): Color {
    return when(stat.stat.name?.lowercase() ?: "") {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: Stat): String {
    return when(stat.stat.name?.lowercase() ?: "") {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> "??"
    }
}