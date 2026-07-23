package com.example.coffee.presentation.screens.cartscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.coffee.presentation.ui_components.MyBottomNavBar
import java.util.Locale

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { CartScreenTopAppBar(navController) },
        bottomBar = { MyBottomNavBar(navController = navController, "Cart") },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Order",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    if (state.cartItems.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 64.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Your cart is empty",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    } else {
                        items(state.cartItems, key = { it.id }) { product ->
                            CartItemCard(
                                product = product,
                                onIncrease = {
                                    viewModel.onEvent(CartEvent.UpdateQuantity(product.id, product.quantity + 1))
                                },
                                onDecrease = {
                                    if (product.quantity > 1) {
                                        viewModel.onEvent(CartEvent.UpdateQuantity(product.id, product.quantity - 1))
                                    } else {
                                        viewModel.onEvent(CartEvent.RemoveFromCart(product.id))
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Payment Summary",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Price",
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "$ ${String.format(Locale.US, "%.2f", state.totalPrice)}",
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Delivery Fee",
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "$ ${String.format(Locale.US, "%.2f", state.deliveryFee)}",
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        PaymentModeCard(state.totalAmount)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
