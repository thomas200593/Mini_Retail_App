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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joda.money.CurrencyUnit
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

private val TAG = DataStorePreferences::class.simpleName

class DataStorePreferences @Inject constructor(
    private val datastore: DataStore<Preferences>,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
){
    val configCurrentData = datastore.data
        .map { data ->
            ConfigCurrent(
                onboardingStatus = data[dsKeyOnboardingStatus] ?.let { onboardingPagesStatus ->
                    OnboardingStatus.valueOf(onboardingPagesStatus)
                } ?: SHOW,
                theme = data[dsKeyTheme] ?.let { themeString ->
                    Theme.valueOf(themeString)
                } ?: SYSTEM,
                dynamicColor = data[dsKeyDynamicColor] ?.let { dynamicColor ->
                    DynamicColor.valueOf(dynamicColor)
                } ?: DISABLED,
                fontSize = data[dsKeyFontSize] ?.let { fontSize ->
                    FontSize.valueOf(fontSize)
                } ?: MEDIUM,
                language = data[dsKeyLanguage] ?.let { languageString ->
                    Language.valueOf(languageString)
                } ?: EN,
                timezone = data[dsKeyTimezone] ?.let { timezoneOffset ->
                    Timezone(timezoneOffset)
                } ?: TIMEZONE_DEFAULT,
                currency = data[dsKeyCurrency] ?.let { currencyCode ->
                    Currency(
                        code = currencyCode,
                        displayName = CurrencyUnit.of(currencyCode).toCurrency().displayName,
                        symbol = CurrencyUnit.of(currencyCode).symbol,
                        defaultFractionDigits = CurrencyUnit.of(currencyCode).toCurrency().defaultFractionDigits,
                        decimalPlaces = CurrencyUnit.of(currencyCode).decimalPlaces
                    )
                } ?: CURRENCY_DEFAULT,
                country = data[dsKeyCountry] ?.let{ countryIsoCode ->
                    Country(
                        isoCode = countryIsoCode,
                        iso03Country = Locale("", countryIsoCode).isO3Country,
                        displayName = Locale("", countryIsoCode).displayName
                    )
                } ?: COUNTRY_DEFAULT
            )
        }

    suspend fun hideOnboarding() = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.hideOnboarding()")
        datastore.edit {
            it[dsKeyOnboardingStatus] = HIDE.name
        }
    }

    val authSessionToken = datastore.data
        .flowOn(ioDispatcher)
        .map { data ->
            AuthSessionToken(
                authProvider = data[dsKeyAuthProvider] ?.let { oAuthProvider ->
                    OAuthProvider.valueOf(oAuthProvider)
                } ?: OAuthProvider.GOOGLE,
                idToken = data[dsKeyAuthSessionToken]
            )
        }

    suspend fun clearAuthSessionToken() = withContext((ioDispatcher)){
        Timber.d("Called : fun $TAG.clearAuthSessionToken()")
        datastore.edit {
            it.remove(dsKeyAuthSessionToken)
        }
    }

    suspend fun saveAuthSessionToken(authSessionToken: AuthSessionToken) = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.saveAuthSessionToken()")
        datastore.edit {
            it[dsKeyAuthSessionToken] = authSessionToken.idToken!!
            it[dsKeyAuthProvider] = authSessionToken.authProvider?.name!!
        }
    }

    suspend fun setThemePreferences(theme: Theme) = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.setThemePreferences()")
        datastore.edit {
            it[dsKeyTheme] = theme.name
        }
    }

    suspend fun setDynamicColorPreferences(dynamicColor: DynamicColor) = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.setDynamicColorPreferences()")
        datastore.edit {
            it[dsKeyDynamicColor] = dynamicColor.name
        }
    }

    suspend fun setLanguagePreferences(language: Language) = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.setLanguagePreferences()")
        datastore.edit {
            it[dsKeyLanguage] = language.name
        }
    }

    suspend fun setTimezonePreferences(timezone: Timezone) = withContext(ioDispatcher){
        Timber.d("Called : fuh $TAG.setTimezonePreferences()")
        datastore.edit {
            it[dsKeyTimezone] = timezone.timezoneOffset
        }
    }

    suspend fun setCurrencyPreferences(currency: Currency) = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.setCurrencyPreferences()")
        datastore.edit {
            it[dsKeyCurrency] = currency.code
        }
    }

    suspend fun setFontSizePreferences(fontSize: FontSize) = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.setFontSizePreferences()")
        datastore.edit {
            it[dsKeyFontSize] = fontSize.name
        }
    }

    suspend fun setCountryPreferences(country: Country) = withContext(ioDispatcher){
        Timber.d("Called : fun $TAG.setCountryPreferences()")
        datastore.edit {
            it[dsKeyCountry] = country.isoCode
        }
    }
}