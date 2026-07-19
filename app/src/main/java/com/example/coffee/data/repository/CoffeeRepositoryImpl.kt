package com.example.coffee.data.repository

import com.example.coffee.data.local.dao.CoffeeDao
import com.example.coffee.data.mapper.toCoffeeEntity
import com.example.coffee.data.mapper.toProduct
import com.example.coffee.domain.model.Product
import com.example.coffee.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoffeeRepositoryImpl @Inject constructor(
    private val dao: CoffeeDao
) : CoffeeRepository {

    override fun getProducts(): Flow<List<Product>> {
        return dao.getAllProducts().map { entities ->
            entities.map { it.toProduct() }
        }
    }

    override fun getProductById(id: Int): Flow<Product?> {
        return dao.getProductById(id).map { it?.toProduct() }
    }

    override suspend fun toggleFavorite(id: Int) {
        dao.toggleFavorite(id)
    }

    override suspend fun insertProducts(products: List<Product>) {
        dao.insertProducts(products.map { it.toCoffeeEntity() })
    }

    override fun getCartItems(): Flow<List<Product>> {
        return dao.getCartItems().map { entities ->
            entities.map { it.toProduct() }
        }
    }

    override suspend fun updateCartStatus(id: Int, isInCart: Boolean) {
        dao.updateCartStatus(id, isInCart)
    }

    override suspend fun updateQuantity(id: Int, quantity: Int) {
        dao.updateQuantity(id, quantity)
    }

    override fun getFavouriteProducts(): Flow<List<Product>> {
        return dao.getFavouriteProducts().map { entities ->
            entities.map { it.toProduct() }
        }
    }
}
