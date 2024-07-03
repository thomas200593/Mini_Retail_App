package com.thomas200593.mini_retail_app.features.business.ui.master_data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.business.navigation.DestinationMasterData
import com.thomas200593.mini_retail_app.features.business.repository.MasterDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MasterDataViewModel @Inject constructor(
    private val masterDataRepository: MasterDataRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _businessMasterDataMenuPreferences: MutableState<RequestState<Set<DestinationMasterData>>> = mutableStateOf(RequestState.Idle)
    val businessMasterDataMenuPreferences = _businessMasterDataMenuPreferences

    fun onOpen(sessionState: SessionState) = viewModelScope.launch(ioDispatcher){
        getBusinessMasterDataMenuPreferences(sessionState)
    }

    private suspend fun getBusinessMasterDataMenuPreferences(sessionState: SessionState) = viewModelScope.launch(ioDispatcher){
        _businessMasterDataMenuPreferences.value = RequestState.Loading
        _businessMasterDataMenuPreferences.value = try {
            RequestState.Success(masterDataRepository.getMenuData(sessionState))
        }catch (e: Throwable){
            RequestState.Error(e)
        }
    }
}
