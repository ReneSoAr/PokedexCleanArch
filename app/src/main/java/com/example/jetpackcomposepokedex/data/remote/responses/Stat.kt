package com.example.jetpackcomposepokedex.data.remote.responses

/**
 * Representa un stat de Pokémon en la respuesta de la API.
 * 
 * Estructura real de la API:
 * {
 *   "base_stat": 45,
 *   "effort": 0,
 *   "stat": {
 *     "name": "hp",
 *     "url": "https://pokeapi.co/api/v2/stat/1/"
 *   }
 * }
 */
data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: StatInfo
)

/**
 * Información del stat (nombre y URL).
 */
data class StatInfo(
    val name: String,
    val url: String
)