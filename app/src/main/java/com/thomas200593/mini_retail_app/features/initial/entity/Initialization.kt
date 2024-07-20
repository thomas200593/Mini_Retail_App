package com.thomas200593.mini_retail_app.features.initial.entity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.component.Form.Component.UseCase.UiText
import com.thomas200593.mini_retail_app.features.app_config.entity.AppConfig
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.initial.ui.initialization.InitializationUiEvent

data class Initialization(val configCurrent: AppConfig.ConfigCurrent, val languages: Set<Language>)

data class InitializationUiFormState(
    val uiFormLegalName: String = String(),
    val uiFormLegalNameError: UiText? = UiText.StringResource(R.string.str_field_required),
    val uiFormCommonName: String = String(),
    val uiFormCommonNameError: UiText? = UiText.StringResource(R.string.str_field_required),
    val uiFormEnableSubmitBtn: Boolean = false,
    val initBizProfileResult: InitializationUiEvent.InitBizProfileResult
)

data class InitializationUiState(
    val initializationData: RequestState<Initialization> = RequestState.Idle,
    val uiEnableWelcomeMessage: Boolean = true,
    val uiEnableInitManualForm: Boolean = false,
    val initializationUiFormState: InitializationUiFormState,
    val uiEnableLoadingDialog: MutableState<Boolean> = mutableStateOf(false),
    val uiEnableEmptyDialog: MutableState<Boolean> = mutableStateOf(false),
    val uiEnableSuccessDialog: MutableState<Boolean> = mutableStateOf(false),
    val uiEnableErrorDialog: MutableState<Boolean> = mutableStateOf(false)
)