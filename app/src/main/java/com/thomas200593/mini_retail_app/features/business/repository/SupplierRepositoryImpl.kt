package com.thomas200593.mini_retail_app.features.business.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.business.dao.SupplierDao
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SupplierRepositoryImpl @Inject constructor(
    private val dao: SupplierDao,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): SupplierRepository {
    override fun getAllSuppliers(orderBy: String, directionAsc: Int): Flow<PagingData<Supplier>> {
        val pagingSourceFactory = {
            dao.getAllSuppliers(
                orderBy = orderBy,
                directionAsc = directionAsc
            )
        }
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = pagingSourceFactory
        ).flow.flowOn(ioDispatcher)
    }

    override fun searchSuppliers(
        searchQuery: String?,
        orderBy: String,
        directionAsc: Int
    ): Flow<PagingData<Supplier>> {
        val pagingSourceFactory = {
            dao.searchSuppliers(
                searchQuery = "%$searchQuery%",
                orderBy = orderBy,
                directionAsc = directionAsc
            )
        }
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = pagingSourceFactory
        ).flow.flowOn(ioDispatcher)
    }

    override suspend fun testGen(supplier: Supplier) =
        dao.testGen(supplier = supplier)
}