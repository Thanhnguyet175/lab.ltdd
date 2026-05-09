package com.example.connectfireblass

class Course {
    data class Course(
        var courseID: String? = "",          // ID định danh
        var courseName: String? = "",        // Tên khóa học
        var courseDuration: String? = "",    // Thời lượng
        var courseDescription: String? = ""  // Mô tả chi tiết
    )
}