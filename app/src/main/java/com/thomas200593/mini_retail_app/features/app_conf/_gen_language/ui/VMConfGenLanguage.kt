package com.thomas200593.mini_retail_app.features.app_conf._gen_language.ui

import androidx.appcompat.app.AppCompatDelegate.setApplicationLocales
import androidx.core.os.LocaleListCompat.create
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.features.app_conf._gen_language.domain.UCGetConfGenLanguage
import com.thomas200593.mini_retail_app.features.app_conf._gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.app_conf._gen_language.repository.RepoConfGenLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class VMConfGenLanguage @Inject constructor(
    private val repoConfGenLanguage: RepoConfGenLanguage,
    ucGetConfGenLanguage: UCGetConfGenLanguage,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    val configData = ucGetConfGenLanguage.invoke().flowOn(ioDispatcher).stateIn(
        scope = viewModelScope,
        started = Eagerly,
        initialValue = Loading
    )
    fun setLanguage(language: Language) = viewModelScope.launch {
        repoConfGenLanguage.setLanguage(language)
        setApplicationLocales(create(Locale(language.code)))
    }
}