package com.thomas200593.mini_retail_app.features.app_conf._g_language.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.app_conf._g_language.domain.UCGetConfGenLanguage
import com.thomas200593.mini_retail_app.features.app_conf._g_language.entity.Language
import com.thomas200593.mini_retail_app.features.app_conf._g_language.repository.RepoConfGenLanguage
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
    private val repoConfGenLanguage: RepoConfGenLanguage,
    UCGetConfGenLanguage: UCGetConfGenLanguage,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){

    val configData = UCGetConfGenLanguage.invoke().flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ResourceState.Loading
        )

    fun setLanguage(language: Language) = viewModelScope.launch {
        repoConfGenLanguage.setLanguage(language)
        AppCompatDelegate
            .setApplicationLocales(LocaleListCompat.create(Locale(language.code)))
    }
}