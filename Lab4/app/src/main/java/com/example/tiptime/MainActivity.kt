package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiptime.ui.theme.TipTimeTheme
import java.text.NumberFormat

private val Cream     = Color(0xFFF5F0E8)
private val CardWhite = Color(0xFFFFFFFF)
private val Espresso  = Color(0xFF2C1A0E)
private val Terracota = Color(0xFFBF5B30)
private val TextMid   = Color(0xFF7A6A5A)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipTimeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Cream) {
                    TipTimeLayout()
                }
            }
        }
    }
}

@Composable
fun TipTimeLayout() {
    var amountInput by remember { mutableStateOf("") }
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .safeDrawingPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        // Header
        Text("TIP", style = TextStyle(fontSize = 48.sp, fontWeight = FontWeight.Black, color = Espresso, letterSpacing = (-1).sp))
        Text("CALCULATOR", style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Terracota, letterSpacing = 5.sp))

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(CardWhite)
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            EditNumberField(
                value = amountInput,
                onValueChange = { amountInput = it },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Result card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Terracota)
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Column {
                Text("TIP AMOUNT", style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFD8C0), letterSpacing = 3.sp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(tip, style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Black, color = Color.White, letterSpacing = (-1).sp))
                Text("at 15% tip rate", style = TextStyle(fontSize = 11.sp, color = Color(0xFFFFD8C0)))
            }
        }
    }
}

@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = { Text("0.00", style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color(0xFFCCC0B0))) },
        prefix = { Text("$  ", style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Espresso)) },
        label = { Text("Bill Amount", style = TextStyle(color = TextMid)) },
        textStyle = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Espresso),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Terracota,
            unfocusedIndicatorColor = Color(0xFFE0D4C4),
            cursorColor = Terracota
        ),
        modifier = modifier
    )
}

private fun calculateTip(amount: Double, tipPercent: Double = 15.0): String {
    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0E8)
@Composable
fun TipTimeLayoutPreview() {
    TipTimeTheme {
        TipTimeLayout()
    }
}