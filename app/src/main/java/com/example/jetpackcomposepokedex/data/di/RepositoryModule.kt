package com.example.jetpackcomposepokedex.data.di

import com.example.jetpackcomposepokedex.data.repository.PokemonRepositoryImpl
import com.example.jetpackcomposepokedex.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPokemonRepository(
        impl: PokemonRepositoryImpl
    ): PokemonRepository

}