package com.thomas200593.mini_retail_app.work.workers.session_monitor.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.auth.domain.UCValidateAuthSession
import com.thomas200593.mini_retail_app.features.auth.repository.RepoAuth
import com.thomas200593.mini_retail_app.work.factory.FactoryCustomWorker
import com.thomas200593.mini_retail_app.work.workers.session_monitor.worker.WorkerSessionMonitor
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class FactoryWorkerSessionMonitor @Inject constructor(
    private val repoAuth: RepoAuth,
    private val ucValidateAuthSession: UCValidateAuthSession,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): FactoryCustomWorker{
    override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
        return WorkerSessionMonitor(
            context = appContext,
            workerParameters = params,
            repoAuth = repoAuth,
            ucValidateAuthSession = ucValidateAuthSession,
            ioDispatcher = ioDispatcher
        )
    }
}