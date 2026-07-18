package com.example.coffee.presentation.screens.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffee.R
import com.example.coffee.domain.model.Product
import com.example.coffee.domain.use_case.CoffeeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coffeeUseCases: CoffeeUseCases
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow("All Coffee")
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    private val _isLoading = MutableStateFlow(false)

    val state: StateFlow<HomeUiState> = combine(
        _searchText,
        _selectedCategory,
        _products,
        _isLoading
    ) { searchText, selectedCategory, products, isLoading ->

        val query = searchText.lowercase().trim()

        val filtered = products.filter { product ->
            val matchesCategory = selectedCategory == "All Coffee" ||
                    product.name.contains(selectedCategory, ignoreCase = true)

            val matchesSearch = query.isEmpty() ||
                    product.name.lowercase().contains(query) ||
                    product.description.lowercase().contains(query)

            matchesCategory && matchesSearch
        }

        HomeUiState(
            products = filtered,
            searchText = searchText,
            selectedCategory = selectedCategory,
            isLoading = isLoading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )

    init {
        getProducts()
    }

    private fun getProducts() {
        _isLoading.value = true
        coffeeUseCases.getProducts().onEach { products ->
            if (products.isNotEmpty()) {
                _products.value = products
                _isLoading.value = false
            } else {
                seedDatabase()
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnSearchTextChange -> _searchText.value = event.searchText
            is HomeEvent.OnCategorySelected -> _selectedCategory.value = event.category
            is HomeEvent.ToggleFavorite -> {
                viewModelScope.launch {
                    coffeeUseCases.toggleFavorite(event.id)
                }
            }

            is HomeEvent.AddToCart -> {
                viewModelScope.launch {
                    // Update the product's isInCart status in the local database
                    coffeeUseCases.updateCartStatus(event.id, true)
                }
            }
        }
    }

    private fun seedDatabase() {
        viewModelScope.launch {
            val dummyProducts = listOf(
                Product(
                    id = 1,
                    name = "Espresso",
                    description = "Strong",
                    price = 100.0,
                    imageRes = R.drawable.coffee_1
                ),
                Product(
                    id = 2,
                    name = "Cappuccino",
                    description = "Medium",
                    price = 10.0,
                    imageRes = R.drawable.coffee_2
                ),
                Product(
                    id = 3,
                    name = "Latte",
                    description = "Medium",
                    price = 80.0,
                    imageRes = R.drawable.coffee_3
                ),
                Product(
                    id = 4,
                    name = "Macchiato",
                    description = "Medium",
                    price = 70.0,
                    imageRes = R.drawable.coffee_4
                ),
                Product(
                    id = 5,
                    name = "Mocha",
                    description = "Medium",
                    price = 90.0,
                    imageRes = R.drawable.coffee_5
                ),
                Product(
                    id = 6,
                    name = "Americano",
                    description = "Medium",
                    price = 40.0,
                    imageRes = R.drawable.coffee_6
                )
            )
            coffeeUseCases.insertProducts(dummyProducts)
        }
    }
}

sealed class HomeEvent {
    data class ToggleFavorite(val id: Int) : HomeEvent()
    data class OnSearchTextChange(val searchText: String) : HomeEvent()
    data class OnCategorySelected(val category: String) : HomeEvent()
    data class AddToCart(val id: Int) : HomeEvent()
}
