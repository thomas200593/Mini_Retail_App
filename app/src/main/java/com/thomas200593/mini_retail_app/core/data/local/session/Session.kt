package com.thomas200593.mini_retail_app.core.data.local.session

import kotlinx.coroutines.flow.Flow

interface Session{
    val currentUserSession: Flow<SessionState>
}