package com.ekezet.xrated.rates.data.cache

import com.ekezet.xrated.base.data.cache.CacheStore.ExpirationStrategy
import com.ekezet.xrated.base.data.cache.CachedItem
import com.ekezet.xrated.rates.di.ExchangeRatesMap
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

/**
 * The reference rates are usually updated around 16:00 CET on every working day,
 * except on TARGET closing days. They are based on a regular daily concertation
 * procedure between central banks across Europe, which normally takes place at 14:15 CET.
 *
 * @see <a href="https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html">Euro foreign exchange reference rates</a>
 * @author kiri
 */
class ExchangeRatesExpirationStrategy(
    private val updateHourOfDay: Int
) : ExpirationStrategy<RatesCacheKey, ExchangeRatesMap>() {
    override fun isExpired(item: CachedItem<RatesCacheKey, ExchangeRatesMap>): Boolean {
        val now = DateTime.now(CET)
        val savedAt = item.createdAt.toDateTime(CET)
        val isSavedTodayDay = savedAt.isOnDay(now)
        val isSavedPastUpdateHour = savedAt.isPastUpdateHour()
        return if (isSavedTodayDay && !isSavedPastUpdateHour && now.isPastUpdateHour()) {
            true
        } else if (!isSavedTodayDay && !isSavedPastUpdateHour && !now.isPastUpdateHour()) {
            true
        } else savedAt.isBefore(now.minusDays(1))
    }

    private fun DateTime.isOnDay(dt: DateTime) =
        dt.year == year && dt.monthOfYear == monthOfYear && dt.dayOfMonth == dayOfMonth

    private fun DateTime.isPastUpdateHour() = updateHourOfDay <= hourOfDay

    companion object {
        private val CET = DateTimeZone.forID("CET")
    }
}
