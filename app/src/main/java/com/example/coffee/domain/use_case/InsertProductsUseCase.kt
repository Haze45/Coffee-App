package com.example.coffee.domain.use_case

import com.example.coffee.domain.model.Product
import com.example.coffee.domain.repository.CoffeeRepository
import javax.inject.Inject

class InsertProductsUseCase @Inject constructor(
    private val repository: CoffeeRepository
) {
    suspend operator fun invoke(products: List<Product>) {
        repository.insertProducts(products)
    }
}
