package com.thomas200593.mini_retail_app.features.business.biz_m_data.di

import com.thomas200593.mini_retail_app.features.business.biz_m_data.repository.RepoMasterData
import com.thomas200593.mini_retail_app.features.business.biz_m_data.repository.RepoMasterDataImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModBizMasterData {
    @Binds
    internal abstract fun bindsRepository(impl: RepoMasterDataImpl): RepoMasterData
}