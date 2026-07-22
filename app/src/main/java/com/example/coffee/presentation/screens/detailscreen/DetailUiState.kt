package com.example.coffee.presentation.screens.detailscreen

import com.example.coffee.domain.model.Product

data class DetailUiState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val selectedSize: String = "M" // Default size
)
