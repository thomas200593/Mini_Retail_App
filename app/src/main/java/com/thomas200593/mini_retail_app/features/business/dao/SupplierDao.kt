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
    @Query("""
SELECT * FROM 
supplier
WHERE 1 = 1
ORDER BY
    CASE WHEN :directionAsc = 1 THEN :orderBy END ASC,
    CASE WHEN :directionAsc = 0 THEN :orderBy END DESC
    """)
    fun getAllSuppliers(orderBy: String, directionAsc: Int): PagingSource<Int, Supplier>

    @Query("""
SELECT * FROM
supplier
WHERE 1 = 1
AND (
    (gen_id LIKE :searchQuery) OR
    (spr_legal_name LIKE :searchQuery)
)
ORDER BY 
    CASE WHEN :directionAsc = 1 THEN :orderBy END ASC,
    CASE WHEN :directionAsc = 0 THEN :orderBy END DESC
    """)
    fun searchSuppliers(searchQuery: String, orderBy: String, directionAsc: Int): PagingSource<Int, Supplier>

    @Insert(entity = Supplier::class, onConflict = OnConflictStrategy.IGNORE)
    fun testGen(supplier: Supplier)
}

class SupplierDaoImpl @Inject constructor(
    private val dbHelper: AppLocalDatabaseHelper
): SupplierDao{
    override fun getAllSuppliers(orderBy: String, directionAsc: Int): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().getAllSuppliers(
            orderBy = orderBy,
            directionAsc = directionAsc
        )

    override fun searchSuppliers(
        searchQuery: String,
        orderBy: String,
        directionAsc: Int
    ): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().searchSuppliers(
            searchQuery = searchQuery,
            orderBy = orderBy,
            directionAsc = directionAsc
        )

    override fun testGen(supplier: Supplier) =
        dbHelper.getSupplierDao().testGen(supplier = supplier)
}