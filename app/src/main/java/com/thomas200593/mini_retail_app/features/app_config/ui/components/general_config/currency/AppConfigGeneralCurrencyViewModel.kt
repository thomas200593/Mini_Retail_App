package com.thomas200593.mini_retail_app.features.app_config.ui.components.general_config.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppConfigGeneralCurrencyViewModel @Inject constructor(
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    fun onOpen() {
        viewModelScope.launch(ioDispatcher) {
            getCurrencyPreferences()
        }
    }

    private suspend fun getCurrencyPreferences() {

    }
}
