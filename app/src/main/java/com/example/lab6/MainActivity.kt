package com.example.lab6

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            val viewModel: OrderViewModel = viewModel()

            val context = LocalContext.current

            MaterialTheme {

                NavHost(
                    navController = navController,
                    startDestination = CupcakeScreen.Start.name
                ) {

                    // START SCREEN
                    composable(CupcakeScreen.Start.name) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFFFFD6E8),
                                            Color.White
                                        )
                                    )
                                )
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp)
                                    .verticalScroll(rememberScrollState()),

                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {

                                Card(
                                    shape = RoundedCornerShape(30.dp),
                                    elevation = CardDefaults.cardElevation(10.dp)
                                ) {

                                    Column(
                                        modifier = Modifier
                                            .padding(25.dp),

                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {

                                        Image(
                                            painter = painterResource(
                                                id = R.drawable.cupcake
                                            ),
                                            contentDescription = null,
                                            modifier = Modifier.size(180.dp)
                                        )

                                        Spacer(
                                            modifier = Modifier.height(15.dp)
                                        )

                                        Text(
                                            text = "Order Cupcakes",
                                            fontSize = 30.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFD81B60)
                                        )

                                        Spacer(
                                            modifier = Modifier.height(25.dp)
                                        )

                                        BeautifulButton(
                                            text = "1 Cupcake"
                                        ) {

                                            viewModel.setQuantity(1)

                                            navController.navigate(
                                                CupcakeScreen.Flavor.name
                                            )
                                        }

                                        Spacer(
                                            modifier = Modifier.height(12.dp)
                                        )

                                        BeautifulButton(
                                            text = "6 Cupcakes"
                                        ) {

                                            viewModel.setQuantity(6)

                                            navController.navigate(
                                                CupcakeScreen.Flavor.name
                                            )
                                        }

                                        Spacer(
                                            modifier = Modifier.height(12.dp)
                                        )

                                        BeautifulButton(
                                            text = "12 Cupcakes"
                                        ) {

                                            viewModel.setQuantity(12)

                                            navController.navigate(
                                                CupcakeScreen.Flavor.name
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // FLAVOR SCREEN
                    composable(CupcakeScreen.Flavor.name) {

                        SelectionScreen(
                            title = "Choose Flavor",
                            options = listOf(
                                "Vanilla",
                                "Chocolate",
                                "Red Velvet",
                                "Strawberry"
                            ),
                            onSelect = {

                                viewModel.setFlavor(it)

                                navController.navigate(
                                    CupcakeScreen.Pickup.name
                                )
                            }
                        )
                    }

                    // PICKUP SCREEN
                    composable(CupcakeScreen.Pickup.name) {

                        SelectionScreen(
                            title = "Pickup Date",
                            options = listOf(
                                "Today",
                                "Tomorrow",
                                "Next Week"
                            ),
                            onSelect = {

                                viewModel.setDate(it)

                                navController.navigate(
                                    CupcakeScreen.Summary.name
                                )
                            }
                        )
                    }

                    // SUMMARY SCREEN
                    composable(CupcakeScreen.Summary.name) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFFFFE0F0),
                                            Color.White
                                        )
                                    )
                                )
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp),

                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {

                                Card(
                                    shape = RoundedCornerShape(30.dp),
                                    elevation = CardDefaults.cardElevation(10.dp)
                                ) {

                                    Column(
                                        modifier = Modifier
                                            .padding(25.dp),

                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {

                                        Icon(
                                            imageVector = Icons.Default.Cake,
                                            contentDescription = null,
                                            tint = Color(0xFFD81B60),
                                            modifier = Modifier.size(80.dp)
                                        )

                                        Spacer(
                                            modifier = Modifier.height(20.dp)
                                        )

                                        Text(
                                            text = "Order Summary",
                                            fontSize = 28.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFD81B60)
                                        )

                                        Spacer(
                                            modifier = Modifier.height(20.dp)
                                        )

                                        SummaryText(
                                            "Quantity",
                                            "${viewModel.quantity.value}"
                                        )

                                        SummaryText(
                                            "Flavor",
                                            viewModel.flavor.value
                                        )

                                        SummaryText(
                                            "Pickup",
                                            viewModel.date.value
                                        )

                                        SummaryText(
                                            "Price",
                                            viewModel.price.value
                                        )

                                        Spacer(
                                            modifier = Modifier.height(25.dp)
                                        )

                                        BeautifulButton(
                                            text = "Send Order"
                                        ) {

                                            val summary = """
                                                Quantity: ${viewModel.quantity.value}
                                                Flavor: ${viewModel.flavor.value}
                                                Pickup: ${viewModel.date.value}
                                                Price: ${viewModel.price.value}
                                            """.trimIndent()

                                            val intent =
                                                Intent(Intent.ACTION_SEND)

                                            intent.type = "text/plain"

                                            intent.putExtra(
                                                Intent.EXTRA_TEXT,
                                                summary
                                            )

                                            context.startActivity(
                                                Intent.createChooser(
                                                    intent,
                                                    "Send Order"
                                                )
                                            )
                                        }

                                        Spacer(
                                            modifier = Modifier.height(12.dp)
                                        )

                                        OutlinedButton(
                                            onClick = {

                                                viewModel.reset()

                                                navController.popBackStack(
                                                    CupcakeScreen.Start.name,
                                                    false
                                                )
                                            },
                                            modifier = Modifier.fillMaxWidth()
                                        ) {

                                            Text("Cancel")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
