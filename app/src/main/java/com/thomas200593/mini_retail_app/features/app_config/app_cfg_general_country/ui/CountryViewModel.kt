package com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_country.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_country.domain.UseCaseGetCountryConfig
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_country.entity.Country
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_country.repository.RepositoryAppCfgGeneralCountry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repoAppCfgGeneralCountry: RepositoryAppCfgGeneralCountry,
    useCaseGetCountryConfig: UseCaseGetCountryConfig,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel(){

    val configData = useCaseGetCountryConfig.invoke().flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    fun setCountry(country: Country) = viewModelScope.launch{
        repoAppCfgGeneralCountry.setCountry(country)
    }
}