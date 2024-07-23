package com.thomas200593.mini_retail_app.features.app_conf._gen_country.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.features.app_conf._gen_country.domain.UCGetConfCountry
import com.thomas200593.mini_retail_app.features.app_conf._gen_country.entity.Country
import com.thomas200593.mini_retail_app.features.app_conf._gen_country.repository.RepoConfGenCountry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGenCountry @Inject constructor(
    private val repoConfGenCountry: RepoConfGenCountry,
    ucGetConfCountry: UCGetConfCountry,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel(){
    val confData = ucGetConfCountry.invoke().flowOn(ioDispatcher).stateIn(
        scope = viewModelScope,
        started = Eagerly,
        initialValue = Loading
    )
    fun setCountry(country: Country) =
        viewModelScope.launch{ repoConfGenCountry.setCountry(country) }
}