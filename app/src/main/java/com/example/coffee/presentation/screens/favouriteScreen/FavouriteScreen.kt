package com.example.coffee.presentation.screens.favouriteScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coffee.R
import com.example.coffee.domain.model.Product
import com.example.coffee.presentation.ui_components.MyBottomNavBar

@Composable
fun FavouriteScreen(navController: NavController) {
    var favouriteProduct by remember {
        mutableStateOf(
            listOf(
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
                )
            )
        )
    }

    Scaffold(
        topBar = { FavouriteScreenTopAppBar() },
        bottomBar = { MyBottomNavBar(navController = navController, "Favourite") }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            item {
                favouriteProduct.forEach { product ->
                    FavouriteItemCart(
                        product,
                        onRemove = { favouriteProduct = favouriteProduct - product }
                    )
                }
            }

        }
    }


}