package com.thomas200593.mini_retail_app.features.business.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.thomas200593.mini_retail_app.core.data.local.database.AppLocalDatabaseHelper
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import javax.inject.Inject

@Dao
interface SupplierDao{
    @Query("""
SELECT 
    spr.seq_id,
    spr.gen_id,
    spr.audit_trail,
    spr.spr_biz_identity
FROM supplier spr
    """)
    fun pagingSource(): PagingSource<Int, Supplier>
}

class SupplierDaoImpl @Inject constructor(
    private val dbHelper: AppLocalDatabaseHelper
): SupplierDao{
    override fun pagingSource(): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().pagingSource()
}