package com.thomas200593.mini_retail_app.features.business.biz_profile.di

import com.thomas200593.mini_retail_app.features.business.biz_profile.util.BusinessExtFnImpl
import com.thomas200593.mini_retail_app.features.business.biz_profile.util.ExtFnBusiness
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BusinessExtFnModule {
    @Binds
    abstract fun bindsImplementation(impl: BusinessExtFnImpl): ExtFnBusiness
}