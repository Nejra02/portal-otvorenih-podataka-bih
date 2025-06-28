package com.example.projekatzavrsni.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Blue,
    onPrimary = White,
    background = DirtyWhite,
    onBackground = DarkGrey,
    surface = White,
    onSurface = DarkGrey,
    secondary = Grey,
    onSecondary = DarkGrey
)

private val DarkColorScheme = darkColorScheme(
    primary = Blue,
    onPrimary = White,
    background = DarkGrey,
    onBackground = White,
    surface = Grey,
    onSurface = White,
    secondary = Color.Gray,
    onSecondary = White
)

@Composable
fun ProjekatZavrsniTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes(),
        content = content
    )
}
