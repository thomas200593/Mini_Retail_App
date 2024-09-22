package com.thomas200593.mini_retail_app.core.data.local.database.entity_common

import android.content.Context
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStringArray.Handler.StringArrayResource
import com.thomas200593.mini_retail_app.core.design_system.util.HlpStringArray.StringArrayResources.BizLegalType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
data class LegalType(
    val identifierKey: String,
    val additionalInfo: String? = null,
    val legalDocumentType: LegalDocumentType? = null,
    val auditTrail: AuditTrail = AuditTrail()
)

interface RepoLegalType {
    fun getIdentityKeyDefault(): String
    fun getRefData(): Flow<Map<String, String>>
}

class RepoLegalTypeImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : RepoLegalType {
    override fun getIdentityKeyDefault(): String =
        StringArrayResource(BizLegalType).asMap(context).entries.first().key

    override fun getRefData(): Flow<Map<String, String>> =
        flowOf(StringArrayResource(BizLegalType).asMap(context)).flowOn(ioDispatcher)
}