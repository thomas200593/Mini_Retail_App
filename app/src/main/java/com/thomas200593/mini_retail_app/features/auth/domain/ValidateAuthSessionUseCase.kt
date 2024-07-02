package com.thomas200593.mini_retail_app.features.auth.domain

import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ValidateAuthSessionUseCase @Inject constructor(
    private val repository: AuthRepository,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(authSessionToken: AuthSessionToken): Boolean = withContext(ioDispatcher){
        if(repository.validateAuthSessionToken(authSessionToken)){
            repository.saveAuthSessionToken(authSessionToken)
            true
        }else{
            repository.clearAuthSessionToken()
            false
        }
    }
}