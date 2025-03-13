package com.example.retrofitdavidcarlos.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitdavidcarlos.model.Game
import com.example.retrofitdavidcarlos.model.GameResponse
import com.example.retrofitdavidcarlos.model.Busqueda
import com.example.retrofitdavidcarlos.room.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchBarViewModel : ViewModel() {

    private val _roomRepository = RoomRepository()
    val roomRepository = _roomRepository

    private val _busqueda = MutableLiveData<String>("")
    val busqueda: LiveData<String> = _busqueda

    private val _isSearchActive = MutableLiveData<Boolean>(false)
    val isSearchActive: LiveData<Boolean> = _isSearchActive

    private val _filteredGames = MutableLiveData<List<Game>>(emptyList())
    val filteredGames: LiveData<List<Game>> = _filteredGames

    private val _historialBusqueda = MutableLiveData<List<String>>()
    val historialBusqueda: LiveData<List<String>> = _historialBusqueda


    fun actualizarBusqueda(query: String, games: GameResponse) {
        _busqueda.value = query
        _isSearchActive.value = query.isNotBlank()

        if (query.isNotBlank()) {
            // Crear objeto Busqueda y añadirlo a la base de datos
            val nuevaBusqueda = Busqueda(
                query = query,
                timestamp = System.currentTimeMillis()
            )
            añadirBusqueda(nuevaBusqueda)

            // Filtrar los juegos
            _filteredGames.value = games.results.filter {
                it.name.contains(query, ignoreCase = true)
            }
        } else {
            _filteredGames.value = emptyList()
        }
    }

    // Función para limpiar la barra
    fun limpiarBusqueda() {
        _busqueda.value = ""
        _isSearchActive.value = false
        _filteredGames.value = emptyList()
    }

    /*
     * FUNCIONES DE ROOM
     */
    fun actualizarHistorial(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val historial = roomRepository.getAllQueries()
                withContext(Dispatchers.Main) {
                    _historialBusqueda.value = historial.map { it.query }.toList()
                }
            } catch (e: Exception){
                Log.e("Database", "Error al actualizar el historial de búsqueda", e)
            }
        }
    }

    fun borrarHistorial(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                roomRepository.borrarHistorial()
                actualizarHistorial()
            } catch (e: Exception) {
                Log.e("Database", "Error al borrar el historial")
            }
        }
    }

    fun añadirBusqueda(busqueda: Busqueda){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!roomRepository.estaGuardado(busqueda.query)){
                    roomRepository.addQuery(busqueda)
                    actualizarHistorial()
                }
            }catch (e: Exception) {
                Log.e("Database", "Error al guardar la búsqueda", e)
            }
        }
    }
}