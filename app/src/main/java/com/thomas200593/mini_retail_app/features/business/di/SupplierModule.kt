package com.thomas200593.mini_retail_app.features.business.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.thomas200593.mini_retail_app.core.data.local.database.AppLocalDatabaseHelper
import com.thomas200593.mini_retail_app.features.business.dao.SupplierDao
import com.thomas200593.mini_retail_app.features.business.dao.SupplierDaoImpl
import com.thomas200593.mini_retail_app.features.business.entity.supplier.Supplier
import com.thomas200593.mini_retail_app.features.business.repository.SupplierRepository
import com.thomas200593.mini_retail_app.features.business.repository.SupplierRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SupplierModule {
    @Binds
    abstract fun bindsSupplierRepository(
        impl: SupplierRepositoryImpl
    ): SupplierRepository

    @Binds
    abstract fun bindsSupplierDao(
        impl: SupplierDaoImpl
    ): SupplierDao
}