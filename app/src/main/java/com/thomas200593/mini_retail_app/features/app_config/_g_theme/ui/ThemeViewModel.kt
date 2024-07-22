package com.thomas200593.mini_retail_app.features.app_config._g_theme.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.app_config._g_theme.domain.GetThemeConfigUseCase
import com.thomas200593.mini_retail_app.features.app_config._g_theme.entity.Theme
import com.thomas200593.mini_retail_app.features.app_config._g_theme.repository.RepositoryAppCfgGeneralTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val repositoryAppCfgGeneralTheme: RepositoryAppCfgGeneralTheme,
    getThemeConfigUseCase: GetThemeConfigUseCase,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    val configData = getThemeConfigUseCase.invoke().flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ResourceState.Loading
        )

    fun setTheme(theme: Theme) = viewModelScope.launch {
        repositoryAppCfgGeneralTheme.setTheme(theme)
    }
}