package com.example.doan


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.draw.scale
// ─────────────────────────────────────────────
//  ROOT — Bottom Nav Shell
// ─────────────────────────────────────────────
@Composable
fun AdminApp() {
    var currentTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = { AdminBottomNav(currentTab) { currentTab = it } },
        containerColor = BgCream
    ) { padding ->
        Box(Modifier.padding(padding)) {
            when (currentTab) {
                0 -> HomeScreen()
                1 -> OrdersScreen()
                2 -> StockScreen()
                3 -> ReportScreen()
            }
        }
    }
}

@Composable
fun AdminBottomNav(selected: Int, onSelect: (Int) -> Unit) {
    val items = listOf(
        Triple(Icons.Outlined.Home,         Icons.Filled.Home,         "HOME"),
        Triple(Icons.Outlined.Receipt,      Icons.Filled.Receipt,      "ORDERS"),
        Triple(Icons.Outlined.Inventory2,   Icons.Filled.Inventory2,   "STOCK"),
        Triple(Icons.Outlined.BarChart,     Icons.Filled.BarChart,     "REPORT"),
    )
    NavigationBar(
        containerColor = White,
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .shadow(8.dp, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        items.forEachIndexed { idx, (outlineIcon, filledIcon, label) ->
            NavigationBarItem(
                selected = selected == idx,
                onClick  = { onSelect(idx) },
                icon = {
                    Icon(
                        imageVector = if (selected == idx) filledIcon else outlineIcon,
                        contentDescription = label,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(label, fontSize = 9.sp, letterSpacing = 0.5.sp)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = TeaBrown,
                    selectedTextColor   = TeaBrown,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor      = TeaBrownPale
                )
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  SCREEN 1 — HOME / OVERVIEW
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun HomeScreen() {
    val recentOrders = listOf(
        listOf("#NL-9482", "An Nguyen",   "Oolong Milk Tea"),
        listOf("#NL-9483", "Minh Hoang",  "Matcha Foam Tea (M)"),
        listOf("#NL-9484", "Thuy Duong",  "Brown Sugar Boba"),
        listOf("#NL-9485", "Khanh Linh",  "Classic Milk Tea (XL)"),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
            .verticalScroll(rememberScrollState())
    ) {
        // ── Top Bar ──────────────────────────────
        AdminTopBar(title = "Overview", subtitle = "MORNING, ADMINISTRATOR")

        Column(Modifier.padding(horizontal = 16.dp)) {

            // ── Stat Cards ───────────────────────
            Spacer(Modifier.height(16.dp))
            StatCard(
                label = "TODAY'S REVENUE",
                value = "5,280k VND",
                badge = "+12.5%",
                badgePositive = true,
                iconBg = Color(0xFFFCE4D6),
                icon = Icons.Default.AttachMoney
            )
            Spacer(Modifier.height(12.dp))
            StatCard(
                label = "TOTAL ORDERS",
                value = "142",
                badge = "+6.2%",
                badgePositive = true,
                iconBg = Color(0xFFFFE0B2),
                icon = Icons.Default.ShoppingCart
            )
            Spacer(Modifier.height(12.dp))
            StatCard(
                label = "NEW CUSTOMERS",
                value = "36",
                badge = "+1k",
                badgePositive = true,
                iconBg = Color(0xFFE0F2F1),
                icon = Icons.Default.People
            )

            // ── Recent Orders ────────────────────
            Spacer(Modifier.height(24.dp))
            Text("Recent Orders", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text("Real-time update of your shop performance.", fontSize = 12.sp, color = TextSecondary)
            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {},
                colors  = ButtonDefaults.buttonColors(containerColor = TeaBrown),
                shape   = RoundedCornerShape(8.dp),
                modifier = Modifier.height(36.dp)
            ) {
                Icon(Icons.Default.Upload, null, Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text("Export Report", fontSize = 13.sp)
            }

            Spacer(Modifier.height(12.dp))

            // Table header
            Card(
                shape  = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(TeaBrownPale)
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        TableHeaderCell("ORDER ID", Modifier.weight(1.2f))
                        TableHeaderCell("CUSTOMER",  Modifier.weight(1.2f))
                        TableHeaderCell("PRODUCT",   Modifier.weight(1.6f))
                    }
                    recentOrders.forEachIndexed { idx, row ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .background(if (idx % 2 == 0) White else BgCream)
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(row[0], fontSize = 12.sp, color = TeaBrown, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1.2f))
                            Row(Modifier.weight(1.2f), verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(TeaBrownLight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(row[1].first().toString(), fontSize = 10.sp, color = White, fontWeight = FontWeight.Bold)
                                }
                                Spacer(Modifier.width(4.dp))
                                Text(row[1], fontSize = 11.sp, color = TextPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                            Text(row[2], fontSize = 11.sp, color = TextSecondary, modifier = Modifier.weight(1.6f), maxLines = 2, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun StatCard(label: String, value: String, badge: String, badgePositive: Boolean, iconBg: Color, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Card(
        shape  = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, Modifier.size(24.dp), tint = TeaBrown)
            }
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(label, fontSize = 11.sp, letterSpacing = 0.5.sp, color = TextSecondary)
                Text(value, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            }
            Box(
                Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (badgePositive) GreenLight else Color(0xFFFFEBEE))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(badge, fontSize = 12.sp, color = if (badgePositive) GreenTeal else StatusDelayed, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  SCREEN 2 — ORDER MANAGEMENT
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun OrdersScreen() {
    var selectedFilter by remember { mutableStateOf(0) }
    val filters = listOf("In Progress", "Completed", "All Orders")

    val orders = listOf(
        OrderData("#NL-8842", "Minh Hoang Nguyen", listOf("Oolong Milk Tea ×2", "Ceremonial Matcha Latte ×1"), "185.000đ", "BREWING",  true),
        OrderData("#NL-8861", "Elena Rodriguez",   listOf("Brown Sugar Boba ×1"),                               "65.000đ",  "PENDING",  false),
        OrderData("#NL-8860", "Tran Kieu Anh",      listOf("Jasmine Green Tea ×4"),                             "140.000đ", "DELAYED",  false),
        OrderData("#NL-8835", "Liam Carter",        listOf("Traditional Milk Tea ×2"),                          "110.000đ", "BREWING",  true),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
    ) {
        AdminTopBar(title = "Order Management", subtitle = "Managing the heart of your digital tea house.")

        // Filter tabs
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filters.forEachIndexed { idx, label ->
                val sel = selectedFilter == idx
                Box(
                    Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (sel) TeaBrown else White)
                        .border(1.dp, if (sel) TeaBrown else DividerColor, RoundedCornerShape(8.dp))
                        .clickable { selectedFilter = idx }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(label, fontSize = 12.sp, color = if (sel) White else TextSecondary, fontWeight = if (sel) FontWeight.Bold else FontWeight.Normal)
                }
            }
        }

        LazyColumn(
            contentPadding    = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(orders) { order -> OrderCard(order) }

            // Kitchen Overview card
            item {
                Spacer(Modifier.height(4.dp))
                KitchenOverviewCard()
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

data class OrderData(
    val id: String, val customer: String, val items: List<String>,
    val total: String, val status: String, val canComplete: Boolean
)

@Composable
fun OrderCard(order: OrderData) {
    val statusColor = when (order.status) {
        "BREWING"   -> StatusBrewing
        "PENDING"   -> StatusPending
        "DELAYED"   -> StatusDelayed
        else        -> StatusCompleted
    }
    Card(
        shape  = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(order.id, fontSize = 11.sp, color = TextSecondary)
                Spacer(Modifier.weight(1f))
                Box(
                    Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(statusColor.copy(alpha = 0.12f))
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text("● ${order.status}", fontSize = 11.sp, color = statusColor, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(order.customer, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Spacer(Modifier.height(10.dp))
            order.items.forEach { item ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 3.dp)) {
                    Box(
                        Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(TeaBrownPale),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🍵", fontSize = 14.sp)
                    }
                    Spacer(Modifier.width(10.dp))
                    Text(item, fontSize = 13.sp, color = TextPrimary)
                }
            }
            Spacer(Modifier.height(12.dp))
            Divider(color = DividerColor)
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("TOTAL AMOUNT", fontSize = 10.sp, color = TextSecondary, letterSpacing = 0.5.sp)
                    Text(order.total, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TeaBrown)
                }
                Button(
                    onClick = {},
                    colors  = ButtonDefaults.buttonColors(containerColor = if (order.canComplete) TeaBrown else Color(0xFF757575)),
                    shape   = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text(if (order.canComplete) "Complete Order" else if (order.status == "PENDING") "Process Order" else "Update Status", fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun KitchenOverviewCard() {
    Card(
        shape  = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TeaBrown),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(20.dp)) {
            Text("Kitchen\nOverview", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = White)
            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                KitchenStat("ORDERS IN\nQUEUE", "12", Modifier.weight(1f))
                KitchenStat("AVG. PREP\nTIME", "8 min", Modifier.weight(1f))
            }
            Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(3) {
                    Box(
                        Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(TeaBrownLight)
                            .border(2.dp, TeaBrown, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👤", fontSize = 14.sp)
                    }
                    if (it < 2) Spacer(Modifier.width((-8).dp))
                }
                Spacer(Modifier.width(12.dp))
                Box(
                    Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(TeaBrownLight)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) { Text("+3", fontSize = 12.sp, color = White, fontWeight = FontWeight.Bold) }
                Spacer(Modifier.width(8.dp))
                Text("5 Baristas Active Now", fontSize = 12.sp, color = White.copy(alpha = 0.8f))
            }
        }
    }
}

@Composable
fun KitchenStat(label: String, value: String, modifier: Modifier) {
    Box(
        modifier
            .clip(RoundedCornerShape(12.dp))
            .background(TeaBrownDark)
            .padding(12.dp)
    ) {
        Column {
            Text(label, fontSize = 10.sp, color = White.copy(alpha = 0.7f), letterSpacing = 0.5.sp)
            Spacer(Modifier.height(4.dp))
            Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = White)
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  SCREEN 3 — TEA COLLECTION / STOCK
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun StockScreen() {
    data class TeaItem(
        val name: String, val price: String, val description: String,
        val status: String, val badge: String?, val emoji: String
    )

    val teas = listOf(
        TeaItem("Uji Matcha Latte",        "55,000đ", "Ceremonial grade Uji matcha whisked with silky steamed milk and a hint of honey.", "INSTOCK",    "MATCHA",     "🍵"),
        TeaItem("Roasted Oolong",          "48,000đ", "Deeply roasted tea leaves providing a smoky, nutty flavor profile with a clean finish.", "INSTOCK", "OOLONG",     "🫖"),
        TeaItem("Hibiscus Berry",          "62,000đ", "A refreshing summer blend of tart hibiscus flowers and sweet wildberries.", "OUTOFSTOCK", null,         "🌺"),
        TeaItem("Classic Signature Milk Tea", "42,000đ", "Our proprietary blend of high-mountain black tea and creamy fresh milk, served with house-made honey boba pearls.", "INSTOCK", "BESTSELLER", "🧋"),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
    ) {
        // Header
        Row(
            Modifier
                .fillMaxWidth()
                .background(White)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Menu, null, Modifier.size(22.dp), tint = TextPrimary)
            Spacer(Modifier.width(10.dp))
            Text("Trà Sữa NL Admin", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary, modifier = Modifier.weight(1f))
            Icon(Icons.Default.AccountCircle, null, Modifier.size(30.dp), tint = TeaBrown)
        }

        Column(
            Modifier
                .fillMaxWidth()
                .background(White)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        ) {
            Text("Tea Collection", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TeaBrown, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
            Text("Curate and manage your artisanal tea blends.\nAdjust availability and pricing for the season.", fontSize = 13.sp, color = TextSecondary, lineHeight = 20.sp)
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(
                    onClick = {},
                    shape  = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, DividerColor),
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(Icons.Default.FilterList, null, Modifier.size(16.dp), tint = TextSecondary)
                    Spacer(Modifier.width(4.dp))
                    Text("Filter", fontSize = 13.sp, color = TextSecondary)
                }
                Button(
                    onClick = {},
                    colors  = ButtonDefaults.buttonColors(containerColor = TeaBrown),
                    shape   = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(Icons.Default.Add, null, Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("New Product", fontSize = 13.sp)
                }
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(teas) { tea ->
                TeaProductCard(tea.name, tea.price, tea.description, tea.status, tea.badge, tea.emoji)
            }
        }
    }
}

@Composable
fun TeaProductCard(name: String, price: String, description: String, status: String, badge: String?, emoji: String) {
    val isInStock = status == "INSTOCK"
    Card(
        shape  = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            // Image area
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                if (isInStock) Color(0xFF4A7C59) else Color(0xFF5A5A5A),
                                if (isInStock) Color(0xFF2C4A35) else Color(0xFF3A3A3A)
                            )
                        )
                    )
            ) {
                Text(emoji, fontSize = 64.sp, modifier = Modifier.align(Alignment.Center))
                if (badge != null) {
                    Box(
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (badge == "BESTSELLER") Color(0xFFFF8F00) else GreenTeal)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(badge, fontSize = 10.sp, color = White, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                    }
                }
                if (status == "SEASONAL END") {
                    Box(
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 12.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0x99000000))
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Text("SEASONAL END", fontSize = 11.sp, color = White, letterSpacing = 1.sp)
                    }
                }
            }

            Column(Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary, modifier = Modifier.weight(1f))
                    Text(price, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TeaBrown)
                }
                Spacer(Modifier.height(6.dp))
                Text(description, fontSize = 13.sp, color = TextSecondary, lineHeight = 19.sp)
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(
                        checked  = isInStock,
                        onCheckedChange = {},
                        colors   = SwitchDefaults.colors(
                            checkedThumbColor   = White,
                            checkedTrackColor   = GreenTeal,
                            uncheckedThumbColor = White,
                            uncheckedTrackColor = TextSecondary.copy(alpha = 0.4f)
                        ),
                        modifier = Modifier.scale(0.8f)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        if (isInStock) "INSTOCK" else "OUTOFSTOCK",
                        fontSize = 11.sp,
                        color    = if (isInStock) GreenTeal else TextSecondary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Default.Edit, null, Modifier.size(18.dp), tint = TextSecondary)
                    Spacer(Modifier.width(12.dp))
                    Icon(Icons.Default.Delete, null, Modifier.size(18.dp), tint = StatusDelayed.copy(alpha = 0.7f))
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
//  SCREEN 4 — STATISTICAL INSIGHTS / REPORT
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun ReportScreen() {
    val weekDays    = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
    val barHeights  = listOf(0.45f, 0.60f, 0.50f, 0.80f, 0.70f, 1.00f, 0.65f)
    val revenueBreakdown = listOf(
        Triple("Signature Milk Teas",  0.65f, TeaBrown),
        Triple("Fruit Teas & Coolers", 0.22f, GreenTeal),
        Triple("Toppings & Add-ons",   0.13f, TeaBrownLight),
    )
    val transactions = listOf(
        listOf("#TR-8942", "An Le",   "5 Signature Teas",   "COMPLETED",  "485k"),
        listOf("#TR-8945", "Minh Thu","10 Variety Cups...", "PROCESSING", "1.2M"),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgCream)
            .verticalScroll(rememberScrollState())
    ) {
        AdminTopBar(title = "Statistical Insights", subtitle = "PERFORMANCE OVERVIEW")

        Column(Modifier.padding(horizontal = 16.dp)) {

            // ── Monthly Revenue Card ──────────────
            Spacer(Modifier.height(16.dp))
            Card(
                shape  = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFFCE4D6)),
                        contentAlignment = Alignment.Center
                    ) { Icon(Icons.Default.TrendingUp, null, Modifier.size(24.dp), tint = TeaBrown) }
                    Spacer(Modifier.width(16.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Monthly Revenue", fontSize = 12.sp, color = TextSecondary, letterSpacing = 0.3.sp)
                        Text("124.8M", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text("VND", fontSize = 11.sp, color = TextSecondary)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.TrendingUp, null, Modifier.size(14.dp), tint = GreenTeal)
                            Spacer(Modifier.width(4.dp))
                            Text("+12.4% from last month", fontSize = 11.sp, color = GreenTeal)
                        }
                    }
                    // Mini bar chart
                    Row(
                        Modifier.height(48.dp),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        listOf(0.5f, 0.7f, 0.4f, 0.9f).forEach { h ->
                            Box(
                                Modifier
                                    .width(8.dp)
                                    .fillMaxHeight(h)
                                    .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                                    .background(if (h == 0.9f) TeaBrown else DividerColor)
                            )
                        }
                    }
                }
            }

            // ── Top Seller ────────────────────────
            Spacer(Modifier.height(12.dp))
            Card(
                shape  = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFFFF8E1)),
                        contentAlignment = Alignment.Center
                    ) { Text("⭐", fontSize = 22.sp) }
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text("TOP SELLER", fontSize = 10.sp, letterSpacing = 1.sp, color = TextSecondary)
                        Text("Oolong Milk Tea", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text("1,240 units sold", fontSize = 12.sp, color = TextSecondary)
                    }
                }
            }

            // ── Customer Growth ───────────────────
            Spacer(Modifier.height(12.dp))
            Card(
                shape  = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("CUSTOMER GROWTH", fontSize = 10.sp, letterSpacing = 1.sp, color = TextSecondary)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("842", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Spacer(Modifier.width(8.dp))
                        Text("New Users", fontSize = 14.sp, color = TextSecondary)
                    }
                    Spacer(Modifier.height(8.dp))
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(BgInput)
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth(0.72f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    Brush.horizontalGradient(listOf(GreenTeal, Color(0xFF1B5E20)))
                                )
                        )
                    }
                }
            }

            // ── Weekly Sales Chart ────────────────
            Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Weekly\nSales\nTrends", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary, lineHeight = 26.sp)
                    Spacer(Modifier.height(4.dp))
                    Text("Aggregated revenue performance\nacross 7 days", fontSize = 11.sp, color = TextSecondary)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("WEEKLY" to true, "MONTHLY" to false).forEach { (label, sel) ->
                        Box(
                            Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (sel) TeaBrown else White)
                                .border(1.dp, if (sel) TeaBrown else DividerColor, RoundedCornerShape(6.dp))
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(label, fontSize = 10.sp, color = if (sel) White else TextSecondary, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Card(
                shape  = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        weekDays.forEachIndexed { idx, day ->
                            val h = barHeights[idx]
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    Modifier
                                        .width(24.dp)
                                        .fillMaxHeight(h)
                                        .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                        .background(
                                            if (h == 1.00f)
                                                Brush.verticalGradient(listOf(TeaBrownLight, TeaBrown))
                                            else
                                                Brush.verticalGradient(listOf(DividerColor, BgInput))
                                        )
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        weekDays.forEach { day ->
                            Text(day, fontSize = 9.sp, color = TextSecondary, letterSpacing = 0.3.sp)
                        }
                    }
                }
            }

            // ── Revenue Breakdown ─────────────────
            Spacer(Modifier.height(16.dp))
            Card(
                shape  = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Revenue Breakdown", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Spacer(Modifier.height(12.dp))
                    revenueBreakdown.forEach { (label, fraction, color) ->
                        Column(Modifier.padding(vertical = 6.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(label, fontSize = 13.sp, color = TextPrimary, modifier = Modifier.weight(1f))
                                Text("${(fraction * 100).toInt()}%", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = color)
                            }
                            Spacer(Modifier.height(4.dp))
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(BgInput)
                            ) {
                                Box(
                                    Modifier
                                        .fillMaxWidth(fraction)
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(color)
                                )
                            }
                        }
                    }
                }
            }

            // ── New Season Projection ─────────────
            Spacer(Modifier.height(12.dp))
            Card(
                shape  = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = TeaBrownPale),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("New Season Projection", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Summer season is expected to increase Fruit Tea sales by 40%. Recommend restocking Lychee and Peach syrups.",
                        fontSize = 13.sp, color = TextSecondary, lineHeight = 20.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("View Marketing Plan →", fontSize = 13.sp, color = TeaBrown, fontWeight = FontWeight.Bold)
                }
            }

            // ── Notable Recent Transactions ───────
            Spacer(Modifier.height(16.dp))
            Text("Notable Recent\nTransactions", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Spacer(Modifier.height(12.dp))

            // Search bar
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(White)
                    .border(1.dp, DividerColor, RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Search, null, Modifier.size(18.dp), tint = TextSecondary)
                Spacer(Modifier.width(8.dp))
                Text("Search orders", fontSize = 13.sp, color = TextHint)
            }

            Spacer(Modifier.height(10.dp))
            Card(
                shape  = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(TeaBrownPale)
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        listOf("ORDER ID", "CUSTOMER", "ITEMS", "STATUS", "VALUE").forEach { h ->
                            Text(h, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TextSecondary, letterSpacing = 0.3.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        }
                    }
                    transactions.forEachIndexed { idx, tx ->
                        val statusColor = if (tx[3] == "COMPLETED") StatusCompleted else StatusPending
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .background(if (idx % 2 == 0) White else BgCream)
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(tx[0], fontSize = 10.sp, color = TeaBrown, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                            Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                Box(Modifier.size(20.dp).clip(CircleShape).background(TeaBrownLight), contentAlignment = Alignment.Center) {
                                    Text(tx[1].first().toString(), fontSize = 9.sp, color = White, fontWeight = FontWeight.Bold)
                                }
                            }
                            Text(tx[2], fontSize = 9.sp, color = TextSecondary, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, maxLines = 2)
                            Box(
                                Modifier.weight(1f).clip(RoundedCornerShape(4.dp)).background(statusColor.copy(alpha = 0.12f)).padding(horizontal = 4.dp, vertical = 2.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(tx[3], fontSize = 8.sp, color = statusColor, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                            }
                            Text(tx[4], fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextPrimary, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

// ─────────────────────────────────────────────
//  SHARED COMPONENTS
// ─────────────────────────────────────────────
@Composable
fun AdminTopBar(title: String, subtitle: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Menu, null, Modifier.size(22.dp), tint = TextPrimary)
            Spacer(Modifier.width(10.dp))
            Text("Trà Sữa NL Admin", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary, modifier = Modifier.weight(1f))
            Icon(Icons.Default.AccountCircle, null, Modifier.size(30.dp), tint = TeaBrown)
        }
        Spacer(Modifier.height(8.dp))
        Text(subtitle, fontSize = 10.sp, letterSpacing = 1.sp, color = TextSecondary)
        Text(title, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
    }
}

@Composable
fun TableHeaderCell(text: String, modifier: Modifier) {
    Text(text, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary, letterSpacing = 0.5.sp, modifier = modifier)
}

