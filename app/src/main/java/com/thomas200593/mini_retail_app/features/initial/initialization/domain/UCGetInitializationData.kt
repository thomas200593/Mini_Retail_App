package com.thomas200593.mini_retail_app.features.initial.initialization.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.*
import com.thomas200593.mini_retail_app.features.app_conf._gen_language.domain.UCGetConfGenLanguage
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.Initialization
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UCGetInitializationData @Inject constructor(
    private val ucGetConfGenLanguage: UCGetConfGenLanguage,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
){
    operator fun invoke() = ucGetConfGenLanguage.invoke().flowOn(ioDispatcher).catch { t -> Error(t) }
        .map { langConfig ->
            Success(
                data = Initialization(
                    configCurrent = langConfig.data.configCurrent,
                    languages = langConfig.data.languages
                )
            )
        }
}