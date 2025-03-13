package com.example.retrofitdavidcarlos.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.retrofitdavidcarlos.model.Busqueda

@Dao
interface HistorialDao {

    @Query("SELECT * FROM historial")
    fun getAllQueries(): List<Busqueda>

    @Insert
    fun addQuery(busqueda: Busqueda)

    @Query("DELETE FROM historial")
    fun borrarHistorial()

    @Query("SELECT `query` FROM historial WHERE `query` = :busqueda ")
    fun findByQuery(busqueda: String): List<String>

}
