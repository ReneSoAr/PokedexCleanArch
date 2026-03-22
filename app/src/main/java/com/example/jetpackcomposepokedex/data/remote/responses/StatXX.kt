package com.example.jetpackcomposepokedex.data.remote.responses

/**
 * Representa un stat de Pokémon en respuestas de API para stats pasados.
 * 
 * Estructura de la API:
 * {
 *   "base_stat": 45,
 *   "effort": 0,
 *   "stat": {
 *     "name": "hp",
 *     "url": "https://pokeapi.co/api/v2/stat/1/"
 *   }
 * }
 */
data class StatXX(
    val base_stat: Int,
    val effort: Int,
    val stat: StatInfo
)