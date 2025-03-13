package com.example.retrofitdavidcarlos.view.compact

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.retrofitdavidcarlos.R
import com.example.retrofitdavidcarlos.model.Game
import com.example.retrofitdavidcarlos.model.GameResponse
import com.example.retrofitdavidcarlos.nav.Routes
import com.example.retrofitdavidcarlos.view.util.LoadingIndicator
import com.example.retrofitdavidcarlos.view.util.MenuEstado
import com.example.retrofitdavidcarlos.viewmodel.ApiViewModel
import com.example.retrofitdavidcarlos.viewmodel.ListViewModel
import com.example.retrofitdavidcarlos.viewmodel.RoomViewModel
import com.example.retrofitdavidcarlos.viewmodel.SearchBarViewModel

// Estructura general del home con topbar y bottombar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCompact(navController: NavHostController, apiViewModel: ApiViewModel, roomViewModel: RoomViewModel, listViewModel: ListViewModel, searchBarViewModel: SearchBarViewModel) {
    val showLoading: Boolean by apiViewModel.loading.observeAsState(true)
    val games: GameResponse by apiViewModel.games.observeAsState(GameResponse(emptyList()))

    LaunchedEffect(Unit) {
        apiViewModel.getGames()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.t1),
                            contentDescription = "Logo t1",
                            modifier = Modifier.size(60.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("BiblioGamer", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                },
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
                LoadingIndicator()
            } else {
                ContenidoPrincipal(
                    paddingValues = innerPadding,
                    myViewModel = searchBarViewModel,
                    listViewModel = listViewModel,
                    apiViewModel = apiViewModel,
                    roomViewModel = roomViewModel,
                    games = games,
                    navController = navController
                )
            }
        }
    }
}

// Muestra todos los juegos hasta que se escribe algo en al barra de busqueda
@Composable
fun ContenidoPrincipal(paddingValues: PaddingValues, myViewModel: SearchBarViewModel, listViewModel: ListViewModel, apiViewModel: ApiViewModel, roomViewModel: RoomViewModel, games: GameResponse, navController: NavHostController){

    val busqueda by myViewModel.busqueda.observeAsState("")
    val isSearchActive by myViewModel.isSearchActive.observeAsState(false)
    val filteredGames by myViewModel.filteredGames.observeAsState(emptyList())
    val historial by myViewModel.historialBusqueda.observeAsState(emptyList())

    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBar(
            query = busqueda,
            onQueryChange = {
                myViewModel.actualizarBusqueda(it, games)
            },
            onClearQuery = {
                myViewModel.limpiarBusqueda()
            },
            searchBarViewModel = myViewModel
        )

        if (isSearchActive){
            ResultadosBusqueda(
                query = busqueda,
                filteredGames = filteredGames,
                navController = navController,
                listViewModel = listViewModel,
                roomViewModel = roomViewModel
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(games.results) { game ->
                    GameItem(navController, game, listViewModel, roomViewModel)
                }
            }
        }
    }
}

// Se muestran los juegos que contengan la palabra de la query
// Muestra un mensaje si no encuentra algun juego que contenga la palabra de la query
@Composable
fun ResultadosBusqueda(
    query: String,
    filteredGames: List<Game>,
    navController: NavHostController,
    listViewModel: ListViewModel,
    roomViewModel: RoomViewModel
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (filteredGames.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No se encontraron resultados que coincidan con \"$query\"",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredGames) { game ->
                    GameItem(navController, game, listViewModel, roomViewModel)
                }
            }
        }
    }
}

// Barra de busqueda
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    searchBarViewModel: SearchBarViewModel
) {
    var showHistorial by remember { mutableStateOf(false) }
    val historial by searchBarViewModel.historialBusqueda.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        searchBarViewModel.actualizarHistorial()
    }

    LaunchedEffect(historial) {
        if (historial.isEmpty()) {
            showHistorial = false
        }
    }

    Column {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Busca tus juegos")
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .onFocusChanged {
                    if (it.isFocused && historial.isNotEmpty()) {
                        showHistorial = true
                    }
                },
            singleLine = true,
            trailingIcon = {
                Row {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = onClearQuery) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Limpiar búsqueda"
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            if (query.isNotEmpty()) {
                                searchBarViewModel.guardarBusqueda(query)
                                if (historial.isNotEmpty() || !showHistorial) {
                                    showHistorial = !showHistorial
                                }
                            }
                        },
                        enabled = query.isNotEmpty()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Ver historial",
                            tint = if (query.isNotEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                        )
                    }
                }
            }
        )

        // Solo mostrar el historial si hay busquedas recientes
        if (historial.isNotEmpty()) {
            AnimatedVisibility(
                visible = showHistorial,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Historial de búsqueda",
                                style = MaterialTheme.typography.titleMedium
                            )
                            IconButton(
                                onClick = {
                                    searchBarViewModel.borrarHistorial()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Borrar historial"
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(historial) { busqueda ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onQueryChange(busqueda)
                                            showHistorial = false
                                        }
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.List,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = busqueda,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            searchBarViewModel.eliminarBusqueda(busqueda)
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Eliminar búsqueda",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Navegacion por tabs entre Home y Listas
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
            selected = currentDestination?.hierarchy?.any { it.route == Routes.HomeCompact.route } == true,
            onClick = {
                navController.navigate(Routes.HomeCompact.route) {
                    popUpTo(Routes.HomeCompact.route) { inclusive = true }
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
            selected = currentDestination?.hierarchy?.any { it.route == Routes.ListasCompact.route } == true,
            onClick = {
                navController.navigate(Routes.ListasCompact.route) {
                    popUpTo(Routes.HomeCompact.route)
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

// Composable Card para cada juego
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

                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More options")
                    }

                    MenuEstado(
                        game = game,
                        roomViewModel = roomViewModel,
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    )

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