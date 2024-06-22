package com.thomas200593.mini_retail_app.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.app.MainActivityUiState.Loading
import com.thomas200593.mini_retail_app.app.MainActivityUiState.Success
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

private val TAG = MainViewModel::class.simpleName

@HiltViewModel
class MainViewModel @Inject constructor(
    appConfigRepository: AppConfigRepository
) : ViewModel(){
    val uiState: StateFlow<MainActivityUiState> = appConfigRepository.configCurrentData.onEach {
        Timber.d("$TAG.uiState : $it")
    }.map {
        Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.Eagerly
    )
}