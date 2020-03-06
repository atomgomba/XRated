package com.ekezet.xrated.rates.data.cache

import com.ekezet.xrated.base.data.cache.CacheStore.ExpirationStrategy
import com.ekezet.xrated.base.data.cache.CachedItem
import com.ekezet.xrated.rates.di.ExchangeRatesMap
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants.SATURDAY
import org.joda.time.DateTimeConstants.SUNDAY

/**
 * @author kiri
 */
class WeekendExpirationStrategy : ExpirationStrategy<RatesCacheKey, ExchangeRatesMap>() {
    override fun isExpired(item: CachedItem<RatesCacheKey, ExchangeRatesMap>): Boolean {
        val isWeekend = DateTime.now().dayOfWeek in listOf(SATURDAY, SUNDAY)
        if (isWeekend) {
            // prevent other expiration strategies to trigger
            finish()
        }
        return false
    }
}
