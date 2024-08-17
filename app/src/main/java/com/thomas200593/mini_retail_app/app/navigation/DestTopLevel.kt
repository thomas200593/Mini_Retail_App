package com.thomas200593.mini_retail_app.app.navigation

import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Business
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Dashboard
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.Reporting
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs.UserProfile

enum class DestTopLevel(val scrGraphs: ScrGraphs){
    DASHBOARD(scrGraphs = Dashboard),
    BUSINESS(scrGraphs = Business),
    REPORTING(scrGraphs = Reporting),
    USER_PROFILE(scrGraphs = UserProfile)
}