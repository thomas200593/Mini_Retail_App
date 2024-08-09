package com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.ui.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.domain.UCGetSupplierList
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.entity.Supplier
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.entity.dto.SortSupplier
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.repository.RepoSupplier
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
    useCase: UCGetSupplierList,
    private val repository: RepoSupplier,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    companion object{
        private const val KEY_SEARCH_QUERY = "searchQuery"
        private const val KEY_SORT_BY = "sortBy"
    }

    var searchQuery by mutableStateOf(savedStateHandle.get<String>(KEY_SEARCH_QUERY) ?: String())
        private set

    var sortBy by mutableStateOf(savedStateHandle.get<SortSupplier>(KEY_SORT_BY) ?: SortSupplier.GEN_ID_ASC)
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
        savedStateHandle[KEY_SEARCH_QUERY] = searchQuery
    }

    fun updateSortBy(sortBy: SortSupplier) = viewModelScope.launch(ioDispatcher) {
        this@SupplierListViewModel.sortBy = sortBy
        savedStateHandle[KEY_SORT_BY] = sortBy
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