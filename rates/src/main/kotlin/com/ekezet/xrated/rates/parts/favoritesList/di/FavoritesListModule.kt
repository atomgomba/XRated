package com.ekezet.xrated.rates.parts.favoritesList.di

import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.rates.base.items.ExchangeRateListItemSpec
import com.ekezet.xrated.rates.base.items.views.ExchangeRatesAdapter
import com.ekezet.xrated.rates.di.FAVORITES
import com.ekezet.xrated.rates.parts.favoritesList.FavoritesListInteractor
import com.ekezet.xrated.rates.parts.favoritesList.FavoritesListPart
import com.ekezet.xrated.rates.parts.favoritesList.FavoritesListPresenter
import com.ekezet.xrated.rates.parts.favoritesList.FavoritesListSpec.Interactor
import com.ekezet.xrated.rates.parts.favoritesList.FavoritesListSpec.View
import com.ekezet.xrated.rates.parts.favoritesList.di.FavoritesListModule.Binder
import com.ekezet.xrated.rates.parts.favoritesList.views.FavoritesListView
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
class FavoritesListModule {
    @Module
    abstract class Binder {
        @Binds @ActivityScope abstract fun bindView(view: FavoritesListView): View
        @Binds @ActivityScope abstract fun bindInteractor(interactor: FavoritesListInteractor): Interactor
        @Binds @ActivityScope abstract fun bindPresenter(presenter: FavoritesListPresenter): View.Presenter
        @Binds @Named(FAVORITES) @ActivityScope
        abstract fun bindListItemPresenter(presenter: FavoritesListPresenter): ExchangeRateListItemSpec.View.Presenter
    }

    @Provides @ActivityScope fun providePart(
        view: View,
        interactor: Interactor,
        presenter: View.Presenter
    ) =
        FavoritesListPart(view, interactor, presenter, null)

    @Provides @Named(FAVORITES) @ActivityScope
    fun provideListAdapter(@Named(FAVORITES) presenter: ExchangeRateListItemSpec.View.Presenter) =
        ExchangeRatesAdapter(presenter)
}
