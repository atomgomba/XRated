package com.ekezet.xrated.rates.parts.favoritesList.views

import android.annotation.SuppressLint
import android.content.Context
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.rates.base.items.views.ExchangeRatesAdapter
import com.ekezet.xrated.rates.base.views.BaseListView
import com.ekezet.xrated.rates.di.FAVORITES
import com.ekezet.xrated.rates.parts.favoritesList.FavoritesListSpec.View
import javax.inject.Inject
import javax.inject.Named

/**
 * @author kiri
 */
@SuppressLint("ViewConstructor")
@ActivityScope
class FavoritesListView @Inject constructor(
    context: Context,
    @Named(FAVORITES) adapter: ExchangeRatesAdapter,
    presenter: View.Presenter
) : BaseListView(context, adapter, presenter), View
