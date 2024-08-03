package com.thomas200593.mini_retail_app.features.business.biz_profile.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState
import com.thomas200593.mini_retail_app.features.business.biz_profile.repository.RepoBizProfile
import com.thomas200593.mini_retail_app.features.business.util.ExtFnBusiness
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBizProfileSummaryUseCase @Inject constructor(
    private val repository: RepoBizProfile,
    private val bizExtFn: ExtFnBusiness,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke() = repository.getBusinessProfile().flowOn(ioDispatcher)
        .catch { ResourceState.Error(it) }
        .map {
            if(it != null){ ResourceState.Success(bizExtFn.bizProfileToBizProfileSummary(it)) }
            else{ ResourceState.Empty }
        }
}