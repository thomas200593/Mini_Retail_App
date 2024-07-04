package com.thomas200593.mini_retail_app.features.business.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.thomas200593.mini_retail_app.features.business.dao.SupplierDao
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SupplierRepositoryImpl @Inject constructor(
    private val pager: Pager<Int, Supplier>,
    private val dao: SupplierDao
): SupplierRepository {
    override fun getSupplierList(): Flow<PagingData<Supplier>> {
        return pager.flow.map { pagedData ->
            pagedData.map { it }
        }
    }

    override suspend fun testGen100(supplier: Supplier) = dao.create(supplier)
}