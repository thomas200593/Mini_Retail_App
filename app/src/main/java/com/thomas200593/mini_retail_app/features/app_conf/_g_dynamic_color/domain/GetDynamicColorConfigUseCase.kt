package com.thomas200593.mini_retail_app.features.app_conf._g_dynamic_color.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.app_conf._g_dynamic_color.repository.RepositoryAppCfgGeneralDynamicColor
import com.thomas200593.mini_retail_app.features.app_conf._g_dynamic_color.entity.ConfigDynamicColor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDynamicColorConfigUseCase @Inject constructor(
    private val appCfgRepository: RepoAppConf,
    private val repositoryAppCfgGeneralDynamicColor: RepositoryAppCfgGeneralDynamicColor,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
){
    operator fun invoke() = combine(
        appCfgRepository.configCurrent, flow { emit(repositoryAppCfgGeneralDynamicColor.getDynamicColors()) }
    ){ configCurrent, dynamicColors ->
        ResourceState.Success(
            data = ConfigDynamicColor(configCurrent = configCurrent, dynamicColors = dynamicColors)
        )
    }.flowOn(ioDispatcher).catch { t -> ResourceState.Error(t) }.map { it }
}