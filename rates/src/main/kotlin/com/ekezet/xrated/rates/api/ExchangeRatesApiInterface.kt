package com.ekezet.xrated.rates.api

import com.ekezet.xrated.rates.api.responses.ExchangeRatesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * @author kiri
 */
interface ExchangeRatesApiInterface {
    @GET("/{day}")
    suspend fun getDailyRates(
        @Path("day") day: String,
        @QueryMap params: Map<String, String>
    ): ExchangeRatesResponse
}
