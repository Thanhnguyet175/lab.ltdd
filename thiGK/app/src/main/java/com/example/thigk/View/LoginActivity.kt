package com.example.thigk.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

private val BgDark     = Color(0xFF0F111A) // Nền đen sâu
private val CardColor  = Color(0xFF1E2130) // Thẻ Form màu xám xanh đậm
private val OrangePri  = Color(0xFFA584E8) // Tím sáng Neon (Thay cho Cam/Tím đậm)
private val OrangeAcc  = Color(0xFF7E57C2) // Tím trung bình
private val TextLight  = Color(0xFFF0F4FF) // Chữ tiêu đề trắng xanh nổi bật
private val TextMuted  = Color(0xFFB0B8D1) // Chữ phụ xám nhạt
private val DivColor   = Color(0xFF2F3750) // Đường kẻ ngăn cách tối



class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                onLoginSuccess = {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val sharedPref = remember { context.getSharedPreferences("MyPrefs", android.content.Context.MODE_PRIVATE) }
    val savedEmail = remember { sharedPref.getString("last_email", "") ?: "" }

    var email by remember { mutableStateOf(savedEmail) }
    var password by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }

    var isRegistering by remember { mutableStateOf(false) }

    var regEmail by remember { mutableStateOf("") }
    var regPass by remember { mutableStateOf("") }
    var regConfirmPass by remember { mutableStateOf("") }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.08f,
        animationSpec = infiniteRepeatable(animation = tween(1800, easing = EaseInOutSine), repeatMode = RepeatMode.Reverse),
        label = "pulseScale"
    )

    BackHandler(enabled = isRegistering) { isRegistering = false }

    Box(
        modifier = Modifier.fillMaxSize().background(BgDark)
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) },
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.size(340.dp).offset(y = (-80).dp).blur(60.dp)
            .background(brush = Brush.radialGradient(colors = listOf(OrangePri.copy(alpha = 0.12f), Color.Transparent)), shape = CircleShape)
        )

        // MÀN HÌNH ĐĂNG NHẬP
        AnimatedVisibility(
            visible = !isRegistering,
            enter = fadeIn() + slideInHorizontally { -it },
            exit = fadeOut() + slideOutHorizontally { -it }
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.scale(pulseScale).size(100.dp).clip(RoundedCornerShape(28.dp)).background(Brush.linearGradient(colors = listOf(OrangePri, OrangeAcc))), contentAlignment = Alignment.Center) { Text("🗒️", fontSize = 48.sp) }
                Spacer(modifier = Modifier.height(24.dp))
                Text("NOTES", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = TextLight)
                Text("Huu Loi", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextMuted, letterSpacing = 3.sp)
                Spacer(modifier = Modifier.height(48.dp))

                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CardColor), shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Xác thực người dùng 👋", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextLight)
                        Spacer(modifier = Modifier.height(20.dp))

                        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, leadingIcon = { Icon(Icons.Default.Email, null, tint = OrangePri) }, modifier = Modifier.fillMaxWidth(), singleLine = true, colors = loginFieldColors(), shape = RoundedCornerShape(14.dp))
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Mật khẩu") }, leadingIcon = { Icon(Icons.Default.Lock, null, tint = OrangePri) }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(), singleLine = true, colors = loginFieldColors(), shape = RoundedCornerShape(14.dp))

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                if (email.isNotBlank() && password.isNotBlank()) {
                                    isLoading = true
                                    auth.signInWithEmailAndPassword(email.trim(), password)
                                        .addOnSuccessListener { isLoading = false; onLoginSuccess() }
                                        .addOnFailureListener { isLoading = false; Toast.makeText(context, "Lỗi đăng nhập!", Toast.LENGTH_SHORT).show() }
                                }
                            },
                            enabled = !isLoading,
                            modifier = Modifier.fillMaxWidth().height(52.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = OrangePri),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            if (isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                            else Text("ĐĂNG NHẬP", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedButton(onClick = { isRegistering = true }, modifier = Modifier.fillMaxWidth().height(52.dp), border = BorderStroke(1.5.dp, OrangePri.copy(alpha = 0.6f)), shape = RoundedCornerShape(14.dp)) {
                            Text("TẠO TÀI KHOẢN MỚI", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = OrangePri)
                        }
                    }
                }
            }
        }

        // MÀN HÌNH ĐĂNG KÝ
        AnimatedVisibility(
            visible = isRegistering,
            enter = fadeIn() + slideInHorizontally { it },
            exit = fadeOut() + slideOutHorizontally { it }
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CardColor), shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { isRegistering = false }) { Icon(Icons.Default.ArrowBack, null, tint = OrangePri) }
                            Text("Đăng ký tài khoản", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextLight)
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        OutlinedTextField(value = regEmail, onValueChange = { regEmail = it }, label = { Text("Nhập Email") }, leadingIcon = { Icon(Icons.Default.Email, null, tint = OrangePri) }, modifier = Modifier.fillMaxWidth(), singleLine = true, colors = loginFieldColors(), shape = RoundedCornerShape(14.dp))
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(value = regPass, onValueChange = { regPass = it }, label = { Text("Nhập mật khẩu") }, leadingIcon = { Icon(Icons.Default.Lock, null, tint = OrangePri) }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(), singleLine = true, colors = loginFieldColors(), shape = RoundedCornerShape(14.dp))
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = regConfirmPass, onValueChange = { regConfirmPass = it },
                            label = { Text("Nhập lại mật khẩu") },
                            leadingIcon = { Icon(if (regConfirmPass == regPass && regPass.isNotEmpty()) Icons.Default.CheckCircle else Icons.Default.Lock, null, tint = if (regConfirmPass == regPass && regPass.isNotEmpty()) Color.Green else OrangePri) },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(), singleLine = true, colors = loginFieldColors(), shape = RoundedCornerShape(14.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                if (regPass != regConfirmPass) {
                                    Toast.makeText(context, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                if (regEmail.isNotBlank() && regPass.length >= 6) {
                                    isLoading = true
                                    auth.createUserWithEmailAndPassword(regEmail.trim(), regPass)
                                        .addOnSuccessListener { isLoading = false; isRegistering = false; email = regEmail; Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show() }
                                        .addOnFailureListener { e -> isLoading = false; Toast.makeText(context, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show() }
                                }
                            },
                            enabled = !isLoading && regEmail.isNotBlank() && regPass.isNotEmpty() && regConfirmPass.isNotEmpty(),
                            modifier = Modifier.fillMaxWidth().height(52.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = OrangePri),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            if (isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                            else Text("HOÀN TẤT ĐĂNG KÝ", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun loginFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = OrangePri, unfocusedBorderColor = DivColor,
    focusedLabelColor = OrangePri, unfocusedLabelColor = TextMuted,
    focusedTextColor = TextLight, unfocusedTextColor = TextLight,
    cursorColor = OrangePri
)