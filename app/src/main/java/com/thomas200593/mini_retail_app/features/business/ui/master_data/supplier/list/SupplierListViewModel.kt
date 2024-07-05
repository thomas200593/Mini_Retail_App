package com.thomas200593.mini_retail_app.features.business.ui.master_data.supplier.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.business.domain.GetSupplierListUseCase
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import com.thomas200593.mini_retail_app.features.business.entity.supplier.dto.SupplierDataOrdering
import com.thomas200593.mini_retail_app.features.business.repository.SupplierRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ulid.ULID
import javax.inject.Inject

@HiltViewModel
class SupplierListViewModel @Inject constructor(
    useCase: GetSupplierListUseCase,
    private val repository: SupplierRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    val searchQuery: MutableStateFlow<String> = MutableStateFlow(String())
    private val order: MutableStateFlow<SupplierDataOrdering> = MutableStateFlow(SupplierDataOrdering.GEN_ID_ASC)
    private val debounceDuration = 500L

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val supplierPagingDataFlow : Flow<PagingData<Supplier>> = searchQuery
            .debounce(debounceDuration)
            .flatMapLatest { query -> useCase(query, order.value) }
            .cachedIn(scope = viewModelScope)
            .flowOn(ioDispatcher)

    fun performSearch(query: String) = viewModelScope.launch(ioDispatcher){
        searchQuery.value = query
    }

    fun updateDataOrdering(orderBy: SupplierDataOrdering) = viewModelScope.launch(ioDispatcher){
        order.value = orderBy
    }

    fun testGen() = viewModelScope.launch(ioDispatcher) {
        repeat(10){
            val supplier = Supplier(
                seqId = 0,
                genId = ULID.randomULID(),
                sprLegalName = "Supplier ${Math.random()}",
                auditTrail = AuditTrail()
            )
            repository.testGen(supplier)
        }
    }
}
