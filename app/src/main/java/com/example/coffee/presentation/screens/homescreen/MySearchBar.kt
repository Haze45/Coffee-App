package com.example.coffee.presentation.screens.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.coffee.R

@Composable
fun MySearchBar(
    value: String,
    onValueChange: (String) -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { 
                Text(
                    text = "Search Coffee", 
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                ) 
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.regular_outline_search),
                    contentDescription = "Search",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            shape = RoundedCornerShape(16.dp), // Simplified shape for better theme consistency
            singleLine = true,
            modifier = Modifier.weight(1f).height(56.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .size(56.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.regular_outline_filter),
                contentDescription = "Filter",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
