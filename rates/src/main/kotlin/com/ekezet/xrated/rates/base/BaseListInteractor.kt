package com.ekezet.xrated.rates.base

import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.base.BaseInteractor
import com.ekezet.xrated.base.api.ApiResponse.FailureResponse
import com.ekezet.xrated.base.api.ApiResponse.SuccessResponse
import com.ekezet.xrated.base.utils.PrefsManager
import com.ekezet.xrated.base.utils.PrefsManager.Companion.PREF_BASE_AMOUNT
import com.ekezet.xrated.base.utils.PrefsManager.Companion.PREF_BASE_CURRENCY
import com.ekezet.xrated.base.utils.PrefsManager.Companion.PREF_NUM_FORMAT_LANGUAGE
import com.ekezet.xrated.rates.base.BaseListSpec.Interactor
import com.ekezet.xrated.rates.base.BaseListSpec.View
import com.ekezet.xrated.rates.base.items.viewmodels.ExchangeRateListItem
import com.ekezet.xrated.rates.data.FavoritesRepository
import com.ekezet.xrated.rates.data.RatesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

/**
 * @author kiri
 */
abstract class BaseListInteractor<V : View, IP : Interactor.Presenter<V>> constructor(
    private val ratesRepository: RatesRepository,
    internal val favoritesRepository: FavoritesRepository,
    internal val prefsManager: PrefsManager
) : BaseInteractor<IP>(), Interactor<V, IP> {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        launch(Dispatchers.Main) {
            ratesRepository.exchangeRatesChannel.consumeEach {
                when (it) {
                    is SuccessResponse -> onExchangeRatesReceived(it.data)
                    is FailureResponse -> presenter.onLoadingError()
                }
            }
        }
        launch(Dispatchers.Main) {
            prefsManager.changesChannel.consumeEach {
                when (it) {
                    PREF_BASE_CURRENCY -> presenter.onBaseCurrencyChanged()
                    PREF_BASE_AMOUNT -> refreshExchangeRates()
                    PREF_NUM_FORMAT_LANGUAGE -> presenter.onNumberFormatChanged()
                }
            }
        }
        launch(Dispatchers.Main) {
            favoritesRepository.favoritesChannel.consumeEach {
                refreshExchangeRates()
            }
        }
    }

    override fun updateDailyRates() = launch {
        ratesRepository.fetchLatestDailyRates(prefsManager.baseCurrency)
    }

    override fun toggleFavorite(item: ExchangeRateListItem) {
        launch(Dispatchers.Main) {
            if (item.isFavorite) {
                favoritesRepository.removeFavorites(item.currencyCode)
            } else {
                favoritesRepository.addFavorite(item.currencyCode)
            }
        }
    }

    override fun changeBaseCurrency(baseCurrency: String) {
        if (prefsManager.baseCurrency != baseCurrency) {
            prefsManager.baseCurrency = baseCurrency
        }
    }

    private fun refreshExchangeRates() {
        val latestData = ratesRepository.exchangeRatesChannel.valueOrNull?.data ?: return
        onExchangeRatesReceived(latestData)
    }
}
