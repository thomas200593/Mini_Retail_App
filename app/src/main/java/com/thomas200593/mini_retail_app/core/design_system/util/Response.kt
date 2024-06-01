package com.thomas200593.mini_retail_app.core.design_system.util

sealed class Response<out T> {
    data object Loading: Response<Nothing>()
    data class Success<out T>(val data : T?) : Response<T>()
    data class Error(val e : Exception) : Response<Nothing>()
}