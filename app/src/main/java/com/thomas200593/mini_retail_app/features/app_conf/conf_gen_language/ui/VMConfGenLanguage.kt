package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.ui

import androidx.appcompat.app.AppCompatDelegate.setApplicationLocales
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.LocaleListCompat.create
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.domain.UCGetConfGenLanguage
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.ConfigLanguages
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.repository.RepoConfGenLanguage
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.ui.VMConfGenLanguage.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.ui.VMConfGenLanguage.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.ui.VMConfGenLanguage.UiEvents.ButtonEvents.BtnSetPrefLanguageEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.ui.VMConfGenLanguage.UiEvents.DialogEvents.DlgDenySetDataEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.ui.VMConfGenLanguage.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.ui.VMConfGenLanguage.UiStateConfigLanguage.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.ui.VMConfGenLanguage.UiStateConfigLanguage.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class VMConfGenLanguage @Inject constructor(
    private val repoConfGenLanguage: RepoConfGenLanguage,
    private val ucGetConfGenLanguage: UCGetConfGenLanguage,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    sealed interface UiStateConfigLanguage {
        data object Loading : UiStateConfigLanguage
        data class Success(val configLanguages: ConfigLanguages) : UiStateConfigLanguage
    }
    data class UiState(
        val configLanguages: UiStateConfigLanguage = Loading,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgLoadingAuth: MutableState<Boolean> = mutableStateOf(false),
        val dlgLoadingGetData: MutableState<Boolean> = mutableStateOf(false),
        val dlgDenySetData: MutableState<Boolean> = mutableStateOf(false),
        val dlgScrDesc: MutableState<Boolean> = mutableStateOf(false)
    )
    sealed interface UiEvents {
        data class OnOpenEvents(
            val sessionState: SessionState,
            val currentScreen: ScrGraphs
        ) : UiEvents
        sealed interface ButtonEvents : UiEvents {
            sealed interface BtnNavBackEvents : ButtonEvents {
                data object OnClick : BtnNavBackEvents
            }
            sealed interface BtnScrDescEvents : ButtonEvents {
                data object OnClick : BtnScrDescEvents
                data object OnDismiss : BtnScrDescEvents
            }
            sealed interface BtnSetPrefLanguageEvents : ButtonEvents {
                data class OnAllow(val language: Language) : BtnSetPrefLanguageEvents
                data object OnDeny : BtnSetPrefLanguageEvents
            }
        }
        sealed interface DialogEvents : UiEvents {
            sealed interface DlgDenySetDataEvents : DialogEvents {
                data object OnDismiss : DlgDenySetDataEvents
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events) {
            is OnOpenEvents -> onOpenEvent(events.sessionState, events.currentScreen)
            is BtnNavBackEvents.OnClick -> resetDialogAndUiState()
            is BtnScrDescEvents.OnClick ->
                { resetDialogState(); updateDialogState(dlgScrDesc = true) }
            is BtnScrDescEvents.OnDismiss -> resetDialogState()
            is BtnSetPrefLanguageEvents.OnDeny -> onDenySet()
            is BtnSetPrefLanguageEvents.OnAllow -> onAllowSet(events.language)
            is DlgDenySetDataEvents.OnDismiss -> resetDialogAndUiState()
        }
    }

    private fun updateDialogState(
        dlgLoadingAuth: Boolean = false,
        dlgLoadingGetData: Boolean = false,
        dlgDenySetData: Boolean = false,
        dlgScrDesc: Boolean = false
    ) = _uiState.update { it.copy(
        dialogState = it.dialogState.copy(
            dlgLoadingAuth = mutableStateOf(dlgLoadingAuth),
            dlgLoadingGetData = mutableStateOf(dlgLoadingGetData),
            dlgDenySetData = mutableStateOf(dlgDenySetData),
            dlgScrDesc = mutableStateOf(dlgScrDesc)
        )
    ) }
    private fun resetDialogState() = _uiState.update { it.copy(dialogState = DialogState()) }
    private fun resetUiStateConfigLanguage() = _uiState.update { it.copy(configLanguages = Loading) }
    private fun resetDialogAndUiState() { resetDialogState(); resetUiStateConfigLanguage() }
    private fun onOpenEvent(sessionState: SessionState, currentScreen: ScrGraphs) {
        resetUiStateConfigLanguage(); resetDialogState()
        when(sessionState) {
            SessionState.Loading -> updateDialogState(dlgLoadingAuth = true)
            is SessionState.Invalid -> if(currentScreen.usesAuth) onDenySet()
            else viewModelScope.launch {
                resetUiStateConfigLanguage(); updateDialogState(dlgLoadingGetData = true)
                ucGetConfGenLanguage.invoke().flowOn(ioDispatcher).collect{ data ->
                    _uiState.update {
                        it.copy(
                            configLanguages = Success(data),
                            dialogState = DialogState()
                        )
                    }
                }
            }
            is SessionState.Valid -> viewModelScope.launch{
                resetUiStateConfigLanguage(); updateDialogState(dlgLoadingGetData = true)
                ucGetConfGenLanguage.invoke().flowOn(ioDispatcher).collect{ data ->
                    _uiState.update {
                        it.copy(
                            configLanguages = Success(data),
                            dialogState = DialogState()
                        )
                    }
                }
            }
        }
    }
    private fun onDenySet() { resetDialogState(); updateDialogState(dlgDenySetData = true) }
    private fun onAllowSet(language: Language) {
        resetDialogState()
        viewModelScope.launch {
            repoConfGenLanguage.setLanguage(language = language)
            setApplicationLocales(create(Locale(language.code)))
        }
    }
}