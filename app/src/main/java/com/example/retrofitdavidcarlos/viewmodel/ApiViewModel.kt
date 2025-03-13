package com.example.retrofitdavidcarlos.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitdavidcarlos.api.Repository
import com.example.retrofitdavidcarlos.model.Estado
import com.example.retrofitdavidcarlos.model.Game
import com.example.retrofitdavidcarlos.room.RoomRepository
import com.example.retrofitdavidcarlos.model.GameResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ApiViewModel : ViewModel() {

    private val repository = Repository()
    private val _roomRepository = RoomRepository()
    val roomRepository = _roomRepository
    private val _loading = MutableLiveData(true)
    val loading = _loading
    private val _games = MutableLiveData<GameResponse>()
    val games = _games
    private val _listaFavoritos = MutableLiveData<List<Game>>()
    val listaFavoritos: LiveData<List<Game>> = _listaFavoritos
    private val _favoritos = MutableLiveData<Set<Int>>(setOf())
    val favoritos = _favoritos

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
    fun getFavorios(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = roomRepository.getFavorites()
            withContext(Dispatchers.Main){
                _listaFavoritos.value = response
            }
        }
    }

    fun addFavorito(game: Game) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                game.is_favorite = !game.is_favorite

                if (!roomRepository.findByName(game)) {
                    game.state = Estado.SIN_ESTADO
                    roomRepository.addJuego(game)
                } else {
                    roomRepository.updateFav(game, game.is_favorite)
                }

                withContext(Dispatchers.Main) {
                    games.value?.let { currentGames ->
                        val updatedResults = currentGames.results.map {
                            if (it.id == game.id) {
                                it.copy(is_favorite = game.is_favorite)
                            } else {
                                it
                            }
                        }
                        games.value = GameResponse(updatedResults)
                    }
                    getFavorios()
                }
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
                    game.state = Estado.SIN_ESTADO
                    roomRepository.addJuego(game)
                }
            } catch (e: Exception) {
                Log.e("Database", "Error al guardar el juego: ${e.message}")
            }
        }
    }

}