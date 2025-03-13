package com.example.retrofitdavidcarlos.view.compact

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.retrofitdavidcarlos.model.Game
import com.example.retrofitdavidcarlos.nav.Routes
import com.example.retrofitdavidcarlos.viewmodel.ApiViewModel
import com.example.retrofitdavidcarlos.viewmodel.RoomViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun InfoExpanded(navController: NavHostController, apiViewModel: ApiViewModel, roomViewModel: RoomViewModel, id: Int) {
    val games by apiViewModel.games.observeAsState()
    val game = games?.results?.find { it.id == id }
    val esFavorito by roomViewModel.juegosFavoritos.observeAsState(setOf())

    LaunchedEffect(Unit) {
        roomViewModel.actualizarJuegosGuardados()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = game?.name ?: "Detalles del juego") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver atrás")
                    }
                },
                actions = {
                    IconButton(onClick = { game?.let { roomViewModel.addFavorito(it) } }) {
                        Icon(
                            imageVector = if (game?.let { esFavorito.contains(it.name) } == true)
                                Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (game?.let { esFavorito.contains(it.name) } == true)
                                Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        game?.let { gameDetails ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                GlideImage(
                    model = gameDetails.background_image,
                    contentDescription = "Game Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = gameDetails.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    DetalleItem("Fecha de lanzamiento", gameDetails.released)
                    DetalleItem("Rating", "${gameDetails.rating}/5 (${gameDetails.ratings_count} votos)")
                    DetalleItem("Metacritic", gameDetails.metacritic.toString())
                    DetalleItem("Tiempo de juego", "${gameDetails.playtime} horas")
                    DetalleItem("Actualizado", gameDetails.updated)
                }
            }
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No se encontró información del juego")
            }
        }
    }
}

@Composable
private fun DetalleItem(titulo: String, valor: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}