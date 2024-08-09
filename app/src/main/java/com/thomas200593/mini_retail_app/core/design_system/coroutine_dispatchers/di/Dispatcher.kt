package com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatchers: Dispatchers.Dispatchers)