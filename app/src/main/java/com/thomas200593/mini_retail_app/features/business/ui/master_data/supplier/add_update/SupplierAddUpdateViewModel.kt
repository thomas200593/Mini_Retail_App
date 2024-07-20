package com.thomas200593.mini_retail_app.features.business.ui.master_data.supplier.add_update

import androidx.lifecycle.ViewModel
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SupplierAddUpdateViewModel @Inject constructor(

): ViewModel(){
    fun onEvent(event: SupplierAddUpdateUiEvent){
        when(event){
            is SupplierAddUpdateUiEvent.OnOpen -> {
                when(event.sessionState){
                    SessionState.Loading -> {
                        //TODO SHOW LOADING PAGE
                    }
                    is SessionState.Invalid -> {
                        //TODO SHOW OPERATION NOT ALLOWED
                    }
                    is SessionState.Valid -> {
                        //TODO CHECK IF ID EXISTS THEN UPDATE, ELSE INSERT
                    }
                }
            }
        }
    }
}
