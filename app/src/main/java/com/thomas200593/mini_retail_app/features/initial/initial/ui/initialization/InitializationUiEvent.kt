package com.thomas200593.mini_retail_app.features.initial.initial.ui.initialization

import com.thomas200593.mini_retail_app.features.app_conf._g_language.entity.Language
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BusinessProfileSummary

sealed class InitializationUiEvent{
    data object OnOpen: InitializationUiEvent()
    data class OnChangeLanguage(val language: Language): InitializationUiEvent()
    data object BeginInitBizProfileManual: InitializationUiEvent()
    data class OnUiFormLegalNameChanged(val legalName: String): InitializationUiEvent()
    data class OnUiFormCommonNameChanged(val commonName: String): InitializationUiEvent()
    data class OnUiFormSubmitInitManual(val bizProfile: BusinessProfileSummary): InitializationUiEvent()
    data object OnUiFormCancelInitManual: InitializationUiEvent()
    data class BeginInitBizProfileDefault(val bizProfile: BusinessProfileSummary): InitializationUiEvent()
    sealed class InitBizProfileResult {
        data object Idle: InitBizProfileResult()
        data object Loading: InitBizProfileResult()
        data object Empty: InitBizProfileResult()
        data class Success(val bizProfileSummary: BusinessProfileSummary): InitBizProfileResult()
        data class Error(val t: Throwable?): InitBizProfileResult()
    }
}