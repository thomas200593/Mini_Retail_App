package com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.ui.add_update

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.dto.BizProfileSummary

sealed class SupplierAddUpdateUiEvent {
    data object OnSessionCheckLoading: SupplierAddUpdateUiEvent()
    data class OnSessionValid(val session: SessionState.Valid): SupplierAddUpdateUiEvent()
    data class OnSessionInvalid(val error: SessionState.Invalid): SupplierAddUpdateUiEvent()
    sealed class SupplierAddUpdateResult {
        data object Idle: SupplierAddUpdateResult()
        data object Loading: SupplierAddUpdateResult()
        data class Success(val bizProfileSummary: BizProfileSummary): SupplierAddUpdateResult()
        data class Error(val t: Throwable?): SupplierAddUpdateResult()
    }
}