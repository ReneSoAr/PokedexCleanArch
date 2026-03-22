package com.example.jetpackcomposepokedex.presentation.pokemonDetail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.LocalIndication
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
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
import com.example.jetpackcomposepokedex.domain.models.PokemonModel
import com.example.jetpackcomposepokedex.domain.models.PokemonStat
import com.example.jetpackcomposepokedex.domain.models.PokemonType
import kotlin.math.round

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    dominantColor: Color,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
            .padding(bottom = 16.dp)
    ) {
        PokemonDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .align(Alignment.TopCenter)
        )

        when (state) {
            is PokemonDetailState.Loading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is PokemonDetailState.Error -> {
                Text(
                    text = (state as PokemonDetailState.Error).message,
                    color = Color.Red,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is PokemonDetailState.Success -> {
                val pokemon = (state as PokemonDetailState.Success).pokemon
                
                PokemonDetailContent(
                    pokemon = pokemon,
                    topPadding = topPadding,
                    pokemonImageSize = pokemonImageSize,
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

@Composable
private fun PokemonDetailContent(
    pokemon: PokemonModel,
    topPadding: Dp,
    pokemonImageSize: Dp,
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
            text = "#${pokemon.id} ${pokemon.name.replaceFirstChar { it.uppercase() }}",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        PokemonTypeSection(types = pokemon.types)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        PokemonDetailDataSection(
            weight = pokemon.weight,
            height = pokemon.height
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        PokemonBaseStatSection(stats = pokemon.stats)
    }
}

@Composable
private fun PokemonDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Box(
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(Color.Black, Color.Transparent)
                )
            )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 32.dp)
                .size(36.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = LocalIndication.current
                ) { navController.popBackStack() }
        )
    }
}

@Composable
private fun PokemonTypeSection(
    types: List<PokemonType>,
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
                    text = type.displayName.uppercase(),
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
private fun PokemonDetailDataSection(
    weight: Int,
    height: Int,
    modifier: Modifier = Modifier
) {
    val weightInKg = remember { round(weight * 100f) / 1000f }
    val heightInMeters = remember { round(height * 100f) / 1000f }
    
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        PokemonDetailDataItem(
            value = weightInKg,
            unit = "kg",
            icon = painterResource(id = R.drawable.ic_weight)
        )
        
        VerticalDivider(
            modifier = Modifier.height(40.dp),
            color = Color.LightGray
        )
        
        PokemonDetailDataItem(
            value = heightInMeters,
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
            text = "$value $unit",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun PokemonBaseStatSection(
    stats: List<PokemonStat>,
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
    stat: PokemonStat,
    maxValue: Int,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember { mutableStateOf(false) }
    
    val animatedProgress by animateFloatAsState(
        targetValue = if (animationPlayed) stat.value / maxValue.toFloat() else 0f,
        animationSpec = tween(animDuration, animDelay)
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
                if (isSystemInDarkTheme()) Color(0xFF222222) else Color.LightGray
            )
    ) {
        // Barra de color con el stat
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedProgress)
                .clip(CircleShape)
                .background(stat.type.color)
        ) {
            // Texto dentro de la barra de color
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stat.type.abbreviation,
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