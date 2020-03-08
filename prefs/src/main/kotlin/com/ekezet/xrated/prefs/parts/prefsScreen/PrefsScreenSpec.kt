package com.ekezet.xrated.prefs.parts.prefsScreen

import com.ekezet.xrated.base.arch.IFragmentPresenter
import com.ekezet.xrated.base.arch.IFragmentView
import com.ekezet.xrated.base.arch.IInteractor
import com.ekezet.xrated.base.arch.IRouter
import com.ekezet.xrated.base.arch.Part
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Interactor
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.View

/**
 * @author kiri
 */
interface PrefsScreenSpec {
    interface View : IFragmentView {
        var numberFormatLocale: String

        interface Presenter : IFragmentPresenter<View> {
            fun provideNumberFormatSummary(): CharSequence
            fun onNumberFormatSelected(language: String)
        }
    }

    interface Interactor : IInteractor<Interactor.Presenter> {
        interface Presenter : IFragmentPresenter<View> {
            fun onNumberFormatChanged()
        }
    }

    interface Router : IRouter {
        fun startItemPicker()
    }
}

typealias PrefsScreenPart = Part<View, View.Presenter, Interactor.Presenter>
