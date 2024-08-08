package com.thomas200593.mini_retail_app.core.design_system.util.countries.di

import com.thomas200593.mini_retail_app.core.design_system.util.countries.HlpCountries
import com.thomas200593.mini_retail_app.core.design_system.util.countries.HlpCountriesImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ModHlpCountries {
    @Binds
    abstract fun bindsImplementation(impl: HlpCountriesImpl): HlpCountries

    companion object {
        @Provides
        @CountryDefault
        fun providesCountryDefault() = HlpCountriesImpl.COUNTRY_DEFAULT
    }
}