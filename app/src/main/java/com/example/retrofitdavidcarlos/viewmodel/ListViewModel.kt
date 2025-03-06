package com.example.retrofitdavidcarlos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitdavidcarlos.model.Game
import com.example.retrofitdavidcarlos.model.Lista

class ListViewModel: ViewModel() {
    private val _listas = MutableLiveData<List<Lista>>()
    val listas: LiveData<List<Lista>> = _listas
    private val _maxListas = 10
    val maxListas = _maxListas

    // Mapa para almacenar los juegos de cada lista
    private val _juegosEnListas = MutableLiveData<Map<String, Set<Game>>>(mapOf())
    val juegosEnListas: LiveData<Map<String, Set<Game>>> = _juegosEnListas

    init {
        _listas.value = listOf(
            Lista(
                id = "1",
                name = "Jugando",
                itemCount = 0,
                isDefault = true
            ),
            Lista(
                id = "2",
                name = "Jugado",
                itemCount = 0,
                isDefault = true
            ),
            Lista(
                id = "3",
                name = "Pendiente",
                itemCount = 0,
                isDefault = true
            )
        )
        _juegosEnListas.value = _listas.value?.associate { it.id to emptySet<Game>() } ?: emptyMap()
    }

    fun crearLista(name: String) {
        if ((_listas.value?.size ?: 0) < _maxListas) {
            val currentLists = _listas.value ?: emptyList()
            val newList = Lista(
                id = System.currentTimeMillis().toString(),
                name = name,
                itemCount = 0,
                isDefault = false
            )

            val updatedLists = currentLists + newList
            _listas.value = updatedLists

            // Inicializar el conjunto de juegos para la nueva lista
            val currentGames = _juegosEnListas.value ?: emptyMap()
            _juegosEnListas.value = currentGames + (newList.id to emptySet())
        }
    }

    fun eliminarLista(id: String) {
        val currentLists = _listas.value ?: emptyList()
        val updatedLists = currentLists.filter { it.id != id || it.isDefault }
        _listas.value = updatedLists

        // Eliminar los juegos de la lista eliminada
        val currentGames = _juegosEnListas.value ?: emptyMap()
        _juegosEnListas.value = currentGames - id
    }

    fun agregarJuegoALista(listaId: String, game: Game) {
        val currentGames = _juegosEnListas.value ?: emptyMap()
        val listaJuegos = currentGames[listaId] ?: emptySet()
        val updatedGames = currentGames + (listaId to (listaJuegos + game))
        _juegosEnListas.value = updatedGames

        // Actualizar el contador de la lista
        _listas.value = _listas.value?.map { lista ->
            if (lista.id == listaId) {
                lista.copy(itemCount = (updatedGames[listaId]?.size ?: 0))
            } else {
                lista
            }
        }
    }

    fun eliminarJuegoDeLista(listaId: String, game: Game) {
        val currentGames = _juegosEnListas.value ?: emptyMap()
        val listaJuegos = currentGames[listaId] ?: emptySet()
        val updatedGames = currentGames + (listaId to (listaJuegos - game))
        _juegosEnListas.value = updatedGames

        // Actualizar el contador de la lista
        _listas.value = _listas.value?.map { lista ->
            if (lista.id == listaId) {
                lista.copy(itemCount = (updatedGames[listaId]?.size ?: 0))
            } else {
                lista
            }
        }
    }

    fun estaJuegoEnLista(listaId: String, game: Game): Boolean {
        return _juegosEnListas.value?.get(listaId)?.contains(game) ?: false
    }
}