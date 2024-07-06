package com.thomas200593.mini_retail_app.features.business.repository

import androidx.paging.PagingData
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import com.thomas200593.mini_retail_app.features.business.entity.supplier.dto.SortSupplier
import kotlinx.coroutines.flow.Flow

interface SupplierRepository{
    fun getAllSuppliers(sortBy: SortSupplier): Flow<PagingData<Supplier>>
    fun searchSuppliers(searchQuery: String, sortBy: SortSupplier): Flow<PagingData<Supplier>>
    suspend fun testGen(supplier: Supplier)
}