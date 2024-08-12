package com.thomas200593.mini_retail_app.features.business.biz_profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Address
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Contact
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Link
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
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BizAddressesBtnEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BizContactsBtnEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BizIdIndustryBtnEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BizIdLegalBtnEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BizIdNameBtnEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BizIdTaxationBtnEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BizLinksBtnEvents
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
            //NavBack
            sealed class BtnNavBackEvents: ButtonEvents() {
                data object OnClick: BtnNavBackEvents()
            }
            //BizIdName
            sealed class BizIdNameBtnEvents: ButtonEvents(){
                sealed class BtnUpdateEvents: BizIdNameBtnEvents(){
                    data class OnClick(val sessionState: SessionState): BtnUpdateEvents()
                }
                sealed class BtnResetEvents: BizIdNameBtnEvents(){
                    data class OnClick(val sessionState: SessionState): BtnResetEvents()
                }
            }
            //BizIdIndustry
            sealed class BizIdIndustryBtnEvents: ButtonEvents(){
                sealed class BtnUpdateEvents: BizIdIndustryBtnEvents(){
                    data class OnClick(val sessionState: SessionState): BtnUpdateEvents()
                }
                sealed class BtnResetEvents: BizIdIndustryBtnEvents(){
                    data class OnClick(val sessionState: SessionState): BtnResetEvents()
                }
            }
            //BizIdLegal
            sealed class BizIdLegalBtnEvents: ButtonEvents(){
                sealed class BtnUpdateEvents: BizIdLegalBtnEvents(){
                    data class OnClick(val sessionState: SessionState): BtnUpdateEvents()
                }
                sealed class BtnResetEvents: BizIdLegalBtnEvents(){
                    data class OnClick(val sessionState: SessionState): BtnResetEvents()
                }
            }
            //BizIdTaxation
            sealed class BizIdTaxationBtnEvents: ButtonEvents(){
                sealed class BtnUpdateEvents: BizIdTaxationBtnEvents(){
                    data class OnClick(val sessionState: SessionState): BtnUpdateEvents()
                }
                sealed class BtnResetEvents: BizIdTaxationBtnEvents(){
                    data class OnClick(val sessionState: SessionState): BtnResetEvents()
                }
            }
            //BizAddresses
            sealed class BizAddressesBtnEvents: ButtonEvents(){
                sealed class BtnAddEvents: BizAddressesBtnEvents() {
                    data class OnClick(val sessionState: SessionState): BtnAddEvents()
                }
                sealed class BtnUpdateEvents: BizAddressesBtnEvents() {
                    data class OnClick(val sessionState: SessionState, val address: Address): BtnUpdateEvents()
                }
                sealed class BtnDeleteEvents: BizAddressesBtnEvents(){
                    data class OnClick(val sessionState: SessionState, val address: Address): BtnDeleteEvents()
                }
                sealed class BtnDeleteAllEvents: BizAddressesBtnEvents(){
                    data class OnClick(val sessionState: SessionState): BtnDeleteAllEvents()
                }
            }
            //BizContacts
            sealed class BizContactsBtnEvents: ButtonEvents(){
                sealed class BtnAddEvents: BizContactsBtnEvents() {
                    data class OnClick(val sessionState: SessionState): BtnAddEvents()
                }
                sealed class BtnUpdateEvents: BizContactsBtnEvents() {
                    data class OnClick(val sessionState: SessionState, val contact: Contact): BtnUpdateEvents()
                }
                sealed class BtnDeleteEvents: BizContactsBtnEvents(){
                    data class OnClick(val sessionState: SessionState, val contact: Contact): BtnDeleteEvents()
                }
                sealed class BtnDeleteAllEvents: BizContactsBtnEvents(){
                    data class OnClick(val sessionState: SessionState): BtnDeleteAllEvents()
                }
            }
            //BizLinks
            sealed class BizLinksBtnEvents: ButtonEvents(){
                sealed class BtnAddEvents: BizLinksBtnEvents() {
                    data class OnClick(val sessionState: SessionState): BtnAddEvents()
                }
                sealed class BtnUpdateEvents: BizLinksBtnEvents() {
                    data class OnClick(val sessionState: SessionState, val link: Link): BtnUpdateEvents()
                }
                sealed class BtnDeleteEvents: BizLinksBtnEvents(){
                    data class OnClick(val sessionState: SessionState, val link: Link): BtnDeleteEvents()
                }
                sealed class BtnDeleteAllEvents: BizLinksBtnEvents(){
                    data class OnClick(val sessionState: SessionState): BtnDeleteAllEvents()
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events){
            is OnOpenEvents -> onOpenEvent(events.sessionState)
            is BtnNavBackEvents.OnClick -> {/*TODO*/}
            is BizIdNameBtnEvents.BtnUpdateEvents.OnClick -> {/*TODO*/}
            is BizIdNameBtnEvents.BtnResetEvents.OnClick -> {/*TODO*/}
            is BizIdIndustryBtnEvents.BtnUpdateEvents.OnClick -> {/*TODO*/}
            is BizIdIndustryBtnEvents.BtnResetEvents.OnClick -> {/*TODO*/}
            is BizIdLegalBtnEvents.BtnUpdateEvents.OnClick -> {/*TODO*/}
            is BizIdLegalBtnEvents.BtnResetEvents.OnClick -> {/*TODO*/}
            is BizIdTaxationBtnEvents.BtnUpdateEvents.OnClick -> {/*TODO*/}
            is BizIdTaxationBtnEvents.BtnResetEvents.OnClick -> {/*TODO*/}
            is BizAddressesBtnEvents.BtnAddEvents.OnClick -> {/*TODO*/}
            is BizAddressesBtnEvents.BtnUpdateEvents.OnClick -> {/*TODO*/}
            is BizAddressesBtnEvents.BtnDeleteEvents.OnClick -> {/*TODO*/}
            is BizAddressesBtnEvents.BtnDeleteAllEvents.OnClick -> {/*TODO*/}
            is BizContactsBtnEvents.BtnAddEvents.OnClick -> {/*TODO*/}
            is BizContactsBtnEvents.BtnDeleteAllEvents.OnClick -> {/*TODO*/}
            is BizContactsBtnEvents.BtnDeleteEvents.OnClick -> {/*TODO*/}
            is BizContactsBtnEvents.BtnUpdateEvents.OnClick -> {/*TODO*/}
            is BizLinksBtnEvents.BtnAddEvents.OnClick -> {/*TODO*/}
            is BizLinksBtnEvents.BtnDeleteAllEvents.OnClick -> {/*TODO*/}
            is BizLinksBtnEvents.BtnDeleteEvents.OnClick -> {/*TODO*/}
            is BizLinksBtnEvents.BtnUpdateEvents.OnClick -> {/*TODO*/}
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