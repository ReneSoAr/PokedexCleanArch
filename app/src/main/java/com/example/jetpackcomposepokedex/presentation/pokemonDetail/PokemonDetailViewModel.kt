package com.example.jetpackcomposepokedex.presentation.pokemonDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposepokedex.domain.models.PokemonModel
import com.example.jetpackcomposepokedex.domain.repository.PokemonRepository
import com.example.jetpackcomposepokedex.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PokemonDetailState {
    data object Loading : PokemonDetailState()
    data class Success(val pokemon: PokemonModel) : PokemonDetailState()
    data class Error(val message: String) : PokemonDetailState()
}

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<PokemonDetailState>(PokemonDetailState.Loading)
    val state: StateFlow<PokemonDetailState> = _state.asStateFlow()

    private val pokemonName: String = checkNotNull(savedStateHandle["pokemonName"])

    init {
        loadPokemon()
    }

    private fun loadPokemon() {
        viewModelScope.launch {
            _state.value = PokemonDetailState.Loading
            
            when (val result = pokemonRepository.getPokemonInfo(pokemonName)) {
                is Resource.Success -> {
                    result.data?.let { 
                        _state.value = PokemonDetailState.Success(it)
                    } ?: run {
                        _state.value = PokemonDetailState.Error("Pokemon not found")
                    }
                }
                is Resource.Error -> {
                    _state.value = PokemonDetailState.Error(
                        result.message ?: "Unknown error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = PokemonDetailState.Loading
                }
            }
        }
    }
}