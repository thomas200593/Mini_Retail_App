package com.thomas200593.mini_retail_app.features.initial.initialization.entity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.thomas200593.mini_retail_app.R.string.str_field_required
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Idle
import com.thomas200593.mini_retail_app.core.ui.component.CustomForm.Component.UseCase.UiText
import com.thomas200593.mini_retail_app.core.ui.component.CustomForm.Component.UseCase.UiText.StringResource
import com.thomas200593.mini_retail_app.features.app_conf._gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.app_conf.app_config.entity.AppConfig.ConfigCurrent
import com.thomas200593.mini_retail_app.features.initial.initialization.ui.UiEventInitialization.InitBizProfileResult

data class Initialization(val configCurrent: ConfigCurrent, val languages: Set<Language>)

data class InitializationUiFormState(
    val uiFormLegalName: String = String(),
    val uiFormLegalNameError: UiText? = StringResource(str_field_required),
    val uiFormCommonName: String = String(),
    val uiFormCommonNameError: UiText? = StringResource(str_field_required),
    val uiFormEnableSubmitBtn: Boolean = false,
    val initBizProfileResult: InitBizProfileResult
)

data class InitializationUiState(
    val initializationData: ResourceState<Initialization> = Idle,
    val uiEnableWelcomeMessage: Boolean = true,
    val uiEnableInitManualForm: Boolean = false,
    val initializationUiFormState: InitializationUiFormState,
    val uiEnableLoadingDialog: MutableState<Boolean> = mutableStateOf(false),
    val uiEnableEmptyDialog: MutableState<Boolean> = mutableStateOf(false),
    val uiEnableSuccessDialog: MutableState<Boolean> = mutableStateOf(false),
    val uiEnableErrorDialog: MutableState<Boolean> = mutableStateOf(false)
)