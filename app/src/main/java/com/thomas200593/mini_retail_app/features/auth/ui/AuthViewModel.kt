package com.thomas200593.mini_retail_app.features.auth.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _idToken = MutableStateFlow(AuthSessionToken())
    val idToken = _idToken
    fun authSignInWithGoogle(context: Context, coroutineScope: CoroutineScope) =
        viewModelScope.launch {
            val result = authRepository.beginSignInWithGoogle(context, coroutineScope)
            _idToken.value = result.getOrDefault(AuthSessionToken())
        }
}