package com.thomas200593.mini_retail_app.core.design_system.util

import java.time.Instant.now

object Constants {
    //ID
    const val ID = "ID"
    //System
    const val SYSTEM: String = "System"
    //Current UNIX Epoch Seconds
    val NOW_EPOCH_SECOND: Long = now().epochSecond
    /**
     * System Notification Constants
     */
    const val MAX_NUM_NOTIFICATIONS: Int = 5
    const val TARGET_ACTIVITY_NAME: String = "com.thomas200593.mini_retail_app.app.ActMain"
}