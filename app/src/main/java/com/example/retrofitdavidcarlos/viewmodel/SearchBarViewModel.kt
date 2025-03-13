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

    private val roomRepository = RoomRepository()

    private val _busqueda = MutableLiveData<String>("")
    val busqueda: LiveData<String> = _busqueda

    private val _isSearchActive = MutableLiveData<Boolean>(false)
    val isSearchActive: LiveData<Boolean> = _isSearchActive

    private val _filteredGames = MutableLiveData<List<Game>>(emptyList())
    val filteredGames: LiveData<List<Game>> = _filteredGames

    private val _historialBusqueda = MutableLiveData<List<String>>(emptyList())
    val historialBusqueda: LiveData<List<String>> = _historialBusqueda

    // Actualiza la búsqueda y filtra los juegos
    fun actualizarBusqueda(query: String, games: GameResponse) {
        _busqueda.value = query
        _isSearchActive.value = query.isNotBlank()

        if (query.isNotBlank()) {
            _filteredGames.value = games.results.filter {
                it.name.contains(query, ignoreCase = true)
            }
        } else {
            _filteredGames.value = emptyList()
        }
    }

    // Guarda una búsqueda al presionar la lupa
    fun guardarBusqueda(query: String) {
        if (query.isBlank()) return

        val nuevaBusqueda = Busqueda(
            query = query,
            timestamp = System.currentTimeMillis()
        )
        añadirBusqueda(nuevaBusqueda)
    }

    // Limpia la barra de búsqueda
    fun limpiarBusqueda() {
        _busqueda.value = ""
        _isSearchActive.value = false
        _filteredGames.value = emptyList()
    }

    // Actualiza el historial de búsquedas
    fun actualizarHistorial(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val historial = roomRepository.getAllQueries()
                withContext(Dispatchers.Main) {
                    // Ordenar por timestamp y limitar a 5
                    _historialBusqueda.value = historial
                        .sortedByDescending { it.timestamp }
                        .take(5)
                        .map { it.query }
                }
            } catch (e: Exception){
                Log.e("Database", "Error al actualizar el historial de búsqueda", e)
            }
        }
    }

    // Elimina el historial completo
    fun borrarHistorial(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                roomRepository.borrarHistorial()
                withContext(Dispatchers.Main) {
                    _historialBusqueda.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("Database", "Error al borrar el historial")
            }
        }
    }

    // Elimina una búsqueda específica X
    fun eliminarBusqueda(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                roomRepository.eliminarBusqueda(query)
                actualizarHistorial()
            } catch (e: Exception) {
                Log.e("Database", "Error al eliminar la búsqueda", e)
            }
        }
    }

    // Añade una búsqueda nueva o actualiza una existente
    fun añadirBusqueda(busqueda: Busqueda){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Comprueba si ya existe la búsqueda
                if (roomRepository.estaGuardado(busqueda.query)){
                    // Actualiza el timestamp si ya existe
                    roomRepository.actualizarTimestamp(busqueda.query, busqueda.timestamp)
                } else {
                    // Antes de añadir, comprueba si ya hay 5 búsquedas
                    val historial = roomRepository.getAllQueries()
                    if (historial.size >= 5) {
                        // Encuentra la búsqueda más antigua y la elimina
                        val masAntigua = historial.minByOrNull { it.timestamp }
                        if (masAntigua != null) {
                            roomRepository.eliminarBusqueda(masAntigua.query)
                        }
                    }
                    // Añade la nueva búsqueda
                    roomRepository.addQuery(busqueda)
                }
                actualizarHistorial()
            } catch (e: Exception) {
                Log.e("Database", "Error al guardar la búsqueda", e)
            }
        }
    }
}