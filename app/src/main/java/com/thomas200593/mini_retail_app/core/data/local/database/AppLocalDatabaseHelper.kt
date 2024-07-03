package com.thomas200593.mini_retail_app.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.TypeConverterAuditTrail
import com.thomas200593.mini_retail_app.features.business.dao.BusinessProfileDao
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.BusinessProfile
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.TypeConverterAddress
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.TypeConverterBizIdentity
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.TypeConverterContact
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.TypeConverterLinks
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        BusinessProfile::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        TypeConverterAuditTrail::class,
        TypeConverterBizIdentity::class,
        TypeConverterAddress::class,
        TypeConverterContact::class,
        TypeConverterLinks::class
    ]
)
abstract class AppLocalDatabaseHelper: RoomDatabase(){
    abstract fun getBusinessProfileDao(): BusinessProfileDao
}