package com.example.retrofitdavidcarlos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historial")
data class Busqueda(
    @PrimaryKey
    val query: String,
    val timestamp: Long
)