package com.example.coffee.presentation.screens.cartscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.coffee.domain.model.Product
import com.example.coffee.presentation.theme.LightBrown
import com.example.coffee.presentation.theme.LightGray
import java.util.Locale


@Composable
fun CartItemCard(
    product: Product,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    // Calculate total price for this item based on quantity
    val itemTotalPrice = product.price * product.quantity

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightGray
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.DarkGray
                    )
                )

                // Display dynamic price
                Text(
                    text = "$ ${String.format(Locale.US, "%.2f", itemTotalPrice)}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = LightBrown,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                IconButton(
                    onClick = onDecrease,
                    modifier = Modifier
                        .background(
                            color = if (product.quantity > 1) LightBrown.copy(alpha = 0.23f) else Color.Red.copy(alpha = 0.1f),
                            shape = CircleShape
                        )
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = if (product.quantity > 1) Icons.Default.Remove else Icons.Default.Delete,
                        contentDescription = if (product.quantity > 1) "Decrease" else "Remove",
                        tint = if (product.quantity > 1) LightBrown else Color.Red,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = product.quantity.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                IconButton(
                    onClick = onIncrease,
                    modifier = Modifier
                        .background(
                            color = LightBrown.copy(alpha = 0.23f),
                            shape = CircleShape
                        )
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase",
                        tint = LightBrown,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
