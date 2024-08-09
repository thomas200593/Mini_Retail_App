package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.entity.ConfigCurrency
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.repository.RepoConfGenCurrency
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UCGetConfCurrency @Inject constructor(
    private val repoAppConf: RepoAppConf,
    private val repoImplConfGenCurrency: RepoConfGenCurrency,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke() =
        combine(repoAppConf.configCurrent, flow { emit(repoImplConfGenCurrency.getCurrencies()) })
        { configCurrent, currencies -> Success(data = ConfigCurrency(configCurrent = configCurrent, currencies = currencies)) }
            .flowOn(ioDispatcher).catch { t -> Error(t) }.map { it }
}