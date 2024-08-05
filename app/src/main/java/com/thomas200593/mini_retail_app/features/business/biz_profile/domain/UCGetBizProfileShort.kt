package com.thomas200593.mini_retail_app.features.business.biz_profile.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Empty
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Error
import com.thomas200593.mini_retail_app.core.design_system.util.ResourceState.Success
import com.thomas200593.mini_retail_app.features.business.biz_profile.repository.RepoBizProfile
import com.thomas200593.mini_retail_app.features.business.biz_profile.util.ExtFnBizProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UCGetBizProfileShort @Inject constructor(
    private val repoBizProfile: RepoBizProfile,
    private val extFnBizProfile: ExtFnBizProfile,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke() = repoBizProfile.getBizProfile().flowOn(ioDispatcher)
        .catch { Error(it) }
        .map {
            if(it != null){ Success(extFnBizProfile.bizProfileToBizProfileShort(it)) }
            else{ Empty }
        }
}