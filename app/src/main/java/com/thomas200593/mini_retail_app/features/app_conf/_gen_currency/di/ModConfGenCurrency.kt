package com.thomas200593.mini_retail_app.features.app_conf._gen_currency.di

import com.thomas200593.mini_retail_app.features.app_conf._gen_currency.repository.RepoConfGenCurrency
import com.thomas200593.mini_retail_app.features.app_conf._gen_currency.repository.RepoImplConfGenCurrency
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModConfGenCurrency{
    @Binds
    internal abstract fun bindsRepository(
        impl: RepoImplConfGenCurrency
    ): RepoConfGenCurrency
}