package com.thomas200593.mini_retail_app.features.app_conf._gen_language.di

import com.thomas200593.mini_retail_app.features.app_conf._gen_language.repository.RepoConfGenLanguage
import com.thomas200593.mini_retail_app.features.app_conf._gen_language.repository.RepoImplConfGenLanguage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModConfGenLanguage{
    @Binds
    internal abstract fun bindsRepository(impl: RepoImplConfGenLanguage): RepoConfGenLanguage
}