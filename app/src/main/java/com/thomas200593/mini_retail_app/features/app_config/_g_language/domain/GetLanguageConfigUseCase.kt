package com.thomas200593.mini_retail_app.features.app_config._g_language.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.app_config.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.app_config.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.app_config._g_language.repository.RepositoryAppCfgGeneralLanguage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLanguageConfigUseCase @Inject constructor(
    private val appCfgRepository: AppConfigRepository,
    private val repositoryAppCfgGeneralLanguage: RepositoryAppCfgGeneralLanguage,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {
    fun invoke() = combine(
        appCfgRepository.configCurrent, flow { emit(repositoryAppCfgGeneralLanguage.getLanguages()) }
    ){ configCurrent, languages ->
        ResourceState.Success(
            data = AppConfig.ConfigLanguages(configCurrent = configCurrent, languages = languages)
        )
    }.flowOn(ioDispatcher).catch { t -> ResourceState.Error(t) }.map { it }
}