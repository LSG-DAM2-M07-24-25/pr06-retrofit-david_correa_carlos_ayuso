package com.example.retrofitdavidcarlos.api

class Repository {
    val apiInterface = GameApiService.create()

    suspend fun getAllGames() = apiInterface.getAllGames()
}