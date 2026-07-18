package com.example.coffee.domain.use_case

import com.example.coffee.domain.repository.CoffeeRepository
import javax.inject.Inject

class UpdateQuantityUseCase @Inject constructor(
    private val repository: CoffeeRepository
) {
    suspend operator fun invoke(id: Int, quantity: Int) {
        repository.updateQuantity(id, quantity)
    }
}
