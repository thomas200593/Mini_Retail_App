package com.thomas200593.mini_retail_app.features.auth.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.repository.RepoAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UCValidateAuthSession @Inject constructor(
    private val repoAuth: RepoAuth,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(authSessionToken: AuthSessionToken): Boolean = withContext(ioDispatcher){
        if(repoAuth.validateAuthSessionToken(authSessionToken)) { repoAuth.saveAuthSessionToken(authSessionToken); true }
        else { repoAuth.clearAuthSessionToken(); false }
    }
}