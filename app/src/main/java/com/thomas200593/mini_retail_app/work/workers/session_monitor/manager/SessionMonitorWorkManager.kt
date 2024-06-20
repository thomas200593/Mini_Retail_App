package com.thomas200593.mini_retail_app.work.workers.session_monitor.manager

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.thomas200593.mini_retail_app.work.workers.session_monitor.worker.SessionMonitorWorker

private const val TAG = "SessionMonitorWorkManager"
object SessionMonitorWorkManager{

    private const val WORK_NAME: String = TAG

    fun initialize(context: Context){
        WorkManager.getInstance(context).apply {
            enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                SessionMonitorWorker.startUpWork()
            )
        }
    }

    fun terminate(context: Context){
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }
}