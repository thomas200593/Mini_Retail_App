package com.thomas200593.mini_retail_app.features.app_conf._gen_timezone.di

import com.thomas200593.mini_retail_app.features.app_conf._gen_timezone.repository.RepoConfGenTimezone
import com.thomas200593.mini_retail_app.features.app_conf._gen_timezone.repository.RepoImplConfGenTimezone
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModConfGenTimezone{
    @Binds
    internal abstract fun bindsRepository(impl: RepoImplConfGenTimezone): RepoConfGenTimezone
}