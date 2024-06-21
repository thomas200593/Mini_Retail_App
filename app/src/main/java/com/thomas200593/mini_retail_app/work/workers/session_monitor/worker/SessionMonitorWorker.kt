package com.thomas200593.mini_retail_app.work.workers.session_monitor.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.util.concurrent.TimeUnit

private val TAG = SessionMonitorWorker::class.simpleName
@HiltWorker
class SessionMonitorWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val authRepository: AuthRepository
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        Timber.d("Called : $TAG.doWork()")
        if(! authRepository.validateAuthSessionToken(authRepository.authSessionToken.first())){
            Timber.d("$TAG.doWork() Result : SessionInvalid, Invalidate Session")
            authRepository.clearAuthSessionToken()
            return Result.success()
        }else{
            Timber.d("$TAG.doWork() Result : SessionValid")
            return Result.success()
        }
    }

    companion object {
        private val WorkerConstraint
            get() = Constraints.Builder().build().also {
                Timber.d("Getting $TAG Work Constraints : $it")
            }

        fun startUpWork() = PeriodicWorkRequestBuilder<SessionMonitorWorker>(
            repeatInterval = 15,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        ).setConstraints(WorkerConstraint).build()
    }
}