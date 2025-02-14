package com.example.retrofitdavidcarlos.room

import com.example.retrofitdavidcarlos.model.Game

class Repository {
    val daoInterface = GameApplication.database.GameDao()

    suspend fun getFavorites(): MutableList<Game> = daoInterface.getFavorites()
    suspend fun findByName(game: Game) = daoInterface.findByName(game.name).isNotEmpty()
    suspend fun getPendientes(): MutableList<Game> = daoInterface.getPendientes()
    suspend fun getJugando(): MutableList<Game> = daoInterface.getJugando()
    suspend fun getJugados(): MutableList<Game> = daoInterface.getJugados()
}