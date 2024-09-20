package com.thomas200593.mini_retail_app.core.design_system.util

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.thomas200593.mini_retail_app.R.array

object HlpStringArray{
    enum class StringArrayResources(@ArrayRes val resKeyId: Int, @ArrayRes val resValueId: Int){
        BizIndustries(resKeyId = array.biz_industry_keys, resValueId = array.biz_industry_values)
    }

    sealed class Handler {
        data class DynamicStringArray(val values: Map<String, String>): Handler()
        class StringArrayResource(val resArray: StringArrayResources): Handler()

        @Composable
        fun asMap() = when (this) {
            is DynamicStringArray -> values
            is StringArrayResource -> {
                val keys = LocalContext.current.resources.getStringArray(resArray.resKeyId)
                val values = LocalContext.current.resources.getStringArray(resArray.resValueId)
                if(keys.size != values.size) emptyMap() else keys.zip(values).toMap()
            }
        }

        fun asMap(context: Context): Map<String, String> = when (this) {
            is DynamicStringArray -> values
            is StringArrayResource -> {
                val keys = context.resources.getStringArray(resArray.resKeyId)
                val values = context.resources.getStringArray(resArray.resValueId)
                if (keys.size != values.size) emptyMap() else keys.zip(values).toMap()
            }
        }

        fun findByKey(key: String, context: Context): String? =
            asMap(context)[key]

        @Composable
        fun findByKey(key: String): String? =
            asMap()[key]
    }
}