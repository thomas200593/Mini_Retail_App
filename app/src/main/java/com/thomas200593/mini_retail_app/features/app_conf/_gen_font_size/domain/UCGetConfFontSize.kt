package com.thomas200593.mini_retail_app.features.app_conf._gen_font_size.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.app_conf._gen_font_size.entity.ConfigFontSizes
import com.thomas200593.mini_retail_app.features.app_conf._gen_font_size.repository.RepoConfGenFontSize
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UCGetConfFontSize @Inject constructor(
    private val repoAppConf: RepoAppConf,
    private val repoConfGenFontSize: RepoConfGenFontSize,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) {
    fun invoke() = combine(
        flow = repoAppConf.configCurrent, flow2 = flow { emit(repoConfGenFontSize.getFontSizes()) }
    ){ configCurrent, fontSizes ->
        Success(data = ConfigFontSizes(configCurrent = configCurrent, fontSizes = fontSizes))
    }.flowOn(ioDispatcher).catch { t -> Error(t) }.map { it }
}