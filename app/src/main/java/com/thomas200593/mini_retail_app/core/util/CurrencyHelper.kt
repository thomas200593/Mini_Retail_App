package com.thomas200593.mini_retail_app.core.util

import com.thomas200593.mini_retail_app.features.app_config.entity.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.money.CurrencyUnit
import timber.log.Timber

private const val TAG = "CurrencyHelper"
object CurrencyHelper {
    private const val USD = "USD"
    val CURRENCY_DEFAULT = Currency(USD)
    suspend fun getCurrencies() = withContext(Dispatchers.IO){
        Timber.d("Called : %s.getCurrencies()", TAG)
        val currencies = CurrencyUnit.registeredCurrencies()
        val sets = HashSet<Currency>()
        for(currency in currencies){
            val code = currency.code
            sets.add(
                Currency(
                    code = code
                )
            )
        }
        sets.toList().sortedBy { it.code }
    }
}