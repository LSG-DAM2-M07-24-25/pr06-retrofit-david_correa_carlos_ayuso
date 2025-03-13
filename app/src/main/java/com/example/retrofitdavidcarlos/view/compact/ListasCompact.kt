package com.example.retrofitdavidcarlos.view.compact

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.retrofitdavidcarlos.model.Game
import com.example.retrofitdavidcarlos.model.GameResponse
import com.example.retrofitdavidcarlos.model.Lista
import com.example.retrofitdavidcarlos.nav.Routes
import com.example.retrofitdavidcarlos.viewmodel.ApiViewModel
import com.example.retrofitdavidcarlos.viewmodel.ListViewModel
import com.example.retrofitdavidcarlos.viewmodel.RoomViewModel

@Composable
fun ListasCompact(
    navController: NavHostController,
    apiViewModel: ApiViewModel,
    roomViewModel: RoomViewModel
) {
    val pendientes by roomViewModel.listaPendientes.observeAsState(emptyList())
    val jugando by roomViewModel.listaJugando.observeAsState(emptyList())
    val jugados by roomViewModel.listaJugados.observeAsState(emptyList())
    var tabSeleccionado by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        roomViewModel.actualizarListasEstado()
    }

    Scaffold(
        topBar = { Topbar(tabSeleccionado) { tabSeleccionado = it } },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        if (tabSeleccionado == 0) {
            Favoritos(padding, navController, roomViewModel)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        ListaEstadoContainer(
                            nombre = "Jugando",
                            cantidad = jugando.size,
                            onClick = { navController.navigate("listaJugando") }
                        )
                    }
                    item {
                        ListaEstadoContainer(
                            nombre = "Jugados",
                            cantidad = jugados.size,
                            onClick = { navController.navigate("listaJugados") }
                        )
                    }
                    item {
                        ListaEstadoContainer(
                            nombre = "Pendientes",
                            cantidad = pendientes.size,
                            onClick = { navController.navigate("listaPendientes") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Topbar(tabSeleccionado: Int, onTabSelected: (Int) -> Unit) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mis listas",
                style = MaterialTheme.typography.titleLarge
            )
        }

        TabRow(
            selectedTabIndex = tabSeleccionado,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            Tab(
                selected = tabSeleccionado == 0,
                onClick = { onTabSelected(0) },
                text = { Text("FAVORITOS") }
            )
            Tab(
                selected = tabSeleccionado == 1,
                onClick = { onTabSelected(1) },
                text = { Text("LISTAS") }
            )
        }
    }
}

@Composable
fun ListaEstadoContainer(
    nombre: String,
    cantidad: Int,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)  // Aumentando la altura del contenedor
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)  // Aumentando el padding interno
                .fillMaxHeight(),  // Ocupando toda la altura disponible
            verticalArrangement = Arrangement.Center  // Centrando el contenido verticalmente
        ) {
            Text(
                text = nombre,
                style = MaterialTheme.typography.titleMedium,  // Texto del título más grande
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "$cantidad ${if (cantidad == 1) "Juego" else "Juegos"}",
                style = MaterialTheme.typography.bodyMedium,  // Texto del cuerpo más grande
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)  // Mayor espacio entre textos
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContenidoListaEstado(
    games: List<Game>,
    navController: NavHostController,
    roomViewModel: RoomViewModel,
    titulo: String
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titulo) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver atrás")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(games) { game ->

                TarjetaGame(
                    game = game,
                    navController = navController,
                    roomViewModel = roomViewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TarjetaGame(game: Game, navController: NavHostController, roomViewModel: RoomViewModel){
    val esFavorito by roomViewModel.juegosFavoritos.observeAsState(setOf())

    LaunchedEffect(game.is_favorite) {
        roomViewModel.actualizarJuegosGuardados()
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = { navController.navigate(Routes.InfoCompact.createRoute(game.id)) }
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

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = game.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = {

                                roomViewModel.addFavorito(game)
                            }
                        ) {
                            Icon(
                                imageVector = if (esFavorito.contains(game.name)) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Favorito",
                                tint = if (esFavorito.contains(game.name)) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Fecha: ${game.released ?: "N/A"} | Rating: ${game.rating}/5",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
@Composable
fun Favoritos(
    paddingValues: PaddingValues,
    navController: NavHostController,
    roomViewModel: RoomViewModel
) {
    val favoritos by roomViewModel.listaFavoritos.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        roomViewModel.getFavoritos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(favoritos) { game ->
                TarjetaGame(
                    game = game,
                    navController = navController,
                    roomViewModel = roomViewModel
                )
            }
        }
    }
}