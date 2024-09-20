package com.thomas200593.mini_retail_app.core.data.local.database.di

import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.RepoIndustries
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.RepoIndustriesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModIndustries {
    @Binds
    abstract fun bindsRepository(impl: RepoIndustriesImpl) : RepoIndustries
}