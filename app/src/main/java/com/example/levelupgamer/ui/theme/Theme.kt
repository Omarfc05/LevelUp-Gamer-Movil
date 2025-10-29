package com.example.levelupgamer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.levelupgamer.R

private val DarkColorScheme = darkColorScheme(
    primary = ElectricBlue,
    secondary = NeonGreen,
    background = BlackBackground,
    surface = BlackBackground,
    onPrimary = WhiteText,
    onBackground = WhiteText,
    onSurface = WhiteText
)

@Composable
fun LevelUpGamerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
