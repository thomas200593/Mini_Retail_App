package com.thomas200593.mini_retail_app.features.app_conf.conf_gen.di

import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.repository.RepoConfGen
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen.repository.RepoImplConfGen
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