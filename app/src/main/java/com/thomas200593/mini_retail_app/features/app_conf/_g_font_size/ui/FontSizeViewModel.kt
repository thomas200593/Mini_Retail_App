package com.thomas200593.mini_retail_app.features.app_conf._g_font_size.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.app_conf._g_font_size.domain.GetFontSizeConfigUseCase
import com.thomas200593.mini_retail_app.features.app_conf._g_font_size.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_conf._g_font_size.repository.RepositoryAppCfgGeneralFontSize
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FontSizeViewModel @Inject constructor(
    private val repositoryAppCfgGeneralFontSize: RepositoryAppCfgGeneralFontSize,
    getFontSizeConfigUseCase: GetFontSizeConfigUseCase,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    val configData = getFontSizeConfigUseCase.invoke().flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ResourceState.Loading
        )

    fun setFontSize(fontSize: FontSize) = viewModelScope.launch{
        repositoryAppCfgGeneralFontSize.setFontSize(fontSize)
    }
}
