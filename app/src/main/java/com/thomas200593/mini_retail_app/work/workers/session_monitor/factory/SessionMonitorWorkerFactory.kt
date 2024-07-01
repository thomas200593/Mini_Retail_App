package com.thomas200593.mini_retail_app.work.workers.session_monitor.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.auth.domain.ValidateAuthSessionUseCase
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import com.thomas200593.mini_retail_app.work.factory.CustomWorkerFactory
import com.thomas200593.mini_retail_app.work.workers.session_monitor.worker.SessionMonitorWorker
import kotlinx.coroutines.CoroutineDispatcher
import timber.log.Timber
import javax.inject.Inject

private val TAG = SessionMonitorWorkerFactory::class.simpleName

class SessionMonitorWorkerFactory @Inject constructor(
    private val authRepository: AuthRepository,
    private val validateAuthSessionUseCase: ValidateAuthSessionUseCase,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): CustomWorkerFactory{
    override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
        Timber.d("Called : fun $TAG.create()")
        return SessionMonitorWorker(
            context = appContext,
            workerParameters = params,
            authRepository = authRepository,
            validateAuthSessionUseCase = validateAuthSessionUseCase,
            ioDispatcher = ioDispatcher
        )
    }
}