package com.thomas200593.mini_retail_app.features.app_config.domain

import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.app_config.repository.ConfigGeneralRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLanguageConfigUseCase @Inject constructor(
    private val appCfgRepository: AppConfigRepository,
    private val cfgGeneralRepository: ConfigGeneralRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {
    fun invoke() = combine(
        appCfgRepository.configCurrent, flow { emit(cfgGeneralRepository.getLanguages()) }
    ){ configCurrent, languages ->
        RequestState.Success(
            data = AppConfig.ConfigLanguages(configCurrent = configCurrent, languages = languages)
        )
    }.flowOn(ioDispatcher).catch { t -> RequestState.Error(t) }.map { it }
}