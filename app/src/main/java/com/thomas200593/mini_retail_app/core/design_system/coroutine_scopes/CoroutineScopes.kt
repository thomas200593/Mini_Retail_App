package com.thomas200593.mini_retail_app.core.design_system.coroutine_scopes

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CoroutineScopes(val options: CoroutineScopesJob.Options)
