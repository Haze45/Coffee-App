package com.example.coffee.presentation.screens.cartscreen

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
class CartViewModel @Inject constructor(
    private val coffeeUseCases: CoffeeUseCases
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    private val _isLoading = MutableStateFlow(false)

    val state: StateFlow<CartUiState> = combine(
        _cartItems,
        _isLoading
    ) { items, loading ->
        CartUiState(
            cartItems = items,
            isLoading = loading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CartUiState()
    )

    init {
        getCartItems()
    }

    private fun getCartItems() {
        _isLoading.value = true
        coffeeUseCases.getCartItems().onEach { items ->
            _cartItems.value = items
            _isLoading.value = false
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: CartEvent) {
        when (event) {
            is CartEvent.RemoveFromCart -> {
                viewModelScope.launch {
                    coffeeUseCases.updateCartStatus(event.productId, false)
                }
            }

            is CartEvent.UpdateQuantity -> {
                viewModelScope.launch {
                    coffeeUseCases.updateQuantity(event.productId, event.newQuantity)
                }
            }
        }
    }
}

sealed class CartEvent {
    data class RemoveFromCart(val productId: Int) : CartEvent()
    data class UpdateQuantity(val productId: Int, val newQuantity: Int) : CartEvent()
}
