package com.ekezet.xrated.rates.api.responses

import com.squareup.moshi.Json
import org.joda.time.DateTime

/**
 * @author kiri
 */
data class ExchangeRatesResponse(
    @field:Json(name = "base") val base: String,
    @field:Json(name = "date") val date: DateTime,
    @field:Json(name = "rates") val rates: Map<String, Float>
)
