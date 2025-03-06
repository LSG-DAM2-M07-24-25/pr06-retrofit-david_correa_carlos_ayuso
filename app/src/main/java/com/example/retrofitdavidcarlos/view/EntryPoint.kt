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

@Composable
fun EntryPoint(
    navigationController: NavHostController,
    apiViewModel: ApiViewModel,
    listViewModel: ListViewModel,
    roomViewModel: RoomViewModel,
    deviceType: String
) {
    when (deviceType) {
        "compact" -> {
            AppNavigationCompact(navigationController, apiViewModel, listViewModel, roomViewModel)
        }
        "medium" -> {
            AppNavigationMedium(navigationController, apiViewModel, roomViewModel)
        }
        "expanded" -> {
            AppNavigationExpanded(navigationController, apiViewModel)
        }
        else -> {
            AppNavigationCompact(navigationController, apiViewModel, listViewModel, roomViewModel)
        }
    }
}

@Composable
fun AppNavigationCompact(navigationController: NavHostController, apiViewModel: ApiViewModel, listViewModel: ListViewModel, roomViewModel: RoomViewModel){
    NavHost(
        navController = navigationController,
        startDestination = Routes.HomeCompact.route
    ){
        composable(Routes.HomeCompact.route){
            HomeCompact(navigationController, apiViewModel, roomViewModel)
        }

        composable(
            Routes.InfoCompact.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            InfoCompact(
                apiViewModel = apiViewModel,
                navController = navigationController,
                id = backStackEntry.arguments?.getInt("id") ?: 0
            )
        }

        composable(Routes.ListasCompact.route){
            ListasCompact(navigationController, apiViewModel, listViewModel, roomViewModel)
        }

        composable(
            Routes.ContenidoListasCompact.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            ContenidoListasCompact(
                apiViewModel = apiViewModel,
                navController = navigationController,
                listViewModel = listViewModel,
                id = backStackEntry.arguments?.getInt("id") ?: 0
            )
        }

        composable(Routes.CrearListaCompact.route){
            CrearListaCompact(navigationController, listViewModel)
        }
    }
}

@Composable
fun AppNavigationMedium(navigationController: NavHostController, apiViewModel: ApiViewModel, roomViewModel: RoomViewModel){
    NavHost(
        navController = navigationController,
        startDestination = Routes.HomeMedium.route
    ){
        composable(Routes.HomeMedium.route){
            HomeMedium(navigationController, apiViewModel,)
        }

        composable(Routes.InfoMedium.route){
            InfoMedium(navigationController, apiViewModel)
        }

        composable(Routes.ListasMedium.route){
            ListasMedium(navigationController, apiViewModel)
        }

        composable(Routes.JugandoMedium.route){
            JugandoMedium(navigationController, apiViewModel)
        }

        composable(Routes.JugadoMedium.route){
            JugadoMedium(navigationController, apiViewModel)
        }

        composable(Routes.PendienteMedium.route){
            PendienteMedium(navigationController, apiViewModel)
        }
    }
}

@Composable
fun AppNavigationExpanded(navigationController: NavHostController, apiViewModel: ApiViewModel){
    NavHost(
        navController = navigationController,
        startDestination = Routes.HomeExpanded.route
    ){
        composable(Routes.HomeExpanded.route){
            HomeExpanded(navigationController, apiViewModel)
        }

        composable(Routes.InfoExpanded.route){
            InfoExpanded(navigationController, apiViewModel)
        }

        composable(Routes.ListasExpanded.route){
            ListasExpanded(navigationController, apiViewModel)
        }

        composable(Routes.JugandoExpanded.route){
            JugandoExpanded(navigationController, apiViewModel)
        }

        composable(Routes.JugadoExpanded.route){
            JugandoExpanded(navigationController, apiViewModel)
        }

        composable(Routes.PendienteExpanded.route){
            PendienteExpanded(navigationController, apiViewModel)
        }
    }
}