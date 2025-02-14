package com.example.retrofitdavidcarlos.api

import com.example.retrofitdavidcarlos.model.GameResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Query
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface GameApiService {
    @GET("games")
    suspend fun getAllGames(
        @Query("key") apiKey: String = "244ec649ea554979b3cfb39c90ee1100"
    ): Response<GameResponse>

    companion object {
        private const val BASE_URL = "https://api.rawg.io/api/"

        fun create(): GameApiService {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(GameApiService::class.java)
        }
    }
}