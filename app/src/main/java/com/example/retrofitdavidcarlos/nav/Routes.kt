package com.example.retrofitdavidcarlos.nav

sealed class Routes(val route: String) {
    // Vistas Compact
    object HomeCompact : Routes("home_compact")
    object InfoCompact : Routes("info_compact/{id}") {
        fun createRoute(id: Int) = "info_compact/$id"
    }
    object ListasCompact : Routes("listas_compact")
    object ContenidoListasCompact : Routes("contenido_listas_compact/{id}"){
        fun createRoute(id: Int) = "contenido_listas_compact/$id"
    }
    object CrearListaCompact : Routes("crear_lista_compact")


    // Vistas Medium
    object HomeMedium : Routes("home_medium")
    object InfoMedium : Routes("info_medium/{id}") {
        fun createRoute(id: Int) = "info_medium/$id"
    }
    object ListasMedium : Routes("listas_medium")
    object ContenidoListasMedium : Routes("contenido_listas_medium/{id}"){
        fun createRoute(id: Int) = "contenido_listas_medium/$id"
    }
    object CrearListaMedium : Routes("crear_lista_medium")


    // Vistas Expanded
    object HomeExpanded : Routes("home_expanded")
    object InfoExpanded : Routes("info_expanded/{id}") {
        fun createRoute(id: Int) = "info_expanded/$id"
    }
    object ListasExpanded : Routes("listas_expanded")
    object ContenidoListasExpanded : Routes("contenido_listas_expanded/{id}"){
        fun createRoute(id: Int) = "contenido_listas_expanded/$id"
    }
    object CrearListaExpanded : Routes("crear_lista_expanded")

}