package com.thomas200593.mini_retail_app.features.initial.initialization.domain

import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.RepoIndustries
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.RepoLegalDocType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.RepoLegalType
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.RepoTaxation
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.domain.UCGetConfGenLanguage
import com.thomas200593.mini_retail_app.features.initial.initialization.entity.Initialization
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetInitializationData @Inject constructor(
    private val ucGetConfGenLanguage: UCGetConfGenLanguage,
    private val repoIndustries: RepoIndustries,
    private val repoLegalType: RepoLegalType,
    private val repoLegalDocType: RepoLegalDocType,
    private val repoTaxType: RepoTaxation,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
){
    operator fun invoke() = combine(
        ucGetConfGenLanguage.invoke(),
        repoIndustries.getRefData(),
        repoLegalType.getRefData(),
        repoLegalDocType.getRefData(),
        repoTaxType.getRefData()
    ){ confGenLang, industries, legalType, legalDocType, taxation ->
        Success(
            data = Initialization(
                configCurrent = confGenLang.configCurrent,
                languages = confGenLang.languages,
                industries = industries,
                legalType = legalType,
                legalDocType = legalDocType,
                taxation = Pair(
                    first = taxation.first,
                    second = taxation.second.countries
                )
            )
        )
    }.flowOn(ioDispatcher)
}