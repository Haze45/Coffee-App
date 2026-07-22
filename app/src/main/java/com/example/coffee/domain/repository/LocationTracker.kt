package com.example.coffee.domain.repository

interface LocationTracker {
    suspend fun getCurrentLocation(): String?
}
