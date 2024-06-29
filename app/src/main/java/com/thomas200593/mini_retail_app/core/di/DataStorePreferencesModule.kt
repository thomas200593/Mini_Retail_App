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
import timber.log.Timber
import javax.inject.Singleton

private val TAG = DataStorePreferencesModule::class.simpleName

@Module
@InstallIn(SingletonComponent::class)
object DataStorePreferencesModule {
    @Provides
    @Singleton
    fun providesDataStorePreferences(@ApplicationContext context: Context) =
        create(
            produceFile = {
                Timber.d("Called : fun $TAG.provideDataStorePreferences()")
                context.preferencesDataStoreFile(APP_LOCAL_DATASTORE_FILENAME)
            }
        )
}