package com.thomas200593.mini_retail_app.work.workers.session_monitor.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints.Builder
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result.success
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.auth.domain.UCValidateAuthSession
import com.thomas200593.mini_retail_app.features.auth.repository.RepoAuth
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.util.concurrent.TimeUnit.MINUTES

@HiltWorker
class WorkerSessionMonitor @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repoAuth: RepoAuth,
    private val ucValidateAuthSession: UCValidateAuthSession,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        if(ucValidateAuthSession.invoke(repoAuth.authSessionToken.flowOn(ioDispatcher).first())){
            Timber.d("fun doWork() returned : SessionValid")
            return success()
        }else{
            Timber.d("fun doWork() returned : SessionInvalid")
            return success()
        }
    }

    companion object {
        private val WorkerConstraint
            get() = Builder().build()

        fun startUpWork() = PeriodicWorkRequestBuilder<WorkerSessionMonitor>(
            repeatInterval = 15, repeatIntervalTimeUnit = MINUTES
        ).setConstraints(WorkerConstraint).build()
    }
}