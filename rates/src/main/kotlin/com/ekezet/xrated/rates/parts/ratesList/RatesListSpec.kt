package com.ekezet.xrated.rates.parts.ratesList

import com.ekezet.xrated.base.arch.Part
import com.ekezet.xrated.rates.base.BaseListSpec
import com.ekezet.xrated.rates.parts.ratesList.RatesListSpec.Interactor
import com.ekezet.xrated.rates.parts.ratesList.RatesListSpec.View

/**
 * @author kiri
 */
interface RatesListSpec {
    interface View : BaseListSpec.View {
        interface Presenter : BaseListSpec.View.Presenter<View>
    }

    interface Interactor : BaseListSpec.Interactor<View, Interactor.Presenter> {
        interface Presenter : BaseListSpec.Interactor.Presenter<View>
    }
}

typealias RatesListPart = Part<View, View.Presenter, Interactor.Presenter>
