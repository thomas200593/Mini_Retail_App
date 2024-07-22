package com.thomas200593.mini_retail_app.core.data.local.session

import androidx.annotation.StringRes
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.auth.entity.UserData
import com.thomas200593.mini_retail_app.features.auth.repository.RepoAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface Session{
    val currentUserSession: Flow<SessionState>
}

class SessionImpl @Inject constructor(
    repoAuth: RepoAuth,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
): Session {
    override val currentUserSession: Flow<SessionState> = repoAuth.authSessionToken
        .flowOn(ioDispatcher).catch { SessionState.Invalid(throwable = it, reason = R.string.str_session_error) }
        .map { authToken ->
            if(repoAuth.validateAuthSessionToken(authToken)){
                val userData = repoAuth.mapAuthSessionTokenToUserData(authToken)
                if(userData != null){ SessionState.Valid(userData = userData) }
                else{ SessionState.Invalid(throwable = null, reason = R.string.str_session_expired) }
            }
            else{ SessionState.Invalid(throwable = null, reason = R.string.str_session_expired) }
        }
}

sealed interface SessionState{
    data object Loading: SessionState
    data class Valid(val userData: UserData): SessionState
    data class Invalid(val throwable: Throwable?, @StringRes val reason: Int): SessionState
}