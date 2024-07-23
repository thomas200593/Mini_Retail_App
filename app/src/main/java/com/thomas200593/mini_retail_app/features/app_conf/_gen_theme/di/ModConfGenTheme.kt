package com.thomas200593.mini_retail_app.features.app_conf._gen_theme.di

import com.thomas200593.mini_retail_app.features.app_conf._gen_theme.repository.RepoConfGenTheme
import com.thomas200593.mini_retail_app.features.app_conf._gen_theme.repository.RepoImplConfGenTheme
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModConfGenTheme{
    @Binds
    internal abstract fun bindsRepository(impl: RepoImplConfGenTheme): RepoConfGenTheme
}