package com.thomas200593.mini_retail_app.features.business.biz_profile.di

import com.thomas200593.mini_retail_app.features.business.biz_profile.dao.DaoBizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.dao.DaoBizProfileImpl
import com.thomas200593.mini_retail_app.features.business.biz_profile.repository.RepoBizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.repository.RepoBizProfileImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModBizProfile {
    @Binds
    internal abstract fun bindsRepository(impl: RepoBizProfileImpl): RepoBizProfile

    @Binds
    internal abstract fun bindsDao(impl: DaoBizProfileImpl): DaoBizProfile
}