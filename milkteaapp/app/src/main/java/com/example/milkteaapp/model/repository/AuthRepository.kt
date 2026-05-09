package com.example.milkteaapp.model.repository

import com.example.milkteaapp.model.data.User
import com.example.milkteaapp.model.data.UserRole
import com.example.milkteaapp.model.remote.FirebaseAuthSource
import com.example.milkteaapp.model.remote.FirestoreSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository xử lý đăng ký, đăng nhập và phân quyền.
 * Kết hợp [FirebaseAuthSource] (Auth) và [FirestoreSource] (lưu profile).
 */
@Singleton
class AuthRepository @Inject constructor(
    private val authSource: FirebaseAuthSource,
    private val firestoreSource: FirestoreSource
) {
    /** UID người dùng hiện tại, null nếu chưa đăng nhập */
    val currentUid: String? get() = authSource.currentUid

    /** Trả về true nếu đang có session đăng nhập */
    val isLoggedIn: Boolean get() = authSource.currentUser != null

    // ── Đăng ký ──────────────────────────────────────────────────────────────

    /**
     * Đăng ký tài khoản mới.
     * 1. Tạo tài khoản trên Firebase Auth
     * 2. Lưu profile User lên Firestore với role = CUSTOMER
     *
     * @return [Result.success] chứa [User] nếu thành công
     */
    suspend fun register(
        fullName: String,
        email: String,
        password: String,
        phoneNumber: String? = null
    ): Result<User> = withContext(Dispatchers.IO) {
        runCatching {
            val firebaseUser = authSource.register(email, password)
            val user = User(
                uid         = firebaseUser.uid,
                fullName    = fullName,
                email       = email,
                phoneNumber = phoneNumber,
                role        = UserRole.CUSTOMER
            )
            firestoreSource.saveUser(user)
            user
        }
    }

    // ── Đăng nhập ────────────────────────────────────────────────────────────

    /**
     * Đăng nhập và lấy profile từ Firestore.
     * @return [Result.success] chứa [User] nếu thành công
     */
    suspend fun login(email: String, password: String): Result<User> =
        withContext(Dispatchers.IO) {
            runCatching {
                val firebaseUser = authSource.login(email, password)
                val user = firestoreSource.getUser(firebaseUser.uid)
                    ?: throw Exception("Không tìm thấy thông tin người dùng.")

                if (!user.isActive) throw Exception("Tài khoản đã bị khoá. Vui lòng liên hệ quản trị.")
                user
            }
        }

    // ── Lấy profile hiện tại ─────────────────────────────────────────────────

    /**
     * Lấy profile của người dùng đang đăng nhập từ Firestore.
     * Dùng khi khởi động app (session còn hiệu lực).
     */
    suspend fun getCurrentUser(): Result<User> = withContext(Dispatchers.IO) {
        runCatching {
            val uid = authSource.currentUid
                ?: throw Exception("Chưa đăng nhập.")
            firestoreSource.getUser(uid)
                ?: throw Exception("Không tìm thấy profile người dùng.")
        }
    }

    // ── Đặt lại mật khẩu ─────────────────────────────────────────────────────

    suspend fun sendPasswordReset(email: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching { authSource.sendPasswordReset(email) }
        }

    // ── Đăng xuất ────────────────────────────────────────────────────────────

    fun logout() = authSource.logout()
}