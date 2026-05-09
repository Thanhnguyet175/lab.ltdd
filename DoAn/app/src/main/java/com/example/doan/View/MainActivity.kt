package com.example.doan.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.doan.AdminApp
import com.example.doan.ViewModel.AuthScreen


private val LightColorScheme = lightColorScheme(
    primary         = AppColors.Brown,
    onPrimary       = Color.White,
    primaryContainer    = AppColors.CreamDark,
    secondary       = AppColors.BrownLight,
    background      = AppColors.Cream,
    surface         = AppColors.White,
    onBackground    = AppColors.TextDark,
    onSurface       = AppColors.TextDark,
)
@Composable
fun MilkTeaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content     = content
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MilkTeaTheme {
                var isLoggedIn by remember { mutableStateOf(true) }

                if (!isLoggedIn) {
                    // Gọi AuthScreen để có đầy đủ các Tab
                    AuthScreen(onLoginSuccess = { isLoggedIn = true })
                } else {
                    AdminApp()
                }
            }
        }
    }
}