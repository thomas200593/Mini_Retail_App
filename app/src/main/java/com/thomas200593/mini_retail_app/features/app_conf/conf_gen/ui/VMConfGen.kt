package com.thomas200593.mini_retail_app.features.app_conf.conf_gen.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.*
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.navigation.DestConfGen
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.repository.RepoConfGen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMConfGen @Inject constructor(
    private val repoConfGen: RepoConfGen,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _menuData: MutableState<ResourceState<Set<DestConfGen>>> = mutableStateOf(Idle)
    val menuData = _menuData

    fun onOpen(sessionState: SessionState) =
        viewModelScope.launch(ioDispatcher) { getMenuData(sessionState) }

    private suspend fun getMenuData(sessionState: SessionState) = viewModelScope.launch(ioDispatcher) {
        _menuData.value = Loading
        _menuData.value = try{ Success(repoConfGen.getMenuData(sessionState)) }
        catch (e: Throwable){ Error(e) }
    }
}