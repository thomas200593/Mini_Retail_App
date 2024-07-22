package com.thomas200593.mini_retail_app.features.business.di

import com.thomas200593.mini_retail_app.features.business.dao.SupplierDao
import com.thomas200593.mini_retail_app.features.business.dao.SupplierDaoImpl
import com.thomas200593.mini_retail_app.features.business.repository.SupplierRepository
import com.thomas200593.mini_retail_app.features.business.repository.SupplierRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SupplierModule {
    @Binds
    abstract fun bindsRepository(
        impl: SupplierRepositoryImpl
    ): SupplierRepository

    @Binds
    abstract fun bindsDao(
        impl: SupplierDaoImpl
    ): SupplierDao
}