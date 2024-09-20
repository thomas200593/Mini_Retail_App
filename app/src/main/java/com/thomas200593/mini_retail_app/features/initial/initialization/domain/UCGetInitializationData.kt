package com.thomas200593.mini_retail_app.features.initial.initialization.domain

import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.RepoIndustries
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.domain.UCGetConfGenLanguage
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.Initialization
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetInitializationData @Inject constructor(
    private val ucGetConfGenLanguage: UCGetConfGenLanguage,
    private val repoIndustries: RepoIndustries,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
){
    operator fun invoke() = combine(
        ucGetConfGenLanguage.invoke(),
        repoIndustries.getRefData()
    ){ confGenLang, industries ->
        Success(
            data = Initialization(
                configCurrent = confGenLang.configCurrent,
                languages = confGenLang.languages,
                industries = industries
            )
        )
    }.flowOn(ioDispatcher)
}