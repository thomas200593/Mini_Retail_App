package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.domain.UCGetConfGenTheme
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.entity.ConfigThemes
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.entity.Theme
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.repository.RepoConfGenTheme
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiEvents.BtnSelectThemeEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiStateConfigTheme.Error
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiStateConfigTheme.Loading
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_theme.ui.VMConfGenTheme.UiStateConfigTheme.Success
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
class VMConfGenTheme @Inject constructor(
    private val repoConfGenTheme: RepoConfGenTheme,
    private val ucGetConfGenTheme: UCGetConfGenTheme,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    sealed interface UiStateConfigTheme{
        data object Loading: UiStateConfigTheme
        data class Success(val configThemes: ConfigThemes): UiStateConfigTheme
        data class Error(val t: Throwable): UiStateConfigTheme
    }
    data class UiState(
        val configThemes: UiStateConfigTheme = Loading,
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
        sealed class BtnSelectThemeEvents: ButtonEvents(){
            data class OnClick(val theme: Theme): BtnSelectThemeEvents()
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events){
            OnOpenEvents -> onOpenEvent()
            BtnNavBackEvents.OnClick -> onBtnNavBackClicked()
            is BtnSelectThemeEvents.OnClick -> onSaveSelectedTheme(events.theme)
        }
    }
    private fun onOpenEvent() = viewModelScope.launch(ioDispatcher) {
        updateDialogState(
            dlgLoadDataEnabled = true,
            dlgLoadDataError = false
        )
        ucGetConfGenTheme.invoke().flowOn(ioDispatcher)
            .catch { e -> _uiState.update { it.copy(configThemes = Error(e)) }
                updateDialogState(
                    dlgLoadDataEnabled = false,
                    dlgLoadDataError = true
                )
            }
            .collect{ result ->
                _uiState.update {
                    it.copy(configThemes = Success(result.data), dialogState = DialogState())
                }
            }
    }
    private fun onBtnNavBackClicked() =
        _uiState.update { it.copy(dialogState = DialogState()) }
    private fun onSaveSelectedTheme(theme: Theme) =
        viewModelScope.launch(ioDispatcher) { repoConfGenTheme.setTheme(theme) }
    private fun updateDialogState(
        dlgLoadDataEnabled: Boolean = false,
        dlgLoadDataError: Boolean = false
    ) {
        _uiState.update { it.copy(
            dialogState = it.dialogState.copy(
                dlgLoadDataEnabled = mutableStateOf(dlgLoadDataEnabled),
                dlgLoadDataErrorEnabled = mutableStateOf(dlgLoadDataError)
            )
        ) }
    }
}