package com.example.coffee.presentation.screens.cartscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.coffee.domain.model.Product
import java.util.Locale

@Composable
fun CartItemCard(
    product: Product,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    val itemTotalPrice = product.price * product.quantity

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(16.dp)
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
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(75.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Text(
                    text = String.format(Locale.US, "$ %.2f", itemTotalPrice),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .background(
                            color = if (product.quantity > 1)
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                            else Color.Red.copy(alpha = 0.1f),
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .clickable { onDecrease() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (product.quantity > 1) Icons.Default.Remove else Icons.Default.Delete,
                        contentDescription = "Decrease",
                        tint = if (product.quantity > 1) MaterialTheme.colorScheme.primary else Color.Red,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    text = product.quantity.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .clickable { onIncrease() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
