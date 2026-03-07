package com.plcoding.jetpackcomposepokedex.domain.models

import com.plcoding.jetpackcomposepokedex.data.remote.responses.Ability
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Cries
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Form
import com.plcoding.jetpackcomposepokedex.data.remote.responses.GameIndice
import com.plcoding.jetpackcomposepokedex.data.remote.responses.HeldItem
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Move
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PastAbility
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PastStat
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Species
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Sprites
import com.plcoding.jetpackcomposepokedex.data.remote.responses.StatXX
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Type

data class PokemonModel(
    val abilities: List<Ability>,
    val base_experience: Int,
    val cries: Cries,
    val forms: List<Form>,
    val game_indices: List<GameIndice>,
    val height: Int,
    val held_items: List<HeldItem>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Int,
    val past_abilities: List<PastAbility>,
    val past_stats: List<PastStat>,
    val past_types: List<Any?>,
    val species: Species,
    val sprites: Sprites,
    val stats: List<StatXX>,
    val types: List<Type>,
    val weight: Int
)
