package com.example.composetutorial.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Твои цвета из Figma
val DarkBackground = Color(0xFF121212) // Темный фон
val SportOrange = Color(0xFFFF6B00)    // Оранжевый акцент (кнопки)
val LightGrey = Color(0xFFB0B0B0)      // Серый для второстепенного текста

private val DarkColorScheme = darkColorScheme(
    primary = SportOrange,
    secondary = SportOrange,
    background = DarkBackground,
    surface = Color(0xFF1E1E1E), // Чуть светлее фона для "облачка" сообщения
    onBackground = Color.White,
    onSurface = Color.White
)

// Светлую тему тоже немного подкрасим оранжевым
private val LightColorScheme = lightColorScheme(
    primary = SportOrange,
    secondary = SportOrange,
    background = Color.White,
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun ComposeTutorialTheme(
    darkTheme: Boolean = true, // ПРИНУДИТЕЛЬНО ВКЛЮЧАЕМ ТЕМНУЮ ТЕМУ (как в твоем макете)
    dynamicColor: Boolean = false, // ОТКЛЮЧАЕМ динамические цвета, чтобы использовались твои
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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Оставляем стандартную типографику
        content = content
    )
}