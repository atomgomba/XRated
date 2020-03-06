package com.ekezet.xrated.parts.home

import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.base.BaseInteractor
import com.ekezet.xrated.base.api.ApiResponse.FailureResponse
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.base.utils.PrefsManager
import com.ekezet.xrated.base.utils.PrefsManager.Companion.PREF_BASE_AMOUNT
import com.ekezet.xrated.base.utils.PrefsManager.Companion.PREF_BASE_CURRENCY
import com.ekezet.xrated.di.MENU
import com.ekezet.xrated.parts.home.HomeSpec.Interactor
import com.ekezet.xrated.parts.home.di.NavigationChannel
import com.ekezet.xrated.rates.data.FavoritesRepository
import com.ekezet.xrated.rates.data.RatesRepository
import com.ekezet.xrated.rates.di.ExchangeRatesStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

/**
 * @author kiri
 */
@ActivityScope
class HomeInteractor @Inject constructor(
    private val prefsManager: PrefsManager,
    @Named(MENU) private val navigationChannel: NavigationChannel,
    private val ratesRepository: RatesRepository,
    private val favoritesRepository: FavoritesRepository,
    private val ratesCacheStore: ExchangeRatesStore
) : BaseInteractor<Interactor.Presenter>(), Interactor {
    override val coroutineContext = Dispatchers.Main

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        owner.lifecycle.addObserver(ratesCacheStore)
        launch {
            prefsManager.changesChannel.consumeEach {
                if (it == PREF_BASE_CURRENCY) {
                    presenter.onBaseCurrencyChanged(prefsManager.baseCurrency)
                } else if (it == PREF_BASE_AMOUNT) {
                    presenter.onBaseAmountChanged()
                }
            }
        }
        launch {
            navigationChannel.consumeEach {
                presenter.onNavigationItemSelected(it)
            }
        }
        launch {
            ratesRepository.exchangeRatesChannel.consumeEach {
                if (it is FailureResponse) {
                    presenter.onLoadingError(it.throwable)
                }
            }
        }
    }

    override fun hasFavorites() = favoritesRepository.favoritesChannel.value.isNotEmpty()

    override fun fetchBaseCurrency() {
        presenter.onBaseCurrencyChanged(prefsManager.baseCurrency)
    }
}
