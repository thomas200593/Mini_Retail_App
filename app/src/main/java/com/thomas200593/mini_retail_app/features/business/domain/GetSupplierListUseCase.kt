package com.thomas200593.mini_retail_app.features.business.domain

import androidx.paging.PagingData
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import com.thomas200593.mini_retail_app.features.business.entity.supplier.dto.SortSupplier
import com.thomas200593.mini_retail_app.features.business.repository.SupplierRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetSupplierListUseCase @Inject constructor(
    private val repository: SupplierRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(
        searchQuery: String?,
        sortBy: SortSupplier
    ): Flow<PagingData<Supplier>> =
        if(searchQuery.isNullOrEmpty() || searchQuery.isBlank()){
            repository.getAllSuppliers(sortBy = sortBy).flowOn(ioDispatcher)
        }else{
            repository.searchSuppliers(searchQuery = searchQuery, sortBy = sortBy).flowOn(ioDispatcher)
        }
}