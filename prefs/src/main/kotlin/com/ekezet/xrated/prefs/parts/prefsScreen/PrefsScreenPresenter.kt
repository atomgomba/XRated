package com.ekezet.xrated.prefs.parts.prefsScreen

import com.ekezet.xrated.base.BasePresenter
import com.ekezet.xrated.base.di.FragmentScope
import com.ekezet.xrated.base.utils.PrefsManager
import com.ekezet.xrated.prefs.R
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.*
import com.ekezet.xrated.prefs.parts.prefsScreen.data.Language
import java.util.*
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
        onNumberFormatChanged()
    }

    override fun onNumberFormatClicked() {
        router!!.startItemPicker(languages, t(R.string.prefs__pref__number_format__picker_title))
    }

    override fun onNumberFormatSelected(language: String) {
        prefsManager.numFormatLanguageCode = language
    }

    override fun onNumberFormatChanged() {
        view.updateNumberFormat(prefsManager.numFormatLanguageCode)
    }

    override fun provideNumberFormatSummary(): CharSequence {
        val locale = prefsManager.numFormatLocale
        val default = Locale.getDefault()
        return if (locale == default) {
            t(R.string.prefs__pref__number_format_default, default.displayLanguage)
        } else {
            locale.displayLanguage
        }
    }
}
