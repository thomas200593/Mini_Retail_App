package com.thomas200593.mini_retail_app.core.design_system.util

sealed class ResourceState<out T> {
    data object Idle: ResourceState<Nothing>()
    data object Loading: ResourceState<Nothing>()
    data object Empty: ResourceState<Nothing>()
    data class Success<out T>(val data : T) : ResourceState<T>()
    data class Error(val t : Throwable) : ResourceState<Nothing>()
}