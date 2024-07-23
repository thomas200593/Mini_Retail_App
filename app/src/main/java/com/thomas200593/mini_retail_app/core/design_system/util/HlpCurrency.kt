package com.thomas200593.mini_retail_app.core.design_system.util

import com.thomas200593.mini_retail_app.features.app_conf._gen_currency.entity.Currency
import kotlinx.coroutines.Dispatchers
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
    suspend fun getCurrencyList() = withContext(Dispatchers.IO) {
        val currencies = registeredCurrencies()
        val sets = HashSet<Currency>()
        for (currency in currencies) {
            sets.add(
                Currency(
                    code = currency.code,
                    displayName = currency.toCurrency().displayName,
                    symbol = currency.symbol,
                    defaultFractionDigits = currency.toCurrency().defaultFractionDigits,
                    decimalPlaces = currency.decimalPlaces
                )
            )
        }
        sets.toList().sortedBy { it.code }
    }
}