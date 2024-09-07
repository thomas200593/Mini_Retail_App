package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.entity.ConfigTimezones
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.repository.RepoConfGenTimezone
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetConfTimezone @Inject constructor(
    private val repoAppConf: RepoAppConf,
    private val repoConfGenTimezone: RepoConfGenTimezone,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) {
    fun invoke() = combine(
        flow = repoAppConf.configCurrent,
        flow2 = repoConfGenTimezone.getTimezones()
    ){ configCurrent, timezones ->
        ConfigTimezones(configCurrent = configCurrent, timezones = timezones)
    }.flowOn(ioDispatcher)
}