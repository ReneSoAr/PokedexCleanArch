package com.example.jetpackcomposepokedex.presentation.pokemonList

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.jetpackcomposepokedex.domain.repository.PokemonRepository
import com.example.jetpackcomposepokedex.utils.Constants.PAGE_SIZE
import com.example.jetpackcomposepokedex.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la lista de Pokémon.
 * 
 * Maneja:
 * - Carga paginada de Pokémon desde la API
 * - Estado de carga y errores
 * - Cálculo del color dominante de las imágenes
 */
@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var curPage = 0

    /** Lista de Pokémon cargados */
    var pokemonList = mutableStateOf<List<PokedexListEntryUiState>>(listOf())

    /** Mensaje de error (vacío si no hay error) */
    var loadError = mutableStateOf("")

    /** Indica si está cargando datos */
    var isLoading = mutableStateOf(false)

    /** Indica si se alcanzó el final de la lista */
    var endReached = mutableStateOf(false)

    /** Texto de búsqueda actual */
    var searchQuery = mutableStateOf("")

    /**
     * Lista filtrada según el texto de búsqueda.
     * Se actualiza automáticamente cuando cambia searchQuery o pokemonList.
     */
    val filteredPokemonList = derivedStateOf {
        if (searchQuery.value.isEmpty()) {
            pokemonList.value
        } else {
            pokemonList.value.filter {
                it.pokemonName.contains(searchQuery.value, ignoreCase = true)
            }
        }
    }

    init {
        loadPokemonPaginated()
    }

    /**
     * Carga Pokémon de forma paginada.
     * 
     * Se llama automáticamente al inicializar y cuando se llega
     * al final de la lista (scroll infinito).
     */
    fun loadPokemonPaginated() {
        viewModelScope.launch {
            // Evitar cargas múltiples simultáneas
            if (isLoading.value) return@launch

            isLoading.value = true
            loadError.value = ""

            val result = repository.getPokemonList(PAGE_SIZE, curPage * PAGE_SIZE)

            when (result) {
                is Resource.Success -> {
                    result.data?.let { data ->
                        endReached.value = curPage * PAGE_SIZE >= data.count

                        val pokedexEntries = data.results.map { entry ->
                            PokedexListEntryUiState(
                                pokemonName = entry.displayName,
                                imageUrl = entry.imageUrl ?: "",
                                number = entry.id
                            )
                        }

                        curPage++
                        pokemonList.value += pokedexEntries
                    }
                    isLoading.value = false
                }

                is Resource.Error -> {
                    loadError.value = result.message ?: "Error desconocido"
                    isLoading.value = false
                }

                is Resource.Loading -> {
                    // Estado de carga ya establecido arriba
                }
            }
        }
    }

    /**
     * Actualiza el texto de búsqueda y filtra la lista.
     * 
     * @param query Texto a buscar
     */
    fun searchPokemon(query: String) {
        searchQuery.value = query
    }

    /**
     * Calcula el color dominante de una imagen.
     * 
     * Usa la biblioteca Palette de AndroidX para extraer
     * el color más representativo de la imagen.
     * 
     * @param drawable Imagen cargada
     * @param onFinish Callback con el color calculado
     */
    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        try {
            val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

            Palette.from(bmp).generate { palette ->
                palette?.dominantSwatch?.rgb?.let { colorValue ->
                    onFinish(Color(colorValue))
                } ?: onFinish(Color.White)  // Fallback si no hay color
            }
        } catch (e: Exception) {
            onFinish(Color.White)  // Fallback en caso de error
        }
    }
}