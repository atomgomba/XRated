package com.ekezet.xrated.rates.base.items

import com.ekezet.xrated.rates.base.items.viewmodels.ExchangeRateListItem

/**
 * @author kiri
 */
interface ExchangeRateListItemSpec {
    interface View {
        var currencyCode: CharSequence
        var value: CharSequence
        var flag: String
        var isFavorite: Boolean
        fun setContentDescriptions(copyDesc: CharSequence, favoriteDesc: CharSequence)

        interface Presenter {
            fun onBindListItem(itemView: View, item: ExchangeRateListItem)
            fun onFavoriteButtonClicked(item: ExchangeRateListItem)
            fun onBaseCurrencySelected(item: ExchangeRateListItem)
            fun onCopyButtonClicked(item: ExchangeRateListItem)
        }
    }
}
