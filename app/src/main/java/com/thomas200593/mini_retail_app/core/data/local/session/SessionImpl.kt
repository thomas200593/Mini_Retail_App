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
import timber.log.Timber
import javax.inject.Inject

private val TAG = SessionImpl::class.simpleName

class SessionImpl @Inject constructor(
    authRepository: AuthRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
): Session {
    override val currentUserSession: Flow<SessionState> = authRepository
        .authSessionToken.flowOn(ioDispatcher)
        .catch {
            Timber.d("val $TAG.currentUserSession returned : Throwable -> $it")
            SessionState.Invalid(
                throwable = it,
                reason = R.string.str_session_error
            )
        }
        .map { authToken ->
            if(authRepository.validateAuthSessionToken(authToken)){
                val userData = authRepository.mapAuthSessionTokenToUserData(authToken)
                if(userData != null){
                    Timber.d("val $TAG.currentUserSession returned : SessionState.Valid($userData)")
                    SessionState.Valid(userData)
                }else{
                    val throwable = null
                    val reason = R.string.str_session_expired
                    Timber.d("val $TAG.currentUserSession returned : SessionState.Invalid(throwable=$throwable, reasonId=$reason)")
                    SessionState.Invalid(
                        throwable = throwable,
                        reason = reason
                    )
                }
            }else{
                val throwable = null
                val reason = R.string.str_session_expired
                Timber.d("val $TAG.currentUserSession returned : SessionState.Invalid(throwable=$throwable, reasonId=$reason)")
                SessionState.Invalid(
                    throwable = null,
                    reason = R.string.str_session_expired
                )
            }
        }
}