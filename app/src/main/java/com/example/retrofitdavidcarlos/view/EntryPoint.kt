package com.example.retrofitdavidcarlos.view

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.retrofitdavidcarlos.nav.Routes
import androidx.navigation.compose.composable
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
        startDestination = Routes.HomeCompact.route
    ){
        composable(Routes.HomeCompact.route){
            HomeCompact(navigationController, apiViewModel)
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
fun AppNavigationExpanded(navigationController: NavHostController, apiViewModel: ApiViewModel){

}