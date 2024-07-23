package com.thomas200593.mini_retail_app.features.app_conf._gen_dynamic_color.di

import com.thomas200593.mini_retail_app.features.app_conf._gen_dynamic_color.repository.RepoConfGenDynamicColor
import com.thomas200593.mini_retail_app.features.app_conf._gen_dynamic_color.repository.RepoImplConfGenDynamicColor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModConfGenDynamicColor {
    @Binds
    internal abstract fun bindsRepository(impl: RepoImplConfGenDynamicColor): RepoConfGenDynamicColor
}