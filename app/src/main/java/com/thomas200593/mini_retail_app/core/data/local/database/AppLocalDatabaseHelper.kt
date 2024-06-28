package com.thomas200593.mini_retail_app.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thomas200593.mini_retail_app.core.data.local.database.entity.TypeConverterAuditTrail
import com.thomas200593.mini_retail_app.features.business.dao.BusinessProfileDao
import com.thomas200593.mini_retail_app.features.business.entity.BusinessProfile
import com.thomas200593.mini_retail_app.features.business.entity.dto.TypeConverterBizIdentity
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
        TypeConverterBizIdentity::class
    ]
)
abstract class AppLocalDatabaseHelper: RoomDatabase(){
    abstract fun getBusinessProfileDao(): BusinessProfileDao
}