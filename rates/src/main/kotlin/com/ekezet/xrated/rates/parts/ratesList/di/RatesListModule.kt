package com.ekezet.xrated.rates.parts.ratesList.di

import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.rates.base.items.ExchangeRateListItemSpec
import com.ekezet.xrated.rates.base.items.views.ExchangeRatesAdapter
import com.ekezet.xrated.rates.di.RATES
import com.ekezet.xrated.rates.parts.ratesList.RatesListInteractor
import com.ekezet.xrated.rates.parts.ratesList.RatesListPart
import com.ekezet.xrated.rates.parts.ratesList.RatesListPresenter
import com.ekezet.xrated.rates.parts.ratesList.RatesListSpec.Interactor
import com.ekezet.xrated.rates.parts.ratesList.RatesListSpec.View
import com.ekezet.xrated.rates.parts.ratesList.di.RatesListModule.Binder
import com.ekezet.xrated.rates.parts.ratesList.views.RatesListView
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * @author kiri
 */
@Module(
    includes = [
        Binder::class
    ]
)
object RatesListModule {
    @Module
    abstract class Binder {
        @Binds @ActivityScope abstract fun bindView(view: RatesListView): View
        @Binds @ActivityScope abstract fun bindInteractor(interactor: RatesListInteractor): Interactor
        @Binds @ActivityScope abstract fun bindPresenter(presenter: RatesListPresenter): View.Presenter
        @Binds @Named(RATES) @ActivityScope abstract fun bindListItemPresenter(presenter: RatesListPresenter): ExchangeRateListItemSpec.View.Presenter
    }

    @Provides @ActivityScope fun providePart(
        view: View,
        interactor: Interactor,
        presenter: View.Presenter
    ) =
        RatesListPart(view, interactor, presenter, null)

    @Provides @Named(RATES) @ActivityScope
    fun provideListAdapter(@Named(RATES) presenter: ExchangeRateListItemSpec.View.Presenter) =
        ExchangeRatesAdapter(presenter)
}
