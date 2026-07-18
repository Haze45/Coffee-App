package com.example.coffee.domain.model

data class Product(
    val id: Int,
    val name : String,
    val description : String,
    val price : Double,
    val imageRes : Int,
    val isFavorite: Boolean = false,
    val quantity: Int = 1
)
