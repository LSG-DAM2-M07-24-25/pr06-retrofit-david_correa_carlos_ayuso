package com.example.retrofitdavidcarlos.view.compact

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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.retrofitdavidcarlos.model.Game
import com.example.retrofitdavidcarlos.model.GameResponse
import com.example.retrofitdavidcarlos.viewmodel.ApiViewModel

@Composable
fun ListasCompact(navController: NavHostController, apiViewModel: ApiViewModel){
    val games: GameResponse by apiViewModel.games.observeAsState(GameResponse(emptyList()))
    var tabSeleccionado by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        apiViewModel.getGames()
    }
    Scaffold(
        topBar = { Topbar(tabSeleccionado){ tabSeleccionado = it} },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        if (tabSeleccionado == 0){
            Favoritos(
                games = games,
                paddingValues = padding
            )
        } else {
            Listas(
                paddingValues = padding,
            )
        }
    }
}

@Composable
fun Topbar(tabSeleccionado: Int, onTabSelected: (Int) -> Unit){
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
fun Favoritos(games: GameResponse, paddingValues: PaddingValues){
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
            items(games.results) { game ->
                TarjetaGame(game = game)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TarjetaGame(game: Game){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        GlideImage(
            model = game.background_image,
            contentDescription = game.name,
            modifier = Modifier
                .width(120.dp)
                .height(150.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = game.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Row {
                    IconButton(onClick = { /* Marcar favorito */ }) {
                        Icon(Icons.Default.FavoriteBorder, "Favorito")
                    }
                    IconButton(onClick = { /* Mostrar opciones */ }) {
                        Icon(Icons.Default.MoreVert, "Más opciones")
                    }
                }
            }

            Text(
                text = "Fecha: ${game.released} |  Rating: ${game.rating}/5",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun Listas(paddingValues: PaddingValues) {
    var pendientesMenuExpanded by remember { mutableStateOf(false) }
    var jugandoMenuExpanded by remember { mutableStateOf(false) }
    var jugadoMenuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        // Jugando
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable(onClick = { /* Acción al hacer clic en Jugando */ }),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jugando",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Box {
                    IconButton(onClick = { jugandoMenuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más opciones"
                        )
                    }
                    DropdownMenu(
                        expanded = jugandoMenuExpanded,
                        onDismissRequest = { jugandoMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Renombrar Critilista") },
                            onClick = { jugandoMenuExpanded = false }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Borrar Critilista",
                                    color = MaterialTheme.colorScheme.error
                                )
                            },
                            onClick = { jugandoMenuExpanded = false }
                        )
                    }
                }
            }
        }

        // Jugado
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable(onClick = { /* Acción al hacer clic en Jugado */ }),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jugado",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Box {
                    IconButton(onClick = { jugadoMenuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más opciones"
                        )
                    }
                    DropdownMenu(
                        expanded = jugadoMenuExpanded,
                        onDismissRequest = { jugadoMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Renombrar Critilista") },
                            onClick = { jugadoMenuExpanded = false }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Borrar Critilista",
                                    color = MaterialTheme.colorScheme.error
                                )
                            },
                            onClick = { jugadoMenuExpanded = false }
                        )
                    }
                }
            }
        }

        // Pendientes
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable(onClick = { /* Acción al hacer clic en Pendientes */ }),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pendientes",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Box {
                    IconButton(onClick = { pendientesMenuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más opciones"
                        )
                    }
                    DropdownMenu(
                        expanded = pendientesMenuExpanded,
                        onDismissRequest = { pendientesMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Renombrar Critilista") },
                            onClick = { pendientesMenuExpanded = false }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Borrar Critilista",
                                    color = MaterialTheme.colorScheme.error
                                )
                            },
                            onClick = { pendientesMenuExpanded = false }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListasPreview(){
    ListasCompact(navController = rememberNavController(), apiViewModel = ApiViewModel())
}