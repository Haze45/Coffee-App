package com.example.coffee.presentation.screens.cartscreen

import com.example.coffee.domain.model.Product

data class CartUiState(
    val cartItems: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val deliveryFee: Double = 1.0
) {
    val totalPrice: Double
        get() = cartItems.sumOf { it.price * it.quantity }

    val totalAmount: Double
        get() = totalPrice + deliveryFee
}
