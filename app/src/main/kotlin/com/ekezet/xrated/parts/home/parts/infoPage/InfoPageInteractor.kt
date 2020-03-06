package com.ekezet.xrated.parts.home.parts.infoPage

import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.base.BaseInteractor
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.base.utils.PrefsManager
import com.ekezet.xrated.parts.home.parts.infoPage.InfoPageSpec.Interactor
import com.ekezet.xrated.rates.data.cache.RatesCacheKey
import com.ekezet.xrated.rates.data.RatesRepository
import com.ekezet.xrated.rates.di.ExchangeRatesStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class InfoPageInteractor @Inject constructor(
    private val ratesRepository: RatesRepository,
    private val ratesCacheStore: ExchangeRatesStore,
    private val prefsManager: PrefsManager
) : BaseInteractor<Interactor.Presenter>(), Interactor {
    override val coroutineContext = Dispatchers.Main

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        launch {
            ratesRepository.exchangeRatesChannel.consumeEach {
                refreshDates()
            }
        }
    }

    override fun clearCache() {
        launch {
            ratesCacheStore.removeAll()
            refreshDates()
        }
    }

    private suspend fun refreshDates() {
        val baseCurrency = prefsManager.baseCurrency
        val latestItem = ratesCacheStore.getItem(RatesCacheKey.latest(), updateAccess = false)
        presenter.onDatesAvailable(latestItem?.data?.get(baseCurrency)?.date, latestItem?.createdAt)
    }
}
