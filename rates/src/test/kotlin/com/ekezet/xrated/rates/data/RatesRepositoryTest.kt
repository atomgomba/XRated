package com.ekezet.xrated.rates.data

import com.ekezet.xrated.base.api.ApiResponse.SuccessResponse
import com.ekezet.xrated.rates.api.ExchangeRatesApiInterface
import com.ekezet.xrated.rates.api.responses.ExchangeRatesResponse
import com.ekezet.xrated.rates.data.cache.RatesCacheKey
import com.ekezet.xrated.rates.di.ExchangeRatesStore
import com.ekezet.xrated.testing.BlockingDispatcher
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * @author kiri
 */
@ExtendWith(MockKExtension::class)
internal class RatesRepositoryTest : CoroutineScope {
    override val coroutineContext = BlockingDispatcher()

    @MockK internal lateinit var apiInterface: ExchangeRatesApiInterface
    @MockK internal lateinit var cacheStore: ExchangeRatesStore

    private lateinit var subject: RatesRepository

    @BeforeEach fun setUp() {
        subject = RatesRepository(apiInterface, cacheStore, coroutineContext)
    }

    @Test fun `fetchLatestDailyRates and no existing data`() {
        val baseCurrency = "HUF"
        val newRates = listOf(
            ExchangeRate("PLN", 123F),
            ExchangeRate("HUF", 123F)
        )
        val response = mockk<ExchangeRatesResponse> {
            every { rates } returns newRates.map { it.currencyCode to it.value }.toMap()
        }

        coEvery { cacheStore.get(any()) } returns null
        coEvery { apiInterface.getDailyRates(any(), any()) } returns response

        var successResponse: SuccessResponse<List<ExchangeRate>>? = null

        launch {
            subject.exchangeRatesChannel.consumeEach {
                if (it is SuccessResponse) {
                    successResponse = it
                }
            }
        }

        runBlocking {
            subject.fetchLatestDailyRates(baseCurrency)
        }

        coVerify {
            cacheStore.put(RatesCacheKey.latest(), mutableMapOf(baseCurrency to response), updateExistingData = true)
        }

        assertTrue(successResponse?.data == newRates)
    }

    @Test fun `fetchLatestDailyRates and existing data set without requested currency`() {
        // TODO
    }

    @Test fun `fetchLatestDailyRates and existing data set with the requested currency already`() {
        // TODO
    }
}
