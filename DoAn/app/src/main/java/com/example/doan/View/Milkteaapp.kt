package com.example.doan.View

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.navigation.compose.*


data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val originalPrice: Int? = null,
    val rating: Double = 4.9,
    val soldCount: String = "1.2k+",
    val categoryId: Int,
    val isBestSeller: Boolean = false,
    val imageColor: Color  // placeholder color for image
)

data class Category(
    val id: Int,
    val name: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

data class Promotion(
    val title: String,
    val subtitle: String,
    val bgColor: Color
)

data class Topping(
    val name: String,
    val price: Int,
    var selected: Boolean = false
)

// ─────────────────────────────────────────────
//  THEME COLORS
// ─────────────────────────────────────────────

object AppColors {
    val Brown       = Color(0xFF6B3A2A)
    val BrownLight  = Color(0xFF8B5A3C)
    val BrownMid    = Color(0xFFB07D5A)
    val Cream       = Color(0xFFFAF3E8)
    val CreamDark   = Color(0xFFF0E4CC)
    val OliveGreen  = Color(0xFF7A8C5E)
    val Peach       = Color(0xFFE8A87C)
    val WarmGray    = Color(0xFFD4C4A8)
    val TextDark    = Color(0xFF2C1810)
    val TextMid     = Color(0xFF6B5040)
    val TextLight   = Color(0xFF9E8070)
    val White       = Color(0xFFFFFFFF)
    val StarYellow  = Color(0xFFFFB800)
    val Success     = Color(0xFF4CAF50)

    // Product image placeholder colors
    val GreenTea    = Color(0xFF8FBC8F)
    val OolongTea   = Color(0xFFC8956C)
    val CoffeeBrown = Color(0xFF8B6355)
    val PinkMilk    = Color(0xFFE8A0B0)
    val FruitTea    = Color(0xFFFF8C69)
    val MatchaGreen = Color(0xFF6B8F5E)
    val WatermelonR = Color(0xFFFF6B6B)
    val PeachColor  = Color(0xFFFFB347)
}


val categories = listOf(
    Category(1, "Trà Sữa", Icons.Outlined.LocalCafe),
    Category(2, "Trà Trái Cây", Icons.Outlined.LocalBar),
    Category(3, "Cà Phê", Icons.Outlined.Coffee),
    Category(4, "Ăn Vặt", Icons.Outlined.Restaurant)
)

val products = listOf(
    Product(1, "Trà Xanh Kem Muối",  45000, null,  4.9, "2.1k+", 1, true,  AppColors.MatchaGreen),
    Product(2, "Trà Ô Long",         35000, null,  4.8, "1.8k+", 1, false, AppColors.OolongTea),
    Product(3, "Cà Phê Sữa",         29000, null,  4.7, "900+",  3, false, AppColors.CoffeeBrown),
    Product(4, "Oolong Milk Tea",     42000, null,  4.9, "1.2k+", 1, true,  AppColors.BrownMid),
    Product(5, "Hồng Trà Sữa Kim Tuyên", 45000, null, 4.8, "1.5k+", 1, false, AppColors.OolongTea),
    Product(6, "Trà Đào Cam Sả",     45000, null,  4.9, "2.3k+", 2, true,  AppColors.PeachColor),
    Product(7, "Matcha Machiato",     50000, null,  4.9, "980+",  3, true,  AppColors.MatchaGreen),
    Product(8, "Trà Dưa Hấu Bạc Hà", 42000, null,  4.7, "740+",  2, false, AppColors.WatermelonR),
    Product(9, "Cà Phê Muối",        48000, null,  4.8, "1.1k+", 3, false, AppColors.CoffeeBrown),
    Product(10, "Dâu Sữa NL",  48000, null,  4.9, "1.7k+", 1, true,  AppColors.PinkMilk),
    Product(11, "Trà Vải Hạt Dưa",  38000, null,  4.6, "560+",  2, false, AppColors.FruitTea),
    Product(12, "Trà Chanh Leo",     35000, null,  4.7, "890+",  2, false, AppColors.FruitTea)
)

val toppings = listOf(
    Topping("Trân châu đen",  10000, true),
    Topping("Thạch trái cây", 10000, false),
    Topping("Kem cheese",     15000, false),
    Topping("Thạch cà phê",  10000, false),
    Topping("Hạt lựu",        8000, false)
)

val promotions = listOf(
    Promotion("Mua 1 Tặng 1\nTrà Trái Cây", "Áp dụng T2-T4", AppColors.Brown),
    Promotion("Giảm 20%\nCà Phê",           "Chỉ hôm nay",    AppColors.OliveGreen)
)

// ─────────────────────────────────────────────
//  NAVIGATION
// ─────────────────────────────────────────────

@Composable
fun MilkTeaApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("menu/{categoryId}") { back ->
            val catId = back.arguments?.getString("categoryId")?.toIntOrNull() ?: 1
            MenuScreen(navController, catId)
        }
        composable("detail/{productId}") { back ->
            val pId = back.arguments?.getString("productId")?.toIntOrNull() ?: 1
            val product = products.find { it.id == pId } ?: products[0]
            ProductDetailScreen(navController, product)
        }
    }
}

@Composable
fun AppTopBar(title: String, location: String = "Da Nang, Vietnam", navController: NavController? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Cream)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (navController != null) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = AppColors.Brown)
            }
            Spacer(Modifier.width(4.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = AppColors.BrownLight,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(Modifier.width(2.dp))
                Text(
                    location,
                    style = MaterialTheme.typography.labelSmall,
                    color = AppColors.TextLight
                )
            }
            Text(
                title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Brown
                )
            )
        }
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(AppColors.Brown, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Info, contentDescription = null, tint = AppColors.White, modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
fun BottomNavBar(selected: Int, onSelect: (Int) -> Unit) {
    NavigationBar(
        containerColor = AppColors.White,
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            Triple("Home",    Icons.Filled.Home,    Icons.Outlined.Home),
            Triple("Menu",    Icons.Filled.MenuBook, Icons.Outlined.MenuBook),
            Triple("Alerts",  Icons.Filled.Notifications, Icons.Outlined.NotificationsNone),
            Triple("Profile", Icons.Filled.Person,  Icons.Outlined.PersonOutline)
        )
        items.forEachIndexed { index, (label, filledIcon, outlinedIcon) ->
            NavigationBarItem(
                selected = selected == index,
                onClick  = { onSelect(index) },
                icon = {
                    Icon(
                        if (selected == index) filledIcon else outlinedIcon,
                        contentDescription = label,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(label, style = MaterialTheme.typography.labelSmall)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = AppColors.Brown,
                    selectedTextColor   = AppColors.Brown,
                    unselectedIconColor = AppColors.TextLight,
                    unselectedTextColor = AppColors.TextLight,
                    indicatorColor      = AppColors.CreamDark
                )
            )
        }
    }
}

/** Placeholder image box with gradient shimmer */
@Composable
fun ProductImage(color: Color, modifier: Modifier = Modifier, shape: Shape = RoundedCornerShape(12.dp)) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(
                Brush.linearGradient(
                    colors = listOf(color, color.copy(alpha = 0.7f)),
                    start = Offset(0f, 0f),
                    end   = Offset(300f, 300f)
                )
            )
    ) {
        Icon(
            Icons.Outlined.LocalCafe,
            contentDescription = null,
            tint = AppColors.White.copy(alpha = 0.4f),
            modifier = Modifier.align(Alignment.Center).size(40.dp)
        )
    }
}

@Composable
fun StarRating(rating: Double, soldCount: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Filled.Star, contentDescription = null, tint = AppColors.StarYellow, modifier = Modifier.size(12.dp))
        Spacer(Modifier.width(2.dp))
        Text(
            "$rating",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
            color = AppColors.TextDark
        )
        Text(
            "  •  $soldCount đã bán",
            style = MaterialTheme.typography.labelSmall,
            color = AppColors.TextLight
        )
    }
}

// ─────────────────────────────────────────────
//  SCREEN 1 – HOME
// ─────────────────────────────────────────────

@Composable
fun HomeScreen(navController: NavController) {
    var selectedNav by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = AppColors.Cream,
        bottomBar = {
            BottomNavBar(selectedNav) { selectedNav = it }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(scrollState)
        ) {
            // Top bar
            AppTopBar("NL Tea")

            // Hero banner
            HeroBanner()

            Spacer(Modifier.height(16.dp))

            // Category icons
            CategoryRow(navController)

            Spacer(Modifier.height(20.dp))

            // Promotions
            SectionHeader("Khuyến Mãi", "Xem tất cả") {}
            PromotionRow()

            Spacer(Modifier.height(20.dp))

            // Best sellers
            SectionHeader("Bán Chạy Nhất", "") {}
            BestSellerSection(navController)

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun HeroBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(AppColors.Brown, Color(0xFF3D1F0F)),
                    start = Offset(0f, 0f),
                    end   = Offset(800f, 400f)
                )
            )
    ) {
        // Decorative circles
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(Color.White.copy(alpha = 0.05f), radius = 120f, center = Offset(size.width * 0.8f, size.height * 0.3f))
            drawCircle(Color.White.copy(alpha = 0.04f), radius = 80f,  center = Offset(size.width * 0.7f, size.height * 0.8f))
        }
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(24.dp)
        ) {
            Text(
                "Thưởng thức",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Light,
                    color = AppColors.WarmGray
                )
            )
            Text(
                "từng khoảnh\nkhắc",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = AppColors.White,
                    lineHeight = 32.sp
                )
            )
        }
        // Teapot icon area
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 24.dp)
                .size(80.dp)
                .background(AppColors.White.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Outlined.LocalCafe,
                contentDescription = null,
                tint = AppColors.White.copy(alpha = 0.7f),
                modifier = Modifier.size(44.dp)
            )
        }
    }
}

@Composable
fun CategoryRow(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        categories.forEach { cat ->
            CategoryItem(cat) {
                navController.navigate("menu/${cat.id}")
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(72.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(
                    Brush.linearGradient(listOf(AppColors.BrownLight, AppColors.Brown)),
                    RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                category.icon,
                contentDescription = category.name,
                tint = AppColors.White,
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            category.name,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            ),
            color = AppColors.TextMid,
            maxLines = 2,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SectionHeader(title: String, action: String, onAction: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = AppColors.TextDark
        )
        if (action.isNotEmpty()) {
            Text(
                action,
                style = MaterialTheme.typography.labelMedium,
                color = AppColors.Brown,
                modifier = Modifier.clickable(onClick = onAction)
            )
        }
    }
}

@Composable
fun PromotionRow() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(promotions) { promo ->
            PromotionCard(promo)
        }
    }
}

@Composable
fun PromotionCard(promo: Promotion) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(90.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(promo.bgColor, promo.bgColor.copy(red = promo.bgColor.red * 0.7f))
                )
            )
            .padding(14.dp)
    ) {
        Column {
            Text(
                "SUMMER DEAL",
                style = MaterialTheme.typography.labelSmall,
                color = AppColors.White.copy(alpha = 0.7f)
            )
            Text(
                promo.title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = AppColors.White
            )
            Text(
                promo.subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = AppColors.White.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun BestSellerSection(navController: NavController) {
    val bestSellers = products.filter { it.isBestSeller }.take(3)
    val featuredProduct = bestSellers.firstOrNull()

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        // Featured large card
        featuredProduct?.let { product ->
            BestSellerFeaturedCard(product) {
                navController.navigate("detail/${product.id}")
            }
        }

        Spacer(Modifier.height(12.dp))

        // 2 small cards in row
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            bestSellers.drop(1).take(2).forEach { product ->
                BestSellerSmallCard(product, modifier = Modifier.weight(1f)) {
                    navController.navigate("detail/${product.id}")
                }
            }
        }
    }
}

@Composable
fun BestSellerFeaturedCard(product: Product, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.White)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProductImage(
            color = product.imageColor,
            modifier = Modifier.size(80.dp),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                StarRating(product.rating, product.soldCount)
            }
            Spacer(Modifier.height(4.dp))
            Text(
                product.name,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = AppColors.TextDark
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "${"%,d".format(product.price)}đ",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = AppColors.Brown
            )
        }
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.Brown),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
            modifier = Modifier.height(34.dp)
        ) {
            Text("Thêm ngay", style = MaterialTheme.typography.labelSmall, color = AppColors.White)
        }
    }
}

@Composable
fun BestSellerSmallCard(product: Product, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.White)
            .clickable(onClick = onClick)
            .padding(10.dp)
    ) {
        ProductImage(
            color = product.imageColor,
            modifier = Modifier.fillMaxWidth().height(100.dp),
            shape = RoundedCornerShape(10.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            product.name,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = AppColors.TextDark,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(2.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Star, contentDescription = null, tint = AppColors.StarYellow, modifier = Modifier.size(10.dp))
            Text(
                " ${product.rating}",
                style = MaterialTheme.typography.labelSmall,
                color = AppColors.TextLight
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            "${"%,d".format(product.price)}đ",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = AppColors.Brown
        )
    }
}

// ─────────────────────────────────────────────
//  SCREEN 2 – MENU (category grid)
// ─────────────────────────────────────────────

@Composable
fun MenuScreen(navController: NavController, initialCategoryId: Int) {
    var selectedCategory by remember { mutableStateOf(initialCategoryId) }
    var selectedNav by remember { mutableStateOf(1) }
    val filtered = products.filter { it.categoryId == selectedCategory }

    Scaffold(
        containerColor = AppColors.Cream,
        bottomBar = { BottomNavBar(selectedNav) { selectedNav = it } }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            AppTopBar("Thực Đơn", "TP. Đà Nẵng", navController)

            // Search bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(AppColors.White)
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Search, contentDescription = null, tint = AppColors.TextLight, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Tìm kiếm món yêu thích...", style = MaterialTheme.typography.bodySmall, color = AppColors.TextLight)
                }
            }

            // Category chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    CategoryChip("Tất cả", selectedCategory == -1) { selectedCategory = -1 }
                }
                items(categories) { cat ->
                    CategoryChip(cat.name, selectedCategory == cat.id) { selectedCategory = cat.id }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Product grid
            val displayProducts = if (selectedCategory == -1) products else filtered
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement   = Arrangement.spacedBy(12.dp)
            ) {
                items(displayProducts) { product ->
                    MenuProductCard(product) {
                        navController.navigate("detail/${product.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (selected) AppColors.Brown else AppColors.White)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal),
            color = if (selected) AppColors.White else AppColors.TextMid
        )
    }
}

@Composable
fun MenuProductCard(product: Product, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.White)
            .clickable(onClick = onClick)
    ) {
        Box {
            ProductImage(
                color = product.imageColor,
                modifier = Modifier.fillMaxWidth().height(150.dp),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            // Add button
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .size(30.dp)
                    .background(AppColors.Brown, CircleShape)
                    .clickable(onClick = onClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = AppColors.White, modifier = Modifier.size(18.dp))
            }
        }
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                product.name,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = AppColors.TextDark,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "${"%,d".format(product.price)}đ",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = AppColors.Brown
            )
        }
    }
}

// ─────────────────────────────────────────────
//  SCREEN 3 – PRODUCT DETAIL
// ─────────────────────────────────────────────

@Composable
fun ProductDetailScreen(navController: NavController, product: Product) {
    var selectedSize    by remember { mutableStateOf("Vừa") }
    var selectedSugar   by remember { mutableStateOf("50%") }
    var selectedIce     by remember { mutableStateOf("50%") }
    val toppingState    = remember { toppings.map { it.copy() }.toMutableStateList() }
    val scrollState     = rememberScrollState()

    Scaffold(
        containerColor = AppColors.Cream
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(scrollState)
        ) {
            // Hero image with back + heart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                ProductImage(
                    color = product.imageColor,
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(0.dp)
                )
                // Overlay gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(alpha = 0.2f), Color.Transparent),
                                startY = 0f, endY = 300f
                            )
                        )
                )
                // Back button
                IconButton(
                    onClick  = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .background(AppColors.White.copy(alpha = 0.85f), CircleShape)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = AppColors.Brown)
                }
                // Favourite
                IconButton(
                    onClick  = {},
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .background(AppColors.White.copy(alpha = 0.85f), CircleShape)
                ) {
                    Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Favourite", tint = AppColors.Brown)
                }
            }

            // Detail card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(AppColors.White)
                    .padding(20.dp)
            ) {
                // Name + badge
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        product.name,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = AppColors.TextDark,
                        modifier = Modifier.weight(1f)
                    )
                    if (product.isBestSeller) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(AppColors.Brown)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                "Best\nSeller",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = AppColors.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Description
                Text(
                    "Vị trà đậm đà kết hợp cùng sữa béo ngậy, mang đến trải nghiệm thưởng trà truyền thống đầy tinh tế và êm dịu.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.TextLight,
                    lineHeight = 20.sp
                )

                Spacer(Modifier.height(10.dp))
                StarRating(product.rating, product.soldCount)

                Divider(color = AppColors.CreamDark, modifier = Modifier.padding(vertical = 16.dp))

                // SIZE
                OptionSection(title = "Chọn Size", icon = "📏") {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OptionChip("Nhỏ",  selectedSize == "Nhỏ",  "") { selectedSize = "Nhỏ"  }
                        OptionChip("Vừa",  selectedSize == "Vừa",  "") { selectedSize = "Vừa"  }
                        OptionChip("Lớn",  selectedSize == "Lớn",  "+10k") { selectedSize = "Lớn"  }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // SUGAR
                OptionSection(title = "Mức Đường", icon = "🍬") {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.horizontalScroll(rememberScrollState())) {
                        listOf("0%", "30%", "50%", "70%", "100%").forEach { level ->
                            OptionChip(level, selectedSugar == level, "") { selectedSugar = level }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // ICE
                OptionSection(title = "Mức Đá", icon = "🧊") {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("0%", "50%", "100%").forEach { level ->
                            OptionChip(level, selectedIce == level, "") { selectedIce = level }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // TOPPINGS
                OptionSection(title = "Thêm Topping", icon = "🧋") {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        toppingState.forEachIndexed { index, topping ->
                            ToppingItem(topping) { checked ->
                                toppingState[index] = topping.copy(selected = checked)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Price + Add to Cart
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Tổng cộng", style = MaterialTheme.typography.labelSmall, color = AppColors.TextLight)
                        val extraTopping = toppingState.filter { it.selected }.sumOf { it.price }
                        val sizeExtra    = if (selectedSize == "Lớn") 10000 else 0
                        Text(
                            "${"%,d".format(product.price + extraTopping + sizeExtra)}đ",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = AppColors.Brown
                        )
                    }
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.Brown),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(horizontal = 28.dp, vertical = 14.dp),
                        modifier = Modifier.height(52.dp)
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Thêm vào giỏ", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold))
                    }
                }

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun OptionSection(title: String, icon: String, content: @Composable () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(icon, fontSize = 16.sp)
        Spacer(Modifier.width(6.dp))
        Text(
            title,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
            color = AppColors.TextDark
        )
    }
    Spacer(Modifier.height(10.dp))
    content()
}

@Composable
fun OptionChip(label: String, selected: Boolean, extra: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) AppColors.Brown else AppColors.CreamDark)
            .border(
                width = 1.5.dp,
                color = if (selected) AppColors.Brown else AppColors.WarmGray,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                label,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                color = if (selected) AppColors.White else AppColors.TextMid
            )
            if (extra.isNotEmpty()) {
                Text(
                    extra,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (selected) AppColors.White.copy(alpha = 0.8f) else AppColors.TextLight
                )
            }
        }
    }
}

@Composable
fun ToppingItem(topping: Topping, onChecked: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (topping.selected) AppColors.Cream else AppColors.White)
            .border(1.dp, if (topping.selected) AppColors.BrownMid else AppColors.CreamDark, RoundedCornerShape(10.dp))
            .clickable { onChecked(!topping.selected) }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = topping.selected,
            onCheckedChange = { onChecked(it) },
            colors = CheckboxDefaults.colors(
                checkedColor = AppColors.Brown,
                uncheckedColor = AppColors.WarmGray,
                checkmarkColor = AppColors.White
            ),
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            topping.name,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
            color = AppColors.TextDark,
            modifier = Modifier.weight(1f)
        )
        Text(
            "+${"%,d".format(topping.price)}đ",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
            color = AppColors.Brown
        )
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(AppColors.CreamDark, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Outlined.LocalCafe,
                contentDescription = null,
                tint = AppColors.BrownMid,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}