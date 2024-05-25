package com.thomas200593.mini_retail_app.core.design_system.dispatchers

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val appDispatchers: AppDispatchers)

enum class AppDispatchers{
    Default,
    IO,
}