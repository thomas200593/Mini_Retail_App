package com.thomas200593.mini_retail_app.core.data.local.database.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.thomas200593.mini_retail_app.BuildConfig.APP_LOCAL_DATABASE_FILENAME
import com.thomas200593.mini_retail_app.core.data.local.database.HlpLocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModRoomDatabase {
    @Provides
    @Singleton
    fun providesRoomDatabase(@ApplicationContext context: Context) =
        databaseBuilder(
            context = context,
            klass = HlpLocalDatabase::class.java,
            name = APP_LOCAL_DATABASE_FILENAME
        ).build()
}