package com.example.retrofitdavidcarlos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitdavidcarlos.model.Game
import com.example.retrofitdavidcarlos.model.GameResponse

class SearchBarViewModel : ViewModel() {
    private val _busqueda = MutableLiveData<String>("")
    val busqueda: LiveData<String> = _busqueda

    private val _isSearchActive = MutableLiveData<Boolean>(false)
    val isSearchActive: LiveData<Boolean> = _isSearchActive

    private val _filteredGames = MutableLiveData<List<Game>>(emptyList())
    val filteredGames: LiveData<List<Game>> = _filteredGames

    fun actualizarBusqueda(query: String, games: GameResponse) {
        _busqueda.value = query
        _isSearchActive.value = query.isNotBlank()

        _filteredGames.value = if (query.isBlank()) {
            emptyList()
        } else {
            games.results.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }

    // Función para limpiar la barra
    fun limpiarBusqueda() {
        _busqueda.value = ""
        _isSearchActive.value = false
        _filteredGames.value = emptyList()
    }
}