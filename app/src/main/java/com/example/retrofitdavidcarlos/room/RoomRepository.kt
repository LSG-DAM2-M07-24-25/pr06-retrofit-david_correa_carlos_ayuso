package com.example.retrofitdavidcarlos.room

import com.example.retrofitdavidcarlos.model.Busqueda
import com.example.retrofitdavidcarlos.model.Estado
import com.example.retrofitdavidcarlos.model.Game

class RoomRepository {
    private val gameDaoInterface = GameApplication.database.GameDao()
    private val historialDaoInterface = GameApplication.database.HistorialDao()

    suspend fun getFavorites(): MutableList<Game> = gameDaoInterface.getFavorites()
    suspend fun findByName(game: Game) = gameDaoInterface.findByName(game.name).isNotEmpty()
    suspend fun getPendientes(): List<Game> = gameDaoInterface.getPendientes()
    suspend fun getJugando(): List<Game> = gameDaoInterface.getJugando()
    suspend fun getJugados(): List<Game> = gameDaoInterface.getJugados()
    suspend fun addJuego(game: Game) = gameDaoInterface.addJuego(game)
    suspend fun updateFav(game: Game, favorite: Boolean) = gameDaoInterface.updateFavoriteStatus(game.name, favorite)
    suspend fun updateState(game:Game, estado: Estado) = gameDaoInterface.updateState(game.name, estado)
    suspend fun removeGame(game: Game) = gameDaoInterface.removeGame(game)
    suspend fun isFavorite(game: Game): Boolean = gameDaoInterface.isFavorite(game.name)
    suspend fun getAllGames(): List<Game> = gameDaoInterface.getAllGames()
    suspend fun getAllQueries() = historialDaoInterface.getAllQueries()

    suspend fun borrarHistorial() = historialDaoInterface.borrarHistorial()
    suspend fun addQuery(busqueda: Busqueda) = historialDaoInterface.addQuery(busqueda)
    suspend fun estaGuardado(busqueda: String): Boolean = historialDaoInterface.findByQuery(busqueda).isNotEmpty()

    suspend fun eliminarBusqueda(query: String) = historialDaoInterface.deleteQuery(query)
    suspend fun actualizarTimestamp(query: String, timestamp: Long) = historialDaoInterface.updateTimestamp(query, timestamp)
}