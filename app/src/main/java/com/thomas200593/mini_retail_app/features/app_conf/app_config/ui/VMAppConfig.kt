package com.thomas200593.mini_retail_app.features.app_conf.app_config.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.*
import com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation.DestAppConfig
import com.thomas200593.mini_retail_app.features.app_conf.app_config.repository.RepoAppConf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMAppConfig @Inject constructor(
    private val appCfgRepository: RepoAppConf,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    private val _menuData: MutableState<ResourceState<Set<DestAppConfig>>> = mutableStateOf(Idle)
    val menuData = _menuData

    fun onOpen(sessionState: SessionState) =
        viewModelScope.launch(ioDispatcher) { getMenuData(sessionState) }

    private suspend fun getMenuData(sessionState: SessionState) = viewModelScope.launch(ioDispatcher){
        _menuData.value = Loading
        _menuData.value = try { Success(appCfgRepository.getMenuData(sessionState)) }
        catch (e: Throwable){ Error(e) }
    }
}