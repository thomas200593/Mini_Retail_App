package com.thomas200593.mini_retail_app.core.data.local.datastore.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.thomas200593.mini_retail_app.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModDataStorePref {
    @Provides
    @Singleton
    fun providesDataStorePreferences(@ApplicationContext context: Context) =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(BuildConfig.APP_LOCAL_DATASTORE_FILENAME)
            }
        )
}