package com.thomas200593.mini_retail_app.core.data.local.session

import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionImpl @Inject constructor(
    authRepository: AuthRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
): Session {
    override val currentUserSession: Flow<SessionState> = authRepository
        .authSessionToken.flowOn(ioDispatcher)
        .catch {
            SessionState.Invalid(
                throwable = it,
                reason = R.string.str_session_error
            )
        }
        .map { authToken ->
            if(authRepository.validateAuthSessionToken(authToken)){
                val userData = authRepository.mapAuthSessionTokenToUserData(authToken)
                if(userData != null){
                    SessionState.Valid(userData)
                }else{
                    SessionState.Invalid(
                        throwable = null,
                        reason = R.string.str_session_expired
                    )
                }
            }else{
                SessionState.Invalid(
                    throwable = null,
                    reason = R.string.str_session_expired
                )
            }
        }
}