package com.thomas200593.mini_retail_app.features.auth.di

import com.thomas200593.mini_retail_app.features.auth.repository.RepoAuth
import com.thomas200593.mini_retail_app.features.auth.repository.RepoAuthImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModAuth {
    @Binds
    internal abstract fun bindsRepository(impl: RepoAuthImpl):RepoAuth
}