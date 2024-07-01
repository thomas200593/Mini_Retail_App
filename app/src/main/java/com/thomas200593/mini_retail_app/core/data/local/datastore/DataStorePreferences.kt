package com.thomas200593.mini_retail_app.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AppConfigKeys.dsKeyCountry
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AppConfigKeys.dsKeyCurrency
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AppConfigKeys.dsKeyDynamicColor
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AppConfigKeys.dsKeyFontSize
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AppConfigKeys.dsKeyLanguage
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AppConfigKeys.dsKeyOnboardingStatus
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AppConfigKeys.dsKeyTheme
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AppConfigKeys.dsKeyTimezone
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AuthKeys.dsKeyAuthProvider
import com.thomas200593.mini_retail_app.core.data.local.datastore.DataStoreKeys.AuthKeys.dsKeyAuthSessionToken
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers.Dispatchers.IO
import com.thomas200593.mini_retail_app.core.util.CountryHelper.COUNTRY_DEFAULT
import com.thomas200593.mini_retail_app.core.util.CurrencyHelper.CURRENCY_DEFAULT
import com.thomas200593.mini_retail_app.core.util.TimezoneHelper.TIMEZONE_DEFAULT
import com.thomas200593.mini_retail_app.features.app_config.entity.ConfigCurrent
import com.thomas200593.mini_retail_app.features.app_config.entity.Country
import com.thomas200593.mini_retail_app.features.app_config.entity.Currency
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config.entity.DynamicColor.DISABLED
import com.thomas200593.mini_retail_app.features.app_config.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_config.entity.FontSize.MEDIUM
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.app_config.entity.Language.EN
import com.thomas200593.mini_retail_app.features.app_config.entity.OnboardingStatus
import com.thomas200593.mini_retail_app.features.app_config.entity.OnboardingStatus.HIDE
import com.thomas200593.mini_retail_app.features.app_config.entity.OnboardingStatus.SHOW
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme
import com.thomas200593.mini_retail_app.features.app_config.entity.Theme.SYSTEM
import com.thomas200593.mini_retail_app.features.app_config.entity.Timezone
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider.GOOGLE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joda.money.CurrencyUnit
import java.util.Locale
import javax.inject.Inject

class DataStorePreferences @Inject constructor(
    private val datastore: DataStore<Preferences>,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
){
    //Onboarding
    suspend fun hideOnboarding() = withContext(ioDispatcher){
        datastore.edit {
            it[dsKeyOnboardingStatus] = HIDE.name
        }
    }

    //Auth
    val authSessionToken = datastore.data
        .flowOn(ioDispatcher)
        .map { data ->
            AuthSessionToken(
                authProvider = data[dsKeyAuthProvider] ?.let { oAuthProvider ->
                    OAuthProvider.valueOf(oAuthProvider)
                } ?: GOOGLE,
                idToken = data[dsKeyAuthSessionToken]
            )
        }

    suspend fun clearAuthSessionToken() = withContext((ioDispatcher)){
        datastore.edit {
            it.remove(dsKeyAuthSessionToken)
        }
    }

    suspend fun saveAuthSessionToken(authSessionToken: AuthSessionToken) = withContext(ioDispatcher){
        datastore.edit {
            it[dsKeyAuthSessionToken] = authSessionToken.idToken!!
            it[dsKeyAuthProvider] = authSessionToken.authProvider?.name!!
        }
    }

    //App Config
    val configCurrent = datastore.data
        .map { data ->
            ConfigCurrent(
                onboardingStatus = data[dsKeyOnboardingStatus] ?.let { onboardingStatus ->
                    OnboardingStatus.valueOf(onboardingStatus)
                } ?: SHOW,
                theme = data[dsKeyTheme] ?.let { theme ->
                    Theme.valueOf(theme)
                } ?: SYSTEM,
                dynamicColor = data[dsKeyDynamicColor] ?.let { dynamicColor ->
                    DynamicColor.valueOf(dynamicColor)
                } ?: DISABLED,
                fontSize = data[dsKeyFontSize] ?.let { fontSize ->
                    FontSize.valueOf(fontSize)
                } ?: MEDIUM,
                language = data[dsKeyLanguage] ?.let { language ->
                    Language.valueOf(language)
                } ?: EN,
                timezone = data[dsKeyTimezone] ?.let { offset ->
                    Timezone(offset)
                } ?: TIMEZONE_DEFAULT,
                currency = data[dsKeyCurrency] ?.let { currency ->
                    Currency(
                        code = currency,
                        displayName = CurrencyUnit.of(currency).toCurrency().displayName,
                        symbol = CurrencyUnit.of(currency).symbol,
                        defaultFractionDigits = CurrencyUnit.of(currency).toCurrency().defaultFractionDigits,
                        decimalPlaces = CurrencyUnit.of(currency).decimalPlaces
                    )
                } ?: CURRENCY_DEFAULT,
                country = data[dsKeyCountry] ?.let{ country ->
                    Country(
                        isoCode = country,
                        iso03Country = Locale("", country).isO3Country,
                        displayName = Locale("", country).displayName
                    )
                } ?: COUNTRY_DEFAULT
            )
        }

    suspend fun setTheme(theme: Theme) = withContext(ioDispatcher){
        datastore.edit {
            it[dsKeyTheme] = theme.name
        }
    }

    suspend fun setDynamicColor(dynamicColor: DynamicColor) = withContext(ioDispatcher){
        datastore.edit {
            it[dsKeyDynamicColor] = dynamicColor.name
        }
    }

    suspend fun setLanguage(language: Language) = withContext(ioDispatcher){
        datastore.edit {
            it[dsKeyLanguage] = language.name
        }
    }

    suspend fun setTimezone(timezone: Timezone) = withContext(ioDispatcher){
        datastore.edit {
            it[dsKeyTimezone] = timezone.timezoneOffset
        }
    }

    suspend fun setCurrency(currency: Currency) = withContext(ioDispatcher){
        datastore.edit {
            it[dsKeyCurrency] = currency.code
        }
    }

    suspend fun setFontSize(fontSize: FontSize) = withContext(ioDispatcher){
        datastore.edit {
            it[dsKeyFontSize] = fontSize.name
        }
    }

    suspend fun setCountry(country: Country) = withContext(ioDispatcher){
        datastore.edit {
            it[dsKeyCountry] = country.isoCode
        }
    }
}