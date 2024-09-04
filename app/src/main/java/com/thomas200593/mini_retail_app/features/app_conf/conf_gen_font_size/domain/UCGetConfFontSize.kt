package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.entity.ConfigFontSizes
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.repository.RepoConfGenFontSize
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetConfFontSize @Inject constructor(
    private val repoAppConf: RepoAppConf,
    private val repoConfGenFontSize: RepoConfGenFontSize,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) {
    fun invoke() = combine(
        flow = repoAppConf.configCurrent,
        flow2 = repoConfGenFontSize.getFontSizes()
    ){ configCurrent, fontSizes ->
        ConfigFontSizes(configCurrent = configCurrent, fontSizes = fontSizes)
    }.flowOn(ioDispatcher)
}