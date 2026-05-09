
package com.example.thigk.View

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.example.thigk.ViewModel.NoteViewModel
import com.example.thigk.model.Note
import com.google.firebase.auth.FirebaseAuth

// ── Bảng màu Dark Mode (Tím Đen Huyền Bí) ──────────────────────
val ColorBg      = Color(0xFF0F111A) // Nền đen sâu (Dark Background)
val ColorCard    = Color(0xFF1E2130) // Thẻ ghi chú màu xám xanh đậm
val ColorPrimary = Color(0xFFA584E8) // Tím sáng (để nổi bật trên nền đen)
val ColorAccent  = Color(0xFF7E57C2) // Tím trung bình
val ColorText    = Color(0xFFF0F4FF) // Chữ tiêu đề trắng xanh (rất nổi)
val ColorSubtext = Color(0xFFB0B8D1) // Chữ nội dung xám nhạt
val ColorRed     = Color(0xFFEF5350) // Giữ đỏ cho nút xóa
val ColorField   = Color(0xFF161925) // Nền ô nhập liệu tối hơn Card
val ColorMain    = Color(0xFFD1B3FF) // Tím neon nhẹ cho Header

class MainActivity : ComponentActivity() {
    private val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val config = mapOf("cloud_name" to "dur7hwss0", "api_key" to "792264984411894", "api_secret" to "IOms_marhYA0zsKlgV9O-QxzdJw")
            com.cloudinary.android.MediaManager.init(this, config)
        } catch (_: Exception) {}

        setContent {
            val context = LocalContext.current
            val focusManager = LocalFocusManager.current
            var currentScreen by remember { mutableStateOf("LIST") }
            var selectedNote by remember { mutableStateOf<Note?>(null) }

            val auth = FirebaseAuth.getInstance()
            // Chuyển email về chữ thường để so sánh tuyệt đối chính xác
            val userEmail = auth.currentUser?.email?.lowercase() ?: ""
            val isAdmin = userEmail == "nguyet_admin@gmail.com"

            BackHandler(enabled = currentScreen != "LIST") { currentScreen = "LIST" }

            Box(modifier = Modifier.fillMaxSize().background(ColorBg)
                .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
            ) {
                when (currentScreen) {
                    "LIST" -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            TopBar(onLogout = {
                                val currentEmail = auth.currentUser?.email ?: ""
                                val sharedPref = context.getSharedPreferences("MyPrefs", android.content.Context.MODE_PRIVATE)
                                sharedPref.edit().putString("last_email", currentEmail).apply()
                                viewModel.logout()
                                val intent = android.content.Intent(context, LoginActivity::class.java)
                                intent.flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
                                context.startActivity(intent)
                                finish()
                            })
                            NoteCounter(count = viewModel.noteList.size)

                            if (viewModel.noteList.isEmpty()) {
                                EmptyState(modifier = Modifier.weight(1f))
                            } else {
                                LazyColumn(
                                    modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp),
                                    contentPadding = PaddingValues(top = 8.dp, bottom = 88.dp)
                                ) {
                                    items(viewModel.noteList, key = { it.id ?: it.hashCode().toString() }) { note ->
                                        NoteCard(
                                            note = note,
                                            onNoteClick = {
                                                selectedNote = note
                                                currentScreen = "EDIT"
                                            },
                                            onDelete = { viewModel.deleteNote(note.id!!) }
                                        )
                                    }
                                }
                            }
                        }

                        // Đảm bảo nút FAB nằm trong Box và kiểm tra isAdmin chuẩn xác
                        if (isAdmin) {
                            FloatingActionButton(
                                onClick = { selectedNote = null; currentScreen = "ADD" },
                                modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp),
                                containerColor = ColorPrimary,
                                contentColor = Color.White,
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Icon(Icons.Default.Add, null, modifier = Modifier.size(28.dp))
                            }
                        }
                    }

                    "ADD", "EDIT" -> {
                        EditorScreen(
                            note = selectedNote,
                            onClose = { currentScreen = "LIST" },
                            onSave = { title, desc, fileUri, existingUrl ->
                                viewModel.saveNote(
                                    noteId = if (currentScreen == "ADD") null else selectedNote?.id,
                                    title = title, desc = desc, fileUri = fileUri, existingUrl = existingUrl,
                                    onSuccess = {
                                        currentScreen = "LIST"
                                        Toast.makeText(context, "Thành công!", Toast.LENGTH_SHORT).show()
                                    },
                                    onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(onLogout: () -> Unit) {
    Column {
        Box(modifier = Modifier.fillMaxWidth().background(ColorCard).padding(20.dp, 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("📝", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Row(modifier = Modifier.weight(1f)) {
                    Text("GHI CHÚ", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = ColorMain)
                }
                IconButton(onClick = onLogout) { Icon(Icons.Default.Logout, null, tint = ColorRed, modifier = Modifier.size(22.dp)) }
            }
        }
        GradientLine(listOf(ColorPrimary, ColorAccent, Color.Transparent))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(note: Note?, onClose: () -> Unit, onSave: (String, String, Uri?, String?) -> Unit) {
    var title by remember { mutableStateOf(note?.title ?: "") }
    var description by remember { mutableStateOf(note?.description ?: "") }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var currentUrl by remember { mutableStateOf(note?.fileUrl) }
    val focusManager = LocalFocusManager.current
    val fileLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { selectedUri = it }

    val auth = FirebaseAuth.getInstance()
    val isAdmin = (auth.currentUser?.email?.lowercase() ?: "") == "nguyet_admin@gmail.com"

    Scaffold(
        containerColor = ColorBg,
        topBar = {
            TopAppBar(
                title = { Text(if (note == null) "Ghi chú mới" else if (isAdmin) "Chỉnh sửa" else "Chi tiết", color = ColorText, fontWeight = FontWeight.Bold, fontSize = 17.sp) },
                navigationIcon = { IconButton(onClick = onClose) { Icon(Icons.Default.ArrowBack, null, tint = ColorText) } },
                actions = {
                    if (isAdmin) {
                        Box(modifier = Modifier.padding(end = 12.dp).clip(RoundedCornerShape(10.dp)).background(ColorPrimary).clickable { if (title.isNotBlank()) onSave(title, description, selectedUri, currentUrl) }.padding(horizontal = 18.dp, vertical = 8.dp)) {
                            Text("LƯU", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ColorCard)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }.padding(16.dp)) {
            SectionLabel(Icons.Default.Title, "TIÊU ĐỀ", ColorPrimary)
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(ColorField)) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = transparentFieldColors(ColorPrimary),
                    textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    readOnly = !isAdmin
                )
            }
            GradientLine(listOf(ColorPrimary, ColorAccent, Color.Transparent))
            Spacer(Modifier.height(20.dp))

            SectionLabel(Icons.Default.Notes, "MÔ TẢ", ColorAccent)
            Box(modifier = Modifier.fillMaxWidth().weight(1f).clip(RoundedCornerShape(16.dp)).background(ColorField)) {
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier.fillMaxSize(),
                    colors = transparentFieldColors(ColorAccent),
                    readOnly = !isAdmin
                )
            }
            GradientLine(listOf(ColorAccent, ColorPrimary.copy(0.3f), Color.Transparent))
            Spacer(Modifier.height(20.dp))

            if (selectedUri != null || !currentUrl.isNullOrEmpty()) {
                SectionLabel(Icons.Default.Image, "ẢNH", ColorPrimary)
                Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                    AsyncImage(
                        model = selectedUri ?: currentUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(14.dp)),
                        contentScale = ContentScale.Crop
                    )
                    if (isAdmin) {
                        IconButton(
                            onClick = { selectedUri = null; currentUrl = "" },
                            modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).background(Color.Black.copy(0.5f), CircleShape).size(32.dp)
                        ) {
                            Icon(Icons.Default.Close, null, tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
            }

            if (isAdmin) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(onClick = { fileLauncher.launch("image/*") }, modifier = Modifier.weight(1f).height(44.dp), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.outlinedButtonColors(contentColor = ColorAccent)) {
                        Icon(Icons.Default.Image, null, modifier = Modifier.size(18.dp))
                        Text(" Chọn ảnh", fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun NoteCard(note: Note, onNoteClick: () -> Unit, onDelete: () -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val isAdmin = (auth.currentUser?.email?.lowercase() ?: "") == "nguyet_admin@gmail.com"

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onNoteClick() },
        colors = CardDefaults.cardColors(containerColor = ColorCard),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).background(ColorPrimary, RoundedCornerShape(50)))
                    Text(note.title ?: "", fontWeight = FontWeight.Bold, color = ColorText, modifier = Modifier.padding(start = 10.dp), maxLines = 1, overflow = TextOverflow.Ellipsis)
                }


                Text(note.description ?: "", color = ColorSubtext, fontSize = 14.sp, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(start = 18.dp, top = 4.dp))
            }

            if (!note.fileUrl.isNullOrEmpty()) {
                AsyncImage(model = note.fileUrl, contentDescription = null, modifier = Modifier.padding(start = 12.dp).size(60.dp).clip(RoundedCornerShape(12.dp)), contentScale = ContentScale.Crop)
            }

            if (isAdmin) {
                IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Delete, null, tint = ColorRed.copy(0.7f), modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

@Composable
fun SectionLabel(icon: ImageVector, label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 8.dp)) {
        Icon(icon, null, tint = color, modifier = Modifier.size(16.dp))
        Text(label, color = color, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(start = 8.dp), letterSpacing = 1.sp)
    }
}

@Composable
fun GradientLine(colors: List<Color>) {
    Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(Brush.horizontalGradient(colors)))
}

@Composable
fun NoteCounter(count: Int) {
    Row(modifier = Modifier.padding(20.dp, 12.dp)) {
        Text("Tất cả ", color = ColorSubtext, fontSize = 14.sp)
        Text("$count ghi chú", color = ColorPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

@Composable
fun EmptyState(modifier: Modifier) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("📔", fontSize = 60.sp)
        Text("Chưa có ghi chú", color = ColorText, fontWeight = FontWeight.Bold)
        Text("Nhấn + để bắt đầu", color = ColorSubtext, fontSize = 13.sp)
    }
}

@Composable
fun transparentFieldColors(cursorColor: Color) = TextFieldDefaults.colors(
    focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,
    focusedTextColor = ColorText, unfocusedTextColor = ColorText, cursorColor = cursorColor
)