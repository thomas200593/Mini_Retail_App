package com.thomas200593.mini_retail_app.core.di

import android.content.Context
import androidx.room.Room
import com.thomas200593.mini_retail_app.BuildConfig
import com.thomas200593.mini_retail_app.BuildConfig.APP_LOCAL_DATABASE_FILENAME
import com.thomas200593.mini_retail_app.core.data.local.database.AppLocalDatabaseHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {
    @Provides
    @Singleton
    fun providesRoomDatabase(@ApplicationContext context: Context) = Room
        .databaseBuilder(
            context = context,
            AppLocalDatabaseHelper::class.java,
            APP_LOCAL_DATABASE_FILENAME
        )
        .build()
}