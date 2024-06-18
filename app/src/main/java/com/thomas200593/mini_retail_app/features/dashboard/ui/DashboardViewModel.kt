package com.thomas200593.mini_retail_app.features.dashboard.ui

import androidx.lifecycle.ViewModel
import com.thomas200593.mini_retail_app.features.auth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

}
