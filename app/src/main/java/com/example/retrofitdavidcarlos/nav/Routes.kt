package com.example.retrofitdavidcarlos.nav

sealed class Routes (val route: String){
    object HomeCompact : Routes("home_compact")
    object InfoCompact : Routes("info_compact/{id}"){
        fun createRoute(id: Int) = "info/$id"
    }
    object ListasCompact : Routes("listas_compact")
    object JugadoCompact : Routes("jugado_compact")
    object JugandoCompact : Routes("jugando_compact")
    object PendienteCompact : Routes("pendiente_compact")

    object HomeMedium : Routes("home_medium")
    object InfoMedium : Routes("info_medium")
    object ListasMedium : Routes("listas_medium")
    object JugadoMedium : Routes("jugado_medium")
    object JugandoMedium : Routes("jugando_medium")
    object PendienteMedium : Routes("pendiente_medium")

    object HomeExpanded : Routes("home_expanded")
    object InfoExpanded : Routes("info_expanded")
    object ListasExpanded : Routes("listas_expanded")
    object JugadoExpanded : Routes("jugado_expanded")
    object JugandoExpanded : Routes("jugando_expanded")
    object PendienteExpanded : Routes("pendiente_expanded")
}