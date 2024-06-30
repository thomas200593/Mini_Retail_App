package com.thomas200593.mini_retail_app.features.business.domain

import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.features.business.repository.BusinessProfileRepository
import com.thomas200593.mini_retail_app.features.business.util.BusinessExtFn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBusinessProfileSummaryUseCase @Inject constructor(
    private val businessProfileRepository: BusinessProfileRepository,
    private val businessExtFn: BusinessExtFn,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke() = businessProfileRepository
        .getBusinessProfile()
        .flowOn(ioDispatcher)
        .catch {
            RequestState.Error(it)
        }
        .map {
            val data = it
            if(data == null){
                RequestState.Empty
            }else{
                RequestState.Success(
                    businessExtFn.bizProfileToBizProfileSummary(it)
                )
            }
        }
}