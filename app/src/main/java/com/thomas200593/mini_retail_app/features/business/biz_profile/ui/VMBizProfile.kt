package com.thomas200593.mini_retail_app.features.business.biz_profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.features.business.biz_profile.domain.UCGetBizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfileDtl
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BtnResetBizProfileEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BtnUpdateBizProfileEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.OnOpenEvents
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
    data class UiState(
        val bizProfile: ResourceState<BizProfileDtl> = Idle
    )
    sealed class UiEvents{
        data class OnOpenEvents(val sessionState: SessionState): UiEvents()
        sealed class ButtonEvents: UiEvents(){
            sealed class BtnNavBackEvents: ButtonEvents() {
                data object OnClick: BtnNavBackEvents()
            }
            sealed class BtnResetBizProfileEvents: ButtonEvents() {
                data object OnClick: BtnResetBizProfileEvents()
            }
            sealed class BtnUpdateBizProfileEvents: ButtonEvents() {
                data object OnClick: BtnUpdateBizProfileEvents()
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events){
            is OnOpenEvents -> onOpenEvent(events.sessionState)
            BtnNavBackEvents.OnClick -> {/*TODO*/}
            BtnResetBizProfileEvents.OnClick -> {/*TODO*/}
            BtnUpdateBizProfileEvents.OnClick -> {/*TODO*/}
        }
    }
    private fun onOpenEvent(sessionState: SessionState) = viewModelScope.launch(ioDispatcher){
        ucGetBizProfile.invoke(sessionState).collect{ data -> _uiState.update { it.copy(bizProfile = data) } }
    }
}
