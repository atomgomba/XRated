package com.ekezet.xrated.prefs.parts.prefsScreen

import com.ekezet.xrated.base.BasePresenter
import com.ekezet.xrated.base.di.FragmentScope
import com.ekezet.xrated.base.utils.PrefsManager
import com.ekezet.xrated.prefs.R
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Interactor
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Router
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.View
import java.util.Locale
import javax.inject.Inject

/**
 * @author kiri
 */
@FragmentScope
class PrefsScreenPresenter @Inject constructor(
    private val prefsManager: PrefsManager
) : BasePresenter<View, Interactor, Router>(), View.Presenter, Interactor.Presenter {
    override fun onViewCreated() {
        onNumberFormatChanged()
    }

    override fun onNumberFormatSelected(language: String) {
        prefsManager.numFormatLocaleCode = language
    }

    override fun onNumberFormatChanged() {
        view.numberFormatLocale = prefsManager.numFormatLocaleCode
    }

    override fun provideNumberFormatSummary(): CharSequence {
        val locale = prefsManager.numFormatLocale
        val default = Locale.getDefault()
        return if (locale == default) {
            t(R.string.prefs__pref__number_format_locale_default, default.displayCountry)
        } else {
            locale.displayCountry
        }
    }
}
