package com.ekezet.xrated.rates.parts.ratesList

import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.rates.R
import com.ekezet.xrated.rates.base.BaseListPresenter
import com.ekezet.xrated.rates.parts.ratesList.RatesListSpec.Interactor
import com.ekezet.xrated.rates.parts.ratesList.RatesListSpec.View
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class RatesListPresenter @Inject constructor() : BaseListPresenter<View, Interactor.Presenter, Interactor>(),
    View.Presenter, Interactor.Presenter {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        view.setEmptyText(t(R.string.rates__rates_list_empty))
    }
}
