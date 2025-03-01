package com.example.retrofitdavidcarlos.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.retrofitdavidcarlos.model.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM games WHERE is_favorite = 1")
    fun getFavorites(): MutableList<Game>

    @Query("SELECT * FROM games WHERE name = :name")
    fun findByName(name: String): MutableList<Game?>

    @Query("SELECT * FROM games WHERE state = 'PENDIENTE'")
    fun getPendientes(): MutableList<Game>

    @Query("SELECT * FROM games WHERE state = 'JUGANDO'")
    fun getJugando(): MutableList<Game>

    @Query("SELECT * FROM games WHERE state = 'JUGADO'")
    fun getJugados(): MutableList<Game>

    @Insert
    fun addJuego(juego: Game)

    @Delete
    fun removeFavorite(favoriteGames: Game)

    @Query("UPDATE games SET is_favorite = :isFavorite WHERE name = :name")
    fun updateFavoriteStatus(name: String, isFavorite: Boolean)
}