package com.example.retrofitdavidcarlos.model

class GameResponse(
    var count: Int,
    var next: String?,
    var previous: String?,
    var results: ArrayList<Game>
)