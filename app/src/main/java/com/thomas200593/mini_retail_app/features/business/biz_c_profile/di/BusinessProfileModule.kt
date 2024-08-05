package com.thomas200593.mini_retail_app.features.business.biz_c_profile.di

import com.thomas200593.mini_retail_app.features.business.biz_c_profile.dao.BusinessProfileDao
import com.thomas200593.mini_retail_app.features.business.biz_c_profile.dao.BusinessProfileDaoImpl
import com.thomas200593.mini_retail_app.features.business.biz_c_profile.repository.RepoBizProfile
import com.thomas200593.mini_retail_app.features.business.biz_c_profile.repository.RepoBizProfileImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BusinessProfileModule {
    @Binds
    internal abstract fun bindsRepository(
        impl: RepoBizProfileImpl
    ): RepoBizProfile

    @Binds
    internal abstract fun bindsDao(
        impl: BusinessProfileDaoImpl
    ): BusinessProfileDao
}