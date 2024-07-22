package com.thomas200593.mini_retail_app.features.app_conf._gen_currency.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.app_conf._gen_currency.domain.UCFetchConfCurrency
import com.thomas200593.mini_retail_app.features.app_conf._gen_currency.entity.Currency
import com.thomas200593.mini_retail_app.features.app_conf._gen_currency.repository.RepoImplConfGenCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGenCurrency @Inject constructor(
    private val repoImplConfGenCurrency: RepoImplConfGenCurrency,
    ucFetchConfCurrency: UCFetchConfCurrency,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    val confData = ucFetchConfCurrency.invoke().flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ResourceState.Loading
        )

    fun setCurrency(currency: Currency) = viewModelScope.launch {
        repoImplConfGenCurrency.setCurrency(currency)
    }
}