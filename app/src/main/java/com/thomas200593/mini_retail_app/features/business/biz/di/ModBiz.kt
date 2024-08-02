package com.thomas200593.mini_retail_app.features.business.biz.di

import com.thomas200593.mini_retail_app.features.business.biz.repository.RepoBiz
import com.thomas200593.mini_retail_app.features.business.biz.repository.RepoBizImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModBiz {
    @Binds
    internal abstract fun bindsRepository(impl: RepoBizImpl): RepoBiz
}