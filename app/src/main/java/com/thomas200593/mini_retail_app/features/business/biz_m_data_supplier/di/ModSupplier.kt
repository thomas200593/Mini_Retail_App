package com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.di

import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.dao.DaoSupplier
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.dao.DaoSupplierImpl
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.repository.RepoSupplier
import com.thomas200593.mini_retail_app.features.business.biz_m_data_supplier.repository.RepoSupplierImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModSupplier {
    @Binds
    abstract fun bindsRepository(impl: RepoSupplierImpl): RepoSupplier

    @Binds
    abstract fun bindsDao(impl: DaoSupplierImpl): DaoSupplier
}