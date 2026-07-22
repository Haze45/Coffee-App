package com.example.coffee.data.repository

import android.app.Application
import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import com.example.coffee.domain.repository.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationTrackerImpl @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {

    override suspend fun getCurrentLocation(): String? {
        // 1. Permission and GPS checks
        val hasAccessFineLocationPermission = androidx.core.content.ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = androidx.core.content.ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED

        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!(hasAccessCoarseLocationPermission || hasAccessFineLocationPermission) || !isGpsEnabled) {
            return null
        }

        // 2. Reliability: Try to get a fresh location, fallback to last known
        val location = try {
            locationClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY, 
                null
            ).await() ?: locationClient.lastLocation.await()
        } catch (e: Exception) {
            null
        }

        // 3. Threading: Run Geocoder on IO thread to prevent UI freezing
        return withContext(Dispatchers.IO) {
            location?.let { loc ->
                val geocoder = Geocoder(application, Locale.getDefault())
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        // Use modern Async API for API 33+
                        suspendCancellableCoroutine { continuation ->
                            geocoder.getFromLocation(loc.latitude, loc.longitude, 1) { addresses ->
                                val result = addresses.firstOrNull()?.let { 
                                    "${it.locality ?: it.subAdminArea}, ${it.adminArea}"
                                }
                                continuation.resume(result)
                            }
                        }
                    } else {
                        // Legacy Blocking API
                        @Suppress("DEPRECATION")
                        val addresses = geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
                        addresses?.firstOrNull()?.let { 
                            "${it.locality ?: it.subAdminArea}, ${it.adminArea}"
                        }
                    }
                } catch (e: Exception) {
                    null
                }
            }
        }
    }
}
