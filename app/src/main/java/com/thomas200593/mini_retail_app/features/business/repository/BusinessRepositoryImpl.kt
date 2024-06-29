package com.thomas200593.mini_retail_app.features.business.repository

import com.thomas200593.mini_retail_app.core.data.local.session.SessionState
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.business.navigation.BusinessDestination
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

private val TAG = BusinessRepositoryImpl::class.simpleName

internal class BusinessRepositoryImpl @Inject constructor(
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): BusinessRepository {
    override suspend fun getBusinessMenuData(
        sessionState: SessionState
    ): Set<BusinessDestination> = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.getBusinessMenuData()")
        when(sessionState){
            SessionState.Loading -> {
                emptySet()
            }
            is SessionState.Invalid -> {
                BusinessDestination.entries.filter { !it.usesAuth }.toSet()
            }
            is SessionState.Valid -> {
                BusinessDestination.entries.toSet()
            }
        }
    }
}