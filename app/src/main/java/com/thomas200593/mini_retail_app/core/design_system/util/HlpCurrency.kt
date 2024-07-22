package com.thomas200593.mini_retail_app.core.design_system.util

import com.thomas200593.mini_retail_app.features.app_conf._g_currency.entity.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.money.CurrencyUnit

object HlpCurrency {
    private val USD = CurrencyUnit.USD
    val CURRENCY_DEFAULT = Currency(
        code = USD.code,
        displayName = USD.toCurrency().displayName,
        symbol = USD.symbol,
        defaultFractionDigits = USD.toCurrency().defaultFractionDigits,
        decimalPlaces = USD.decimalPlaces
    )
    suspend fun getCurrencyList() = withContext(Dispatchers.IO) {
        val currencies = CurrencyUnit.registeredCurrencies()
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