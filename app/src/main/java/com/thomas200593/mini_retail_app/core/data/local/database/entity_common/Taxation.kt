package com.thomas200593.mini_retail_app.core.data.local.database.entity_common

import android.content.Context
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.util.HlpCountry
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStringArray.Handler.StringArrayResource
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStringArray.StringArrayResources.BizTaxationType
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.domain.UCGetConfCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.ConfigCountry
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_country.entity.Country
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
data class Taxation(
    val identifierKey: String,
    val taxIdDocNumber: String = String(),
    val taxIssuerCountry: Country = HlpCountry.COUNTRY_DEFAULT,
    val taxRatePercentage: Double = 0.00,
    val taxIncluded: Boolean = false,
    val auditTrail: AuditTrail = AuditTrail()
)

interface RepoTaxation {
    fun getIdentityKeyDefault(): String
    fun getRefData(): Flow<Pair<Map<String, String>, ConfigCountry>>
}

class RepoTaxationImpl @Inject constructor(
    private val ucGetConfigCountry: UCGetConfCountry,
    @ApplicationContext private val context: Context,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
): RepoTaxation {
    override fun getIdentityKeyDefault(): String =
        StringArrayResource(BizTaxationType).asMap(context).entries.first().key
    override fun getRefData(): Flow<Pair<Map<String, String>, ConfigCountry>> =
        combine(
            flow = flowOf(StringArrayResource(BizTaxationType).asMap(context)),
            flow2 = ucGetConfigCountry.invoke()
        ){ tax, confCountry -> Pair(first = tax, second = confCountry) }.flowOn(ioDispatcher)
}