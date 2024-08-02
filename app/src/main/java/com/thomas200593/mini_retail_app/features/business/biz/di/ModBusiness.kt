package com.thomas200593.mini_retail_app.features.business.biz.di

import com.thomas200593.mini_retail_app.features.business.biz.repository.RepoBusiness
import com.thomas200593.mini_retail_app.features.business.biz.repository.RepoBusinessImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModBusiness {
    @Binds
    internal abstract fun bindsRepository(impl: RepoBusinessImpl): RepoBusiness
}