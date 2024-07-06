package com.thomas200593.mini_retail_app.features.business.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.business.dao.SupplierDao
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import com.thomas200593.mini_retail_app.features.business.entity.supplier.dto.SupplierDataOrdering
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SupplierRepositoryImpl @Inject constructor(
    private val dao: SupplierDao,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): SupplierRepository {
    override fun getAllSuppliers(orderBy: SupplierDataOrdering): Flow<PagingData<Supplier>> {
        val pagingSourceFactory = when(orderBy){
            SupplierDataOrdering.GEN_ID_ASC -> {{dao.getAllSortGenIdAsc()}}
            SupplierDataOrdering.GEN_ID_DESC -> {{dao.getAllSortGenIdDesc()}}
            SupplierDataOrdering.LEGAL_NAME_ASC -> {{dao.getAllSortLegalNameAsc()}}
            SupplierDataOrdering.LEGAL_NAME_DESC -> {{dao.getAllSortLegalNameDesc()}}
        }
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = pagingSourceFactory
        ).flow.flowOn(ioDispatcher)
    }

    override fun searchSuppliers(
        query: String,
        orderBy: SupplierDataOrdering
    ): Flow<PagingData<Supplier>> {
        val pagingSourceFactory = when(orderBy){
            SupplierDataOrdering.GEN_ID_ASC -> {{dao.searchSortGenIdAsc(query)}}
            SupplierDataOrdering.GEN_ID_DESC -> {{dao.searchSortGenIdDesc(query)}}
            SupplierDataOrdering.LEGAL_NAME_ASC -> {{dao.searchSortLegalNameAsc(query)}}
            SupplierDataOrdering.LEGAL_NAME_DESC -> {{dao.searchSortLegalNameDesc(query)}}
        }
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = pagingSourceFactory
        ).flow.flowOn(ioDispatcher)
    }

    override suspend fun testGen(supplier: Supplier) =
        dao.testGen(supplier = supplier)
}