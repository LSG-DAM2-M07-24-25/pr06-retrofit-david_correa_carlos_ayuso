package com.example.retrofitdavidcarlos.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.retrofitdavidcarlos.model.Busqueda
import com.example.retrofitdavidcarlos.model.Game

@Database(entities = [Game::class, Busqueda::class], version = 5)
abstract class GameDatabase : RoomDatabase() {
    abstract fun GameDao(): GameDao
    abstract fun HistorialDao(): HistorialDao
}