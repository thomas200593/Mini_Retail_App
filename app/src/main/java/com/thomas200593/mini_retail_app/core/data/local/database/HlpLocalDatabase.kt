package com.thomas200593.mini_retail_app.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.TypeConvAddress
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.TypeConvAuditTrail
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.TypeConvContact
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.TypeConvLinks
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.dao.DaoSupplier
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.entity.Supplier
import com.thomas200593.mini_retail_app.features.business.biz_profile.dao.DaoBizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.BizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.TypeConvBizIdentity
import com.thomas200593.mini_retail_app.features.business.biz_profile.entity.TypeConvBizName
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        BizProfile::class,
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
    abstract fun getBizProfileDao(): DaoBizProfile
    abstract fun getSupplierDao(): DaoSupplier
}