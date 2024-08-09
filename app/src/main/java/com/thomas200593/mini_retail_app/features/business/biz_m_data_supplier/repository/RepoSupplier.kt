package com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.dao.DaoSupplier
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.entity.Supplier
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.entity.dto.SortSupplier
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.entity.dto.SortSupplier.GEN_ID_ASC
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.entity.dto.SortSupplier.GEN_ID_DESC
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.entity.dto.SortSupplier.LEGAL_NAME_ASC
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.entity.dto.SortSupplier.LEGAL_NAME_DESC
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RepoSupplier{
    fun getAllSuppliers(sortBy: SortSupplier): Flow<PagingData<Supplier>>
    fun searchSuppliers(searchQuery: String, sortBy: SortSupplier): Flow<PagingData<Supplier>>
    suspend fun testGen(supplier: Supplier)
}

class RepoSupplierImpl @Inject constructor(
    private val dao: DaoSupplier,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoSupplier {
    override fun getAllSuppliers(sortBy: SortSupplier): Flow<PagingData<Supplier>> {
        val pagingSourceFactory = when(sortBy){
            GEN_ID_ASC -> {{dao.getAllSortGenIdAsc()}}
            GEN_ID_DESC -> {{dao.getAllSortGenIdDesc()}}
            LEGAL_NAME_ASC -> {{dao.getAllSortLegalNameAsc()}}
            LEGAL_NAME_DESC -> {{dao.getAllSortLegalNameDesc()}}
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
            GEN_ID_ASC -> {{dao.searchSortGenIdAsc(searchQuery)}}
            GEN_ID_DESC -> {{dao.searchSortGenIdDesc(searchQuery)}}
            LEGAL_NAME_ASC -> {{dao.searchSortLegalNameAsc(searchQuery)}}
            LEGAL_NAME_DESC -> {{dao.searchSortLegalNameDesc(searchQuery)}}
        }
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = pagingSourceFactory
        ).flow.flowOn(ioDispatcher)
    }

    override suspend fun testGen(supplier: Supplier) = dao.testGen(supplier = supplier)
}