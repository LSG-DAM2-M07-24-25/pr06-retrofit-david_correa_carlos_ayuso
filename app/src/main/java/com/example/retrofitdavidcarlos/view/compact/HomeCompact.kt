package com.example.retrofitdavidcarlos.view.compact

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.retrofitdavidcarlos.model.Game
import com.example.retrofitdavidcarlos.model.GameResponse
import com.example.retrofitdavidcarlos.nav.Routes
import com.example.retrofitdavidcarlos.viewmodel.ApiViewModel

@Composable
fun HomeCompact(navController: NavHostController, apiViewModel: ApiViewModel) {
    val showLoading: Boolean by apiViewModel.loading.observeAsState(true)
    val games: GameResponse by apiViewModel.games.observeAsState(GameResponse(emptyList()))

    LaunchedEffect(Unit) {
        apiViewModel.getGames()
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        if (showLoading) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        } else {
            Text(text = "compact")
            LazyColumn(
                contentPadding = innerPadding
            ) {
                items(games.results) { game ->
                    GameItem(navController, game, apiViewModel)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentDestination?.hierarchy?.any { it.route == Routes.HomeCompact.route } == true,
            onClick = {
                navController.navigate(Routes.HomeCompact.route)
            }
        )

        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.List, contentDescription = "Lists") },
            label = { Text("Listas") },
            selected = currentDestination?.hierarchy?.any { it.route == Routes.ListasCompact.route } == true,
            onClick = {
                navController.navigate(Routes.ListasCompact.route)
            }
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GameItem(navController: NavHostController, game: Game, apiViewModel: ApiViewModel) {
    Card(
        border = BorderStroke(2.dp, Color.LightGray),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp),
        onClick = {navController.navigate(Routes.InfoCompact.createRoute(game.id))}    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageUrl = game.background_image

            GlideImage(
                model = imageUrl,
                contentDescription = "Movie Poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
            )
            
            Column {
                Text(
                    text = game.name,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                )

                Row {
                    IconButton(onClick = {
                        apiViewModel.guardarJuego(game)
                    }) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Guardar juego",
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(onClick = {
                        apiViewModel.addFavorito(game)
                    }) {
                        Icon(
                            imageVector = if (game.is_favorite) Icons.Filled.Favorite else Icons.Default.Favorite,
                            contentDescription = "Favorito",
                            tint = if (game.is_favorite) Color.Red else Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

        }
    }
}