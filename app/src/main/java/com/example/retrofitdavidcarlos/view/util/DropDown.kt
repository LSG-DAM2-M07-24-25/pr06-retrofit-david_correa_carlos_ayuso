package com.example.retrofitdavidcarlos.view.util

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.retrofitdavidcarlos.model.Estado
import com.example.retrofitdavidcarlos.model.Game
import com.example.retrofitdavidcarlos.viewmodel.RoomViewModel

@Composable
fun MenuEstado(
    game: Game,
    roomViewModel: RoomViewModel,
    expanded: Boolean,
    onDismissRequest: () -> Unit
) {
    val estaGuardado by roomViewModel.juegosGuardados.observeAsState(setOf())
    val context = LocalContext.current

    fun realizarAccion(mensaje: String, accion: () -> Unit) {
        accion()
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        onDismissRequest()
        roomViewModel.actualizarJuegosGuardados()
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier.fillMaxWidth(0.75f)
    ) {
        // Título
        Text(
            text = "Opciones para ${game.name}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        HorizontalDivider(thickness = 1.dp)

        // Guardar
        DropdownMenuItem(
            text = {
                Text(
                    "Guardar juego",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Filled.AccountBox,
                    contentDescription = "Guardar juego",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            onClick = {
                realizarAccion("Juego guardado") {
                    roomViewModel.guardarJuego(game)
                }
            },
            enabled = !estaGuardado.contains(game.name)
        )

        HorizontalDivider(thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))

        // Sección de estados
        Text(
            text = "Cambiar estado",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 4.dp)
        )

        // Pendiente
        DropdownMenuItem(
            text = {
                Text(
                    "Pendiente",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Outlined.AddCircle,
                    contentDescription = "Cambiar a Pendiente",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            onClick = {
                realizarAccion("Estado cambiado a Pendiente") {
                    roomViewModel.updateEstado(game, Estado.PENDIENTE)
                }
            }
        )

        // Jugando
        DropdownMenuItem(
            text = {
                Text(
                    "Jugando",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = "Cambiar a Jugando",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            onClick = {
                realizarAccion("Estado cambiado a Jugando") {
                    roomViewModel.updateEstado(game, Estado.JUGANDO)
                }
            }
        )

        // Jugado
        DropdownMenuItem(
            text = {
                Text(
                    "Jugado",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Outlined.CheckCircle,
                    contentDescription = "Cambiar a Jugado",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            },
            onClick = {
                realizarAccion("Estado cambiado a Jugado") {
                    roomViewModel.updateEstado(game, Estado.JUGADO)
                }
            }
        )

        HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

        // Eliminar
        DropdownMenuItem(
            text = {
                Text(
                    "Eliminar de guardados",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Eliminar de Guardados",
                    tint = MaterialTheme.colorScheme.error
                )
            },
            onClick = {
                realizarAccion("Juego eliminado") {
                    roomViewModel.eliminarJuego(game)
                }
            },
            enabled = estaGuardado.contains(game.name)
        )
    }
}