package com.ekezet.xrated.rates.base

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.base.BasePresenter
import com.ekezet.xrated.rates.R
import com.ekezet.xrated.rates.base.BaseListSpec.Interactor
import com.ekezet.xrated.rates.base.BaseListSpec.View
import com.ekezet.xrated.rates.base.items.ExchangeRateListItemSpec
import com.ekezet.xrated.rates.base.items.viewmodels.ExchangeRateListItem
import java.text.NumberFormat

/**
 * @author kiri
 */
open class BaseListPresenter<V : View, IP : Interactor.Presenter<V>, I : Interactor<V, IP>> :
    BasePresenter<V, I, Nothing>(),
    View.Presenter<V>, Interactor.Presenter<V>, ExchangeRateListItemSpec.View.Presenter {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        view.setup(R.layout.view_rates_list)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        interactor!!.updateDailyRates()
    }

    override fun onLoadingFinished(rates: List<ExchangeRateListItem>) {
        view.hideLoading()
        if (rates.isEmpty()) {
            view.hideList()
        } else {
            view.showList()
            view.showItems(rates.sortedBy { it.currencyCode })
        }
    }

    override fun onBaseCurrencySelected(item: ExchangeRateListItem) {
        interactor!!.changeBaseCurrency(item.currencyCode)
    }

    override fun onBaseCurrencyChanged() {
        interactor!!.updateDailyRates()
    }

    override fun onSwipeRefresh() {
        interactor!!.updateDailyRates()
    }

    override fun onLoadingError() {
        view.hideLoading()
    }

    @SuppressLint("DefaultLocale")
    override fun onBindListItem(itemView: ExchangeRateListItemSpec.View, item: ExchangeRateListItem) {
        with(itemView) {
            currencyCode = item.currencyCode.toUpperCase()
            value = item.value.format()
            flag = item.currencyCode
            isFavorite = item.isFavorite
            setContentDescriptions(
                copyDesc = t(R.string.rates__copy_button_content_description, item.currencyCode),
                favoriteDesc = t(R.string.rates__favorite_button_content_description, item.currencyCode)
            )
        }
    }

    private fun Float.format(): CharSequence {
        return NumberFormat.getNumberInstance().apply {
            maximumFractionDigits = when {
                this@format * 1000 < 10 -> 6
                this@format * 100 < 10 -> 5
                this@format * 10 < 10 -> 4
                else -> 3
            }
        }.format(this)
    }

    override fun onCopyButtonClicked(item: ExchangeRateListItem) {
        view.copyToClipboard(item.currencyCode, "${item.value} ${item.currencyCode}")
    }

    override fun onFavoriteButtonClicked(item: ExchangeRateListItem) {
        interactor!!.toggleFavorite(item)
    }
}
