package com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.domain

import androidx.paging.PagingData
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.entity.Supplier
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.entity.dto.SortSupplier
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