package com.example.lab5.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// 👇 thêm import này
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// ================= COLOR =================
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

// ================= SHAPE =================
val Shapes = Shapes(
    small = RoundedCornerShape(50.dp),   // icon tron
    medium = RoundedCornerShape(16.dp),  // card bo goc
    large = RoundedCornerShape(0.dp)
)

// ================= THEME =================
@Composable
fun Lab5Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colorScheme = when {

        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme)
                dynamicDarkColorScheme(context)
            else
                dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes, // 👈 QUAN TRỌNG
        content = content
    )
}