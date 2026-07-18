package com.example.coffee.domain.use_case

import com.example.coffee.domain.model.Product
import com.example.coffee.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: CoffeeRepository
) {
    operator fun invoke(id: Int): Flow<Product?> {
        return repository.getProductById(id)
    }
}
