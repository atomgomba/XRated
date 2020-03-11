package com.ekezet.xrated.prefs.parts.prefsScreen

import com.ekezet.xrated.base.arch.IFragmentPresenter
import com.ekezet.xrated.base.arch.IFragmentView
import com.ekezet.xrated.base.arch.IInteractor
import com.ekezet.xrated.base.arch.IRouter
import com.ekezet.xrated.base.arch.Part
import com.ekezet.xrated.base.parts.itemPicker.Pickable
import com.ekezet.xrated.base.views.Interrogator
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Interactor
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.View
import java.util.Locale

/**
 * @author kiri
 */
interface PrefsScreenSpec {
    interface View : IFragmentView, Interrogator {
        fun updateNumberFormat()
        fun updateOverrideLocale(currentLocale: Locale)

        interface Presenter : IFragmentPresenter<View> {
            fun onOverrideLocaleChanged(enabled: Boolean)
            fun provideNumberFormatSummary(): CharSequence
            fun onNumberFormatSelected(locale: Locale)
            fun onNumberFormatClicked()
        }
    }

    interface Interactor : IInteractor<Interactor.Presenter> {
        interface Presenter : IFragmentPresenter<View> {
            fun onNumberFormatChanged()
        }
    }

    interface Router : IRouter {
        fun startItemPicker(items: List<Pickable>, title: CharSequence, selected: Pickable?)
    }

    companion object {
        const val REQUEST_CODE_LANGUAGE_PICKER: Int = 10
    }
}

typealias PrefsScreenPart = Part<View, View.Presenter, Interactor.Presenter>
