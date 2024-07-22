package com.thomas200593.mini_retail_app.work.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface FactoryCustomWorker {
    fun create(
        appContext: Context,
        params: WorkerParameters
    ): ListenableWorker
}