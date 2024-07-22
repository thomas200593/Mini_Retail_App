package com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_country.domain

import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.app_cfg.entity.AppConfig
import com.thomas200593.mini_retail_app.features.app_config.app_cfg.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_country.repository.RepositoryAppCfgGeneralCountry
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UseCaseGetCountryConfig @Inject constructor(
    private val appCfgRepository: AppConfigRepository,
    private val cfgGeneralCountryRepository: RepositoryAppCfgGeneralCountry,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {
    operator fun invoke() = combine(
        appCfgRepository.configCurrent, flow { emit(cfgGeneralCountryRepository.getCountries()) }
    ){ configCurrent, countries ->
        RequestState.Success(
            data = AppConfig.ConfigCountry(configCurrent = configCurrent, countries = countries)
        )
    }.flowOn(ioDispatcher).catch { t -> RequestState.Error(t) }.map { it }
}