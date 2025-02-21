package com.example.retrofitdavidcarlos.view

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.retrofitdavidcarlos.nav.Routes
import androidx.navigation.compose.composable
import com.example.retrofitdavidcarlos.view.compact.*
import com.example.retrofitdavidcarlos.view.medium.*
import com.example.retrofitdavidcarlos.view.expanded.*
import com.example.retrofitdavidcarlos.viewmodel.ApiViewModel

@Composable
fun EntryPoint(navigationController: NavHostController, apiViewModel: ApiViewModel, windowSize: WindowSizeClass){

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            AppNavigationCompact(navigationController, apiViewModel)
        }
        WindowWidthSizeClass.Medium -> {
            AppNavigationMedium(navigationController, apiViewModel)
        }
        WindowWidthSizeClass.Expanded -> {
            AppNavigationExpanded(navigationController, apiViewModel)
        }
        else -> {
            AppNavigationCompact(navigationController, apiViewModel)
        }
    }

}

@Composable
fun AppNavigationCompact(navigationController: NavHostController, apiViewModel: ApiViewModel){
    NavHost(
        navController = navigationController,
        startDestination = Routes.HomeCompact.route
    ){
        composable(Routes.HomeCompact.route){
            HomeCompact(navigationController, apiViewModel)
        }

        composable(Routes.InfoCompact.route){
            InfoCompact(navigationController, apiViewModel)
        }

        composable(Routes.ListasCompact.route){
            ListasCompact(navigationController, apiViewModel)
        }

        composable(Routes.JugandoCompact.route){
            JugandoCompact(navigationController, apiViewModel)
        }

        composable(Routes.JugadoCompact.route){
            JugadoCompact(navigationController, apiViewModel)
        }

        composable(Routes.PendienteCompact.route){
            PendienteCompact(navigationController, apiViewModel)
        }
    }
}

@Composable
fun AppNavigationMedium(navigationController: NavHostController, apiViewModel: ApiViewModel){
    NavHost(
        navController = navigationController,
        startDestination = Routes.HomeMedium.route
    ){
        composable(Routes.HomeMedium.route){
            HomeMedium(navigationController, apiViewModel)
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