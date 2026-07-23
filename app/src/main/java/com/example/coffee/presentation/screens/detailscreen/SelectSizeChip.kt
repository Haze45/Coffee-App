package com.example.coffee.presentation.screens.detailscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelectSizeChip(
    sizeText: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(46.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = if (selected) MaterialTheme.colorScheme.primaryContainer 
                        else MaterialTheme.colorScheme.surface
            )
            .border(
                width = 1.dp,
                color = if (selected) MaterialTheme.colorScheme.primary 
                        else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = sizeText,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) MaterialTheme.colorScheme.primary 
                    else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
