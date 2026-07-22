package com.example.coffee.presentation.screens.detailscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffee.domain.use_case.CoffeeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val coffeeUseCases: CoffeeUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state: StateFlow<DetailUiState> = _state.asStateFlow()

    init {
        savedStateHandle.get<Int>("productId")?.let { productId ->
            getProduct(productId)
        }
    }

    private fun getProduct(id: Int) {
        _state.update { it.copy(isLoading = true) }
        coffeeUseCases.getProductById(id).onEach { product ->
            _state.update { it.copy(product = product, isLoading = false) }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.ToggleFavorite -> {
                viewModelScope.launch {
                    coffeeUseCases.toggleFavorite(event.id)
                }
            }
            is DetailEvent.SelectSize -> {
                _state.update { it.copy(selectedSize = event.size) }
            }
            is DetailEvent.AddToCart -> {
                viewModelScope.launch {
                    coffeeUseCases.updateCartStatus(event.id, true)
                }
            }
        }
    }
}

sealed class DetailEvent {
    data class ToggleFavorite(val id: Int) : DetailEvent()
    data class SelectSize(val size: String) : DetailEvent()
    data class AddToCart(val id: Int) : DetailEvent()
}
