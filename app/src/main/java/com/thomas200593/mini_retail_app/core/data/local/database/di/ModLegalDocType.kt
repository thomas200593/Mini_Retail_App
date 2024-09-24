package com.thomas200593.mini_retail_app.core.data.local.database.di

import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.RepoLegalDocType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.RepoLegalDocTypeImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModLegalDocType {
    @Binds
    abstract fun bindsRepository(impl: RepoLegalDocTypeImpl) : RepoLegalDocType
}