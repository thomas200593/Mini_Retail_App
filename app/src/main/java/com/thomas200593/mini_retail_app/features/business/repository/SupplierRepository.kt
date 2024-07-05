package com.thomas200593.mini_retail_app.features.business.repository

import androidx.paging.PagingData
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import com.thomas200593.mini_retail_app.features.business.entity.supplier.dto.SupplierDataOrdering
import kotlinx.coroutines.flow.Flow

interface SupplierRepository{
    fun getAllSuppliers(orderBy: SupplierDataOrdering): Flow<PagingData<Supplier>>
    fun searchSuppliers(query: String, orderBy: SupplierDataOrdering): Flow<PagingData<Supplier>>
    suspend fun testGen(supplier: Supplier)
}