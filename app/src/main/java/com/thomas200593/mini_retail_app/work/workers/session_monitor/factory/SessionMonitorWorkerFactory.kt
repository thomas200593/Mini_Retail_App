package com.thomas200593.mini_retail_app.work.workers.session_monitor.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import com.thomas200593.mini_retail_app.work.factory.CustomWorkerFactory
import com.thomas200593.mini_retail_app.work.workers.session_monitor.worker.SessionMonitorWorker
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "SessionMonitorWorkerFactory"

class SessionMonitorWorkerFactory @Inject constructor(
    private val authRepository: AuthRepository
): CustomWorkerFactory{
    override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
        Timber.d("Called %s.create()", TAG)
        return SessionMonitorWorker(
            appContext,
            params,
            authRepository
        )
    }
}