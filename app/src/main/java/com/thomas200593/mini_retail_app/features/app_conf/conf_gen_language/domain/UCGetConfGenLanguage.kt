package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.ConfigLanguages
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.repository.RepoConfGenLanguage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetConfGenLanguage @Inject constructor(
    private val repoAppConf: RepoAppConf,
    private val repoConfGenLanguage: RepoConfGenLanguage,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) {
    fun invoke() = combine(
        flow = repoAppConf.configCurrent,
        flow2 = repoConfGenLanguage.getLanguages()
    ){ configCurrent, languages ->
        ConfigLanguages(configCurrent = configCurrent, languages = languages)
    }.flowOn(ioDispatcher)
}