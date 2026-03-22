package com.example.jetpackcomposepokedex.presentation.pokemonList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.foundation.LocalIndication
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetpackcomposepokedex.R
import com.example.jetpackcomposepokedex.presentation.navigation.Screen
import com.example.jetpackcomposepokedex.presentation.ui.theme.RobotoCondensed

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    // Lista filtrada como State observable - se actualiza automáticamente
    val pokemonList by viewModel.filteredPokemonList
    val isLoading by viewModel.isLoading
    val loadError by viewModel.loadError
    val endReached by viewModel.endReached

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "Pokemon logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            SearchBar(
                hint = "Buscar Pokémon...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onSearch = { query ->
                    viewModel.searchPokemon(query)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Estado de error
            if (loadError.isNotEmpty()) {
                ErrorState(
                    message = loadError,
                    onRetry = { viewModel.loadPokemonPaginated() }
                )
            }

            // Loading inicial
            if (isLoading && pokemonList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            // Lista vacía (sin datos y sin error)
            if (!isLoading && pokemonList.isEmpty() && loadError.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay Pokémon disponibles",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // Lista de Pokémon
            if (pokemonList.isNotEmpty()) {
                PokemonList(
                    pokemonList = pokemonList,
                    isLoading = isLoading,
                    endReached = endReached,
                    onLoadMore = { viewModel.loadPokemonPaginated() },
                    onPokemonClick = { entry, dominantColor ->
                        navController.navigate(
                            Screen.PokemonDetail.createRoute(
                                dominantColor.toArgb(),
                                entry.pokemonName
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun PokemonList(
    pokemonList: List<PokedexListEntryUiState>,
    isLoading: Boolean,
    endReached: Boolean,
    onLoadMore: () -> Unit,
    onPokemonClick: (PokedexListEntryUiState, Color) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        val itemsCount = if (pokemonList.size % 2 == 0) {
            pokemonList.size / 2
        } else {
            pokemonList.size / 2 + 1
        }

        items(itemsCount) { index ->
            // Cargar más cuando estamos cerca del final
            if (index >= itemsCount - 1 && !endReached && !isLoading) {
                onLoadMore()
            }

            PokedexRow(
                rowIndex = index,
                entries = pokemonList,
                onPokemonClick = onPokemonClick
            )
        }

        // Indicador de carga al final
        if (isLoading && pokemonList.isNotEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun PokedexRow(
    rowIndex: Int,
    entries: List<PokedexListEntryUiState>,
    onPokemonClick: (PokedexListEntryUiState, Color) -> Unit
) {
    Column {
        Row {
            // Primer Pokémon
            if (rowIndex * 2 < entries.size) {
                PokedexEntry(
                    entry = entries[rowIndex * 2],
                    onClick = onPokemonClick,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Segundo Pokémon (si existe)
            if (rowIndex * 2 + 1 < entries.size) {
                PokedexEntry(
                    entry = entries[rowIndex * 2 + 1],
                    onClick = onPokemonClick,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun PokedexEntry(
    entry: PokedexListEntryUiState,
    onClick: (PokedexListEntryUiState, Color) -> Unit,
    modifier: Modifier = Modifier
) {
    // Generar un color basado en el número del Pokémon (consistente para cada Pokémon)
    val dominantColor = remember(entry.number) {
        generateColorFromNumber(entry.number)
    }

    val interactionSource = remember { MutableInteractionSource() }
    var isLoadingImage by remember { mutableStateOf(true) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current
            ) {
                onClick(entry, dominantColor)
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                // Loading mientras carga la imagen
                if (isLoadingImage) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.scale(0.5f)
                    )
                }

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(entry.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = entry.pokemonName,
                    modifier = Modifier.size(100.dp),
                    onSuccess = {
                        isLoadingImage = false
                    },
                    onError = {
                        isLoadingImage = false
                    }
                )
            }

            Text(
                text = entry.pokemonName,
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Genera un color consistente basado en el número del Pokémon.
 * Cada número siempre generará el mismo color.
 */
private fun generateColorFromNumber(number: Int): Color {
    val colors = listOf(
        Color(0xFF78C850), // Verde (planta)
        Color(0xFFF08030), // Naranja (fuego)
        Color(0xFF6890F0), // Azul (agua)
        Color(0xFFF8D030), // Amarillo (eléctrico)
        Color(0xFFA890F0), // Púrpura (psíquico)
        Color(0xFFA040A0), // Morado (veneno)
        Color(0xFFE0C068), // Marrón (tierra)
        Color(0xFF98D8D8), // Cyan (hielo)
        Color(0xFFB8A038), // Amarillo oscuro (roca)
        Color(0xFF705898), // Violeta (fantasma)
        Color(0xFF7038F8), // Azul oscuro (dragón)
        Color(0xFFB8B8D0), // Gris (acero)
        Color(0xFFFFA07A), // Salmón
        Color(0xFF20B2AA), // Verde mar
        Color(0xFF87CEEB), // Azul cielo
        Color(0xFFDDA0DD), // Ciruela
        Color(0xFF90EE90), // Verde claro
        Color(0xFFFFB6C1)  // Rosa
    )
    return colors[number % colors.size]
}

@Composable
fun ErrorState(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Reintentar")
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "Buscar...",
    onSearch: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = {
            text = it
            onSearch(it)
        },
        placeholder = { Text(hint) },
        singleLine = true,
        modifier = modifier,
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}