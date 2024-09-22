package com.thomas200593.mini_retail_app.core.data.local.database.di

import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.RepoLegalType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.RepoLegalTypeImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModLegalType {
    @Binds
    abstract fun bindsRepository(impl: RepoLegalTypeImpl) : RepoLegalType
}