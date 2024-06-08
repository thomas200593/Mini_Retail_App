package com.thomas200593.mini_retail_app.core.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory.create
import androidx.datastore.preferences.preferencesDataStoreFile
import com.thomas200593.mini_retail_app.BuildConfig.APP_LOCAL_DATASTORE_FILENAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStorePreferencesModule {
    @Provides
    @Singleton
    fun provideDataStorePreferences(@ApplicationContext context: Context) =
        create(
            produceFile = {
                context.preferencesDataStoreFile(APP_LOCAL_DATASTORE_FILENAME)
            }
        )
}