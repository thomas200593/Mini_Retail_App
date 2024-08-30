package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.entity.ConfigDynamicColor
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.repository.RepoConfGenDynamicColor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetConfDynamicColor @Inject constructor(
    private val repoAppConf: RepoAppConf,
    private val repoConfGenDynamicColor: RepoConfGenDynamicColor,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
){
    operator fun invoke() = combine(
        flow = repoAppConf.configCurrent,
        flow2 = repoConfGenDynamicColor.getDynamicColors()
    ) { configCurrent, dynamicColors -> ConfigDynamicColor(configCurrent = configCurrent, dynamicColors = dynamicColors) }
        .flowOn(ioDispatcher)
}