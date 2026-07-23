package com.example.coffee.presentation.screens.cartscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffee.R

@Composable
fun PaymentModeCard(totalAmount: Double) {

    var expanded by remember { mutableStateOf(false) }
    var selectedMode by remember { mutableStateOf("Online") }
    val paymentMode = listOf("Online", "Cash")

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = if (selectedMode == "Online") R.drawable.mobile_banking else R.drawable.wallet),
                        contentDescription = "Payment Method",
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = selectedMode,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        val displayAmount = if (selectedMode == "Online") totalAmount else totalAmount + 1.0
                        Text(
                            text = "$ ${String.format("%.2f", displayAmount)}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Box {
                    Icon(
                        painter = painterResource(id = R.drawable.regular_outline_arrow_down),
                        contentDescription = "Change payment mode",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { expanded = true },
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                    ) {
                        paymentMode.forEach { mode ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = mode,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                onClick = {
                                    selectedMode = mode
                                    expanded = false
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(
                                            if (mode == "Online") R.drawable.mobile_banking else R.drawable.wallet
                                        ),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .background(
                                        color = if (selectedMode == mode) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent
                                    )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Place Order",
                    fontSize = 18.sp
                )
            }
        }
    }
}
