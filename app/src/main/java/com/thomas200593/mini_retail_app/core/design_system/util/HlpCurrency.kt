package com.thomas200593.mini_retail_app.core.design_system.util

import com.thomas200593.mini_retail_app.features.app_conf.conf_gen_currency.entity.Currency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.joda.money.CurrencyUnit.USD
import org.joda.money.CurrencyUnit.registeredCurrencies

object HlpCurrency {
    private val CURRENCY_USD = USD
    val CURRENCY_DEFAULT = Currency(
        code = CURRENCY_USD.code,
        displayName = CURRENCY_USD.toCurrency().displayName,
        symbol = CURRENCY_USD.symbol,
        defaultFractionDigits = CURRENCY_USD.toCurrency().defaultFractionDigits,
        decimalPlaces = CURRENCY_USD.decimalPlaces
    )
    suspend fun getCurrencyList(dispatcher: CoroutineDispatcher) = withContext(dispatcher) {
        registeredCurrencies().map {
            Currency(
                code = it.code,
                displayName = it.toCurrency().displayName,
                symbol = it.symbol,
                defaultFractionDigits = it.toCurrency().defaultFractionDigits,
                decimalPlaces = it.decimalPlaces
            )
        }.sortedBy { it.code }
    }
}