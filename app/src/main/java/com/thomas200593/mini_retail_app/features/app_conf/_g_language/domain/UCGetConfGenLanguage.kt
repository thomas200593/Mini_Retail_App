package com.thomas200593.mini_retail_app.features.app_conf._g_language.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.app_conf._g_language.repository.RepoConfGenLanguage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UCGetConfGenLanguage @Inject constructor(
    private val appCfgRepository: RepoAppConf,
    private val repoConfGenLanguage: RepoConfGenLanguage,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {
    fun invoke() = combine(
        appCfgRepository.configCurrent, flow { emit(repoConfGenLanguage.getLanguages()) }
    ){ configCurrent, languages ->
        ResourceState.Success(
            data = AppConfig.ConfigLanguages(configCurrent = configCurrent, languages = languages)
        )
    }.flowOn(ioDispatcher).catch { t -> ResourceState.Error(t) }.map { it }
}