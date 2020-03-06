package com.ekezet.xrated.rates.data

import com.ekezet.xrated.rates.data.cache.ExchangeRatesExpirationStrategy
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * @author kiri
 */
internal class ExchangeRatesExpirationStrategyTest {
    private val updateHour = 16
    private val subject =
        ExchangeRatesExpirationStrategy(updateHour)

    @Test fun `fresh item from today past the update`() {
        mockkStatic(DateTime::class)

        every { DateTime.now(any<DateTimeZone>()) } returns date("2020-02-02T${updateHour + 1}:00:00")

        assertFalse(subject.isExpired(mockk {
            every { createdAt } returns date("2020-02-02T$updateHour:00:00")
        }))
    }

    @Test fun `fresh item from today before the update`() {
        mockkStatic(DateTime::class)

        every { DateTime.now(any<DateTimeZone>()) } returns date("2020-02-02T${updateHour - 1}:00:00")

        assertFalse(subject.isExpired(mockk {
            every { createdAt } returns date("2020-02-02T${updateHour - 2}:00:00")
        }))
    }

    @Test fun `fresh item from yesterday`() {
        mockkStatic(DateTime::class)

        every { DateTime.now(any<DateTimeZone>()) } returns date("2020-02-02T${updateHour - 3}:00:00")

        assertFalse(subject.isExpired(mockk {
            every { createdAt } returns date("2020-02-01T${updateHour + 4}:00:00")
        }))
    }

    @Test fun `expired item from today past the update`() {
        mockkStatic(DateTime::class)

        every { DateTime.now(any<DateTimeZone>()) } returns date("2020-02-02T${updateHour + 1}:00:00")

        assertTrue(subject.isExpired(mockk {
            every { createdAt } returns date("2020-02-02T${updateHour - 1}:00:00")
        }))
    }

    @Test fun `expired item from yesterday`() {
        mockkStatic(DateTime::class)

        every { DateTime.now(any<DateTimeZone>()) } returns date("2020-02-02T${updateHour - 3}:00:00")

        assertTrue(subject.isExpired(mockk {
            every { createdAt } returns date("2020-02-01T${updateHour - 1}:00:00")
        }))
    }

    @Test fun `expired item from the past`() {
        mockkStatic(DateTime::class)

        every { DateTime.now(any<DateTimeZone>()) } returns date("2020-02-02T${updateHour - 3}:00:00")

        assertTrue(subject.isExpired(mockk {
            every { createdAt } returns date("2020-01-31T$updateHour:00:00")
        }))
    }

    @AfterEach fun tearDown() {
        unmockkStatic(DateTime::class)
    }

    private fun date(value: String): DateTime =
        DateTime.parse(value).withZoneRetainFields(DateTimeZone.forID("CET"))
}
