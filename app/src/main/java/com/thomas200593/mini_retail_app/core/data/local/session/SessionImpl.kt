package com.thomas200593.mini_retail_app.core.data.local.session

import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionImpl @Inject constructor(
    authRepository: AuthRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): Session {
    override val userSession: Flow<RequestState<UserData?>> =
        authRepository
            .authSessionToken
            .flowOn(ioDispatcher)
            .catch {
                RequestState.Error(it)
            }
            .map { authToken ->
                if (authRepository.validateAuthSessionToken(authToken)) {
                    val userData = authRepository.mapAuthSessionTokenToUserData(authToken)
                    RequestState.Success(userData)
                } else {
                    RequestState.Success(null)
                }
            }
}