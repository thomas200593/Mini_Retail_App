package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.entity.ConfigCurrency
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.repository.RepoConfGenCurrency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetConfCurrency @Inject constructor(
    private val repoAppConf: RepoAppConf,
    private val repoConfGenCurrency: RepoConfGenCurrency,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke() = combine(
        flow = repoAppConf.configCurrent,
        flow2 = repoConfGenCurrency.getCurrencies()
    ) { configCurrent, currencies -> ConfigCurrency(configCurrent = configCurrent, currencies = currencies) }
        .flowOn(ioDispatcher)
}