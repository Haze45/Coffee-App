package com.example.coffee.presentation.screens.favouriteScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class FavouriteViewModel @Inject constructor(
    private val coffeeUseCases: CoffeeUseCases
) : ViewModel() {

    private val _favouriteProducts = MutableStateFlow<List<Product>>(emptyList())
    private val _isLoading = MutableStateFlow(false)

    val state: StateFlow<FavouriteUiState> = combine(
        _favouriteProducts,
        _isLoading
    ) { products, loading ->
        FavouriteUiState(
            favouriteProducts = products,
            isLoading = loading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FavouriteUiState()
    )

    init {
        getFavouriteProducts()
    }

    private fun getFavouriteProducts() {
        _isLoading.value = true
        coffeeUseCases.getFavouriteProducts()
            .onEach { products ->
                _favouriteProducts.value = products
                _isLoading.value = false
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: FavouriteEvent) {
        when (event) {
            is FavouriteEvent.ToggleFavorite -> {
                viewModelScope.launch {
                    coffeeUseCases.toggleFavorite(event.productId)
                }
            }

            is FavouriteEvent.AddToCart -> {
                viewModelScope.launch {
                    coffeeUseCases.updateCartStatus(event.productId, true)
                }
            }
        }
    }
}

sealed class FavouriteEvent {
    data class ToggleFavorite(val productId: Int) : FavouriteEvent()
    data class AddToCart(val productId: Int) : FavouriteEvent()
}
