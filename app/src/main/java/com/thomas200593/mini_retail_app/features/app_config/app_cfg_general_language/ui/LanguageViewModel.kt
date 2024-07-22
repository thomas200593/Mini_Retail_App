package com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_language.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_language.domain.GetLanguageConfigUseCase
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_language.entity.Language
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_language.repository.RepositoryAppCfgGeneralLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val repositoryAppCfgGeneralLanguage: RepositoryAppCfgGeneralLanguage,
    getLanguageConfigUseCase: GetLanguageConfigUseCase,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){

    val configData = getLanguageConfigUseCase.invoke().flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = RequestState.Loading
        )

    fun setLanguage(language: Language) = viewModelScope.launch {
        repositoryAppCfgGeneralLanguage.setLanguage(language)
        AppCompatDelegate
            .setApplicationLocales(LocaleListCompat.create(Locale(language.code)))
    }
}