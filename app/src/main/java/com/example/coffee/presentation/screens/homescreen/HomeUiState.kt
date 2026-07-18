package com.example.coffee.presentation.screens.homescreen

import com.example.coffee.domain.model.Product

data class HomeUiState(
    val products: List<Product> = emptyList(),
    val searchText: String = "",
    val selectedCategory: String = "All Coffee",
    val isLoading: Boolean = false,
    val location: String = "Bokaro Steel City, Jharkhand"
)
