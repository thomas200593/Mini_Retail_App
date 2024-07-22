package com.thomas200593.mini_retail_app.features.app_config.cfg_general_font_size.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.app_config.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.app_config.cfg_general_font_size.repository.RepositoryAppCfgGeneralFontSize
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFontSizeConfigUseCase @Inject constructor(
    private val appCfgRepository: AppConfigRepository,
    private val repositoryAppCfgGeneralFontSize: RepositoryAppCfgGeneralFontSize,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {
    fun invoke() = combine(
        appCfgRepository.configCurrent, flow { emit(repositoryAppCfgGeneralFontSize.getFontSizes()) }
    ){ configCurrent, fontSizes ->
        RequestState.Success(
            data = AppConfig.ConfigFontSizes(configCurrent = configCurrent, fontSizes = fontSizes)
        )
    }.flowOn(ioDispatcher).catch { t -> RequestState.Error(t) }.map { it }
}