package com.example.coffee.domain.use_case

import com.example.coffee.domain.model.Product
import com.example.coffee.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val repository: CoffeeRepository
) {
    operator fun invoke(): Flow<List<Product>> {
        return repository.getCartItems()
    }
}
