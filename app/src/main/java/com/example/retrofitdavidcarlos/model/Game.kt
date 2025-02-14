package com.example.retrofitdavidcarlos.model

data class Game(
    val added: Int,
    val background_image: String,
    val clip: Any,
    val dominant_color: String,
    val id: Int,
    val metacritic: Int,
    val name: String,
    val playtime: Int,
    val rating: Double,
    val rating_top: Int,
    val ratings_count: Int,
    val released: String,
    val reviews_count: Int,
    val reviews_text_count: Int,
    val saturated_color: String,
    val slug: String,
    val suggestions_count: Int,
    val tba: Boolean,
    val updated: String,
    val user_game: Any
)