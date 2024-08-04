package com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.di

import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.dao.DaoSupplier
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.dao.DaoSupplierImpl
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.repository.SupplierRepository
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.repository.SupplierRepositoryImpl
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
        impl: DaoSupplierImpl
    ): DaoSupplier
}