package com.thomas200593.mini_retail_app.features.business.biz_profile.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.thomas200593.mini_retail_app.core.data.local.database.HlpLocalDatabase
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Dao
interface DaoBizProfile{
    @Query("""
SELECT 
    bp.seq_id, 
    bp.gen_id, 
    bp.biz_identity, 
    bp.addresses,
    bp.contacts,
    bp.links,
    bp.audit_trail 
FROM biz_profile bp LIMIT 1
    """)
    fun getBizProfile(): Flow<BizProfile>

    @Upsert(BizProfile::class)
    suspend fun setInitBizProfile(bizProfile: BizProfile): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun testGenerate(bizProfile: BizProfile)
}

class DaoBizProfileImpl @Inject constructor(
    private val dbHelper: HlpLocalDatabase
): DaoBizProfile {
    override fun getBizProfile(): Flow<BizProfile> =
        dbHelper.getBizProfileDao().getBizProfile()

    override suspend fun setInitBizProfile(bizProfile: BizProfile): Long =
        dbHelper.getBizProfileDao().setInitBizProfile(bizProfile)

    override suspend fun testGenerate(bizProfile: BizProfile) =
        dbHelper.getBizProfileDao().testGenerate(bizProfile)
}