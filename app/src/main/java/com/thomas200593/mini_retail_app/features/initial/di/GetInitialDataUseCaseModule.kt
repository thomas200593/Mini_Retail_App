package com.thomas200593.mini_retail_app.features.initial.di

import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.app_config.repository.AppConfigRepository
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import com.thomas200593.mini_retail_app.features.initial.domain.GetInitialDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GetInitialDataUseCaseModule {
    @Provides
    @Singleton
    fun providesGetInitialDataUseCase(
        authRepository: AuthRepository,
        appConfigRepository: AppConfigRepository,
        @Dispatcher(Dispatchers.Dispatchers.IO) ioDispatcher: CoroutineDispatcher
    ) = GetInitialDataUseCase(
        authRepository = authRepository,
        appConfigRepository = appConfigRepository,
        ioDispatcher = ioDispatcher
    )
}