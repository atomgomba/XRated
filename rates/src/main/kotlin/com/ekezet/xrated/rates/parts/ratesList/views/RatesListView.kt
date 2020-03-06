package com.ekezet.xrated.rates.parts.ratesList.views

import android.annotation.SuppressLint
import android.content.Context
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.rates.base.items.views.ExchangeRatesAdapter
import com.ekezet.xrated.rates.base.views.BaseListView
import com.ekezet.xrated.rates.di.RATES
import com.ekezet.xrated.rates.parts.ratesList.RatesListSpec.View
import javax.inject.Inject
import javax.inject.Named

/**
 * @author kiri
 */
@SuppressLint("ViewConstructor")
@ActivityScope
class RatesListView @Inject constructor(
    context: Context,
    @Named(RATES) adapter: ExchangeRatesAdapter,
    presenter: View.Presenter
) : BaseListView(context, adapter, presenter), View
