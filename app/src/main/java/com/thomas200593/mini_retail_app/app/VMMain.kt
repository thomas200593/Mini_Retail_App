package com.thomas200593.mini_retail_app.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.app.UiStateMain.Loading
import com.thomas200593.mini_retail_app.app.UiStateMain.Success
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VMMain @Inject constructor(
    repoAppConf: RepoAppConf,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel(){
    val uiState: StateFlow<UiStateMain> = repoAppConf.configCurrent.flowOn(ioDispatcher)
        .map { Success(confCurrent = it) }.stateIn(
            scope = viewModelScope,
            initialValue = Loading,
            started = Eagerly
        )
}