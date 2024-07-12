package com.thomas200593.mini_retail_app.features.app_config.ui.config_general.font_size

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.app_config.repository.ConfigGeneralRepository
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
class FontSizeViewModel @Inject constructor(
    appConfigRepository: AppConfigRepository,
    private val configGeneralRepository: ConfigGeneralRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _fontSizes: MutableState<RequestState<Set<FontSize>>> = mutableStateOf(RequestState.Idle)
    val fontSizes = _fontSizes
    val configCurrent = appConfigRepository.configCurrent
        .flowOn(ioDispatcher).catch { RequestState.Error(it) }.map { RequestState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        getFontSizes()
    }


    private fun getFontSizes() = viewModelScope.launch(ioDispatcher) {
        _fontSizes.value = RequestState.Loading
        _fontSizes.value = try { RequestState.Success(configGeneralRepository.getFontSizes()) }
        catch (e: Throwable){ RequestState.Error(e) }
    }

    fun setFontSize(fontSize: FontSize) = viewModelScope.launch{
        configGeneralRepository.setFontSize(fontSize)
    }
}
