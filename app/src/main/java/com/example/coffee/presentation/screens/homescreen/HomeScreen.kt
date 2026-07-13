package com.example.coffee.presentation.screens.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffee.R
import com.example.coffee.domain.model.Product
import com.example.coffee.presentation.ui_components.MyBottomNavBar

@Composable
fun HomeScreen(navController: NavController) {
    val location = "Bokaro Steel City, Jharkhand"
    Scaffold(
        bottomBar = { MyBottomNavBar(navController, "Home") }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f / 3f)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF303030),
                            Color(0xFF1F1F1F),
                            Color(0xFF121212)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(innerPadding)
        ) {

            val product = listOf(
                Product(
                    id = 1,
                    name = "Espresso",
                    description = "Strong",
                    100.0,
                    imageRes = R.drawable.coffee_1
                ),
                Product(
                    id = 2,
                    name = "Cappuccino",
                    description = "Medium",
                    10.0,
                    imageRes = R.drawable.coffee_2
                ),
                Product(
                    id = 3,
                    name = "Latte",
                    description = "Medium",
                    80.0,
                    imageRes = R.drawable.coffee_3
                ),
                Product(
                    id = 4,
                    name = "Macchiato",
                    description = "Medium",
                    70.0,
                    imageRes = R.drawable.coffee_4
                ),
                Product(
                    id = 5,
                    name = "Mocha",
                    description = "Medium",
                    90.0,
                    imageRes = R.drawable.coffee_5
                ),
                Product(
                    id = 6,
                    name = "Americano",
                    description = "Medium",
                    40.0,
                    imageRes = R.drawable.coffee_6
                )
            )

            ProductsGrid(products = product, navController = navController) {
                Text(
                    text = "Location",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = location,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Change Location",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))

                MySearchBar()

                Spacer(modifier = Modifier.height(40.dp))
                Image(
                    painter = painterResource(id = R.drawable.banner_1),
                    contentDescription = "Home Banner"
                )

                Spacer(modifier = Modifier.height(14.dp))

                HomeScreenCategories()
            }

        }

    }
}
