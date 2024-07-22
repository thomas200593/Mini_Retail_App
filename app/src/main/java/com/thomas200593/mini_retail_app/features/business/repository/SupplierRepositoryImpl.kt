package com.thomas200593.mini_retail_app.features.business.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.business.dao.SupplierDao
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import com.thomas200593.mini_retail_app.features.business.entity.supplier.dto.SortSupplier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SupplierRepositoryImpl @Inject constructor(
    private val dao: SupplierDao,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): SupplierRepository {
    override fun getAllSuppliers(sortBy: SortSupplier): Flow<PagingData<Supplier>> {
        val pagingSourceFactory = when(sortBy){
            SortSupplier.GEN_ID_ASC -> {{dao.getAllSortGenIdAsc()}}
            SortSupplier.GEN_ID_DESC -> {{dao.getAllSortGenIdDesc()}}
            SortSupplier.LEGAL_NAME_ASC -> {{dao.getAllSortLegalNameAsc()}}
            SortSupplier.LEGAL_NAME_DESC -> {{dao.getAllSortLegalNameDesc()}}
        }
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = pagingSourceFactory
        ).flow.flowOn(ioDispatcher)
    }

    override fun searchSuppliers(
        searchQuery: String,
        sortBy: SortSupplier
    ): Flow<PagingData<Supplier>> {
        val pagingSourceFactory = when(sortBy){
            SortSupplier.GEN_ID_ASC -> {{dao.searchSortGenIdAsc(searchQuery)}}
            SortSupplier.GEN_ID_DESC -> {{dao.searchSortGenIdDesc(searchQuery)}}
            SortSupplier.LEGAL_NAME_ASC -> {{dao.searchSortLegalNameAsc(searchQuery)}}
            SortSupplier.LEGAL_NAME_DESC -> {{dao.searchSortLegalNameDesc(searchQuery)}}
        }
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = pagingSourceFactory
        ).flow.flowOn(ioDispatcher)
    }

    override suspend fun testGen(supplier: Supplier) =
        dao.testGen(supplier = supplier)
}