package com.thomas200593.mini_retail_app.core.notification

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface Notification {

}

@Singleton
class SystemNotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
): Notification{
    fun postWorkerResultSessionExpiredNotifications() = with(context){

    }
}