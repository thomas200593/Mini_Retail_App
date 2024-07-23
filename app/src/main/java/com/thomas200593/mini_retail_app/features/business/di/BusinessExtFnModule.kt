package com.thomas200593.mini_retail_app.features.business.di

import com.thomas200593.mini_retail_app.features.business.util.ExtFnBusiness
import com.thomas200593.mini_retail_app.features.business.util.BusinessExtFnImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BusinessExtFnModule {
    @Binds
    abstract fun bindsImplementation(
        impl: BusinessExtFnImpl
    ): ExtFnBusiness
}