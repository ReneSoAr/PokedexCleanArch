package com.example.jetpackcomposepokedex.presentation.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.jetpackcomposepokedex.domain.model.StatType

/**
 * Colors for Pokemon stats.
 */
object StatColors {
    val HP = Color(0xFFFF0000)
    val Attack = Color(0xFFF08030)
    val Defense = Color(0xFFF8D030)
    val SpecialAttack = Color(0xFF6890F0)
    val SpecialDefense = Color(0xFF78C850)
    val Speed = Color(0xFFF85888)
    val Unknown = Color(0xFF808080)

    fun getColor(type: StatType): Color {
        return when (type) {
            StatType.HP -> HP
            StatType.ATTACK -> Attack
            StatType.DEFENSE -> Defense
            StatType.SPECIAL_ATTACK -> SpecialAttack
            StatType.SPECIAL_DEFENSE -> SpecialDefense
            StatType.SPEED -> Speed
            StatType.UNKNOWN -> Unknown
        }
    }
}