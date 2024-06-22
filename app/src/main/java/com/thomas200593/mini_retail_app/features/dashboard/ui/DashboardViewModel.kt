package com.thomas200593.mini_retail_app.features.dashboard.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

private val TAG = DashboardViewModel::class.simpleName

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {
    fun onOpen() {
        Timber.d("Called : fun $TAG.onOpen()")
    }
}
