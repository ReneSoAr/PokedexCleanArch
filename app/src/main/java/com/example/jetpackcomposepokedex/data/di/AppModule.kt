package com.example.jetpackcomposepokedex.data.di

import com.example.jetpackcomposepokedex.data.remote.PokeApi
import com.example.jetpackcomposepokedex.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Módulo de Hilt para proveer dependencias de red (Retrofit).
 * 
 * Este módulo solo provee:
 * - PokeApi: Interface de Retrofit para llamadas a la API
 * 
 * El repositorio se provee en RepositoryModule usando @Binds (Clean Architecture).
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provee la API de Retrofit para PokeAPI.
     */
    @Singleton
    @Provides
    fun providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }
}
