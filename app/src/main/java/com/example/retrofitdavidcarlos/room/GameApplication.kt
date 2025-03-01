package com.example.retrofitdavidcarlos.room

import android.app.Application
import androidx.room.Room

/**
 * Aquesta classe està pensada per tal de que només se'n pugui instanciar
 * un únic objecte en tot el present projecte, comportant-se com una classe estàtica
 * Això s'anomena patró Singleton (que vol dir solitari o únic) i fa referència a
 * que només se'n crea un objecte. Aquest objecte únic és global i accessible areu del programa.
 * És útil per a gestionar recursos compartits com per exemple una connexió a BD, una API, etc.
 *
 * S'executarà només començar l'aplicació ja que està dins de AndroidManifest.xml i crearà la BD
 */
class GameApplication : Application() {

    // Creem un atribut estàtic de la classe
    companion object {
        lateinit var database: GameDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        // Aquí determinem el nom de la base de dades. En el nostre cas "PokemonDatabase"
        database = Room.databaseBuilder(
            applicationContext,
            GameDatabase::class.java,
            "game_database"
        ).fallbackToDestructiveMigration().build()
    }
}