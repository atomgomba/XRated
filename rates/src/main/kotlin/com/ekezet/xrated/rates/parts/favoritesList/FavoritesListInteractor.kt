package com.ekezet.xrated.rates.parts.favoritesList

import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.base.utils.PrefsManager
import com.ekezet.xrated.rates.base.BaseListInteractor
import com.ekezet.xrated.rates.base.items.viewmodels.ExchangeRateListItem
import com.ekezet.xrated.rates.data.ExchangeRate
import com.ekezet.xrated.rates.data.FavoritesRepository
import com.ekezet.xrated.rates.data.RatesRepository
import com.ekezet.xrated.rates.parts.favoritesList.FavoritesListSpec.Interactor
import com.ekezet.xrated.rates.parts.favoritesList.FavoritesListSpec.Interactor.Presenter
import com.ekezet.xrated.rates.parts.favoritesList.FavoritesListSpec.View
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class FavoritesListInteractor @Inject constructor(
    ratesRepository: RatesRepository,
    favoritesRepository: FavoritesRepository,
    prefsManager: PrefsManager
) : BaseListInteractor<View, Presenter>(ratesRepository, favoritesRepository, prefsManager), Interactor {
    override fun onExchangeRatesReceived(rates: List<ExchangeRate>) {
        val baseAmount = prefsManager.baseAmount
        presenter.onLoadingFinished(rates.filter {
            favoritesRepository.isFavorite(it.currencyCode)
        }.map {
            ExchangeRateListItem(
                it.currencyCode,
                it.value * baseAmount,
                true
            )
        })
    }
}
