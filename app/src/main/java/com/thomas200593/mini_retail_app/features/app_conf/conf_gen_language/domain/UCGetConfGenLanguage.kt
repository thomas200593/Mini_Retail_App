package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.ConfigLanguages
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.repository.RepoConfGenLanguage
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UCGetConfGenLanguage @Inject constructor(
    private val repoAppConf: RepoAppConf,
    private val repoConfGenLanguage: RepoConfGenLanguage,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) {
    fun invoke() = combine(
        flow = repoAppConf.configCurrent, flow2 = flow { emit(repoConfGenLanguage.getLanguages()) }
    ){ configCurrent, languages ->
        Success(data = ConfigLanguages(configCurrent = configCurrent, languages = languages))
    }.flowOn(ioDispatcher).catch { t -> Error(t) }.map { it }
}