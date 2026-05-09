package com.example.milkteaapp.model.data

import com.google.firebase.Timestamp

enum class PromotionType {
    PERCENT_DISCOUNT, // Giảm %
    FIXED_DISCOUNT,   // Giảm tiền mặt
    BUY_X_GET_Y,      // Mua X tặng Y
    FREE_GIFT         // Quà tặng
}

data class Promotion(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val bannerImageUrl: String? = null,
    val type: PromotionType = PromotionType.PERCENT_DISCOUNT,
    val discountValue: Double = 0.0,
    val buyQuantity: Int = 1,
    val getQuantity: Int = 1,
    val applicableCategoryIds: List<String> = emptyList(),
    val minOrderAmount: Long = 0L,
    val maxUsageCount: Int = 0,
    val currentUsage: Int = 0,
    val startAt: Timestamp = Timestamp.now(),
    val endAt: Timestamp? = null,
    val isActive: Boolean = true
) {
    // 1. Kiểm tra hiệu lực: Dùng bối cảnh phủ định (Guard Clauses) để code bớt lồng nhau
    fun isValid(): Boolean {
        val now = Timestamp.now()

        if (!isActive) return false
        if (now < startAt) return false
        if (endAt != null && now > endAt) return false
        if (maxUsageCount in 1..currentUsage) return false // Nếu có giới hạn và đã dùng hết

        return true
    }

    // 2. Tính số tiền giảm: Viết ngắn gọn bằng biểu thức when
    fun calculateDiscount(orderTotal: Long): Long {
        if (orderTotal < minOrderAmount) return 0L

        return when (type) {
            PromotionType.PERCENT_DISCOUNT -> (orderTotal * (discountValue / 100)).toLong()
            PromotionType.FIXED_DISCOUNT -> discountValue.toLong()
            else -> 0L
        }.coerceAtMost(orderTotal) // Đảm bảo số tiền giảm không lớn hơn tổng hóa đơn
    }
}