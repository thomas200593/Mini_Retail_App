package com.thomas200593.mini_retail_app.core.ui.component

import androidx.compose.runtime.Composable
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.auth.entity.UserData

object SessionUiHandler {
    @Composable
    fun MonitorSession(
        sessionState: RequestState<UserData?>,
        onError: (Throwable) -> Unit,
        onLoading: () -> Unit,
        onSessionInvalid: () -> Unit,
        onSessionValid: (UserData) -> Unit
    ) {
        when(sessionState){
            RequestState.Idle -> Unit
            RequestState.Loading -> {
                onLoading()
            }
            is RequestState.Error -> {
                val error = sessionState.t
                onError(error)
            }
            is RequestState.Success -> {
                val data = sessionState.data
                if(data != null){
                    onSessionValid(data)
                }else{
                    onSessionInvalid()
                }
            }
        }
    }
}