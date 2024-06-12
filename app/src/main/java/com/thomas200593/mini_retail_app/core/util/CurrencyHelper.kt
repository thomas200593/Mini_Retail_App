package com.thomas200593.mini_retail_app.core.util

import com.thomas200593.mini_retail_app.features.app_config.entity.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.money.CurrencyUnit
import timber.log.Timber

private const val TAG = "CurrencyHelper"
object CurrencyHelper {
    private val USD = CurrencyUnit.USD
    val CURRENCY_DEFAULT = Currency(
        code = USD.code,
        displayName = USD.toCurrency().displayName,
        symbol = USD.symbol,
        defaultFractionDigits = USD.toCurrency().defaultFractionDigits,
        decimalPlaces = USD.decimalPlaces
    )
    suspend fun getCurrencies() = withContext(Dispatchers.IO){
        Timber.d("Called : %s.getCurrencies()", TAG)
        val currencies = CurrencyUnit.registeredCurrencies()
        val sets = HashSet<Currency>()
        for(currency in currencies){
            val code = currency.code
            val displayName = currency.toCurrency().displayName
            val symbol = currency.symbol
            val defaultFractionDigits = currency.toCurrency().defaultFractionDigits
            val decimalPlaces = currency.decimalPlaces
            sets.add(
                Currency(
                    code = code,
                    displayName = displayName,
                    symbol = symbol,
                    defaultFractionDigits = defaultFractionDigits,
                    decimalPlaces = decimalPlaces
                )
            )
        }
        sets.toList().sortedBy { it.code }
    }
}