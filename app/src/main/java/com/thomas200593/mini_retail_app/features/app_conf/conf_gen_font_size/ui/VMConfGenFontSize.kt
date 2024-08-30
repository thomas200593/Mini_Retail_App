package com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.domain.UCGetConfFontSize
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.entity.ConfigFontSizes
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.repository.RepoConfGenFontSize
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiEvents.ButtonEvents.BtnNavBackEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiEvents.ButtonEvents.BtnScrDescEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiEvents.ButtonEvents.BtnSetPrefFontSizeEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiEvents.DialogEvents.DlgDenySetDataEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiEvents.OnOpenEvents
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_font_size.ui.VMConfGenFontSize.UiStateConfigFontSize.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class VMConfGenFontSize @Inject constructor(
    private val repoConfGenFontSize: RepoConfGenFontSize,
    private val ucGetConfFontSize: UCGetConfFontSize,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    sealed interface UiStateConfigFontSize {
        data object Loading : UiStateConfigFontSize
        data class Success(val configFontSizes: ConfigFontSizes): UiStateConfigFontSize
    }

    data class UiState(
        val configFontSizes: UiStateConfigFontSize = Loading
    )

    data class DialogState(
        val dlgLoadingAuth: MutableState<Boolean> = mutableStateOf(false),
        val dlgLoadingGetData: MutableState<Boolean> = mutableStateOf(false),
        val dlgDenySetData: MutableState<Boolean> = mutableStateOf(false),
        val dlgScrDesc: MutableState<Boolean> = mutableStateOf(false)
    )

    sealed class UiEvents {
        data class OnOpenEvents(
            val sessionState: SessionState,
            val currentScreen: ScrGraphs
        ) : UiEvents()

        sealed class ButtonEvents : UiEvents() {
            sealed class BtnNavBackEvents : ButtonEvents() {
                data object OnClick : BtnNavBackEvents()
            }

            sealed class BtnScrDescEvents : ButtonEvents() {
                data object OnClick : BtnScrDescEvents()
                data object OnDismiss : BtnScrDescEvents()
            }

            sealed class BtnSetPrefFontSizeEvents : ButtonEvents() {
                data class OnAllow(val fontSize: FontSize) : BtnSetPrefFontSizeEvents()
                data object OnDeny : BtnSetPrefFontSizeEvents()
            }
        }

        sealed class DialogEvents : UiEvents() {
            sealed class DlgDenySetDataEvents : DialogEvents() {
                data object OnDismiss : DlgDenySetDataEvents()
            }
        }
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(events: UiEvents) {
        when(events) {
            is OnOpenEvents -> {/*TODO*/}
            is BtnNavBackEvents.OnClick -> {/*TODO*/}
            is BtnScrDescEvents.OnClick -> {/*TODO*/}
            is BtnScrDescEvents.OnDismiss -> {/*TODO*/}
            is BtnSetPrefFontSizeEvents.OnDeny -> {/*TODO*/}
            is BtnSetPrefFontSizeEvents.OnAllow -> {/*TODO*/}
            is DlgDenySetDataEvents.OnDismiss -> {/*TODO*/}
        }
    }
}
