package com.thomas200593.mini_retail_app.features.business.biz_profile.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
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
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.DialogEvents.DlgDenySessionEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiStateBizProfile.Loading
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiStateBizProfile.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
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
        data class Success(val configCurrent: ConfigCurrent, val bizProfile: BizProfile): UiStateBizProfile
    }
    data class UiState(
        val bizProfile: UiStateBizProfile = Loading,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgLoading: MutableState<Boolean> = mutableStateOf(false),
        val dlgDenySession: MutableState<Boolean> = mutableStateOf(false),
        val dlgScrDesc: MutableState<Boolean> = mutableStateOf(false)
    )
    sealed interface UiEvents {
        data class OnOpenEvents(
            val sessionState: SessionState,
            val currentScreen: ScrGraphs
        ) : UiEvents
        sealed interface DialogEvents: UiEvents {
            sealed interface DlgDenySessionEvents : DialogEvents {
                data object OnShow : DlgDenySessionEvents
                data object OnDismiss : DlgDenySessionEvents
            }
        }
        sealed interface ButtonEvents: UiEvents {
            sealed interface BtnNavBackEvents: ButtonEvents {
                data object OnClick : BtnNavBackEvents
            }
            sealed interface BtnScrDescEvents : ButtonEvents {
                data object OnClick : BtnScrDescEvents
                data object OnDismiss : BtnScrDescEvents
            }
            sealed interface BizIdNameBtnEvents: ButtonEvents{
                sealed interface BtnUpdateEvents: BizIdNameBtnEvents {
                    data object OnClick : BtnUpdateEvents
                }
                sealed interface BtnResetEvents: BizIdNameBtnEvents {
                    data object OnClick : BtnResetEvents
                }
            }
            sealed interface BizIdIndustryBtnEvents: ButtonEvents {
                sealed interface BtnUpdateEvents: BizIdIndustryBtnEvents {
                    data object OnClick : BtnUpdateEvents
                }
                sealed interface BtnResetEvents: BizIdIndustryBtnEvents{
                    data object OnClick : BtnResetEvents
                }
            }
            sealed interface BizIdLegalBtnEvents: ButtonEvents {
                sealed interface BtnUpdateEvents: BizIdLegalBtnEvents{
                    data object OnClick : BtnUpdateEvents
                }
                sealed interface BtnResetEvents: BizIdLegalBtnEvents {
                    data object OnClick : BtnResetEvents
                }
            }
            sealed interface BizIdTaxationBtnEvents: ButtonEvents {
                sealed interface BtnUpdateEvents: BizIdTaxationBtnEvents {
                    data object OnClick: BtnUpdateEvents
                }
                sealed interface BtnResetEvents: BizIdTaxationBtnEvents {
                    data object OnClick: BtnResetEvents
                }
            }
            sealed interface BizAddressesBtnEvents: ButtonEvents {
                sealed interface BtnAddEvents: BizAddressesBtnEvents {
                    data object OnClick : BtnAddEvents
                }
                sealed interface BtnUpdateEvents: BizAddressesBtnEvents {
                    data object OnClick : BtnUpdateEvents
                }
                sealed interface BtnDeleteEvents: BizAddressesBtnEvents {
                    data object OnClick : BtnDeleteEvents
                }
                sealed interface BtnDeleteAllEvents: BizAddressesBtnEvents {
                    data object OnClick : BtnDeleteAllEvents
                }
            }
            sealed interface BizContactsBtnEvents: ButtonEvents {
                sealed interface BtnAddEvents: BizContactsBtnEvents {
                    data object OnClick : BtnAddEvents
                }
                sealed interface BtnUpdateEvents: BizContactsBtnEvents {
                    data object OnClick : BtnUpdateEvents
                }
                sealed interface BtnDeleteEvents: BizContactsBtnEvents {
                    data object OnClick : BtnDeleteEvents
                }
                sealed interface BtnDeleteAllEvents: BizContactsBtnEvents {
                    data object OnClick : BtnDeleteAllEvents
                }
            }
            sealed interface BizLinksBtnEvents: ButtonEvents{
                sealed interface BtnAddEvents: BizLinksBtnEvents {
                    data object OnClick : BtnAddEvents
                }
                sealed interface BtnUpdateEvents: BizLinksBtnEvents {
                    data object OnClick : BtnUpdateEvents
                }
                sealed interface BtnDeleteEvents: BizLinksBtnEvents {
                    data object OnClick : BtnDeleteEvents
                }
                sealed interface BtnDeleteAllEvents: BizLinksBtnEvents {
                    data object OnClick : BtnDeleteAllEvents
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events) {
            is UiEvents.OnOpenEvents -> onOpenEvent(events.sessionState, events.currentScreen)
            is BtnNavBackEvents.OnClick -> resetDialogAndUiState()
            is DlgDenySessionEvents.OnShow -> onDenySession()
            is DlgDenySessionEvents.OnDismiss -> resetDialogAndUiState()
            is BtnScrDescEvents.OnClick ->
                { resetDialogState(); updateDialogState(dlgScrDesc = true) }
            is BtnScrDescEvents.OnDismiss -> resetDialogState()
            is BizIdNameBtnEvents.BtnUpdateEvents.OnClick -> { /*TODO*/ }
            is BizIdNameBtnEvents.BtnResetEvents.OnClick -> { /*TODO*/ }
            is BizIdIndustryBtnEvents.BtnResetEvents.OnClick -> { /*TODO*/ }
            is BizIdIndustryBtnEvents.BtnUpdateEvents.OnClick -> { /*TODO*/ }
            is BizIdLegalBtnEvents.BtnResetEvents.OnClick -> { /*TODO*/ }
            is BizIdLegalBtnEvents.BtnUpdateEvents.OnClick -> { /*TODO*/ }
            is BizIdTaxationBtnEvents.BtnResetEvents.OnClick -> { /*TODO*/ }
            is BizIdTaxationBtnEvents.BtnUpdateEvents.OnClick -> { /*TODO*/ }
            is BizAddressesBtnEvents.BtnAddEvents.OnClick -> { /*TODO*/ }
            is BizAddressesBtnEvents.BtnDeleteAllEvents.OnClick -> { /*TODO*/ }
            is BizAddressesBtnEvents.BtnDeleteEvents.OnClick -> { /*TODO*/ }
            is BizAddressesBtnEvents.BtnUpdateEvents.OnClick -> { /*TODO*/ }
            is BizContactsBtnEvents.BtnAddEvents.OnClick -> { /*TODO*/ }
            is BizContactsBtnEvents.BtnDeleteAllEvents.OnClick -> { /*TODO*/ }
            is BizContactsBtnEvents.BtnDeleteEvents.OnClick -> { /*TODO*/ }
            is BizContactsBtnEvents.BtnUpdateEvents.OnClick -> { /*TODO*/ }
            is BizLinksBtnEvents.BtnAddEvents.OnClick -> { /*TODO*/ }
            is BizLinksBtnEvents.BtnDeleteAllEvents.OnClick -> { /*TODO*/ }
            is BizLinksBtnEvents.BtnDeleteEvents.OnClick -> { /*TODO*/ }
            is BizLinksBtnEvents.BtnUpdateEvents.OnClick -> { /*TODO*/ }
        }
    }

    private fun updateDialogState(
        dlgLoading: Boolean = false,
        dlgScrDesc: Boolean = false,
        dlgDenySession: Boolean = false
    ) = _uiState.update {
        it.copy(
            dialogState = it.dialogState.copy(
                dlgLoading = mutableStateOf(dlgLoading),
                dlgScrDesc = mutableStateOf(dlgScrDesc),
                dlgDenySession = mutableStateOf(dlgDenySession)
            )
        )
    }
    private fun resetDialogState() = _uiState.update { it.copy(dialogState = DialogState()) }
    private fun resetUiStateBizProfile() = _uiState.update { it.copy(bizProfile = Loading) }
    private fun resetDialogAndUiState() { resetDialogState(); resetUiStateBizProfile() }

    private fun onOpenEvent(sessionState: SessionState, currentScreen: ScrGraphs) {
        resetUiStateBizProfile(); resetDialogState()
        when(sessionState) {
            SessionState.Loading -> updateDialogState(dlgLoading = true)
            is SessionState.Invalid -> if(currentScreen.usesAuth) onDenySession()
            else viewModelScope.launch {
                resetUiStateBizProfile(); updateDialogState(dlgLoading = true)
                ucGetBizProfile.invoke().flowOn(ioDispatcher).collectLatest { data ->
                    _uiState.update {
                        it.copy(
                            bizProfile = Success(
                                bizProfile = data.first,
                                configCurrent = data.second
                            ),
                            dialogState = DialogState()
                        )
                    }
                }
            }
            is SessionState.Valid -> viewModelScope.launch {
                resetUiStateBizProfile(); updateDialogState(dlgLoading = true)
                ucGetBizProfile.invoke().flowOn(ioDispatcher).collectLatest { data ->
                    _uiState.update {
                        it.copy(
                            bizProfile = Success(
                                bizProfile = data.first,
                                configCurrent = data.second
                            ),
                            dialogState = DialogState()
                        )
                    }
                }
            }
        }
    }
    private fun onDenySession() { resetDialogState(); updateDialogState(dlgDenySession = true) }
}