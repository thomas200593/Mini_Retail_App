package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.domain.UCGetConfFontSize
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.repository.RepoConfGenFontSize
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGenFontSize @Inject constructor(
    private val repoConfGenFontSize: RepoConfGenFontSize,
    ucGetConfFontSize: UCGetConfFontSize,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    val configData = ucGetConfFontSize.invoke().flowOn(ioDispatcher).stateIn(
        scope = viewModelScope,
        started = Eagerly,
        initialValue = Loading
    )
    fun setFontSize(fontSize: FontSize) = viewModelScope.launch{ repoConfGenFontSize.setFontSize(fontSize) }
}
