package com.thomas200593.mini_retail_app.features.business.di

import com.thomas200593.mini_retail_app.features.business.dao.SupplierDao
import com.thomas200593.mini_retail_app.features.business.dao.SupplierDaoImpl
import com.thomas200593.mini_retail_app.features.business.repository.BusinessMasterDataSupplierRepository
import com.thomas200593.mini_retail_app.features.business.repository.BusinessMasterDataSupplierRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SupplierModule {
    @Binds
    abstract fun bindsMasterDataSupplierRepository(
        impl: BusinessMasterDataSupplierRepositoryImpl
    ): BusinessMasterDataSupplierRepository

    @Binds
    abstract fun bindsSupplierDao(
        impl: SupplierDaoImpl
    ): SupplierDao
}