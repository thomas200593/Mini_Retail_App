package com.thomas200593.mini_retail_app.features.app_conf._g_currency.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import com.thomas200593.mini_retail_app.features.app_conf._g_currency.repository.RepoImplConfGenCurrency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UCFetchConfCurrency @Inject constructor(
    private val repoAppConf: RepoAppConf,
    private val repoImplConfGenCurrency: RepoImplConfGenCurrency,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke() = combine(
        repoAppConf.configCurrent, flow { emit(repoImplConfGenCurrency.getCurrencies()) }
    ){ configCurrent, currencies ->
        ResourceState.Success(
            data = AppConfig.ConfigCurrency(configCurrent = configCurrent, currencies = currencies)
        )
    }.flowOn(ioDispatcher).catch { t -> ResourceState.Error(t) }.map { it }
}