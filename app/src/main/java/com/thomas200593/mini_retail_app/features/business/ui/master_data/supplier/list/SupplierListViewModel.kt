package com.thomas200593.mini_retail_app.features.business.ui.master_data.supplier.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.business.domain.GetSupplierListUseCase
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import com.thomas200593.mini_retail_app.features.business.repository.SupplierRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
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
    val supplierPagingDataFlow : Flow<PagingData<Supplier>> = useCase()
        .cachedIn(scope = viewModelScope)
        .flowOn(ioDispatcher)

    fun testGen() = viewModelScope.launch(ioDispatcher) {
        repeat(100){
            val supplier = Supplier(
                seqId = 0,
                genId = ULID.randomULID()
            )
            repository.testGen100(supplier)
        }
    }
}
