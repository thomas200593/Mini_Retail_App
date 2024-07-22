package com.thomas200593.mini_retail_app.core.data.local.database.di

import android.content.Context
import androidx.room.Room
import com.thomas200593.mini_retail_app.BuildConfig
import com.thomas200593.mini_retail_app.core.data.local.database.HelperAppLocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleRoomDatabase {
    @Provides
    @Singleton
    fun providesRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context = context,
            klass = HelperAppLocalDatabase::class.java,
            name = BuildConfig.APP_LOCAL_DATABASE_FILENAME
        ).build()
}