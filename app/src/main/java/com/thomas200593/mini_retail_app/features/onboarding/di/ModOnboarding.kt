package com.thomas200593.mini_retail_app.features.onboarding.di

import com.thomas200593.mini_retail_app.features.onboarding.repository.RepoImplOnboarding
import com.thomas200593.mini_retail_app.features.onboarding.repository.RepoOnboarding
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModOnboarding{
    @Binds
    internal abstract fun bindsRepository(impl: RepoImplOnboarding):RepoOnboarding
}