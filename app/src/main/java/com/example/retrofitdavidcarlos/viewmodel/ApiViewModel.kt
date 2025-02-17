package com.example.retrofitdavidcarlos.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitdavidcarlos.api.Repository
import com.example.retrofitdavidcarlos.model.Estado
import com.example.retrofitdavidcarlos.model.Game
import com.example.retrofitdavidcarlos.model.GameResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ApiViewModel : ViewModel() {

    private val repository = Repository()
    private val roomRepository = com.example.retrofitdavidcarlos.room.RoomRepository()
    private val _loading = MutableLiveData(true)
    val loading = _loading
    private val _games = MutableLiveData<GameResponse>()
    val games = _games

    fun getGames() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAllGames()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _games.value = response.body()
                    _loading.value = false
                } else {
                    Log.e("Error :", response.message())
                }
            }
        }
    }
<<<<<<< Updated upstream
=======

    fun addFavorito(game: Game) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!roomRepository.findByName(game)) {
                    game.is_favorite = true
                    game.state = Estado.PENDIENTE
                    roomRepository.addJuego(game)
                }
                if (!game.is_favorite){
                    roomRepository.updateFav(game, true)
                }else{
                    roomRepository.updateFav(game, false)

                }
                getGames()
            } catch (e: Exception) {
                Log.e("Database", "Error al guardar el juego: ${e.message}")
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
                }
            } catch (e: Exception) {
                Log.e("Database", "Error al guardar el juego: ${e.message}")
            }
        }
    }
>>>>>>> Stashed changes
}