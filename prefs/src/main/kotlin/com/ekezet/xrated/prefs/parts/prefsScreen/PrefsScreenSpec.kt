package com.ekezet.xrated.prefs.parts.prefsScreen

import com.ekezet.xrated.base.arch.IInteractor
import com.ekezet.xrated.base.arch.IPresenter
import com.ekezet.xrated.base.arch.IRouter
import com.ekezet.xrated.base.arch.IView
import com.ekezet.xrated.base.arch.Part
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Interactor
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.View

/**
 * @author kiri
 */
interface PrefsScreenSpec {
    interface View : IView {
        var localeName: CharSequence

        interface Presenter : IPresenter<View> {
            fun onLocaleSelected(language: String)
        }
    }

    interface Interactor : IInteractor<Interactor.Presenter> {
        interface Presenter : IPresenter<View> {
            fun onLocaleChanged(language: String)
        }
    }

    interface Router : IRouter {
        fun startItemPicker()
    }
}

typealias PrefsScreenPart = Part<View, View.Presenter, Interactor.Presenter>
