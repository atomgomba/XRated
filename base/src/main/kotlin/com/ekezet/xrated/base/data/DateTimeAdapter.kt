package com.ekezet.xrated.base.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.joda.time.DateTime

/**
 * @author kiri
 */
class DateTimeAdapter {
    @ToJson fun toJson(value: DateTime) = value.toString()
    @FromJson fun fromJson(value: String): DateTime = DateTime.parse(value)
}
