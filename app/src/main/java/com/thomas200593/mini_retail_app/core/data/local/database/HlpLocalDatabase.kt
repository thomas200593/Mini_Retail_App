package com.thomas200593.mini_retail_app.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.TypeConvAuditTrail
import com.thomas200593.mini_retail_app.features.business.dao.BusinessProfileDao
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.BusinessProfile
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.TypeConvAddress
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.TypeConvBizIdentity
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.TypeConvContact
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.TypeConvLinks
import com.thomas200593.mini_retail_app.features.business.dao.SupplierDao
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.TypeConvBizName
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        BusinessProfile::class,
        Supplier::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        TypeConvAuditTrail::class, TypeConvBizIdentity::class, TypeConvAddress::class,
        TypeConvContact::class, TypeConvLinks::class, TypeConvBizName::class
    ]
)
abstract class HlpLocalDatabase: RoomDatabase(){
    abstract fun getBusinessProfileDao(): BusinessProfileDao
    abstract fun getSupplierDao(): SupplierDao
}