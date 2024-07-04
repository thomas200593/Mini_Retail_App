package com.thomas200593.mini_retail_app.features.business.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thomas200593.mini_retail_app.core.data.local.database.AppLocalDatabaseHelper
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import javax.inject.Inject

@Dao
interface SupplierDao{
    @Query("""SELECT * FROM supplier""")
    fun pagingSource(): PagingSource<Int, Supplier>

    @Insert(entity = Supplier::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(supplier: Supplier)
}

class SupplierDaoImpl @Inject constructor(
    private val dbHelper: AppLocalDatabaseHelper
): SupplierDao{
    override fun pagingSource(): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().pagingSource()

    override suspend fun create(supplier: Supplier) = dbHelper.getSupplierDao().create(supplier)
}