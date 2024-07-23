package com.thomas200593.mini_retail_app.features.app_conf._g_font_size.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.app_conf._g_font_size.repository.RepositoryAppCfgGeneralFontSize
import com.thomas200593.mini_retail_app.features.app_conf._g_font_size.entity.ConfigFontSizes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFontSizeConfigUseCase @Inject constructor(
    private val appCfgRepository: RepoAppConf,
    private val repositoryAppCfgGeneralFontSize: RepositoryAppCfgGeneralFontSize,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {
    fun invoke() = combine(
        appCfgRepository.configCurrent, flow { emit(repositoryAppCfgGeneralFontSize.getFontSizes()) }
    ){ configCurrent, fontSizes ->
        ResourceState.Success(
            data = ConfigFontSizes(configCurrent = configCurrent, fontSizes = fontSizes)
        )
    }.flowOn(ioDispatcher).catch { t -> ResourceState.Error(t) }.map { it }
}