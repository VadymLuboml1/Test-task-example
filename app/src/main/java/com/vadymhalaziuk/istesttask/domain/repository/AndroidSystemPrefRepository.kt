package com.vadymhalaziuk.istesttask.domain.repository

import kotlinx.coroutines.flow.Flow

interface AndroidSystemPrefRepository {
    fun isInternetAvailable(): Boolean
    fun isInternetAvailableFlow(frequency: Long = 500L): Flow<Boolean>
}