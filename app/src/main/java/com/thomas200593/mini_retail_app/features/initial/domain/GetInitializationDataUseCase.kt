package com.thomas200593.mini_retail_app.features.initial.domain

import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.app_config.repository.ConfigGeneralRepository
import com.thomas200593.mini_retail_app.features.initial.entity.Initialization
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetInitializationDataUseCase @Inject constructor(
    private val appCfgRepository: AppConfigRepository,
    private val cfgGeneralRepository: ConfigGeneralRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
){
    operator fun invoke() = combine(
        flow = appCfgRepository.configCurrent,
        flow2 = flow { emit(cfgGeneralRepository.getLanguages()) }
    ){ configCurrent, languages ->
        RequestState.Success(
            data = Initialization(
                configCurrent = configCurrent,
                languages = languages
            )
        )
    }
        .flowOn(ioDispatcher)
        .catch { t -> RequestState.Error(t) }
        .map { it }
}