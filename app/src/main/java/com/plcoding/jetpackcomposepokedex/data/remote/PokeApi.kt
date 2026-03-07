package com.plcoding.jetpackcomposepokedex.data.remote

import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.plcoding.jetpackcomposepokedex.data.remote.responses.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    /**
     * Para obtener la información de un Pokémon, la API requiere que el nombre
     * forme parte de la ruta del endpoint: "pokemon/{name}".
     *
     * Como existen muchos Pokémon, esa parte de la URL se define como un
     * placeholder ({name}), que representa un path parameter.
     *
     * La anotación @Path se usa para enlazar ese path parameter con el
     * parámetro del método (name: String), permitiendo que Retrofit reemplace
     * {name} por el valor real del Pokémon al construir la URL.
     */
    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): Pokemon
}