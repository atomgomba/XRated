package com.ekezet.xrated.prefs.parts.prefsScreen.views

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference.SummaryProvider
import com.ekezet.xrated.base.views.BasePreferenceFragment
import com.ekezet.xrated.prefs.R.string
import com.ekezet.xrated.prefs.R.xml
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenPart
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.View

class PreferencesFragment() : BasePreferenceFragment<PrefsScreenPart, View, View.Presenter>(), View {
    override val fragmentTag = PreferencesFragment::class.qualifiedName ?: "PreferencesFragment"

    override var numberFormatLocale: String
        get() = numberFormatPreference?.value ?: ""
        set(value) {
            numberFormatPreference?.value = value
        }

    private var numberFormatPreference: ListPreference? = null

    override fun setup(idRes: Int) {
        // intentionally left blank
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        numberFormatPreference = preferenceScreen.findPreference<ListPreference?>(getString(string.prefs__pref__number_format_locale_key))
            ?.apply {
                summaryProvider = SummaryProvider<ListPreference> {
                    presenter.provideNumberFormatSummary()
                }
                setOnPreferenceChangeListener { _, newValue ->
                    presenter.onNumberFormatSelected(newValue as String)
                    return@setOnPreferenceChangeListener true
                }
            }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(xml.preferences, rootKey)
    }
}
