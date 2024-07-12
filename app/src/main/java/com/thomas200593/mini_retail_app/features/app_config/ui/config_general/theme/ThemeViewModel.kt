package com.thomas200593.mini_retail_app.features.app_config.ui.config_general.theme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme
import com.thomas200593.mini_retail_app.features.app_config.repository.ConfigGeneralRepository
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    appCfgRepository: AppConfigRepository,
    private val cfgGeneralRepository: ConfigGeneralRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _themes: MutableState<RequestState<Set<Theme>>> = mutableStateOf(RequestState.Idle)
    val themes = _themes
    val configCurrent = appCfgRepository.configCurrent
        .flowOn(ioDispatcher).catch { RequestState.Error(it) }.map { RequestState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        getThemes()
    }

    private fun getThemes() = viewModelScope.launch(ioDispatcher) {
        _themes.value = RequestState.Loading
        _themes.value = try{ RequestState.Success(cfgGeneralRepository.getThemes()) }
        catch (e: Throwable){ RequestState.Error(e) }
    }

    fun setTheme(theme: Theme) = viewModelScope.launch {
        cfgGeneralRepository.setTheme(theme)
    }
}