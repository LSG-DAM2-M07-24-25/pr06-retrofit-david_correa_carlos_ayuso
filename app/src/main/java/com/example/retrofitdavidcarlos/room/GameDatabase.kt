package com.example.retrofitdavidcarlos.room

import androidx.room.RoomDatabase

abstract class GameDatabase: RoomDatabase() {
    abstract fun GameDao(): GameDao
}