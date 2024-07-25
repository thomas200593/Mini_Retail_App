package com.thomas200593.mini_retail_app.core.design_system.util

import kotlinx.coroutines.flow.MutableStateFlow

object HlpStateFlow{
    inline fun <T> MutableStateFlow<T>.update(function: (T) -> T) {
        while (true) {
            val prevValue = value
            val nextValue = function(prevValue)
            if (compareAndSet(prevValue, nextValue)) { return }
        }
    }
}