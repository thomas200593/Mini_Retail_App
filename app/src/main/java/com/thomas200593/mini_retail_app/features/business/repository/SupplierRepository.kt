package com.thomas200593.mini_retail_app.features.business.repository

import androidx.paging.PagingData
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import kotlinx.coroutines.flow.Flow

interface SupplierRepository{
    fun getAllSuppliers(orderBy: String, directionAsc: Int): Flow<PagingData<Supplier>>
    fun searchSuppliers(searchQuery: String?, orderBy: String, directionAsc: Int): Flow<PagingData<Supplier>>
    suspend fun testGen(supplier: Supplier)
}