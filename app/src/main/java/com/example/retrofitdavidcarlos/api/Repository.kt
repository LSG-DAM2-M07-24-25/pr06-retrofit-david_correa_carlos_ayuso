package com.example.retrofitdavidcarlos.api

class Repository {
    val apiInterface = GameApiService.create()
    val token = "244ec649ea554979b3cfb39c90ee1100"
    suspend fun getAllGames() = apiInterface.getAllGames(token)
}