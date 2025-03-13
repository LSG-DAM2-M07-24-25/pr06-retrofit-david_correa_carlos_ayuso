package com.example.retrofitdavidcarlos.view

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import com.example.retrofitdavidcarlos.nav.Routes
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.retrofitdavidcarlos.view.compact.*
import com.example.retrofitdavidcarlos.view.medium.*
import com.example.retrofitdavidcarlos.view.expanded.*
import com.example.retrofitdavidcarlos.viewmodel.ApiViewModel
import com.example.retrofitdavidcarlos.viewmodel.ListViewModel
import com.example.retrofitdavidcarlos.viewmodel.RoomViewModel
import com.example.retrofitdavidcarlos.viewmodel.SearchBarViewModel

@Composable
fun EntryPoint(
    navigationController: NavHostController,
    apiViewModel: ApiViewModel,
    listViewModel: ListViewModel,
    roomViewModel: RoomViewModel,
    searchBarViewModel: SearchBarViewModel,
    deviceType: String
) {
    when (deviceType) {
        "compact" -> {
            AppNavigationCompact(navigationController, apiViewModel, listViewModel, roomViewModel, searchBarViewModel)
        }
        "medium" -> {
            AppNavigationMedium(navigationController, apiViewModel, listViewModel, roomViewModel, searchBarViewModel)
        }
        "expanded" -> {
            AppNavigationExpanded(navigationController, apiViewModel, listViewModel, roomViewModel, searchBarViewModel)
        }
        else -> {
            AppNavigationCompact(navigationController, apiViewModel, listViewModel, roomViewModel, searchBarViewModel)
        }
    }
}

@Composable
fun AppNavigationCompact(navigationController: NavHostController, apiViewModel: ApiViewModel, listViewModel: ListViewModel, roomViewModel: RoomViewModel, searchBarViewModel: SearchBarViewModel){
    NavHost(
        navController = navigationController,
        startDestination = Routes.HomeCompact.route
    ){
        composable(Routes.HomeCompact.route){
            HomeCompact(navigationController, apiViewModel, roomViewModel, listViewModel, searchBarViewModel)
        }

        composable(
            Routes.InfoCompact.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            InfoCompact(
                apiViewModel = apiViewModel,
                navController = navigationController,
                id = backStackEntry.arguments?.getInt("id") ?: 0,
                roomViewModel = roomViewModel
            )
        }

        composable(Routes.ListasCompact.route){
            ListasCompact(navigationController, apiViewModel, roomViewModel)
        }

        // Nuevas rutas para las listas de estado
        composable("listaJugando") {
            ContenidoListaEstadoCompact(
                games = roomViewModel.listaJugando.value ?: emptyList(),
                navController = navigationController,
                roomViewModel = roomViewModel,
                titulo = "Jugando"
            )
        }
        
        composable("listaJugados") {
            ContenidoListaEstadoCompact(
                games = roomViewModel.listaJugados.value ?: emptyList(),
                navController = navigationController,
                roomViewModel = roomViewModel,
                titulo = "Jugados"
            )
        }
        
        composable("listaPendientes") {
            ContenidoListaEstadoCompact(
                games = roomViewModel.listaPendientes.value ?: emptyList(),
                navController = navigationController,
                roomViewModel = roomViewModel,
                titulo = "Pendientes"
            )
        }
    }
}

@Composable
fun AppNavigationMedium(navigationController: NavHostController, apiViewModel: ApiViewModel, listViewModel: ListViewModel, roomViewModel: RoomViewModel, searchBarViewModel: SearchBarViewModel){
    NavHost(
        navController = navigationController,
        startDestination = Routes.HomeMedium.route
    ){
        composable(Routes.HomeMedium.route){
            HomeMedium(navigationController, apiViewModel, roomViewModel, listViewModel)
        }

        composable(
            Routes.InfoMedium.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            InfoMedium(
                apiViewModel = apiViewModel,
                navController = navigationController,
                id = backStackEntry.arguments?.getInt("id") ?: 0,
                roomViewModel = roomViewModel
            )
        }

        composable(Routes.ListasMedium.route){
            ListasMedium(navigationController, apiViewModel, listViewModel, roomViewModel)
        }
    }
}

@Composable
fun AppNavigationExpanded(navigationController: NavHostController, apiViewModel: ApiViewModel, listViewModel: ListViewModel, roomViewModel: RoomViewModel, searchBarViewModel: SearchBarViewModel){
    NavHost(
        navController = navigationController,
        startDestination = Routes.HomeExpanded.route
    ){
        composable(Routes.HomeExpanded.route){
            HomeExpanded(navigationController, apiViewModel, roomViewModel, listViewModel, searchBarViewModel)
        }

        composable(
            Routes.InfoExpanded.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            InfoExpanded(
                apiViewModel = apiViewModel,
                navController = navigationController,
                id = backStackEntry.arguments?.getInt("id") ?: 0,
                roomViewModel = roomViewModel
            )
        }

        composable(Routes.ListasExpanded.route){
            ListasExpanded(navigationController, apiViewModel, roomViewModel)
        }

        // Nuevas rutas para las listas de estado
        composable("listaJugando") {
            ContenidoListaEstadoExpanded(
                games = roomViewModel.listaJugando.value ?: emptyList(),
                navController = navigationController,
                roomViewModel = roomViewModel,
                titulo = "Jugando"
            )
        }

        composable("listaJugados") {
            ContenidoListaEstadoExpanded(
                games = roomViewModel.listaJugados.value ?: emptyList(),
                navController = navigationController,
                roomViewModel = roomViewModel,
                titulo = "Jugados"
            )
        }

        composable("listaPendientes") {
            ContenidoListaEstadoExpanded(
                games = roomViewModel.listaPendientes.value ?: emptyList(),
                navController = navigationController,
                roomViewModel = roomViewModel,
                titulo = "Pendientes"
            )
        }
    }
}