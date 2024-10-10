package com.thomas200593.mini_retail_app.features.onboarding.domain

import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.di.Dispatcher
import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_language.domain.UCGetConfGenLanguage
import com.thomas200593.mini_retail_app.features.onboarding.entity.Onboarding.OnboardingData
import com.thomas200593.mini_retail_app.features.onboarding.repository.RepoOnboarding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UCGetOnboardingData @Inject constructor(
    private val ucGetConfGenLanguage: UCGetConfGenLanguage,
    private val repoOnboarding: RepoOnboarding,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke() = combine(
        ucGetConfGenLanguage.invoke(), flow { emit(repoOnboarding.getOnboardingPages()) }
    ) { langConfig, onboardingPages -> OnboardingData(onboardingPages, langConfig) }.flowOn(ioDispatcher)
}