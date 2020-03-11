package com.ekezet.xrated.prefs.parts.prefsScreen

import com.ekezet.xrated.base.BasePresenter
import com.ekezet.xrated.base.di.FragmentScope
import com.ekezet.xrated.base.utils.PrefsManager
import com.ekezet.xrated.prefs.R
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Interactor
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Router
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.View
import com.ekezet.xrated.prefs.parts.prefsScreen.data.Language
import java.util.Locale
import javax.inject.Inject

/**
 * @author kiri
 */
@FragmentScope
class PrefsScreenPresenter @Inject constructor(
    private val prefsManager: PrefsManager
) : BasePresenter<View, Interactor, Router>(), View.Presenter, Interactor.Presenter {
    private val languages = Language.list()

    override fun onViewCreated() {
        view.updateNumberFormat()
        view.updateOverrideLocale(prefsManager.numFormatLocale as Locale)
    }

    override fun onNumberFormatClicked() {
        val selected = languages.firstOrNull { it.locale == prefsManager.numFormatLocale }
        router!!.startItemPicker(languages, t(R.string.prefs__pref__number_format__picker_title), selected)
    }

    override fun onNumberFormatSelected(locale: Locale) {
        prefsManager.numFormatLocale = locale
    }

    override fun onNumberFormatChanged() {
        view.updateNumberFormat()
    }

    override fun provideNumberFormatSummary(): CharSequence {
        val locale = prefsManager.numFormatLocale as Locale
        return if (locale.displayCountry.isBlank()) {
            locale.displayLanguage
        } else {
            "%s (%s)".format(locale.displayLanguage, locale.displayCountry)
        }
    }

    override fun onOverrideLocaleChanged(enabled: Boolean) {
        if (!enabled) {
            prefsManager.numFormatLocale = null
        }
    }
}
