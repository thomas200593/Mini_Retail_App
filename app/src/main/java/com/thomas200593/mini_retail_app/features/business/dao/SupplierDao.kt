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
SELECT * FROM SUPPLIER ORDER BY gen_id ASC
    """)
    fun getAllSuppliersByGenIdAsc(): PagingSource<Int, Supplier>

    @Query("""
SELECT * FROM SUPPLIER ORDER BY gen_id DESC
    """)
    fun getAllSuppliersByGenIdDesc(): PagingSource<Int, Supplier>

    @Query("""
SELECT * FROM SUPPLIER ORDER BY spr_legal_name ASC
    """)
    fun getAllSupplierByLegalNameAsc(): PagingSource<Int, Supplier>

    @Query("""
SELECT * FROM SUPPLIER ORDER BY spr_legal_name DESC
    """)
    fun getAllSupplierByLegalNameDesc(): PagingSource<Int, Supplier>

    @Query("""
SELECT * FROM SUPPLIER WHERE spr_legal_name LIKE :query ORDER BY spr_legal_name ASC
    """)
    fun searchSupplierByLegalNameAsc(query: String): PagingSource<Int, Supplier>

    @Query("""
SELECT * FROM supplier WHERE spr_legal_name LIKE :query ORDER BY spr_legal_name DESC
    """)
    fun searchSupplierByLegalNameDesc(query: String): PagingSource<Int, Supplier>

    @Query("""
SELECT * FROM supplier WHERE spr_legal_name LIKE :query ORDER BY gen_id ASC
    """)
    fun searchSupplierByGenIdAsc(query: String): PagingSource<Int, Supplier>

    @Query("""
SELECT * FROM supplier WHERE spr_legal_name LIKE :query ORDER BY gen_id DESC
    """)
    fun searchSupplierByGenIdDesc(query: String): PagingSource<Int, Supplier>

    @Insert(entity = Supplier::class, onConflict = OnConflictStrategy.IGNORE)
    fun testGen(supplier: Supplier)
}

class SupplierDaoImpl @Inject constructor(
    private val dbHelper: AppLocalDatabaseHelper
): SupplierDao{
    override fun getAllSuppliersByGenIdAsc(): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().getAllSuppliersByGenIdAsc()

    override fun getAllSuppliersByGenIdDesc(): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().getAllSuppliersByGenIdDesc()

    override fun getAllSupplierByLegalNameAsc(): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().getAllSupplierByLegalNameAsc()

    override fun getAllSupplierByLegalNameDesc(): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().getAllSupplierByLegalNameDesc()

    override fun searchSupplierByGenIdAsc(query: String): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().searchSupplierByGenIdAsc("%$query%")

    override fun searchSupplierByGenIdDesc(query: String): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().searchSupplierByGenIdDesc("%$query%")

    override fun searchSupplierByLegalNameAsc(query: String): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().searchSupplierByLegalNameAsc("%$query%")

    override fun searchSupplierByLegalNameDesc(query: String): PagingSource<Int, Supplier> =
        dbHelper.getSupplierDao().searchSupplierByLegalNameDesc("%$query%")

    override fun testGen(supplier: Supplier) =
        dbHelper.getSupplierDao().testGen(supplier = supplier)
}