package com.example.retrofitdavidcarlos.nav

sealed class Routes (val route: String){
    object Juegos : Routes("juegos")
    object Listas : Routes("listas")
}