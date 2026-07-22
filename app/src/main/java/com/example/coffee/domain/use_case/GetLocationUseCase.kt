package com.example.coffee.domain.use_case

import com.example.coffee.domain.repository.LocationTracker
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationTracker: LocationTracker
) {
    suspend operator fun invoke(): String? {
        return locationTracker.getCurrentLocation()
    }
}
