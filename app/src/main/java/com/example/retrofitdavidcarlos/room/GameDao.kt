package com.example.retrofitdavidcarlos.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.retrofitdavidcarlos.model.Estado
import com.example.retrofitdavidcarlos.model.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM games WHERE is_favorite = 1")
    fun getFavorites(): MutableList<Game>

    @Query("SELECT * FROM games WHERE name = :name")
    fun findByName(name: String): MutableList<Game?>

    @Query("SELECT * FROM games WHERE state = 'PENDIENTE'")
    fun getPendientes(): List<Game>

    @Query("SELECT * FROM games WHERE state = 'JUGANDO'")
    fun getJugando(): List<Game>

    @Query("SELECT * FROM games WHERE state = 'JUGADO'")
    fun getJugados(): List<Game>

    @Insert
    fun addJuego(juego: Game)

    @Delete
    fun removeGame(game: Game)

    @Query("UPDATE games SET is_favorite = :isFavorite WHERE name = :name")
    fun updateFavoriteStatus(name: String, isFavorite: Boolean)

    @Query("UPDATE games SET state = :estado WHERE name = :name ")
    fun updateState(name:String, estado: Estado)

    @Query("SELECT is_favorite FROM games WHERE name = :name")
    fun isFavorite(name: String): Boolean

    @Query("SELECT * FROM games")
    fun getAllGames(): List<Game>
}