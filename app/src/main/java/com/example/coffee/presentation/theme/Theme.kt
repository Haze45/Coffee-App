package com.example.coffee.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = LightBrown,
    onPrimary = Color.White,
    primaryContainer = CoffeeBrown.copy(alpha = 0.3f),
    onPrimaryContainer = LightBrown,
    secondary = CoffeeBrown,
    onSecondary = Color.White,
    background = DeepBlack,
    onBackground = Color.White,
    surface = DarkSurface,
    onSurface = Color.White,
    surfaceVariant = CharcoalGray,
    onSurfaceVariant = TextGray,
    outline = CharcoalGray,
    error = Color(0xFFCF6679)
)

private val LightColorScheme = lightColorScheme(
    primary = LightBrown,
    onPrimary = Color.White,
    primaryContainer = LightBrown.copy(alpha = 0.1f),
    onPrimaryContainer = CoffeeBrown,
    secondary = CoffeeBrown,
    onSecondary = Color.Black,
    background = IvoryWhite,
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    surfaceVariant = LightGray.copy(alpha = 0.5f),
    onSurfaceVariant = DarkTextGray,
    outline = LightGray,
    error = Color(0xFFB00020)
)

@Composable
fun CoffeeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set false to maintain your specific brand colors
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
