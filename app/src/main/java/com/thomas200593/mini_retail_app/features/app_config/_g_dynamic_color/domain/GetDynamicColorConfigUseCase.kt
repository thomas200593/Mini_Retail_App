package com.thomas200593.mini_retail_app.features.app_config._g_dynamic_color.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.app_config.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.app_config.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.app_config._g_dynamic_color.repository.RepositoryAppCfgGeneralDynamicColor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDynamicColorConfigUseCase @Inject constructor(
    private val appCfgRepository: AppConfigRepository,
    private val repositoryAppCfgGeneralDynamicColor: RepositoryAppCfgGeneralDynamicColor,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
){
    operator fun invoke() = combine(
        appCfgRepository.configCurrent, flow { emit(repositoryAppCfgGeneralDynamicColor.getDynamicColors()) }
    ){ configCurrent, dynamicColors ->
        ResourceState.Success(
            data = AppConfig.ConfigDynamicColor(configCurrent = configCurrent, dynamicColors = dynamicColors)
        )
    }.flowOn(ioDispatcher).catch { t -> ResourceState.Error(t) }.map { it }
}