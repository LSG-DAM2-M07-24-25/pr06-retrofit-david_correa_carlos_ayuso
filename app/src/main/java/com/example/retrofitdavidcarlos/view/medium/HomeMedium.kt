package com.example.retrofitdavidcarlos.view.medium

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.retrofitdavidcarlos.model.Game
import com.example.retrofitdavidcarlos.model.GameResponse
import com.example.retrofitdavidcarlos.nav.Routes
import com.example.retrofitdavidcarlos.view.util.MenuEstado
import com.example.retrofitdavidcarlos.viewmodel.ApiViewModel
import com.example.retrofitdavidcarlos.viewmodel.ListViewModel
import com.example.retrofitdavidcarlos.viewmodel.RoomViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMedium(navController: NavHostController, apiViewModel: ApiViewModel, roomViewModel: RoomViewModel, listViewModel: ListViewModel) {
    val showLoading: Boolean by apiViewModel.loading.observeAsState(true)
    val games: GameResponse by apiViewModel.games.observeAsState(GameResponse(emptyList()))

    LaunchedEffect(Unit) {
        apiViewModel.getGames()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Biblioteca Gamer MEDIUM", fontWeight = FontWeight.Bold, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (showLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.secondary
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(games.results) { game ->
                        GameItem(
                            navController,
                            game,
                            listViewModel,
                            roomViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = Color.DarkGray,
        contentColor = Color.White
    ) {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentDestination?.hierarchy?.any { it.route == Routes.HomeMedium.route } == true,
            onClick = {
                navController.navigate(Routes.HomeMedium.route) {
                    popUpTo(Routes.HomeMedium.route) { inclusive = true }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.LightGray,
                unselectedTextColor = Color.LightGray,
                indicatorColor = Color.Gray
            )
        )

        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.List, contentDescription = "Lists") },
            label = { Text("Listas") },
            selected = currentDestination?.hierarchy?.any { it.route == Routes.ListasMedium.route } == true,
            onClick = {
                navController.navigate(Routes.ListasMedium.route) {
                    popUpTo(Routes.HomeMedium.route)
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.LightGray,
                unselectedTextColor = Color.LightGray,
                indicatorColor = Color.Gray
            )
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GameItem(navController: NavHostController, game: Game, listViewModel: ListViewModel, roomViewModel: RoomViewModel) {
    val estaGuardado by roomViewModel.juegosGuardados.observeAsState(setOf())
    val esFavorito by roomViewModel.juegosFavoritos.observeAsState(setOf())
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        roomViewModel.actualizarJuegosGuardados()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                2.dp,
                if (estaGuardado.contains(game.name)) Color.Green else Color.Transparent,
                RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = { navController.navigate(Routes.InfoMedium.createRoute(game.id)) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Game image
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
            ) {
                GlideImage(
                    model = game.background_image,
                    contentDescription = "Game Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Game info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = game.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More options")
                        }

                        MenuEstado(
                            game = game,
                            roomViewModel = roomViewModel,
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            listViewModel = listViewModel
                        )
                    }

                    IconButton(
                        onClick = {
                            roomViewModel.addFavorito(game)
                        }
                    ) {
                        Icon(
                            imageVector = if (esFavorito.contains(game.name)) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (esFavorito.contains(game.name)) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}