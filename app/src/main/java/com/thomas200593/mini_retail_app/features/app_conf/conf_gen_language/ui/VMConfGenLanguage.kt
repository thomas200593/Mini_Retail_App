package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.ui

import androidx.appcompat.app.AppCompatDelegate.setApplicationLocales
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.LocaleListCompat.create
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.domain.UCGetConfGenLanguage
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.ConfigLanguages
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.repository.RepoConfGenLanguage
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.ui.VMConfGenLanguage.UiEvents.BtnSelectLanguageEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.ui.VMConfGenLanguage.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.ui.VMConfGenLanguage.UiEvents.OnOpenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
): ViewModel(){
    data class UiState(
        val configLanguages: ResourceState<ConfigLanguages> = Idle,
        val dialogState: DialogState = DialogState()
    )
    data class DialogState(
        val dlgLoadDataEnabled : MutableState<Boolean> = mutableStateOf(false),
        val dlgLoadDataErrorEnabled : MutableState<Boolean> = mutableStateOf(false)
    )
    sealed class UiEvents {
        data object OnOpenEvents: UiEvents()
        sealed class ButtonEvents : UiEvents(){
            sealed class BtnNavBackEvents: ButtonEvents(){
                data object OnClick: BtnNavBackEvents()
            }
        }
        sealed class BtnSelectLanguageEvents: ButtonEvents(){
            data class OnClick(val language: Language): BtnSelectLanguageEvents()
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents){
        when(events){
            OnOpenEvents -> onOpenEvent()
            BtnNavBackEvents.OnClick -> onBtnNavBackClicked()
            is BtnSelectLanguageEvents.OnClick -> onSaveSelectedLanguage(events.language)
        }
    }
    private fun onOpenEvent() = viewModelScope.launch(ioDispatcher) {
        updateDialogState(
            dlgLoadDataEnabled = true,
            dlgLoadDataError = false
        )
        ucGetConfGenLanguage.invoke().flowOn(ioDispatcher)
            .catch { e ->
                _uiState.update { it.copy(configLanguages = Error(e)) }
                updateDialogState(
                    dlgLoadDataEnabled = false,
                    dlgLoadDataError = true
                )
            }
            .collect{ data ->
                _uiState.update { it.copy(configLanguages = data, dialogState = DialogState()) }
            }
    }
    private fun onBtnNavBackClicked() {
        _uiState.update { it.copy(dialogState = DialogState()) }
    }
    private fun onSaveSelectedLanguage(language: Language) = viewModelScope.launch(ioDispatcher) {
        repoConfGenLanguage.setLanguage(language)
        setApplicationLocales(create(Locale(language.code)))
    }
    private fun updateDialogState(
        dlgLoadDataEnabled: Boolean = false,
        dlgLoadDataError: Boolean = false
    ) {
        _uiState.update {
            it.copy(
                dialogState = it.dialogState.copy(
                    dlgLoadDataEnabled = mutableStateOf(dlgLoadDataEnabled),
                    dlgLoadDataErrorEnabled = mutableStateOf(dlgLoadDataError),
                )
            )
        }
    }
}