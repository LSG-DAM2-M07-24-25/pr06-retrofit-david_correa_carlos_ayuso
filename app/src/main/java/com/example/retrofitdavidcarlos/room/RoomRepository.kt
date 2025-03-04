package com.example.retrofitdavidcarlos.room

import com.example.retrofitdavidcarlos.model.Estado
import com.example.retrofitdavidcarlos.model.Game

class RoomRepository {
    val daoInterface = GameApplication.database.GameDao()

    suspend fun getFavorites(): MutableList<Game> = daoInterface.getFavorites()
    suspend fun findByName(game: Game) = daoInterface.findByName(game.name).isNotEmpty()
    suspend fun getPendientes(): MutableList<Game> = daoInterface.getPendientes()
    suspend fun getJugando(): MutableList<Game> = daoInterface.getJugando()
    suspend fun getJugados(): MutableList<Game> = daoInterface.getJugados()
    suspend fun addJuego(game: Game) = daoInterface.addJuego(game)
    suspend fun updateFav(game: Game, favorite: Boolean) = daoInterface.updateFavoriteStatus(game.name, favorite)
    suspend fun updateState(game:Game, estado: Estado) = daoInterface.updateState(game.name, estado)
    suspend fun removeGame(game: Game) = daoInterface.removeGame(game)
}