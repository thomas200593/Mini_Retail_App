package com.thomas200593.mini_retail_app.features.business.biz_profile.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.thomas200593.mini_retail_app.core.data.local.database.HlpLocalDatabase
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BusinessProfile
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
FROM business_profile bp LIMIT 1
    """)
    fun getBusinessProfile(): Flow<BusinessProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun testGenerate(businessProfile: BusinessProfile)

    @Upsert(BusinessProfile::class)
    fun setInitialBizProfile(businessProfile: BusinessProfile): Long
}

class DaoBizProfileImpl @Inject constructor(
    private val dbHelper: HlpLocalDatabase
): DaoBizProfile {
    override fun getBusinessProfile(): Flow<BusinessProfile?> =
        dbHelper.getBusinessProfileDao().getBusinessProfile()

    override suspend fun testGenerate(businessProfile: BusinessProfile) =
        dbHelper.getBusinessProfileDao().testGenerate(businessProfile)

    override fun setInitialBizProfile(businessProfile: BusinessProfile): Long =
        dbHelper.getBusinessProfileDao().setInitialBizProfile(businessProfile)
}