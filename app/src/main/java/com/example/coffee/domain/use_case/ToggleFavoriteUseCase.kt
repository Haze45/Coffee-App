package com.example.coffee.domain.use_case

import com.example.coffee.domain.repository.CoffeeRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: CoffeeRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.toggleFavorite(id)
    }
}
