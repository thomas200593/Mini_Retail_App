package com.thomas200593.mini_retail_app.features.business.biz_profile.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Address
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Contact
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.Link
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
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.OnSessionInvalidEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiEvents.OnSessionLoadingEvents
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiStateBizProfile.Error
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiStateBizProfile.Loading
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiStateBizProfile.Reject
import com.thomas200593.mini_retail_app.features.business.biz_profile.ui.VMBizProfile.UiStateBizProfile.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
        data object Reject: UiStateBizProfile
        data class Success(val bizProfile: BizProfile, val configCurrent: ConfigCurrent): UiStateBizProfile
        data class Error(val t: Throwable): UiStateBizProfile
    }
    data class UiState(
        val bizProfile: UiStateBizProfile = Loading,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgSessionLoading: MutableState<Boolean> = mutableStateOf(false),
        val dlgSessionInvalid: MutableState<Boolean> = mutableStateOf(false),
        val dlgResetBizIdName: MutableState<Boolean> = mutableStateOf(false),
        val dlgProcessing: MutableState<Boolean> = mutableStateOf(false),
        val dlgDeleteAllBizAddresses: MutableState<Boolean> = mutableStateOf(false)
    )
    sealed class UiEvents{
        data class OnOpenEvents(val sessionState: SessionState): UiEvents()
        data object OnSessionLoadingEvents: UiEvents()
        data object OnSessionInvalidEvents: UiEvents()
        sealed class ButtonEvents: UiEvents(){
            //NavBack
            sealed class BtnNavBackEvents: ButtonEvents() {
                data object OnClick: BtnNavBackEvents()
            }
            //BizIdName
            sealed class BizIdNameBtnEvents: ButtonEvents(){
                sealed class BtnUpdateEvents: BizIdNameBtnEvents(){
                    data object OnClick: BtnUpdateEvents()
                }
                sealed class BtnResetEvents: BizIdNameBtnEvents(){
                    data object OnClick: BtnResetEvents()
                    data class OnConfirm(val sessionState: SessionState): BtnResetEvents()
                    data class OnDismissDialog(val sessionState: SessionState): BtnResetEvents()
                }
            }
            //BizIdIndustry
            sealed class BizIdIndustryBtnEvents: ButtonEvents(){
                sealed class BtnUpdateEvents: BizIdIndustryBtnEvents(){
                    data object OnClick: BtnUpdateEvents()
                }
                sealed class BtnResetEvents: BizIdIndustryBtnEvents(){
                    data object OnClick: BtnResetEvents()
                }
            }
            //BizIdLegal
            sealed class BizIdLegalBtnEvents: ButtonEvents(){
                sealed class BtnUpdateEvents: BizIdLegalBtnEvents(){
                    data object OnClick: BtnUpdateEvents()
                }
                sealed class BtnResetEvents: BizIdLegalBtnEvents(){
                    data object OnClick: BtnResetEvents()
                }
            }
            //BizIdTaxation
            sealed class BizIdTaxationBtnEvents: ButtonEvents(){
                sealed class BtnUpdateEvents: BizIdTaxationBtnEvents(){
                    data object OnClick: BtnUpdateEvents()
                }
                sealed class BtnResetEvents: BizIdTaxationBtnEvents(){
                    data object OnClick: BtnResetEvents()
                }
            }
            //BizAddresses
            sealed class BizAddressesBtnEvents: ButtonEvents(){
                sealed class BtnAddEvents: BizAddressesBtnEvents() {
                    data object OnClick: BtnAddEvents()
                }
                sealed class BtnUpdateEvents: BizAddressesBtnEvents() {
                    data class OnClick(val address: Address): BtnUpdateEvents()
                }
                sealed class BtnDeleteEvents: BizAddressesBtnEvents(){
                    data class OnClick(val address: Address): BtnDeleteEvents()
                }
                sealed class BtnDeleteAllEvents: BizAddressesBtnEvents(){
                    data object OnClick: BtnDeleteAllEvents()
                    data class OnConfirm(val sessionState: SessionState): BtnDeleteAllEvents()
                    data class OnDismissDialog(val sessionState: SessionState): BtnDeleteAllEvents()
                }
            }
            //BizContacts
            sealed class BizContactsBtnEvents: ButtonEvents(){
                sealed class BtnAddEvents: BizContactsBtnEvents() {
                    data object OnClick: BtnAddEvents()
                }
                sealed class BtnUpdateEvents: BizContactsBtnEvents() {
                    data class OnClick(val contact: Contact): BtnUpdateEvents()
                }
                sealed class BtnDeleteEvents: BizContactsBtnEvents(){
                    data class OnClick(val contact: Contact): BtnDeleteEvents()
                }
                sealed class BtnDeleteAllEvents: BizContactsBtnEvents(){
                    data object OnClick: BtnDeleteAllEvents()
                }
            }
            //BizLinks
            sealed class BizLinksBtnEvents: ButtonEvents(){
                sealed class BtnAddEvents: BizLinksBtnEvents() {
                    data object OnClick: BtnAddEvents()
                }
                sealed class BtnUpdateEvents: BizLinksBtnEvents() {
                    data class OnClick(val link: Link): BtnUpdateEvents()
                }
                sealed class BtnDeleteEvents: BizLinksBtnEvents(){
                    data class OnClick(val link: Link): BtnDeleteEvents()
                }
                sealed class BtnDeleteAllEvents: BizLinksBtnEvents(){
                    data object OnClick: BtnDeleteAllEvents()
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events){
            is OnOpenEvents -> onOpenEvent(events.sessionState)
            //On Session Loading
            OnSessionLoadingEvents -> onSessionLoadingEvent()
            //On Session Reject
            is OnSessionInvalidEvents -> onSessionInvalidEvent()
            //NavBack
            is BtnNavBackEvents.OnClick -> onBtnNavBackEvent()
            //BizIdName
            is BizIdNameBtnEvents.BtnUpdateEvents.OnClick -> onBtnBizIdNameUpdateEvent()
            is BizIdNameBtnEvents.BtnResetEvents.OnClick -> onBtnBizIdNameResetEvent()
            is BizIdNameBtnEvents.BtnResetEvents.OnConfirm -> onProcessResetBizIdName(events.sessionState)
            is BizIdNameBtnEvents.BtnResetEvents.OnDismissDialog -> onOpenEvent(events.sessionState)
            //BizIdIndustry
            is BizIdIndustryBtnEvents.BtnUpdateEvents.OnClick -> onBtnBizIdIndustryUpdateEvent()
            is BizIdIndustryBtnEvents.BtnResetEvents.OnClick -> onBtnBizIdIndustryResetEvent()
            //BizIdLegal
            is BizIdLegalBtnEvents.BtnUpdateEvents.OnClick -> onBtnBizIdLegalUpdateEvent()
            is BizIdLegalBtnEvents.BtnResetEvents.OnClick -> onBtnBizIdLegalResetEvent()
            //BizIdTaxation
            is BizIdTaxationBtnEvents.BtnUpdateEvents.OnClick -> onBtnBizIdTaxationUpdateEvent()
            is BizIdTaxationBtnEvents.BtnResetEvents.OnClick -> onBtnBizIdTaxationResetEvent()
            //BizAddresses
            is BizAddressesBtnEvents.BtnAddEvents.OnClick -> onBtnBizAddressesAddEvent()
            is BizAddressesBtnEvents.BtnUpdateEvents.OnClick -> onBtnBizAddressesUpdateEvent(events.address)
            is BizAddressesBtnEvents.BtnDeleteEvents.OnClick -> onBtnBizAddressesDeleteEvent(events.address)
            is BizAddressesBtnEvents.BtnDeleteAllEvents.OnClick -> onBtnBizAddressesDeleteAllEvent()
            is BizAddressesBtnEvents.BtnDeleteAllEvents.OnConfirm -> onProcessResetBizAddresses(events.sessionState)
            is BizAddressesBtnEvents.BtnDeleteAllEvents.OnDismissDialog -> onOpenEvent(events.sessionState)
            //BizContacts
            is BizContactsBtnEvents.BtnAddEvents.OnClick -> onBtnBizContactsAddEvent()
            is BizContactsBtnEvents.BtnUpdateEvents.OnClick -> onBtnBizContactsUpdateEvent(events.contact)
            is BizContactsBtnEvents.BtnDeleteEvents.OnClick -> onBtnBizContactsDeleteEvent(events.contact)
            is BizContactsBtnEvents.BtnDeleteAllEvents.OnClick -> onBtnBizContactsDeleteAllEvent()
            //BizLinks
            is BizLinksBtnEvents.BtnAddEvents.OnClick -> onBtnBizLinksAddEvent()
            is BizLinksBtnEvents.BtnUpdateEvents.OnClick -> onBtnBizLinksUpdateEvent(events.link)
            is BizLinksBtnEvents.BtnDeleteEvents.OnClick -> onBtnBizLinksDeleteEvent(events.link)
            is BizLinksBtnEvents.BtnDeleteAllEvents.OnClick -> onBtnBizLinksDeleteAllEvent()
        }
    }

    /**
     * Update Dialog State
     */
    private fun updateDialogState(
        dlgLoading: Boolean = false,
        dlgSessionInvalid: Boolean = false,
        dlgResetBizIdName: Boolean = false,
        dlgProcessing: Boolean = false,
        dlgDeleteAllBizAddresses: Boolean = false
    ){
        _uiState.update { it.copy(
            dialogState = it.dialogState.copy(
                dlgSessionLoading = mutableStateOf(dlgLoading),
                dlgSessionInvalid = mutableStateOf(dlgSessionInvalid),
                dlgResetBizIdName = mutableStateOf(dlgResetBizIdName),
                dlgProcessing = mutableStateOf(dlgProcessing),
                dlgDeleteAllBizAddresses = mutableStateOf(dlgDeleteAllBizAddresses)
            )
        ) }
    }

    /**
     * Reset Dialog State
     */
    private fun resetDialogState() = _uiState.update { it.copy(dialogState = DialogState()) }

    /**
     * OnOpen
     */
    private fun onOpenEvent(sessionState: SessionState) = viewModelScope.launch(ioDispatcher) {
        when(sessionState){
            SessionState.Loading -> _uiState.update { it.copy(bizProfile = Loading) }
            is SessionState.Invalid -> _uiState.update { it.copy(bizProfile = Reject) }
            is SessionState.Valid -> ucGetBizProfile.invoke().flowOn(ioDispatcher)
                .catch { t -> _uiState.update { it.copy(bizProfile = Error(t)) } }
                .collect{ result -> _uiState.update { it.copy(
                    bizProfile = Success(result.data.first, result.data.second),
                    dialogState = DialogState()
                ) } }
        }
    }

    /**
     * Loading Session
     */
    private fun onSessionLoadingEvent() = resetDialogState()
        .apply { updateDialogState(dlgLoading = true) }

    /**
     * Invalid Session
     */
    private fun onSessionInvalidEvent() = resetDialogState()
        .apply { updateDialogState(dlgSessionInvalid = true) }

    /**
     * NavBack
     */
    private fun onBtnNavBackEvent() = resetDialogState()

    /**
     * BizIdName
     */
    private fun onBtnBizIdNameUpdateEvent() {
        resetDialogState()
        /*TODO*/
    }
    private fun onBtnBizIdNameResetEvent() = resetDialogState()
        .apply { updateDialogState(dlgResetBizIdName = true) }
    private fun onProcessResetBizIdName(sessionState: SessionState) = when(sessionState){
        SessionState.Loading -> _uiState.update { it.copy(bizProfile = Loading) }
        is SessionState.Invalid -> _uiState.update { it.copy(bizProfile = Reject) }
        is SessionState.Valid -> resetDialogState().apply {
            updateDialogState(dlgProcessing = true)
            /*TODO*/
        }
    }

    /**
     * BizIdIndustry
     */
    private fun onBtnBizIdIndustryUpdateEvent() {
        resetDialogState()
        /*TODO*/
    }
    private fun onBtnBizIdIndustryResetEvent() {
        resetDialogState()
        /*TODO*/
    }

    /**
     * BizIdLegal
     */
    private fun onBtnBizIdLegalUpdateEvent() {
        resetDialogState()
        /*TODO*/
    }
    private fun onBtnBizIdLegalResetEvent() {
        resetDialogState()
        /*TODO*/
    }

    /**
     * BizIdTaxation
     */
    private fun onBtnBizIdTaxationUpdateEvent() {
        resetDialogState()
        /*TODO*/
    }
    private fun onBtnBizIdTaxationResetEvent() {
        resetDialogState()
        /*TODO*/
    }

    /**
     * BizAddresses
     */
    private fun onBtnBizAddressesAddEvent() {
        resetDialogState()
        /*TODO*/
    }
    private fun onBtnBizAddressesUpdateEvent(address: Address) {
        resetDialogState()
        /*TODO*/
    }
    private fun onBtnBizAddressesDeleteEvent(address: Address) {
        resetDialogState()
        /*TODO*/
    }
    private fun onBtnBizAddressesDeleteAllEvent() = resetDialogState()
        .apply { updateDialogState(dlgDeleteAllBizAddresses = true) }
    private fun onProcessResetBizAddresses(sessionState: SessionState) = when(sessionState){
        is SessionState.Invalid -> _uiState.update { it.copy(bizProfile = Loading) }
        SessionState.Loading -> _uiState.update { it.copy(bizProfile = Reject) }
        is SessionState.Valid -> resetDialogState().apply {
            updateDialogState(dlgProcessing = true)
            /*TODO*/
        }
    }

    /**
     * BizContacts
     */
    private fun onBtnBizContactsAddEvent() {
        resetDialogState()
        /*TODO*/
    }
    private fun onBtnBizContactsUpdateEvent(contact: Contact) {
        resetDialogState()
        /*TODO*/
    }
    private fun onBtnBizContactsDeleteEvent(contact: Contact) {
        resetDialogState()
        /*TODO*/
    }
    private fun onBtnBizContactsDeleteAllEvent() {
        resetDialogState()
        /*TODO*/
    }

    /**
     * BizLinks
     */
    private fun onBtnBizLinksAddEvent() {
        resetDialogState()
        /*TODO*/
    }
    private fun onBtnBizLinksUpdateEvent(link: Link) {
        resetDialogState()
        /*TODO*/
    }
    private fun onBtnBizLinksDeleteEvent(link: Link) {
        resetDialogState()
        /*TODO*/
    }
    private fun onBtnBizLinksDeleteAllEvent() {
        resetDialogState()
        /*TODO*/
    }
}