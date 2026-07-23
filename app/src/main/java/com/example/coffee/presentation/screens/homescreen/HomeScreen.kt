package com.example.coffee.presentation.screens.homescreen

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.coffee.R
import com.example.coffee.presentation.ui_components.MyBottomNavBar
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val gpsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            viewModel.onEvent(HomeEvent.DetectLocation)
        }
    }

    fun checkGpsAndDetectLocation() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(context)

        settingsClient.checkLocationSettings(builder.build())
            .addOnSuccessListener {
                viewModel.onEvent(HomeEvent.DetectLocation)
            }
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution.intentSender).build()
                        gpsLauncher.launch(intentSenderRequest)
                    } catch (e: Exception) {}
                }
            }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            checkGpsAndDetectLocation()
        }
    }

    Scaffold(
        bottomBar = { MyBottomNavBar(navController, "Home") },
        containerColor = MaterialTheme.colorScheme.background // Ensure Scaffold uses theme background
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Updated Background gradient to be theme-aware
            val headerColor = if (MaterialTheme.colorScheme.onBackground == Color.White) {
                // Dark Mode Colors
                listOf(Color(0xFF0C0F14), Color(0xFF141921))
            } else {
                // Light Mode Colors (Darker brown to keep text visible)
                listOf(Color(0xFF313131), Color(0xFF121212))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f / 3f)
                    .background(brush = Brush.linearGradient(colors = headerColor))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding())
                    .padding(bottom = innerPadding.calculateBottomPadding())
                    .padding(horizontal = 16.dp)
            ) {
                ProductsGrid(
                    products = state.products,
                    navController = navController,
                    onEvent = viewModel::onEvent
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Location",
                            color = Color.LightGray,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                locationPermissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                )
                            }
                        ) {
                            Text(
                                text = state.location,
                                color = Color.White, // Kept white as it's on a dark gradient always
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

                        MySearchBar(
                            value = state.searchText,
                            onValueChange = { viewModel.onEvent(HomeEvent.OnSearchTextChange(it)) }
                        )

                        Spacer(modifier = Modifier.height(30.dp))
                        Image(
                            painter = painterResource(id = R.drawable.banner_1),
                            contentDescription = "Home Banner",
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        HomeScreenCategories(
                            selectedCategory = state.selectedCategory,
                            onCategorySelected = { category ->
                                viewModel.onEvent(HomeEvent.OnCategorySelected(category))
                            }
                        )
                    }
                }
            }
        }
    }
}
