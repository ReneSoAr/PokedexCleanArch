package com.example.jetpackcomposepokedex.presentation.screen.pokemonlist

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposepokedex.domain.repository.PokemonRepository
import com.example.jetpackcomposepokedex.domain.usecase.GetPokemonListUseCase
import com.example.jetpackcomposepokedex.presentation.mapper.toUiModel
import com.example.jetpackcomposepokedex.presentation.model.PokemonListItemUi
import com.example.jetpackcomposepokedex.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Sealed class representing UI states for Pokemon list.
 */
sealed class PokemonListUiState {
    data object Loading : PokemonListUiState()
    data class Success(
        val pokemon: List<PokemonListItemUi>,
        val isLoadingMore: Boolean = false,
        val endReached: Boolean = false
    ) : PokemonListUiState()
    data class Error(val message: String) : PokemonListUiState()
}

@OptIn(FlowPreview::class)
@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    private var currentPage = 0
    private val loadedPokemon = mutableListOf<PokemonListItemUi>()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive.asStateFlow()

    private val _searchResults = MutableStateFlow<List<PokemonListItemUi>>(emptyList())
    val searchResults: StateFlow<List<PokemonListItemUi>> = _searchResults.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    val filteredPokemon: StateFlow<List<PokemonListItemUi>> =
        _searchQuery
            .debounce(300)
            .flatMapLatest { query ->
                flow {
                    if (query.isEmpty()) {
                        _isSearchActive.value = false
                        val currentState = _uiState.value
                        if (currentState is PokemonListUiState.Success) {
                            emit(currentState.pokemon)
                        } else {
                            emit(emptyList())
                        }
                    } else {
                        _isSearchActive.value = true
                        _isSearching.value = true
                        val result = pokemonRepository.searchPokemonByName(query)
                        result.fold(
                            onSuccess = { pokemonList ->
                                val uiItems = pokemonList.mapIndexed { index, item ->
                                    item.toUiModel(generateColor(index))
                                }
                                _searchResults.value = uiItems
                                emit(uiItems)
                            },
                            onFailure = {
                                _searchResults.value = emptyList()
                                emit(emptyList())
                            }
                        )
                        _isSearching.value = false
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    init {
        loadPokemon()
    }

    fun loadPokemon() {
        viewModelScope.launch {
            _uiState.value = PokemonListUiState.Loading

            val result = getPokemonListUseCase(Constants.PAGE_SIZE, 0)

            result.fold(
                onSuccess = { pokemonList ->
                    val uiItems = pokemonList.results.mapIndexed { index, item ->
                        item.toUiModel(generateColor(index + currentPage * Constants.PAGE_SIZE))
                    }
                    loadedPokemon.clear()
                    loadedPokemon.addAll(uiItems)
                    currentPage = 1

                    _uiState.value = PokemonListUiState.Success(
                        pokemon = loadedPokemon.toList(),
                        endReached = pokemonList.results.size >= pokemonList.count
                    )
                },
                onFailure = { error ->
                    _uiState.value = PokemonListUiState.Error(
                        error.message ?: "Unknown error"
                    )
                }
            )
        }
    }

    fun loadMore() {
        if (_isSearchActive.value) return

        val currentState = _uiState.value as? PokemonListUiState.Success ?: return
        if (currentState.isLoadingMore || currentState.endReached) return

        viewModelScope.launch {
            _uiState.update {
                currentState.copy(isLoadingMore = true)
            }

            val result = getPokemonListUseCase(
                Constants.PAGE_SIZE,
                currentPage * Constants.PAGE_SIZE
            )

            result.fold(
                onSuccess = { pokemonList ->
                    val newItems = pokemonList.results.mapIndexed { index, item ->
                        item.toUiModel(
                            generateColor(index + currentPage * Constants.PAGE_SIZE)
                        )
                    }
                    loadedPokemon.addAll(newItems)
                    currentPage++

                    _uiState.value = PokemonListUiState.Success(
                        pokemon = loadedPokemon.toList(),
                        isLoadingMore = false,
                        endReached = loadedPokemon.size >= pokemonList.count
                    )
                },
                onFailure = { error ->
                    _uiState.value = currentState.copy(
                        isLoadingMore = false
                    )
                }
            )
        }
    }

    /**
     * Updates the search query and triggers global search.
     */
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    /**
     * Generates a consistent color based on Pokemon number.
     */
    private fun generateColor(number: Int): Color {
        val colors = listOf(
            Color(0xFF78C850), Color(0xFFF08030), Color(0xFF6890F0),
            Color(0xFFF8D030), Color(0xFFA890F0), Color(0xFFA040A0),
            Color(0xFFE0C068), Color(0xFF98D8D8), Color(0xFFB8A038),
            Color(0xFF705898), Color(0xFF7038F8), Color(0xFFB8B8D0),
            Color(0xFFFFA07A), Color(0xFF20B2AA), Color(0xFF87CEEB),
            Color(0xFFDDA0DD), Color(0xFF90EE90), Color(0xFFFFB6C1)
        )
        return colors[number % colors.size]
    }
}