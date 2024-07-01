package com.thomas200593.mini_retail_app.features.onboarding.di

import com.thomas200593.mini_retail_app.features.onboarding.repository.OnboardingRepository
import com.thomas200593.mini_retail_app.features.onboarding.repository.OnboardingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class OnboardingModule{
    @Binds
    internal abstract fun bindOnboardingRepository(
        impl: OnboardingRepositoryImpl
    ):OnboardingRepository
}