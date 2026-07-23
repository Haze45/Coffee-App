package com.example.coffee.presentation.screens.detailscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun DetailScreen(
    productId: Int,
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            DetailScreenTopAppBar(
                navController = navController,
                isFavorite = state.product?.isFavorite ?: false,
                onFavoriteClick = { state.product?.let { viewModel.onEvent(DetailEvent.ToggleFavorite(it.id)) } }
            )
        },
        bottomBar = {
            state.product?.let { product ->
                DetailScreenBottomAppBar(
                    price = product.price,
                    onAddToCart = { viewModel.onEvent(DetailEvent.AddToCart(product.id)) }
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            } else if (state.product != null) {
                LazyColumn {
                    item {
                        ProductDetailsContent(
                            product = state.product!!,
                            selectedSize = state.selectedSize,
                            onSizeSelected = { size -> viewModel.onEvent(DetailEvent.SelectSize(size)) }
                        )
                    }
                }
            } else {
                Text(
                    text = "Product not found!",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
