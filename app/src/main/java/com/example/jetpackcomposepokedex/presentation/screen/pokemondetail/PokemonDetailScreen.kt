package com.example.jetpackcomposepokedex.presentation.screen.pokemondetail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.jetpackcomposepokedex.R
import com.example.jetpackcomposepokedex.presentation.model.PokemonDetailUi
import com.example.jetpackcomposepokedex.presentation.model.StatUi
import com.example.jetpackcomposepokedex.presentation.model.TypeUi

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    dominantColor: Color,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(start = 16.dp, top = 32.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
        },
        containerColor = dominantColor
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(bottom = 16.dp)
        ) {
            when (val state = uiState) {
                is PokemonDetailUiState.Loading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is PokemonDetailUiState.Error -> {
                    Text(
                        text = state.message,
                        color = Color.Red,
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is PokemonDetailUiState.Success -> {
                    val pokemon = state.pokemon

                    PokemonDetailContent(
                        pokemon = pokemon,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = topPadding + pokemonImageSize / 2f,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            )
                            .shadow(10.dp, RoundedCornerShape(10.dp))
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                    )

                    AsyncImage(
                        model = pokemon.imageUrl,
                        contentDescription = pokemon.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(pokemonImageSize)
                            .offset(y = topPadding)
                            .align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}

@Composable
private fun PokemonDetailContent(
    pokemon: PokemonDetailUi,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 100.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "#${pokemon.id} ${pokemon.name}",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        PokemonTypeSection(types = pokemon.types)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        PokemonDetailDataSection(
            weight = pokemon.weightInKg,
            height = pokemon.heightInMeters
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        PokemonBaseStatSection(stats = pokemon.stats)
    }
}

@Composable
private fun PokemonTypeSection(
    types: List<TypeUi>,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        types.forEach { type ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(type.color)
                    .height(35.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = type.name.uppercase(),
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
private fun PokemonDetailDataSection(
    weight: Float,
    height: Float,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        PokemonDetailDataItem(
            value = weight,
            unit = "kg",
            icon = painterResource(id = R.drawable.ic_weight)
        )
        
        VerticalDivider(
            modifier = Modifier.height(40.dp),
            color = Color.LightGray
        )
        
        PokemonDetailDataItem(
            value = height,
            unit = "m",
            icon = painterResource(id = R.drawable.ic_height)
        )
    }
}

@Composable
private fun PokemonDetailDataItem(
    value: Float,
    unit: String,
    icon: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${"%.1f".format(value)} $unit",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun PokemonBaseStatSection(
    stats: List<StatUi>,
    modifier: Modifier = Modifier
) {
    val maxValue = remember { stats.maxOf { it.value } }
    
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Base Stats",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        stats.forEachIndexed { index, stat ->
            PokemonStatBar(
                stat = stat,
                maxValue = maxValue,
                animDelay = index * 100
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun PokemonStatBar(
    stat: StatUi,
    maxValue: Int,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember { mutableStateOf(false) }
    
    val animatedProgress by animateFloatAsState(
        targetValue = if (animationPlayed) stat.value / maxValue.toFloat() else 0f,
        animationSpec = tween(animDuration, animDelay),
        label = "stat_animation"
    )
    
    LaunchedEffect(Unit) {
        animationPlayed = true
    }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(
                if (androidx.compose.foundation.isSystemInDarkTheme()) 
                    Color(0xFF222222) else Color.LightGray
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedProgress)
                .clip(CircleShape)
                .background(stat.color)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stat.abbreviation,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                Text(
                    text = stat.value.toString(),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}