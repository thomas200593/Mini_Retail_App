package com.thomas200593.mini_retail_app.features.business.ui.master_data.supplier.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.combine
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

    var searchQuery by mutableStateOf(String())
        private set

    var dataOrderBy by mutableStateOf(SupplierDataOrdering.GEN_ID_ASC)
        private set

    @OptIn(ExperimentalCoroutinesApi::class)
    val supplierPagingDataFlow =
        snapshotFlow { dataOrderBy }
            .combine(snapshotFlow { searchQuery }){ orderBy, query -> query to orderBy }
            .flatMapLatest { (query, orderBy) -> useCase(query, orderBy) }
            .cachedIn(scope = viewModelScope)
            .flowOn(ioDispatcher)

    fun performSearch(query: String) = viewModelScope.launch(ioDispatcher) {
        this@SupplierListViewModel.searchQuery = query
    }

    fun updateOrderBy(orderBy: SupplierDataOrdering) = viewModelScope.launch(ioDispatcher) {
        this@SupplierListViewModel.dataOrderBy = orderBy
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