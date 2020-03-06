package com.ekezet.xrated.rates.data

import com.ekezet.xrated.base.api.ApiResponse
import com.ekezet.xrated.base.api.callOnChannel
import com.ekezet.xrated.rates.api.ExchangeRatesApiInterface
import com.ekezet.xrated.rates.api.responses.ExchangeRatesResponse
import com.ekezet.xrated.rates.data.cache.RatesCacheKey
import com.ekezet.xrated.rates.di.ExchangeRatesStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author kiri
 */
@Singleton
class RatesRepository constructor(
    private val apiInterface: ExchangeRatesApiInterface,
    private val cacheStore: ExchangeRatesStore,
    dispatcher: CoroutineDispatcher
) : CoroutineScope {
    @Inject constructor(
        apiInterface: ExchangeRatesApiInterface,
        cacheStore: ExchangeRatesStore
    ) : this(apiInterface, cacheStore, Dispatchers.IO)

    override val coroutineContext = dispatcher

    val exchangeRatesChannel = ConflatedBroadcastChannel<ApiResponse<List<ExchangeRate>>>()

    private var deferredResponse: Deferred<List<ExchangeRate>?>? = null

    suspend fun fetchLatestDailyRates(baseCurrency: String) =
        fetchDailyRates("latest", baseCurrency)

    suspend fun fetchDailyRates(day: String, baseCurrency: String) = coroutineScope {
        deferredResponse?.await()
        deferredResponse = callOnChannel(exchangeRatesChannel) {
            val cacheKey = RatesCacheKey(day)
            val cachedRates = cacheStore.get(cacheKey) ?: HashMap()
            if (cachedRates.contains(baseCurrency)) {
                return@callOnChannel exchangeApiBugfix(cachedRates.getValue(baseCurrency).toList(), baseCurrency)
            }
            val response = apiInterface.getDailyRates(day, mapOf("base" to baseCurrency))
            cachedRates[baseCurrency] = response
            cacheStore.put(cacheKey, cachedRates, updateExistingData = true)
            return@callOnChannel exchangeApiBugfix(response.toList(), baseCurrency)
        }
    }

    private fun ExchangeRatesResponse.toList(): MutableList<ExchangeRate> =
        rates.map { ExchangeRate(it.key, it.value) }.toMutableList()

    /**
     * @see <a href="https://github.com/exchangeratesapi/exchangeratesapi/issues/77">EUR to EUR not working #77</a>
     */
    private fun exchangeApiBugfix(rates: MutableList<ExchangeRate>, baseCurrency: String) =
        if (baseCurrency == "EUR" && rates.none { it.currencyCode == baseCurrency }) {
            rates.apply { add(ExchangeRate("EUR", 1F)) }
        } else {
            rates
        }
}
