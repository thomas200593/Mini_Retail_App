package com.thomas200593.mini_retail_app.core.design_system.util

import java.time.Instant.now

object Constants {
    //System
    const val SYSTEM = "System"
    //Current UNIX Epoch Seconds
    val NOW_EPOCH_SECOND = now().epochSecond
    /**
     * System Notification Constants
     */
    const val MAX_NUM_NOTIFICATIONS = 5
    const val TARGET_ACTIVITY_NAME = "com.thomas200593.mini_retail_app.app.MainActivity"
}