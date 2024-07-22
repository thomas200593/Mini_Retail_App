package com.thomas200593.mini_retail_app.features.initial.domain

import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_language.domain.GetLanguageConfigUseCase
import com.thomas200593.mini_retail_app.features.initial.entity.Initialization
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetInitializationDataUseCase @Inject constructor(
    private val languageConfigUseCase: GetLanguageConfigUseCase,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
){
    operator fun invoke() = languageConfigUseCase.invoke().flowOn(ioDispatcher)
        .catch { t -> RequestState.Error(t) }
        .map { langConfig ->
            RequestState.Success(
                data = Initialization(
                    configCurrent = langConfig.data.configCurrent,
                    languages = langConfig.data.languages
                )
            )
        }
}