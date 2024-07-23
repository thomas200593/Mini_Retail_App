package com.thomas200593.mini_retail_app.core.design_system.util

import com.thomas200593.mini_retail_app.BuildConfig.DEBUG
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant
import timber.log.Timber.Forest.treeCount

object HlpTimber
{ fun initDebugTree(){ if (treeCount == 0 && DEBUG){ plant(tree = DebugTree()) } } }