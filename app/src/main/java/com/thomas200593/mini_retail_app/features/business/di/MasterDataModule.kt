package com.thomas200593.mini_retail_app.features.business.di

import com.thomas200593.mini_retail_app.features.business.repository.MasterDataRepository
import com.thomas200593.mini_retail_app.features.business.repository.MasterDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MasterDataModule {
    @Binds
    internal abstract fun bindsMasterDataRepository(
        impl: MasterDataRepositoryImpl
    ): MasterDataRepository
}