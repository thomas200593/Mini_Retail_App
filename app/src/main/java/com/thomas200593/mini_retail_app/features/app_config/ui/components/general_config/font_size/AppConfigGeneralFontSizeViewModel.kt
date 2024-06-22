package com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.font_size

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private val TAG = AppConfigGeneralFontSizeViewModel::class.simpleName

@HiltViewModel
class AppConfigGeneralFontSizeViewModel @Inject constructor(
    private val appConfigRepository: AppConfigRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _fontSizeSizePreferences: MutableState<RequestState<Set<FontSize>>> = mutableStateOf(RequestState.Idle)
    val fontSizePreferences = _fontSizeSizePreferences
    val configCurrentUiState = appConfigRepository.configCurrentData.flowOn(ioDispatcher)
        .catch { RequestState.Error(it) }
        .map { RequestState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        Timber.d("Called : fun $TAG.onOpen()")
        getFontSizePreferences()
    }


    private fun getFontSizePreferences() = viewModelScope.launch(ioDispatcher) {
        Timber.d("Called : fun $TAG.getFontSizePreferences()")
        _fontSizeSizePreferences.value = RequestState.Loading
        _fontSizeSizePreferences.value = try {
            RequestState.Success(appConfigRepository.getFontSizePreferences())
        }catch (e: Throwable){
            RequestState.Error(e)
        }
    }

    fun saveSelectedFontSize(fontSize: FontSize) = viewModelScope.launch{
        Timber.d("Called : fun $TAG.saveSelectedFontSize()")
        appConfigRepository.setFontSizePreferences(fontSize)
    }
}
