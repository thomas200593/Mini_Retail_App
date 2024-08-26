package com.thomas200593.mini_retail_app.features.app_conf.conf_data.navigation

import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.DataBackup

enum class DestConfData(scrGraphs: ScrGraphs){
    CONF_DATA_BACKUP(scrGraphs = DataBackup)
}