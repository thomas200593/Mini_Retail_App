package com.thomas200593.mini_retail_app.work.workers.session_monitor.manager

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.thomas200593.mini_retail_app.work.workers.session_monitor.worker.SessionMonitorWorker
import timber.log.Timber

private val TAG = SessionMonitorWorkManager::class.simpleName

object SessionMonitorWorkManager{

    private const val WORK_NAME: String = "SessionMonitorWorkManager"

    fun initialize(context: Context){
        Timber.d("Called : fun $TAG.initialize()")
        WorkManager.getInstance(context).apply {
            enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                SessionMonitorWorker.startUpWork()
            )
        }
    }

    fun terminate(context: Context){
        Timber.d("Called : fun $TAG.terminate()")
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }
}