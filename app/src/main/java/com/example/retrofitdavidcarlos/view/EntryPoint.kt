package com.example.retrofitdavidcarlos.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.retrofitdavidcarlos.nav.Routes
import androidx.navigation.compose.composable
import com.example.retrofitdavidcarlos.viewmodel.ApiViewModel

@Composable
fun EntryPoint(navigationController: NavHostController, apiViewModel: ApiViewModel){
    NavHost(
        navController = navigationController,
        startDestination = Routes.Juegos.route
    ){
        composable(Routes.Juegos.route){
            Juegos(navigationController, apiViewModel)
        }

        composable(Routes.Listas.route){
            Listas(navigationController, apiViewModel)
        }
    }
}