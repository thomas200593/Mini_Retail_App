package com.thomas200593.mini_retail_app.features.business.ui.master_data.supplier.add_update

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BusinessProfileSummary

sealed class SupplierAddUpdateUiEvent {
    data object OnSessionCheckLoading: SupplierAddUpdateUiEvent()
    data class OnSessionValid(val session: SessionState.Valid): SupplierAddUpdateUiEvent()
    data class OnSessionInvalid(val error: SessionState.Invalid): SupplierAddUpdateUiEvent()
    sealed class SupplierAddUpdateResult {
        data object Idle: SupplierAddUpdateResult()
        data object Loading: SupplierAddUpdateResult()
        data class Success(val bizProfileSummary: BusinessProfileSummary): SupplierAddUpdateResult()
        data class Error(val t: Throwable?): SupplierAddUpdateResult()
    }
}