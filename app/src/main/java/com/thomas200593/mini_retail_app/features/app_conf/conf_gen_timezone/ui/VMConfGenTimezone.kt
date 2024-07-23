package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.domain.UCGetConfTimezone
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.entity.Timezone
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_timezone.repository.RepoConfGenTimezone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGenTimezone @Inject constructor(
    private val repoConfGenTimezone: RepoConfGenTimezone,
    ucGetConfTimezone: UCGetConfTimezone,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    val configData = ucGetConfTimezone.invoke().flowOn(ioDispatcher).stateIn(
        scope = viewModelScope,
        started = Eagerly,
        initialValue = Loading
    )
    fun setTimezone(timezone: Timezone) = viewModelScope.launch { repoConfGenTimezone.setTimezone(timezone) }
}