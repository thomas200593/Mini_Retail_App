package com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.ui.add_update

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SupplierAddUpdateViewModel @Inject constructor(

): ViewModel(){
    val uiState by mutableStateOf(null)

    fun onEvent(event: SupplierAddUpdateUiEvent){
        when(event){
            SupplierAddUpdateUiEvent.OnSessionCheckLoading -> {}
            is SupplierAddUpdateUiEvent.OnSessionInvalid -> {}
            is SupplierAddUpdateUiEvent.OnSessionValid -> {}
        }
    }
}
