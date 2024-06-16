package com.thomas200593.mini_retail_app.core.data.local.session

import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import kotlinx.coroutines.flow.Flow

interface Session{
    val userSession: Flow<RequestState<UserData?>>
}