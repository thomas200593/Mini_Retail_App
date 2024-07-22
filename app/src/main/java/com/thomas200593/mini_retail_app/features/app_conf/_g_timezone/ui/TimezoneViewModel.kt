package com.thomas200593.mini_retail_app.features.app_conf._g_timezone.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.app_conf._g_timezone.domain.GetTimezoneConfigUseCase
import com.thomas200593.mini_retail_app.features.app_conf._g_timezone.entity.Timezone
import com.thomas200593.mini_retail_app.features.app_conf._g_timezone.repository.RepositoryAppCfgGeneralTimezone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimezoneViewModel @Inject constructor(
    private val repositoryAppCfgGeneralTimezone: RepositoryAppCfgGeneralTimezone,
    getTimezoneConfigUseCase: GetTimezoneConfigUseCase,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    val configData = getTimezoneConfigUseCase.invoke().flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ResourceState.Loading
        )

    fun setTimezone(timezone: Timezone) = viewModelScope.launch {
        repositoryAppCfgGeneralTimezone.setTimezone(timezone)
    }
}