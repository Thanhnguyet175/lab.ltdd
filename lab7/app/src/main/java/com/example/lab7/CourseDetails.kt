package com.example.lab7

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore

class CourseDetails : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Sử dụng màu nền xám nhạt đồng bộ với trang chủ
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFFF5F5F5)
            ) {
                CourseListUI()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseListUI() {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val courseList = remember { mutableStateListOf<Course>() }

    // Lấy dữ liệu từ Firestore
    LaunchedEffect(Unit) {
        db.collection("Courses").get().addOnSuccessListener { querySnapshot ->
            courseList.clear() // Xóa danh sách cũ trước khi thêm mới
            for (document in querySnapshot) {
                val course = document.toObject(Course::class.java)
                courseList.add(course)
            }
        }
    }

    Scaffold(
        topBar = {
            // Thanh tiêu đề có nút Quay lại
            TopAppBar(
                title = { Text("Danh Sách Khóa Học", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        // Lệnh quay lại trang trước (MainActivity)
                        (context as? ComponentActivity)?.finish()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE), // Màu tím đồng bộ
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (courseList.isEmpty()) {
                Text(
                    text = "Đang tải dữ liệu hoặc danh sách trống...",
                    modifier = Modifier.padding(16.dp),
                    color = Color.Gray
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp) // Khoảng cách giữa các thẻ
            ) {
                items(courseList) { course ->
                    CourseCard(course)
                }
            }
        }
    }
}

@Composable
fun CourseCard(course: Course) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp), // Bo góc mềm mại
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = course.courseName ?: "Không tên",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF6200EE)
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)

            Row {
                Text(text = "Thời gian: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = course.courseDuration ?: "", fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Mô tả:",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = course.courseDescription ?: "",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = Color.DarkGray
            )
        }
    }
}