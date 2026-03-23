package com.example.jetpackcomposepokedex.presentation.screen.pokemonlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.LocalIndication
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetpackcomposepokedex.R
import com.example.jetpackcomposepokedex.presentation.model.PokemonListItemUi
import com.example.jetpackcomposepokedex.presentation.navigation.Screen
import com.example.jetpackcomposepokedex.presentation.ui.theme.RobotoCondensed

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                    contentDescription = "Pokemon logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                hint = "Buscar Pokémon...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                onSearch = { query ->
                    searchQuery = query
                }
            )

            when (val state = uiState) {
                is PokemonListUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is PokemonListUiState.Error -> {
                    ErrorState(
                        message = state.message,
                        onRetry = { viewModel.loadPokemon() }
                    )
                }
                is PokemonListUiState.Success -> {
                    val filteredList = if (searchQuery.isEmpty()) {
                        state.pokemon
                    } else {
                        state.pokemon.filter {
                            it.name.contains(searchQuery, ignoreCase = true)
                        }
                    }

                    if (filteredList.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay Pokémon disponibles",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    } else {
                        PokemonList(
                            pokemonList = filteredList,
                            isLoadingMore = state.isLoadingMore,
                            endReached = state.endReached,
                            onLoadMore = { viewModel.loadMore() },
                            onPokemonClick = { entry ->
                                navController.navigate(
                                    Screen.PokemonDetail.createRoute(
                                        entry.dominantColor.toArgb(),
                                        entry.name
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PokemonList(
    pokemonList: List<PokemonListItemUi>,
    isLoadingMore: Boolean,
    endReached: Boolean,
    onLoadMore: () -> Unit,
    onPokemonClick: (PokemonListItemUi) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        val itemsCount = (pokemonList.size + 1) / 2

        items(itemsCount) { index ->
            if (index >= itemsCount - 1 && !endReached && !isLoadingMore) {
                onLoadMore()
            }

            PokedexRow(
                rowIndex = index,
                entries = pokemonList,
                onPokemonClick = onPokemonClick
            )
        }

        if (isLoadingMore) {
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
private fun PokedexRow(
    rowIndex: Int,
    entries: List<PokemonListItemUi>,
    onPokemonClick: (PokemonListItemUi) -> Unit
) {
    Column {
        Row {
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
private fun PokedexEntry(
    entry: PokemonListItemUi,
    onClick: (PokemonListItemUi) -> Unit,
    modifier: Modifier = Modifier
) {
    var isLoadingImage by remember { mutableStateOf(true) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(entry.dominantColor, MaterialTheme.colorScheme.surface)
                )
            )
            .clickable(
                indication = LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick(entry) }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isLoadingImage) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(entry.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = entry.name,
                    modifier = Modifier.size(100.dp),
                    onSuccess = { isLoadingImage = false },
                    onError = { isLoadingImage = false }
                )
            }

            Text(
                text = entry.name,
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ErrorState(
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
private fun SearchBar(
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