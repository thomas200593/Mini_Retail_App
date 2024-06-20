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

private const val TAG = "SessionMonitorWorker"
@HiltWorker
class SessionMonitorWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val authRepository: AuthRepository
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        Timber.d("Called %s.doWork", TAG)
        if(! authRepository.validateAuthSessionToken(authRepository.authSessionToken.first())){
            Timber.d("%s.doWork Result : SessionInvalid, clear residue")
            authRepository.clearAuthSessionToken()
            return Result.success()
        }else{
            Timber.d("%s.doWork Result : SessionValid, do nothing")
            return Result.success()
        }
    }

    companion object {
        private val WorkerConstraint
            get() = Constraints.Builder().build()

        fun startUpWork() = PeriodicWorkRequestBuilder<SessionMonitorWorker>(
            repeatInterval = 15,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        ).setConstraints(WorkerConstraint).build()
    }
}