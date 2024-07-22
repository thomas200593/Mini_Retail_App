package com.thomas200593.mini_retail_app.features.app_conf._general.di

import com.thomas200593.mini_retail_app.features.app_conf._general.repository.RepoConfGen
import com.thomas200593.mini_retail_app.features.app_conf._general.repository.RepoImplConfGen
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ModConfGen {
    @Binds
    internal abstract fun bindsRepository(impl: RepoImplConfGen): RepoConfGen
}