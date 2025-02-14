package com.example.retrofitdavidcarlos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class Game(
    val added: Int,
    val background_image: String,
    val clip: Any,
    val dominant_color: String,
    @PrimaryKey val id: Int,
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
    val user_game: Any,
    var is_favorite: Boolean,
    var state: Estado
)