package com.ekezet.xrated.rates.base

import com.ekezet.xrated.base.arch.IInteractor
import com.ekezet.xrated.base.arch.IPresenter
import com.ekezet.xrated.base.arch.IView
import com.ekezet.xrated.rates.base.items.viewmodels.ExchangeRateListItem
import com.ekezet.xrated.rates.data.ExchangeRate
import kotlinx.coroutines.Job

/**
 * @author kiri
 */
interface BaseListSpec {
    interface View : IView {
        fun setEmptyText(text: CharSequence)
        fun showList()
        fun hideList()
        fun showItems(items: List<ExchangeRateListItem>)
        fun hideLoading()
        fun copyToClipboard(label: CharSequence, text: CharSequence)

        interface Presenter<V : View> : IPresenter<V> {
            fun onSwipeRefresh()
        }
    }

    interface Interactor<V : View, IP : Interactor.Presenter<V>> : IInteractor<IP> {
        fun updateDailyRates(): Job
        fun toggleFavorite(item: ExchangeRateListItem)
        fun changeBaseCurrency(baseCurrency: String)
        fun onExchangeRatesReceived(rates: List<ExchangeRate>)

        interface Presenter<V : View> : IPresenter<V> {
            fun onLoadingFinished(rates: List<ExchangeRateListItem>)
            fun onLoadingError()
            fun onBaseCurrencyChanged()
        }
    }
}
