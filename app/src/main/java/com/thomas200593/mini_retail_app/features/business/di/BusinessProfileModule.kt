package com.thomas200593.mini_retail_app.features.business.di

import com.thomas200593.mini_retail_app.features.business.dao.BusinessProfileDao
import com.thomas200593.mini_retail_app.features.business.dao.BusinessProfileDaoImpl
import com.thomas200593.mini_retail_app.features.business.repository.BusinessProfileRepository
import com.thomas200593.mini_retail_app.features.business.repository.BusinessProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BusinessProfileModule {
    @Binds
    internal abstract fun bindsBusinessProfileRepository(
        impl: BusinessProfileRepositoryImpl
    ): BusinessProfileRepository

    @Binds
    internal abstract fun bindsBusinessProfileDao(
        impl: BusinessProfileDaoImpl
    ): BusinessProfileDao
}