package com.example.birthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.birthday.ui.theme.BirthdayTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Surface


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BirthdayTheme {
                BusinessCard()
            }
        }
    }
    }

@Composable
fun BusinessCard() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFF9C4) 
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // Day len de can giua
            Spacer(modifier = Modifier.weight(1f))

            // Noi dung o giua
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.android_logo),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 16.dp)
                )

                Text(
                    text = "Trần Thị Thanh Nguyệt",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF795548)
                )

                Text(
                    text = "Trường Đại học CNTT&TT Việt - Hàn",
                    fontSize = 16.sp,
                    color = Color(0xFF8D6E63)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Footer
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ContactRow(Icons.Default.Phone, "03666 805 571")
                ContactRow(Icons.Default.Email, "nguyetttt.24it@vku.udn.vn")
                ContactRow(Icons.Default.Share, "@nguyet")
            }
        }
    }
}


    @Composable
    fun ContactRow(icon: ImageVector, text: String) {
        Row(
            modifier = Modifier.width(280.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF6D4C41)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                color = Color(0xFF5D4037) 
            )
        }
    }
