package com.ekezet.xrated.rates.di

import com.ekezet.xrated.base.data.cache.CacheStore
import com.ekezet.xrated.rates.api.responses.ExchangeRatesResponse
import com.ekezet.xrated.rates.data.cache.RatesCacheKey

/**
 * @author kiri
 */
typealias CachedExchangeRates = ExchangeRatesResponse
typealias ExchangeRatesMap = MutableMap<String, CachedExchangeRates>
typealias ExchangeRatesStore = CacheStore<RatesCacheKey, ExchangeRatesMap>
