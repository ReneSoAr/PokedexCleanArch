package com.example.jetpackcomposepokedex.presentation.screen.pokemondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposepokedex.domain.usecase.GetPokemonDetailUseCase
import com.example.jetpackcomposepokedex.presentation.mapper.toUiModel
import com.example.jetpackcomposepokedex.presentation.model.PokemonDetailUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Sealed class representing UI states for Pokemon detail.
 */
sealed class PokemonDetailUiState {
    data object Loading : PokemonDetailUiState()
    data class Success(val pokemon: PokemonDetailUi) : PokemonDetailUiState()
    data class Error(val message: String) : PokemonDetailUiState()
}

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Loading)
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    private val pokemonName: String = checkNotNull(savedStateHandle["pokemonName"])

    init {
        loadPokemon()
    }

    private fun loadPokemon() {
        viewModelScope.launch {
            _uiState.value = PokemonDetailUiState.Loading
            
            val result = getPokemonDetailUseCase(pokemonName)
            
            result.fold(
                onSuccess = { pokemon ->
                    _uiState.value = PokemonDetailUiState.Success(
                        pokemon = pokemon.toUiModel()
                    )
                },
                onFailure = { error ->
                    _uiState.value = PokemonDetailUiState.Error(
                        error.message ?: "Unknown error"
                    )
                }
            )
        }
    }
}