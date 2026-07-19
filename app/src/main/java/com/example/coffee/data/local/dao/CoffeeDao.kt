package com.example.coffee.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coffee.data.local.entity.CoffeeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeeDao {
    @Query("SELECT * FROM coffee_table")
    fun getAllProducts(): Flow<List<CoffeeEntity>>

    @Query("SELECT * FROM coffee_table WHERE id = :id")
    fun getProductById(id: Int): Flow<CoffeeEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<CoffeeEntity>)

    @Query("UPDATE coffee_table SET isFavorite = NOT isFavorite WHERE id = :id")
    suspend fun toggleFavorite(id: Int)

    @Query("SELECT * FROM coffee_table WHERE isInCart = 1")
    fun getCartItems(): Flow<List<CoffeeEntity>>

    @Query("UPDATE coffee_table SET isInCart = :isInCart WHERE id = :id")
    suspend fun updateCartStatus(id: Int, isInCart: Boolean)

    @Query("UPDATE coffee_table SET quantity = :quantity WHERE id = :id")
    suspend fun updateQuantity(id: Int, quantity: Int)

    @Query("SELECT * FROM coffee_table WHERE isFavorite = 1")
    fun getFavouriteProducts(): Flow<List<CoffeeEntity>>
}