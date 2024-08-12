package com.thomas200593.mini_retail_app.features.business.biz_profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Empty
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Loading
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent
import com.thomas200593.mini_retail_app.features.business.biz_profile.domain.UCGetBizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BizIdNameBtnEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiStateBizProfile.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMBizProfile @Inject constructor(
    private val ucGetBizProfile: UCGetBizProfile,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    sealed interface UiStateBizProfile{
        data object Loading: UiStateBizProfile
        data class Success(val bizProfile: BizProfile, val configCurrent: ConfigCurrent): UiStateBizProfile
        data class Error(val t: Throwable): UiStateBizProfile
    }
    data class UiState(
        val bizProfile: UiStateBizProfile = UiStateBizProfile.Loading
    )
    sealed class UiEvents{
        data class OnOpenEvents(val sessionState: SessionState): UiEvents()
        sealed class ButtonEvents: UiEvents(){
            sealed class BtnNavBackEvents: ButtonEvents() {
                data object OnClick: BtnNavBackEvents()
            }
            sealed class BizIdNameBtnEvents: ButtonEvents(){
                sealed class BtnUpdateEvents: BizIdNameBtnEvents(){
                    data class OnClick(val sessionState: SessionState): BtnUpdateEvents()
                }
                sealed class BtnResetEvents: BizIdNameBtnEvents(){
                    data class OnClick(val sessionState: SessionState): BtnResetEvents()
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events){
            is OnOpenEvents -> onOpenEvent(events.sessionState)
            BtnNavBackEvents.OnClick -> {/*TODO*/}
            is BizIdNameBtnEvents.BtnResetEvents.OnClick -> {/*TODO*/}
            is BizIdNameBtnEvents.BtnUpdateEvents.OnClick -> {/*TODO*/}
        }
    }
    private fun onOpenEvent(sessionState: SessionState) = viewModelScope.launch(ioDispatcher){
        ucGetBizProfile.invoke(sessionState).collect{ result ->
            _uiState.update {
                it.copy(
                    bizProfile = when(result){
                        Idle, Loading -> UiStateBizProfile.Loading
                        Empty -> UiStateBizProfile.Error(Throwable("Error Getting Data!"))
                        is Error -> UiStateBizProfile.Error(result.t)
                        is Success -> Success(result.data.first, result.data.second)
                    }
                )
            }
        }
    }
}