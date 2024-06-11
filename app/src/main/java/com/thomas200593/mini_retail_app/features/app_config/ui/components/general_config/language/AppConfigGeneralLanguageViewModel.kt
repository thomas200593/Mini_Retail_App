package com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.language

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AppConfigGeneralLanguageViewModel @Inject constructor(
    private val appConfigRepository: AppConfigRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    private val _languagePreferences: MutableState<RequestState<Set<Language>>> = mutableStateOf(RequestState.Idle)
    val languagePreferences = _languagePreferences
    val configCurrentUiState = appConfigRepository.configCurrentData.flowOn(ioDispatcher)
        .onEach { Timber.d("Config Current State: $it") }
        .catch { RequestState.Error(it) }
        .map { RequestState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    fun onOpen(){
        viewModelScope.launch(ioDispatcher) {
            getLanguagePreferences()
        }
    }

    private suspend fun getLanguagePreferences() {
        _languagePreferences.value = RequestState.Loading
        viewModelScope.launch(ioDispatcher) {
            _languagePreferences.value = try {
                RequestState.Success(appConfigRepository.getLanguagePreferences())
            }catch (e: Throwable){
                RequestState.Error(e)
            }
        }
    }

    fun saveSelectedLanguage(language: Language) = viewModelScope.launch {
        appConfigRepository.setLanguagePreferences(language)
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.create(
                Locale(language.code)
            )
        )
    }
}