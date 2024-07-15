package com.thomas200593.mini_retail_app.features.app_config.ui.config_general.timezone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.domain.GetTimezoneConfigUseCase
import com.thomas200593.mini_retail_app.features.app_config.entity.Timezone
import com.thomas200593.mini_retail_app.features.app_config.repository.ConfigGeneralRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimezoneViewModel @Inject constructor(
    private val cfgGeneralRepository: ConfigGeneralRepository,
    getTimezoneConfigUseCase: GetTimezoneConfigUseCase,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    val configData = getTimezoneConfigUseCase.invoke().flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    fun setTimezone(timezone: Timezone) = viewModelScope.launch {
        cfgGeneralRepository.setTimezone(timezone)
    }
}