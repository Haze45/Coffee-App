package com.example.coffee.presentation.screens.favouriteScreen

import com.example.coffee.domain.model.Product

data class FavouriteUiState(
    val favouriteProducts: List<Product> = emptyList(),
    val isLoading: Boolean = false
)
