package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.domain.UCGetConfGenTheme
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.entity.Theme
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.repository.RepoConfGenTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGenTheme @Inject constructor(
    private val repoConfGenTheme: RepoConfGenTheme,
    ucGetConfGenTheme: UCGetConfGenTheme,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    val configData = ucGetConfGenTheme.invoke().flowOn(ioDispatcher).stateIn(
        scope = viewModelScope,
        started = Eagerly,
        initialValue = Loading
    )
    fun setTheme(theme: Theme) = viewModelScope.launch { repoConfGenTheme.setTheme(theme) }
}