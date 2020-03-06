package com.ekezet.xrated.parts.home.parts.baseAmountEditor

import com.ekezet.xrated.base.arch.IInteractor
import com.ekezet.xrated.base.arch.IPresenter
import com.ekezet.xrated.base.arch.IView
import com.ekezet.xrated.base.arch.Part
import com.ekezet.xrated.parts.home.parts.baseAmountEditor.BaseAmountEditorSpec.Interactor
import com.ekezet.xrated.parts.home.parts.baseAmountEditor.BaseAmountEditorSpec.View

/**
 * @author kiri
 */
interface BaseAmountEditorSpec {
    interface View : IView {
        var baseAmount: CharSequence
        var baseCurrency: CharSequence

        interface Presenter : IPresenter<View> {
            fun onUserChangedBaseAmount()
        }

        fun clearBaseAmountEditFocus()
    }

    interface Interactor : IInteractor<Interactor.Presenter> {
        fun changeBaseAmount(amount: Float)

        interface Presenter : IPresenter<View> {
            fun onBaseAmountLoaded(amount: Float)
            fun onBaseCurrencyChanged(baseCurrency: CharSequence)
        }
    }
}

typealias BaseAmountEditorPart = Part<View, View.Presenter, Interactor.Presenter>
