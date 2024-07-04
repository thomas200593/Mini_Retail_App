package com.thomas200593.mini_retail_app.features.business.repository

import androidx.paging.PagingData
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import kotlinx.coroutines.flow.Flow

interface SupplierRepository{
    fun getSupplierList(): Flow<PagingData<Supplier>>
    suspend fun testGen100(supplier: Supplier)
}