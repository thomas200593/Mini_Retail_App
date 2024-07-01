package com.thomas200593.mini_retail_app.work.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface CustomWorkerFactory {
    fun create(
        appContext: Context,
        params: WorkerParameters
    ): ListenableWorker
}