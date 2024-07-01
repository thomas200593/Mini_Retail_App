package com.thomas200593.mini_retail_app.work.workers.session_monitor.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result.success
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.auth.domain.ValidateAuthSessionUseCase
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.util.concurrent.TimeUnit.MINUTES

private val TAG = SessionMonitorWorker::class.simpleName
@HiltWorker
class SessionMonitorWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val authRepository: AuthRepository,
    private val validateAuthSessionUseCase: ValidateAuthSessionUseCase,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        Timber.d("Called : fun $TAG.doWork()")
        if(validateAuthSessionUseCase.invoke(authRepository.authSessionToken.flowOn(ioDispatcher).first())){
            Timber.d("fun $TAG.doWork() returned : SessionValid")
            return success()
        }else{
            Timber.d("fun $TAG.doWork() returned : SessionInvalid")
            return success()
        }
    }

    companion object {
        private val WorkerConstraint
            get() = Constraints.Builder().build().also {
                Timber.d("Called : fun getter() WorkerConstraint -> $it")
            }

        fun startUpWork() = PeriodicWorkRequestBuilder<SessionMonitorWorker>(
            repeatInterval = 15,
            repeatIntervalTimeUnit = MINUTES
        ).setConstraints(WorkerConstraint).build()
            .also {
                Timber.d("Called : fun $TAG.startUpWork()")
            }
    }
}