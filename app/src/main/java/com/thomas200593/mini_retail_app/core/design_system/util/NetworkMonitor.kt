package com.thomas200593.mini_retail_app.core.design_system.util

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isNetworkOnline: Flow<Boolean>
}