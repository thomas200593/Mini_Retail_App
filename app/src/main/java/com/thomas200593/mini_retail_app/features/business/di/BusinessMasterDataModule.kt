package com.thomas200593.mini_retail_app.features.business.di

import com.thomas200593.mini_retail_app.features.business.repository.BusinessMasterDataRepository
import com.thomas200593.mini_retail_app.features.business.repository.BusinessMasterDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BusinessMasterDataModule {
    @Binds
    internal abstract fun bindsBusinessMasterDataRepository(
        impl: BusinessMasterDataRepositoryImpl
    ): BusinessMasterDataRepository
}