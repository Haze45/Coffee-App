package com.example.coffee.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coffee_table")
data class CoffeeEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageRes: Int,
    val isFavorite: Boolean = false,
    val isInCart: Boolean = false,
    val quantity: Int = 1
)