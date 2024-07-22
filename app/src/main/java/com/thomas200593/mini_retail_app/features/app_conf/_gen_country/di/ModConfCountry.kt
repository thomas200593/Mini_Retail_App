package com.thomas200593.mini_retail_app.features.app_conf._gen_country.di

import com.thomas200593.mini_retail_app.features.app_conf._gen_country.repository.RepoConfGenCountry
import com.thomas200593.mini_retail_app.features.app_conf._gen_country.repository.RepoImplConfGenCountry
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModConfCountry {
    @Binds
    internal abstract fun bindsRepository(impl: RepoImplConfGenCountry): RepoConfGenCountry
}