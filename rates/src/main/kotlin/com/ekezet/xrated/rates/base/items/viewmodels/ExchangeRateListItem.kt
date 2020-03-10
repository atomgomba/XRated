package com.ekezet.xrated.rates.base.items.viewmodels

/**
 * @author kiri
 */
data class ExchangeRateListItem(
    val currencyCode: String,
    val value: Float,
    val isFavorite: Boolean = false
) {
    val id: Long = currencyCode.toCharArray().contentHashCode().toLong()
}
