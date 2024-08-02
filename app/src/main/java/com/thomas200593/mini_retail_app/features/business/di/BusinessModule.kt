package com.thomas200593.mini_retail_app.features.business.di

import com.thomas200593.mini_retail_app.features.business.business.repository.BusinessRepository
import com.thomas200593.mini_retail_app.features.business.business.repository.BusinessRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BusinessModule {
    @Binds
    internal abstract fun bindsRepository(
        impl: BusinessRepositoryImpl
    ): BusinessRepository
}