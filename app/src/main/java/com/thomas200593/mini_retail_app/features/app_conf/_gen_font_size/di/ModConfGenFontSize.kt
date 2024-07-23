package com.thomas200593.mini_retail_app.features.app_conf._gen_font_size.di

import com.thomas200593.mini_retail_app.features.app_conf._gen_font_size.repository.RepoConfGenFontSize
import com.thomas200593.mini_retail_app.features.app_conf._gen_font_size.repository.RepoImplConfGenFontSize
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModConfGenFontSize {
    @Binds
    internal abstract fun bindsRepository(impl: RepoImplConfGenFontSize): RepoConfGenFontSize
}