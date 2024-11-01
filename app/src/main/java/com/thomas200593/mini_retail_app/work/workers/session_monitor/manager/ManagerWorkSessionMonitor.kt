package com.thomas200593.mini_retail_app.work.workers.session_monitor.manager

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy.UPDATE
import androidx.work.WorkManager
import com.thomas200593.mini_retail_app.work.workers.session_monitor.worker.WorkerSessionMonitor.Companion.startUpWork

object ManagerWorkSessionMonitor{
    private const val WORK_NAME: String = "ManagerWorkSessionMonitor"
    fun initialize(context: Context){
        WorkManager.getInstance(context).apply {
            enqueueUniquePeriodicWork(
                WORK_NAME,
                UPDATE,
                startUpWork()
            )
        }
    }
    fun terminate(context: Context){ WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME) }
}