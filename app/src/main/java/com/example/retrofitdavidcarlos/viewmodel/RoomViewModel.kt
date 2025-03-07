package com.example.retrofitdavidcarlos.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import com.example.retrofitdavidcarlos.model.Estado
import com.example.retrofitdavidcarlos.model.Game
import com.example.retrofitdavidcarlos.model.GameResponse
import com.example.retrofitdavidcarlos.room.RoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomViewModel : ViewModel() {

    private val _roomRepository = RoomRepository()
    val roomRepository = _roomRepository
    private val _listaFavoritos = MutableLiveData<List<Game>>()
    val listaFavoritos: LiveData<List<Game>> = _listaFavoritos
    private val _favoritos = MutableLiveData<Set<Int>>(setOf())
    private val _games = MutableLiveData<GameResponse>()
    val games = _games
    private val _juegosGuardados = MutableLiveData<Set<String>>(setOf())
    val juegosGuardados: LiveData<Set<String>> = _juegosGuardados
    
    private val _juegosFavoritos = MutableLiveData<Set<String>>(setOf())
    val juegosFavoritos: LiveData<Set<String>> = _juegosFavoritos

    fun getFavorios() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = roomRepository.getFavorites()
            withContext(Dispatchers.Main) {
                _listaFavoritos.value = response
            }
        }
    }
    fun esFavorito(game: Game): LiveData<Boolean> {
        val resultado = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            val favorito = roomRepository.isFavorite(game)
            withContext(Dispatchers.Main) {
                resultado.value = favorito
            }
        }
        return resultado
    }

    fun actualizarJuegosGuardados() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val juegos = roomRepository.getAllGames()
                val favoritos = roomRepository.getFavorites()
                withContext(Dispatchers.Main) {
                    _juegosGuardados.value = juegos.map { it.name }.toSet()
                    _juegosFavoritos.value = favoritos.map { it.name }.toSet()
                }
            } catch (e: Exception) {
                Log.e("RoomViewModel", "Error al actualizar juegos", e)
            }
        }
    }

    fun addFavorito(game: Game) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val nuevoEstado = !game.is_favorite
                game.is_favorite = nuevoEstado
                if (!roomRepository.findByName(game)) {
                    game.state = Estado.PENDIENTE
                    roomRepository.addJuego(game)
                } else {
                    roomRepository.updateFav(game, nuevoEstado)
                }
                actualizarJuegosGuardados()
            } catch (e: Exception) {
                Log.e("Database", "Error al actualizar favorito: ${e.message}")
            }
        }
    }

    fun guardarJuego(game: Game) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!roomRepository.findByName(game)) {
                    game.is_favorite = false
                    game.state = Estado.PENDIENTE
                    roomRepository.addJuego(game)
                    actualizarJuegosGuardados()
                }
            } catch (e: Exception) {
                Log.e("Database", "Error al guardar el juego: ${e.message}")
            }
        }
    }
    

    fun updateEstado(game: Game, estado: Estado) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
            roomRepository.updateState(game, estado)

            }catch (e: Exception){
                Log.e("Database", "Error al cambiar el Estado del juego: ${e.message}")
            }
        }
    }

    fun eliminarJuego(game: Game){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                roomRepository.removeGame(game)
                actualizarJuegosGuardados()
            }catch (e: Exception){
                Log.e("Database", "Error al eliminar el juego: ${e.message}")
            }
        }
    }

}