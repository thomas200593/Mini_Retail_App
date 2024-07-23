package com.thomas200593.mini_retail_app.features.app_conf._gen_country.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.app_conf._gen_country.entity.ConfigCountry
import com.thomas200593.mini_retail_app.features.app_conf._gen_country.repository.RepoConfGenCountry
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UCGetConfCountry @Inject constructor(
    private val repoAppConf: RepoAppConf,
    private val repoConfGenCountry: RepoConfGenCountry,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke() = combine(
        flow = repoAppConf.configCurrent,
        flow2 = flow { emit(repoConfGenCountry.getCountries()) }
    ) { configCurrent, countries ->
        Success(data = ConfigCountry(configCurrent = configCurrent, countries = countries))
    }.flowOn(ioDispatcher).catch { t -> Error(t) }.map { it }
}