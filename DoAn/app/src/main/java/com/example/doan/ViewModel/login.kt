package com.example.doan.ViewModel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.BasicTextField

// ─────────────────────────────────────────────
//  BRAND COLORS
// ─────────────────────────────────────────────
private val TeaBrown       = Color(0xFF8B3A1E)
private val TeaBrownDark   = Color(0xFF6B2C16)
private val TeaBrownLight  = Color(0xFFC4714A)
private val BgCream        = Color(0xFFF5F0E8)
private val BgInput        = Color(0xFFEDE8DF)
private val TextPrimary    = Color(0xFF2C1A0E)
private val TextSecondary  = Color(0xFF8A7968)
private val HintColor      = Color(0xFFB0A090)
private val DividerColor   = Color(0xFFDDD5C8)
private val FacebookBlue   = Color(0xFF1877F2)
private val White          = Color(0xFFFFFFFF)

// ─────────────────────────────────────────────
//  ACTIVITY
// ─────────────────────────────────────────────
class login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AuthScreen(onLoginSuccess = {})
            }
        }
    }
}

// ─────────────────────────────────────────────
//  ROOT SCREEN — manages tab state
// ─────────────────────────────────────────────
@Composable
fun AuthScreen(onLoginSuccess: () -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
    ) {
        AnimatedContent(
            targetState = selectedTab,
            transitionSpec = {
                fadeIn(tween(300)) togetherWith fadeOut(tween(200))
            },
            label = "tab_transition"
        ) { tab ->
            when (tab) {
                0 -> LoginScreen(
                    onLoginSuccess = onLoginSuccess,
                    onForgotClick  = { selectedTab = 2 },
                    onRegisterClick = { selectedTab = 1 }
                )
                1 -> RegisterScreen(
                    onLoginClick = { selectedTab = 0 }
                )
                2 -> ForgotPasswordScreen(
                    onBackLogin = { selectedTab = 0 }
                )
            }
        }
    }
}

// ─────────────────────────────────────────────
//  SCREEN 1 — ĐĂNG NHẬP
// ─────────────────────────────────────────────
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onForgotClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var email              by remember { mutableStateOf("") }
    var password           by remember { mutableStateOf("") }
    var isPasswordVisible  by remember { mutableStateOf(false) }
    var emailError         by remember { mutableStateOf("") }
    var passwordError      by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
    ) {

        // ── Logo ──────────────────────────────────
        Spacer(Modifier.height(52.dp))
        Text(
            text = "Trà sữa NL",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = TeaBrown,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "TRÀ & TĨNH LẶNG",
            fontSize = 12.sp,
            letterSpacing = 2.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // ── Tab Bar ───────────────────────────────
        Spacer(Modifier.height(32.dp))
        TeaTabBar(
            selectedIndex = 0,
            onLoginClick  = { /* already here */ },
            onRegisterClick = onRegisterClick
        )

        // ── Welcome ───────────────────────────────
        Spacer(Modifier.height(32.dp))
        Text(
            "Chào bạn quay lại",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Vui lòng đăng nhập để tiếp tục hành trình thưởng trà.",
            fontSize = 14.sp,
            color = TextSecondary,
            lineHeight = 22.sp
        )

        // ── Email Field ───────────────────────────
        Spacer(Modifier.height(28.dp))
        TeaFieldLabel("Email hoặc Số điện thoại")
        Spacer(Modifier.height(8.dp))
        TeaTextField(
            value         = email,
            onValueChange = { email = it; emailError = "" },
            placeholder   = "name@example.com",
            leadingIcon   = Icons.Default.Email,
            keyboardType  = KeyboardType.Email,
            errorMsg      = emailError
        )

        // ── Password Field ────────────────────────
        Spacer(Modifier.height(16.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            TeaFieldLabel("Mật khẩu")
            Text(
                "Quên mật khẩu?",
                fontSize = 13.sp,
                color    = TeaBrown,
                modifier = Modifier.clickable { onForgotClick() }
            )
        }
        Spacer(Modifier.height(8.dp))
        TeaPasswordField(
            value             = password,
            onValueChange     = { password = it; passwordError = "" },
            isVisible         = isPasswordVisible,
            onToggleVisibility = { isPasswordVisible = !isPasswordVisible },
            errorMsg          = passwordError
        )

        // ── Login Button ──────────────────────────
        Spacer(Modifier.height(28.dp))
        TeaPrimaryButton(
            text = "Đăng nhập ngay",
            onClick = {
                var valid = true
                if (email.isBlank()) { emailError = "Vui lòng nhập email hoặc số điện thoại"; valid = false }
                if (password.length < 6) { passwordError = "Mật khẩu phải có ít nhất 6 ký tự"; valid = false }
                if (valid) onLoginSuccess()
            }
        )

        // ── Divider ───────────────────────────────
        Spacer(Modifier.height(24.dp))
        TeaDivider("HOẶC TIẾP TỤC VỚI")

        // ── Social Buttons ────────────────────────
        Spacer(Modifier.height(20.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SocialButton(
                modifier  = Modifier.weight(1f),
                label     = "Google",
                bgColor   = White,
                textColor = TextPrimary,
                borderColor = DividerColor,
                iconVector = Icons.Default.Email   // replace with Google icon asset
            )
            SocialButton(
                modifier  = Modifier.weight(1f),
                label     = "Facebook",
                bgColor   = FacebookBlue,
                textColor = White,
                borderColor = FacebookBlue,
                iconVector = Icons.Default.Person  // replace with Facebook icon asset
            )
        }

        // ── Footer ────────────────────────────────
        Spacer(Modifier.height(32.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            FooterLink("Điều khoản")
            TeaFooterDivider()
            FooterLink("Bảo mật")
            TeaFooterDivider()
            FooterLink("Hỗ trợ")
        }
    }
}

// ─────────────────────────────────────────────
//  SCREEN 2 — ĐĂNG KÝ
// ─────────────────────────────────────────────
@Composable
fun RegisterScreen(onLoginClick: () -> Unit) {
    var fullName          by remember { mutableStateOf("") }
    var email             by remember { mutableStateOf("") }
    var phone             by remember { mutableStateOf("") }
    var password          by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
            .verticalScroll(rememberScrollState())
    ) {

        // ── Top Bar ───────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector        = Icons.Outlined.ArrowBack,
                contentDescription = "Quay lại",
                tint               = TeaBrown,
                modifier           = Modifier
                    .size(24.dp)
                    .clickable { onLoginClick() }
            )
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    "Trà sữa NL",
                    fontSize   = 20.sp,
                    fontStyle  = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color      = TeaBrown
                )
            }
            Spacer(Modifier.size(24.dp))
        }



        // ── Form Card ─────────────────────────────
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape  = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(Modifier.padding(24.dp)) {

                RegisterField(
                    label       = "HỌ TÊN",
                    value       = fullName,
                    onChange    = { fullName = it },
                    placeholder = "Nguyễn Văn A",
                    icon        = Icons.Default.Person,
                    keyboardType = KeyboardType.Text
                )
                Spacer(Modifier.height(16.dp))
                RegisterField(
                    label       = "EMAIL",
                    value       = email,
                    onChange    = { email = it },
                    placeholder = "example@tuctac.vn",
                    icon        = Icons.Default.Email,
                    keyboardType = KeyboardType.Email
                )
                Spacer(Modifier.height(16.dp))
                RegisterField(
                    label       = "SỐ ĐIỆN THOẠI",
                    value       = phone,
                    onChange    = { phone = it },
                    placeholder = "0901 234 567",
                    icon        = Icons.Default.Phone,
                    keyboardType = KeyboardType.Phone
                )
                Spacer(Modifier.height(16.dp))

                // Password
                Text(
                    "MẬT KHẨU",
                    fontSize      = 11.sp,
                    letterSpacing = 1.sp,
                    fontWeight    = FontWeight.Bold,
                    color         = TextSecondary
                )
                Spacer(Modifier.height(8.dp))
                TeaPasswordField(
                    value              = password,
                    onValueChange      = { password = it },
                    isVisible          = isPasswordVisible,
                    onToggleVisibility = { isPasswordVisible = !isPasswordVisible }
                )

                Spacer(Modifier.height(24.dp))

                // Register Button
                TeaPrimaryButton(
                    text    = "Đăng ký thành viên ⚡",
                    onClick = {  }
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Đã có tài khoản? ", fontSize = 14.sp, color = TextSecondary)
                    Text(
                        "Đăng nhập",
                        fontSize    = 14.sp,
                        fontWeight  = FontWeight.Bold,
                        color       = TeaBrown,
                        modifier    = Modifier.clickable { onLoginClick() }
                    )
                }
            }
        }

        // ── Quote ─────────────────────────────────
        Text(
            text      = "\"Uống trà là để tìm thấy sự tĩnh lặng trong tâm hồn giữa thế giới vội vã.\"",
            fontSize  = 13.sp,
            fontStyle = FontStyle.Italic,
            color     = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            modifier  = Modifier
                .padding(horizontal = 32.dp)
                .padding(bottom = 32.dp)
        )
    }
}

// ─────────────────────────────────────────────
//  SCREEN 3 — QUÊN MẬT KHẨU
// ─────────────────────────────────────────────
@Composable
fun ForgotPasswordScreen(onBackLogin: () -> Unit) {
    var email      by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
            .verticalScroll(rememberScrollState())
    ) {

        // ── Top Bar ───────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector        = Icons.Outlined.ArrowBack,
                contentDescription = "Quay lại",
                tint               = TeaBrown,
                modifier           = Modifier
                    .size(24.dp)
                    .clickable { onBackLogin() }
            )
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    "Trà sữa NL",
                    fontSize   = 20.sp,
                    fontStyle  = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color      = TeaBrown
                )
            }
            Spacer(Modifier.size(24.dp))
        }

        // ── Hero Image Area ───────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(240.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.radialGradient(
                        colors  = listOf(Color(0xFF5A6E5A), Color(0xFF2A3A2A)),
                        radius  = 600f
                    )
                )
        ) {
            // Ripple circles (decorative)
            val rippleColors = listOf(
                Color.White.copy(alpha = 0.05f),
                Color.White.copy(alpha = 0.08f),
                Color.White.copy(alpha = 0.04f),
            )
            rippleColors.forEachIndexed { i, color ->
                Box(
                    Modifier
                        .size((120 + i * 60).dp)
                        .align(Alignment.Center)
                        .clip(CircleShape)
                        .border(1.dp, color, CircleShape)
                )
            }
            // Leaf icon in center
            Text(
                "🍃",
                fontSize = 48.sp,
                modifier = Modifier.align(Alignment.Center)
            )
            // Caption
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color(0x99333333))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {

            }
        }

        // ── Title ─────────────────────────────────
        Spacer(Modifier.height(32.dp))
        Text(
            "Quên mật khẩu",
            fontSize   = 30.sp,
            fontWeight = FontWeight.Bold,
            color      = TeaBrown,
            textAlign  = TextAlign.Center,
            modifier   = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "Nhập email của bạn để nhận hướng dẫn khôi phục mật khẩu",
            fontSize  = 14.sp,
            color     = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp,
            modifier  = Modifier.padding(horizontal = 32.dp)
        )

        // ── Form ──────────────────────────────────
        Spacer(Modifier.height(32.dp))
        Column(Modifier.padding(horizontal = 24.dp)) {
            Text(
                "ĐỊA CHỈ EMAIL",
                fontSize      = 11.sp,
                letterSpacing = 1.sp,
                fontWeight    = FontWeight.Bold,
                color         = TextSecondary
            )
            Spacer(Modifier.height(8.dp))
            TeaTextField(
                value         = email,
                onValueChange = { email = it; emailError = "" },
                placeholder   = "example@email.com",
                leadingIcon   = Icons.Default.Email,
                keyboardType  = KeyboardType.Email,
                errorMsg      = emailError
            )
            Spacer(Modifier.height(24.dp))
            TeaPrimaryButton(
                text = "Gửi hướng dẫn →",
                onClick = {
                    if (email.isBlank()) {
                        emailError = "Vui lòng nhập địa chỉ email"
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        emailError = "Email không hợp lệ"
                    } else {
                        /* TODO: API call */
                    }
                }
            )
            Spacer(Modifier.height(24.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { onBackLogin() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text("← ", color = TeaBrown, fontSize = 14.sp)
                Text("Quay lại Đăng nhập", color = TeaBrown, fontSize = 14.sp)
            }
        }

        Spacer(Modifier.height(48.dp))
        Text(
            "TRÀ SỮA NL © 2026",
            fontSize      = 11.sp,
            letterSpacing = 1.sp,
            color         = TextSecondary,
            textAlign     = TextAlign.Center,
            modifier      = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )
    }
}

// ─────────────────────────────────────────────
//  REUSABLE COMPONENTS
// ─────────────────────────────────────────────

/** Two-tab bar: Đăng nhập / Đăng ký */
@Composable
fun TeaTabBar(
    selectedIndex: Int,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Column {
        Row(Modifier.fillMaxWidth()) {
            TeaTab("ĐĂNG NHẬP", selectedIndex == 0, Modifier.weight(1f)) { onLoginClick() }
            TeaTab("ĐĂNG KÝ",   selectedIndex == 1, Modifier.weight(1f)) { onRegisterClick() }
        }
        // Full-width divider
        Divider(color = DividerColor, thickness = 1.dp)
        // Active indicator
        Row(Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(2.dp)
                    .background(if (selectedIndex == 0) TeaBrown else Color.Transparent)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(2.dp)
                    .background(if (selectedIndex == 1) TeaBrown else Color.Transparent)
            )
        }
    }
}

@Composable
fun TeaTab(label: String, isSelected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Text(
        text       = label,
        fontSize   = 13.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        letterSpacing = 1.sp,
        color      = if (isSelected) TeaBrown else TextSecondary,
        textAlign  = TextAlign.Center,
        modifier   = modifier
            .clickable { onClick() }
            .padding(vertical = 12.dp)
    )
}

/** Labelled input field */
@Composable
fun TeaFieldLabel(text: String) {
    Text(text, fontSize = 14.sp, color = TextPrimary)
}

/** Standard text input */
@Composable
fun TeaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    errorMsg: String = ""
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(BgInput)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector        = leadingIcon,
                contentDescription = null,
                tint               = TextSecondary,
                modifier           = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(12.dp))
            BasicTextField(
                value         = value,
                onValueChange = onValueChange,
                singleLine    = true,
                textStyle     = androidx.compose.ui.text.TextStyle(
                    fontSize  = 15.sp,
                    color     = TextPrimary
                ),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                decorationBox   = { inner ->
                    if (value.isEmpty()) Text(placeholder, fontSize = 15.sp, color = HintColor)
                    inner()
                },
                modifier = Modifier.weight(1f)
            )
        }
        if (errorMsg.isNotEmpty()) {
            Text(errorMsg, fontSize = 12.sp, color = Color(0xFFD32F2F), modifier = Modifier.padding(start = 4.dp, top = 4.dp))
        }
    }
}

/** Password field with show/hide toggle */
@Composable
fun TeaPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    isVisible: Boolean,
    onToggleVisibility: () -> Unit,
    errorMsg: String = ""
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(BgInput)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector        = Icons.Default.Lock,
                contentDescription = null,
                tint               = TextSecondary,
                modifier           = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(12.dp))
            BasicTextField(
                value             = value,
                onValueChange     = onValueChange,
                singleLine        = true,
                textStyle         = androidx.compose.ui.text.TextStyle(
                    fontSize = 15.sp,
                    color    = TextPrimary
                ),
                visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
                decorationBox        = { inner ->
                    if (value.isEmpty()) Text("••••••••", fontSize = 15.sp, color = HintColor)
                    inner()
                },
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector        = if (isVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = "Toggle password",
                tint               = TextSecondary,
                modifier           = Modifier
                    .size(20.dp)
                    .clickable { onToggleVisibility() }
            )
        }
        if (errorMsg.isNotEmpty()) {
            Text(errorMsg, fontSize = 12.sp, color = Color(0xFFD32F2F), modifier = Modifier.padding(start = 4.dp, top = 4.dp))
        }
    }
}

/** Primary CTA button */
@Composable
fun TeaPrimaryButton(text: String, onClick: () -> Unit) {
    Button(
        onClick  = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape    = RoundedCornerShape(14.dp),
        colors   = ButtonDefaults.buttonColors(containerColor = TeaBrown)
    ) {
        Text(
            text       = text,
            fontSize   = 17.sp,
            fontWeight = FontWeight.Bold,
            color      = White
        )
    }
}

/** "OR CONTINUE WITH" divider row */
@Composable
fun TeaDivider(label: String) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(Modifier.weight(1f), color = DividerColor)
        Text(
            label,
            fontSize      = 11.sp,
            letterSpacing = 1.sp,
            color         = TextSecondary,
            modifier      = Modifier.padding(horizontal = 16.dp)
        )
        Divider(Modifier.weight(1f), color = DividerColor)
    }
}

/** Social login button */
@Composable
fun SocialButton(
    modifier: Modifier,
    label: String,
    bgColor: Color,
    textColor: Color,
    borderColor: Color,
    iconVector: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .height(52.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(iconVector, contentDescription = null, tint = textColor, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(10.dp))
        Text(label, fontSize = 15.sp, color = textColor, fontWeight = FontWeight.Medium)
    }
}

/** Register form field with uppercase label */
@Composable
fun RegisterField(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    placeholder: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType
) {
    Text(
        label,
        fontSize      = 11.sp,
        letterSpacing = 1.sp,
        fontWeight    = FontWeight.Bold,
        color         = TextSecondary
    )
    Spacer(Modifier.height(8.dp))
    TeaTextField(
        value         = value,
        onValueChange = onChange,
        placeholder   = placeholder,
        leadingIcon   = icon,
        keyboardType  = keyboardType
    )
}

/** Footer text link */
@Composable
fun FooterLink(text: String) {
    Text(text, fontSize = 12.sp, color = TextSecondary)
}

/** Vertical separator for footer */
@Composable
fun TeaFooterDivider() {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .width(1.dp)
            .height(12.dp)
            .background(DividerColor)
    )
}