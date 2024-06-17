package com.thomas200593.mini_retail_app.core.data.local.session

import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.features.auth.entity.UserData

sealed interface SessionState{
    data object Loading: SessionState
    data class Valid(val userData: UserData): SessionState
    data class Invalid(val throwable: Throwable?, @StringRes val reason: Int): SessionState
}