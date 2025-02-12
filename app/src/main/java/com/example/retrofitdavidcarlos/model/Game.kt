package com.example.retrofitdavidcarlos.model

import java.util.Date

data class Game(
    var id: Int,
    var slug: String,
    var name: String,
    var released: Date,
    var tba: Boolean,
    var background_image: String,
    var rating: Number,
    var rating_top: Int,
    var ratings: Any,
    var ratings_count: Int,
    var reviews_text_count: String,
    var added: Int,
    var added_by_status: Any,
    var metacritic: Int,
    var playtime: Int,
    var suggestions_count: Int,
    var updated: Date,
    var esrb_rating: Any?,
    var platforms: ArrayList<Any> //TODO: ArrayList<Platform>
) {


}