package com.thomas200593.mini_retail_app.features.app_config.ui.config_general.language

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
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

private val TAG = LanguageViewModel::class.simpleName

@HiltViewModel
class LanguageViewModel @Inject constructor(
    appConfigRepository: AppConfigRepository,
    private val configGeneralRepository: ConfigGeneralRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    private val _languagePreferences: MutableState<RequestState<Set<Language>>> = mutableStateOf(RequestState.Idle)
    val languagePreferences = _languagePreferences
    val configCurrentUiState = appConfigRepository.configCurrent.flowOn(ioDispatcher)
        .catch { RequestState.Error(it) }
        .map { RequestState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        Timber.d("Called : fun $TAG.onOpen()")
        getLanguagePreferences()
    }

    private fun getLanguagePreferences() = viewModelScope.launch(ioDispatcher) {
        Timber.d("Called : fun $TAG.getLanguagePreferences()")
        _languagePreferences.value = RequestState.Loading
        _languagePreferences.value = try {
            RequestState.Success(configGeneralRepository.getLanguages())
        }catch (e: Throwable){
            RequestState.Error(e)
        }
    }

    fun saveSelectedLanguage(language: Language) = viewModelScope.launch {
        Timber.d("Called : fun $TAG.saveSelectedLanguage()")
        configGeneralRepository.setLanguage(language)
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.create(
                Locale(language.code)
            )
        )
    }
}