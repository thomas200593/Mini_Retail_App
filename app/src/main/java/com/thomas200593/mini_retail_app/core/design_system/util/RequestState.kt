package com.thomas200593.mini_retail_app.core.design_system.util

sealed class RequestState<out T> {
    data object Idle: RequestState<Nothing>()
    data object Loading: RequestState<Nothing>()
    data class Success<out T>(val data : T?) : RequestState<T>()
    data class Error(val t : Throwable) : RequestState<Nothing>()
}