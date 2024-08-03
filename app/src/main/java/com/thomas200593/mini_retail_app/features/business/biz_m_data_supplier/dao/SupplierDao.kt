package com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thomas200593.mini_retail_app.core.data.local.database.HlpLocalDatabase
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.entity.Supplier
import javax.inject.Inject

@Dao
interface SupplierDao{
    @Query("""
SELECT * FROM supplier
WHERE 1 = 1
ORDER BY gen_id ASC
    """)
    fun getAllSortGenIdAsc(): PagingSource<Int, Supplier>

    @Query("""
SELECT * FROM supplier 
WHERE 1 = 1
ORDER BY gen_id DESC
    """)
    fun getAllSortGenIdDesc(): PagingSource<Int, Supplier>

    @Query("""
SELECT * FROM supplier
WHERE 1 = 1
ORDER BY spr_legal_name ASC
    """)
    fun getAllSortLegalNameAsc(): PagingSource<Int, Supplier>

    @Query("""
SELECT * FROM supplier 
WHERE 1 = 1
ORDER BY spr_legal_name DESC
    """)
    fun getAllSortLegalNameDesc(): PagingSource<Int, Supplier>

    @Query("""
SELECT * FROM supplier 
WHERE 1 = 1 AND
(
    (gen_id LIKE :searchQuery) OR
    (spr_legal_name LIKE :searchQuery)
) 
ORDER BY gen_id ASC
    """)
    fun searchSortGenIdAsc(searchQuery: String): PagingSource<Int, Supplier>

    @Query("""
SELECT * FROM supplier 
WHERE 1 = 1 AND
(
    (gen_id LIKE :searchQuery) OR
    (spr_legal_name LIKE :searchQuery)
) 
ORDER BY gen_id DESC
    """)
    fun searchSortGenIdDesc(searchQuery: String): PagingSource<Int, Supplier>

    @Query("""
SELECT * FROM supplier 
WHERE 1 = 1 AND
(
    (gen_id LIKE :searchQuery) OR
    (spr_legal_name LIKE :searchQuery)
) 
ORDER BY spr_legal_name ASC
    """)
    fun searchSortLegalNameAsc(searchQuery: String): PagingSource<Int, Supplier>

    @Query("""
SELECT * FROM supplier 
WHERE 1 = 1 AND 
(
    (gen_id LIKE :searchQuery ) OR
    (spr_legal_name LIKE :searchQuery)
)
ORDER BY spr_legal_name DESC
    """)
    fun searchSortLegalNameDesc(searchQuery: String): PagingSource<Int, Supplier>

    @Insert(entity = Supplier::class, onConflict = OnConflictStrategy.IGNORE)
    fun testGen(supplier: Supplier)
}

class SupplierDaoImpl @Inject constructor(
    private val dbHelper: HlpLocalDatabase
): SupplierDao {
    override fun getAllSortGenIdAsc(): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().getAllSortGenIdAsc()

    override fun getAllSortGenIdDesc(): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().getAllSortGenIdDesc()

    override fun getAllSortLegalNameAsc(): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().getAllSortLegalNameAsc()

    override fun getAllSortLegalNameDesc(): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().getAllSortLegalNameDesc()

    override fun searchSortGenIdAsc(searchQuery: String): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().searchSortGenIdAsc("%$searchQuery%")

    override fun searchSortGenIdDesc(searchQuery: String): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().searchSortGenIdDesc("%$searchQuery%")

    override fun searchSortLegalNameAsc(searchQuery: String): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().searchSortLegalNameAsc("%$searchQuery%")

    override fun searchSortLegalNameDesc(searchQuery: String): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().searchSortLegalNameDesc("%$searchQuery%")

    override fun testGen(supplier: Supplier) =
        dbHelper.getSupplierDao().testGen(supplier = supplier)
}