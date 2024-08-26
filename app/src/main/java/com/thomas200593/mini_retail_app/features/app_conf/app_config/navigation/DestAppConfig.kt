package com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation

import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.AppConfigData
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.AppConfigGeneral

enum class DestAppConfig(val scrGraphs: ScrGraphs){
    APP_CONFIG_GENERAL(scrGraphs = AppConfigGeneral),
    APP_CONFIG_DATA(scrGraphs = AppConfigData)
}
