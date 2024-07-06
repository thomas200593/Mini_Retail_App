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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ulid.ULID
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SupplierListViewModel @Inject constructor(
    useCase: GetSupplierListUseCase,
    private val repository: SupplierRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    //Searching Query
    val query: MutableStateFlow<String> =
        MutableStateFlow(String())

    //Data Order By
    val orderBy: MutableStateFlow<SupplierDataOrdering> =
        MutableStateFlow(SupplierDataOrdering.GEN_ID_ASC)

    private val debounceDuration = 500L

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val supplierPagingDataFlow : Flow<PagingData<Supplier>> =
        combine(orderBy, query.debounce(debounceDuration))
        { orderBy, query -> query to orderBy }
            .flatMapLatest { (query, orderBy) -> useCase(query, orderBy) }
            .cachedIn(viewModelScope)
            .flowOn(ioDispatcher)

    fun performSearch(query: String) = viewModelScope.launch(ioDispatcher){
        this@SupplierListViewModel.query.value = query
    }

    //TODO NOT WORKING
    fun updateOrderBy(orderBy: SupplierDataOrdering) = viewModelScope.launch(ioDispatcher){
        this@SupplierListViewModel.orderBy.value = orderBy
    }

    fun testGen() = viewModelScope.launch(ioDispatcher) {
        val supplier = Supplier(
            seqId = 0,
            genId = ULID.randomULID(),
            sprLegalName = "Supplier ${Random.nextInt(0,100)}",
            auditTrail = AuditTrail()
        )
        repository.testGen(supplier)
    }
}