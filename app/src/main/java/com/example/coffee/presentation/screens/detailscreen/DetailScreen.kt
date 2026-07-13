package com.example.coffee.presentation.screens.detailscreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.coffee.R
import com.example.coffee.domain.model.Product

@Composable
fun DetailScreen(productId: Int, navController: NavController) {
    val product = listOf(
        Product(
            id = 1,
            name = "Espresso",
            description = "Strong",
            100.0,
            imageRes = R.drawable.coffee_1
        ),
        Product(
            id = 2,
            name = "Cappuccino",
            description = "Medium",
            10.0,
            imageRes = R.drawable.coffee_2
        ),
        Product(
            id = 3,
            name = "Latte",
            description = "Medium",
            80.0,
            imageRes = R.drawable.coffee_3
        ),
        Product(
            id = 4,
            name = "Macchiato",
            description = "Medium",
            70.0,
            imageRes = R.drawable.coffee_4
        ),
        Product(
            id = 5,
            name = "Mocha",
            description = "Medium",
            90.0,
            imageRes = R.drawable.coffee_5
        ),
        Product(
            id = 6,
            name = "Americano",
            description = "Medium",
            40.0,
            imageRes = R.drawable.coffee_6
        )
    )

    val selectedProduct = product.find { it.id == productId }

    if (selectedProduct == null) {
        Text(text = "Product not found!", color = Color.Red)
        return
    }

    Scaffold(
        topBar = { DetailScreenTopAppBar(navController) },
        bottomBar = { DetailScreenBottomAppBar() }
    ) { innerPadding ->
        LazyColumn() {
            item {
                ProductDetailsContent(selectedProduct, innerPadding)
            }
        }

    }
}