package com.thomas200593.mini_retail_app.features.app_config._g_dynamic_color.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.app_config._g_dynamic_color.domain.GetDynamicColorConfigUseCase
import com.thomas200593.mini_retail_app.features.app_config._g_dynamic_color.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config._g_dynamic_color.repository.RepositoryAppCfgGeneralDynamicColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DynamicColorViewModel @Inject constructor(
    private val repositoryAppCfgGeneralDynamicColor: RepositoryAppCfgGeneralDynamicColor,
    getDynamicColorConfigUseCase: GetDynamicColorConfigUseCase,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    val configData = getDynamicColorConfigUseCase.invoke().flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ResourceState.Loading
        )

    fun setDynamicColor(dynamicColor: DynamicColor) = viewModelScope.launch {
        repositoryAppCfgGeneralDynamicColor.setDynamicColor(dynamicColor)
    }
}