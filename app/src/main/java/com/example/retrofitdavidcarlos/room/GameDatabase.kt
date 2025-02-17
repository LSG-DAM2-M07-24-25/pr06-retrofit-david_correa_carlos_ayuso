package com.example.retrofitdavidcarlos.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.retrofitdavidcarlos.model.Game

@Database(entities = [Game::class], version = 3)
abstract class GameDatabase : RoomDatabase() {
    abstract fun GameDao(): GameDao
}