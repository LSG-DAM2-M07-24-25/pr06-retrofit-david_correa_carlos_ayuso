package com.example.retrofitdavidcarlos.view.util

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.retrofitdavidcarlos.model.Estado
import com.example.retrofitdavidcarlos.model.Game
import com.example.retrofitdavidcarlos.viewmodel.RoomViewModel
import com.example.retrofitdavidcarlos.viewmodel.ListViewModel
import okhttp3.Request


@Composable
fun MenuEstado(
    game: Game,
    roomViewModel: RoomViewModel,
    listViewModel: ListViewModel,
    expanded: Boolean,
    onDismissRequest: () -> Unit
) {
    val estaGuardado by roomViewModel.juegosGuardados.observeAsState(setOf())
    val context = LocalContext.current
    val listas by listViewModel.listas.observeAsState(emptyList())

    fun realizarAccion(mensaje: String, accion: () -> Unit) {
        accion()
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        onDismissRequest()
        roomViewModel.actualizarJuegosGuardados()
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            text = { Text("Guardar") },
            leadingIcon = { Icon(Icons.Outlined.AddCircle, contentDescription = null) },
            onClick = { realizarAccion("Juego guardado") { roomViewModel.guardarJuego(game) } },
            enabled = !estaGuardado.contains(game.name)
        )

        HorizontalDivider()

        DropdownMenuItem(
            text = { Text("Añadir a Pendientes") },
            leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
            onClick = { realizarAccion("Estado cambiado a Pendiente") { roomViewModel.updateEstado(game, Estado.PENDIENTE) } }
        )

        DropdownMenuItem(
            text = { Text("Añadir a Jugando") },
            leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
            onClick = { realizarAccion("Estado cambiado a Jugando") { roomViewModel.updateEstado(game, Estado.JUGANDO) } }
        )

        DropdownMenuItem(
            text = { Text("Añadir a Jugados") },
            leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
            onClick = { realizarAccion("Estado cambiado a Jugado") { roomViewModel.updateEstado(game, Estado.JUGADO) } }
        )

        HorizontalDivider()

        // Sección de listas personalizadas
        Text(
            text = "Añadir a lista personalizada",
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.labelMedium
        )

        listas.filter { !it.isDefault }.forEach { lista ->
            val estaEnLista = listViewModel.estaJuegoEnLista(lista.id, game)
            DropdownMenuItem(
                text = { Text(lista.name) },
                leadingIcon = { 
                    Icon(
                        if (estaEnLista) Icons.Default.Delete else Icons.Outlined.AddCircle,
                        contentDescription = null
                    )
                },
                onClick = {
                    if (estaEnLista) {
                        realizarAccion("Juego eliminado de ${lista.name}") {
                            listViewModel.eliminarJuegoDeLista(lista.id, game)
                        }
                    } else {
                        realizarAccion("Juego añadido a ${lista.name}") {
                            listViewModel.agregarJuegoALista(lista.id, game)
                        }
                    }
                }
            )
        }

        HorizontalDivider()

        DropdownMenuItem(
            text = { Text("Eliminar de Guardados") },
            leadingIcon = { Icon(Icons.Filled.Delete, contentDescription = null) },
            onClick = { realizarAccion("Juego eliminado") { roomViewModel.eliminarJuego(game) } },
            enabled = estaGuardado.contains(game.name)
        )
    }
}

