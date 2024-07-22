package com.thomas200593.mini_retail_app.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.CountryHelper
import com.thomas200593.mini_retail_app.core.design_system.util.CurrencyHelper
import com.thomas200593.mini_retail_app.core.design_system.util.TimezoneHelper
import com.thomas200593.mini_retail_app.features.app_config.app_cfg.entity.AppConfig
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_country.entity.Country
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_currency.entity.Currency
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_dynamic_color.entity.DynamicColor
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_font_size.entity.FontSize
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_language.entity.Language
import com.thomas200593.mini_retail_app.features.onboarding.entity.OnboardingStatus
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_theme.entity.Theme
import com.thomas200593.mini_retail_app.features.app_config.app_cfg_general_timezone.entity.Timezone
import com.thomas200593.mini_retail_app.features.auth.entity.AuthSessionToken
import com.thomas200593.mini_retail_app.features.auth.entity.OAuthProvider
import com.thomas200593.mini_retail_app.features.initial.entity.FirstTimeStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joda.money.CurrencyUnit
import java.util.Locale
import javax.inject.Inject

class DataStorePreferences @Inject constructor(
    private val datastore: DataStore<Preferences>,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher
){
    //Onboarding
    suspend fun hideOnboarding() = withContext(ioDispatcher){ datastore
        .edit { it[DataStoreKeys.AppConfigKeys.dsKeyOnboardingStatus] = OnboardingStatus.HIDE.name }
    }

    //Auth
    val authSessionToken = datastore.data.flowOn(ioDispatcher)
        .map { data ->
            AuthSessionToken(
                authProvider = data[DataStoreKeys.AuthKeys.dsKeyAuthProvider] ?.let { oAuthProvider ->
                    OAuthProvider.valueOf(oAuthProvider)
                } ?: OAuthProvider.GOOGLE,
                idToken = data[DataStoreKeys.AuthKeys.dsKeyAuthSessionToken]
            )
        }

    suspend fun clearAuthSessionToken() = withContext((ioDispatcher)){ datastore
        .edit { it.remove(DataStoreKeys.AuthKeys.dsKeyAuthSessionToken) }
    }

    suspend fun saveAuthSessionToken(authSessionToken: AuthSessionToken) = withContext(ioDispatcher){
        datastore.edit {
            it[DataStoreKeys.AuthKeys.dsKeyAuthSessionToken] = authSessionToken.idToken!!
            it[DataStoreKeys.AuthKeys.dsKeyAuthProvider] = authSessionToken.authProvider?.name!!
        }
    }

    //First Time Status
    val firstTimeStatus = datastore.data.map { data ->
        data[DataStoreKeys.AppConfigKeys.dsKeyFirstTimeStatus]
            ?.let { firstTimeStatus ->
                FirstTimeStatus.valueOf(firstTimeStatus)
            } ?: FirstTimeStatus.YES
    }

    //App Config
    val configCurrent = datastore.data.map { data ->
        AppConfig.ConfigCurrent(
            onboardingStatus = data[DataStoreKeys.AppConfigKeys.dsKeyOnboardingStatus]
                ?.let { onboardingStatus -> OnboardingStatus.valueOf(onboardingStatus) }
                ?: OnboardingStatus.SHOW,
            theme = data[DataStoreKeys.AppConfigKeys.dsKeyTheme]
                ?.let { theme -> Theme.valueOf(theme) }
                ?: Theme.SYSTEM,
            dynamicColor = data[DataStoreKeys.AppConfigKeys.dsKeyDynamicColor]
                ?.let { dynamicColor -> DynamicColor.valueOf(dynamicColor) }
                ?: DynamicColor.DISABLED,
            fontSize = data[DataStoreKeys.AppConfigKeys.dsKeyFontSize]
                ?.let { fontSize -> FontSize.valueOf(fontSize) }
                ?: FontSize.MEDIUM,
            language = data[DataStoreKeys.AppConfigKeys.dsKeyLanguage]
                ?.let { language -> Language.valueOf(language) }
                ?: Language.EN,
            timezone = data[DataStoreKeys.AppConfigKeys.dsKeyTimezone]
                ?.let { offset -> Timezone(offset) }
                ?: TimezoneHelper.TIMEZONE_DEFAULT,
            currency = data[DataStoreKeys.AppConfigKeys.dsKeyCurrency]
                ?.let { currency ->
                    Currency(
                        code = currency,
                        displayName = CurrencyUnit.of(currency).toCurrency().displayName,
                        symbol = CurrencyUnit.of(currency).symbol,
                        defaultFractionDigits = CurrencyUnit.of(currency).toCurrency().defaultFractionDigits,
                        decimalPlaces = CurrencyUnit.of(currency).decimalPlaces
                    )
                } ?: CurrencyHelper.CURRENCY_DEFAULT,
            country = data[DataStoreKeys.AppConfigKeys.dsKeyCountry] ?.let{ country ->
                Country(
                    isoCode = country,
                    iso03Country = Locale("", country).isO3Country,
                    displayName = Locale("", country).displayName
                )
            } ?: CountryHelper.COUNTRY_DEFAULT
        )
    }

    suspend fun setFirstTimeStatus(firstTimeStatus: FirstTimeStatus) = withContext(ioDispatcher){
        datastore.edit { it[DataStoreKeys.AppConfigKeys.dsKeyFirstTimeStatus] = firstTimeStatus.name }
    }

    suspend fun setTheme(theme: Theme) = withContext(ioDispatcher){
        datastore.edit { it[DataStoreKeys.AppConfigKeys.dsKeyTheme] = theme.name }
    }

    suspend fun setDynamicColor(dynamicColor: DynamicColor) = withContext(ioDispatcher){
        datastore.edit { it[DataStoreKeys.AppConfigKeys.dsKeyDynamicColor] = dynamicColor.name }
    }

    suspend fun setLanguage(language: Language) = withContext(ioDispatcher){
        datastore.edit { it[DataStoreKeys.AppConfigKeys.dsKeyLanguage] = language.name }
    }

    suspend fun setTimezone(timezone: Timezone) = withContext(ioDispatcher){
        datastore.edit { it[DataStoreKeys.AppConfigKeys.dsKeyTimezone] = timezone.timezoneOffset }
    }

    suspend fun setCurrency(currency: Currency) = withContext(ioDispatcher){
        datastore.edit { it[DataStoreKeys.AppConfigKeys.dsKeyCurrency] = currency.code }
    }

    suspend fun setFontSize(fontSize: FontSize) = withContext(ioDispatcher){
        datastore.edit { it[DataStoreKeys.AppConfigKeys.dsKeyFontSize] = fontSize.name }
    }

    suspend fun setCountry(country: Country) = withContext(ioDispatcher){
        datastore.edit { it[DataStoreKeys.AppConfigKeys.dsKeyCountry] = country.isoCode }
    }
}