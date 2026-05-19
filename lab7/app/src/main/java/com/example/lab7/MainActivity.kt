package com.example.lab7

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore

data class Course(
    var courseID: String? = "",
    var courseName: String? = "",
    var courseDuration: String? = "",
    var courseDescription: String? = ""
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                FirebaseUI()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirebaseUI() {
    val context = LocalContext.current
    val courseName = remember { mutableStateOf("") }
    val courseDuration = remember { mutableStateOf("") }
    val courseDescription = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "QUẢN LÝ KHÓA HỌC",
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 26.sp,
                color = Color(0xFF6200EE)
            ),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                CustomTextField(courseName, "Tên khóa học")
                Spacer(modifier = Modifier.height(12.dp))
                CustomTextField(courseDuration, "Thời gian học")
                Spacer(modifier = Modifier.height(12.dp))
                CustomTextField(courseDescription, "Mô tả chi tiết")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (courseName.value.isEmpty() || courseDuration.value.isEmpty() || courseDescription.value.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                } else {
                    addDataToFirebase(courseName.value, courseDuration.value, courseDescription.value, context)
                    courseName.value = ""
                    courseDuration.value = ""
                    courseDescription.value = ""
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text(text = "THÊM KHÓA HỌC", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                context.startActivity(Intent(context, CourseDetails::class.java))
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            border = BorderStroke(1.dp, Color(0xFF6200EE))
        ) {
            Text(text = "XEM TẤT CẢ KHÓA HỌC", color = Color(0xFF6200EE))
        }
    }
}

@Composable
fun CustomTextField(state: MutableState<String>, label: String) {
    OutlinedTextField(
        value = state.value,
        onValueChange = { state.value = it },
    label = { Text(label) },
    modifier = Modifier.fillMaxWidth(),
    singleLine = true,
    shape = MaterialTheme.shapes.small
    )
}

fun addDataToFirebase(
    name: String,
    duration: String,
    description: String,
    context: Context
) {
    val db = FirebaseFirestore.getInstance()
    val dbCourses = db.collection("Courses")
    val id = dbCourses.document().id
    val course = Course(id, name, duration, description)
    dbCourses.document(id).set(course)
        .addOnSuccessListener {
            Toast.makeText(context, "Thêm khóa học thành công!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
        }
}