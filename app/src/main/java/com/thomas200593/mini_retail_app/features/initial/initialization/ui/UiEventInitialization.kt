package com.thomas200593.mini_retail_app.features.initial.initialization.ui

import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.entity.Language
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BizProfileSummary

sealed class UiEventInitialization{
    data object OnOpen: UiEventInitialization()
    data class OnChangeLanguage(val language: Language): UiEventInitialization()
    data object BeginInitBizProfileManual: UiEventInitialization()
    data class OnUiFormLegalNameChanged(val legalName: String): UiEventInitialization()
    data class OnUiFormCommonNameChanged(val commonName: String): UiEventInitialization()
    data class OnUiFormSubmitInitManual(val bizProfile: BizProfileSummary): UiEventInitialization()
    data object OnUiFormCancelInitManual: UiEventInitialization()
    data class BeginInitBizProfileDefault(val bizProfile: BizProfileSummary): UiEventInitialization()
    sealed class InitBizProfileResult {
        data object Idle: InitBizProfileResult()
        data object Loading: InitBizProfileResult()
        data object Empty: InitBizProfileResult()
        data class Success(val bizProfileSummary: BizProfileSummary): InitBizProfileResult()
        data class Error(val t: Throwable?): InitBizProfileResult()
    }
}