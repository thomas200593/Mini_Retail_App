package com.thomas200593.mini_retail_app.features.app_conf.app_config.navigation

import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfigData
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.ConfigGeneral

enum class DestAppConfig(val scrGraphs: ScrGraphs){
    CONFIG_GENERAL(scrGraphs = ConfigGeneral),
    CONFIG_DATA(scrGraphs = ConfigData)
}
