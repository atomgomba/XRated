package com.ekezet.xrated.rates.data.cache

import com.ekezet.xrated.base.data.cache.CacheKey

/**
 * @author kiri
 */
inline class RatesCacheKey(
    private val day: String
) : CacheKey {
    override fun toString() = day

    companion object {
        fun latest() = RatesCacheKey("latest")
    }
}
