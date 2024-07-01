package com.thomas200593.mini_retail_app.core.data.local.session

import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Invalid
import com.thomas200593.mini_retail_app.core.data.local.session.SessionState.Valid
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionImpl @Inject constructor(
    repository: AuthRepository,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
): Session {
    override val currentUserSession: Flow<SessionState> = repository.authSessionToken
        .flowOn(ioDispatcher)
        .catch {
            Invalid(throwable = it, reason = R.string.str_session_error)
        }
        .map { authToken ->
            if(repository.validateAuthSessionToken(authToken)){
                val userData = repository.mapAuthSessionTokenToUserData(authToken)
                if(userData != null){
                    Valid(userData = userData)
                }else{
                    Invalid(throwable = null, reason = R.string.str_session_expired)
                }
            }else{
                Invalid(throwable = null, reason = R.string.str_session_expired)
            }
        }
}