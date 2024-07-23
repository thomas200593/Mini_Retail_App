package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.domain.UCGetConfDynamicColor
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_dynamic_color.repository.RepoConfGenDynamicColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGenDynamicColor @Inject constructor(
    private val repoConfGenDynamicColor: RepoConfGenDynamicColor,
    ucGetConfDynamicColor: UCGetConfDynamicColor,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    val configData = ucGetConfDynamicColor.invoke().flowOn(ioDispatcher).stateIn(
        scope = viewModelScope,
        started = Eagerly,
        initialValue = Loading
    )
    fun setDynamicColor(dynamicColor: DynamicColor) =
        viewModelScope.launch { repoConfGenDynamicColor.setDynamicColor(dynamicColor) }
}