package com.thomas200593.mini_retail_app.core.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import kotlinx.coroutines.flow.StateFlow

object SessionHandler {
    @Composable
    fun SessionHandler(
        sessionState: StateFlow<SessionState>,
        onLoading: () -> Unit,
        onInvalid: (Throwable?, Int) -> Unit,
        onValid: (UserData?) -> Unit
    ) {
        val session by sessionState.collectAsStateWithLifecycle()
        LaunchedEffect(key1 = session) {
            when(session){
                SessionState.Loading -> onLoading.invoke()
                is SessionState.Invalid -> {
                    val throwable = (session as SessionState.Invalid).throwable
                    val reason = (session as SessionState.Invalid).reason
                    onInvalid(
                        throwable,
                        reason
                    )
                }
                is SessionState.Valid -> {
                    val userData = (session as SessionState.Valid).userData
                    onValid(userData)
                }
            }
        }
    }
}