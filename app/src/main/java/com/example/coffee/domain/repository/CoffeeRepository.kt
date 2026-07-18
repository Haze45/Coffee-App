package com.example.coffee.domain.repository

import com.example.coffee.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CoffeeRepository {
    fun getProducts(): Flow<List<Product>>
    fun getProductById(id: Int): Flow<Product?>
    suspend fun toggleFavorite(id: Int)
    suspend fun insertProducts(products: List<Product>)
    fun getCartItems(): Flow<List<Product>>
    suspend fun updateCartStatus(id: Int, isInCart: Boolean)
    suspend fun updateQuantity(id: Int, quantity: Int)
}
