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
import com.thomas200593.mini_retail_app.features.business.entity.supplier.dto.SortSupplier
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

    var sortBy by mutableStateOf(SortSupplier.GEN_ID_ASC)
        private set

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagedDataFlow =
        snapshotFlow { sortBy }
            .combine(snapshotFlow { searchQuery }){ sortBy, searchQuery -> searchQuery to sortBy }
            .flatMapLatest { (searchQuery, sortBy) -> useCase(searchQuery = searchQuery, sortBy = sortBy) }
            .cachedIn(scope = viewModelScope)
            .flowOn(ioDispatcher)

    fun performSearch(searchQuery: String) = viewModelScope.launch(ioDispatcher) {
        this@SupplierListViewModel.searchQuery = searchQuery
    }

    fun updateSortBy(sortBy: SortSupplier) = viewModelScope.launch(ioDispatcher) {
        this@SupplierListViewModel.sortBy = sortBy
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