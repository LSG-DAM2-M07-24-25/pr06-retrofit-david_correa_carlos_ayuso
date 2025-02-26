package com.example.retrofitdavidcarlos.model

data class Lista (
    val id: String,
    val name: String,
    val itemCount: Int,
    val isDefault: Boolean = false
)