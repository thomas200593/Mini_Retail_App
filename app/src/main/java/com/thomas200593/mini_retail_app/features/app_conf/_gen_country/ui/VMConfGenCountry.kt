package com.thomas200593.mini_retail_app.features.app_conf._gen_country.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.app_conf._gen_country.domain.UCFetchConfCountry
import com.thomas200593.mini_retail_app.features.app_conf._gen_country.entity.Country
import com.thomas200593.mini_retail_app.features.app_conf._gen_country.repository.RepoConfGenCountry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGenCountry @Inject constructor(
    private val repoConfGenCountry: RepoConfGenCountry,
    ucFetchConfCountry: UCFetchConfCountry,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel(){
    val confData = ucFetchConfCountry.invoke().flowOn(ioDispatcher).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = ResourceState.Loading
    )
    fun setCountry(country: Country) =
        viewModelScope.launch{ repoConfGenCountry.setCountry(country) }
}