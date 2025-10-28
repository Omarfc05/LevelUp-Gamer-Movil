package com.example.levelupgamer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 🎨 Paleta de colores gamer
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1E90FF),    // Azul Eléctrico
    secondary = Color(0xFF39FF14),  // Verde Neón
    background = Color(0xFF000000), // Fondo negro
    surface = Color(0xFF121212),    // Superficie oscura
    onPrimary = Color.White,        // Texto sobre botones azules
    onSecondary = Color.Black,      // Texto sobre botones verdes
    onBackground = Color.White,     // Texto principal
    onSurface = Color(0xFFD3D3D3)   // Texto secundario gris claro
)

@Composable
fun LevelUpGamerTheme(
    darkTheme: Boolean = true, // Siempre modo oscuro
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
