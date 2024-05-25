package com.thomas200593.mini_retail_app.features.auth.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val isSessionValid : Flow<Boolean>
}