package com.ekezet.xrated.prefs.parts.prefsScreen.views

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.Preference.SummaryProvider
import androidx.preference.SwitchPreference
import com.ekezet.xrated.base.parts.itemPicker.views.ItemPickerActivity.Companion.EXTRA_RESULT
import com.ekezet.xrated.base.views.BasePreferenceFragment
import com.ekezet.xrated.prefs.R
import com.ekezet.xrated.prefs.R.xml
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenPart
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Companion.REQUEST_CODE_LANGUAGE_PICKER
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.View
import com.ekezet.xrated.prefs.parts.prefsScreen.data.Language
import java.util.Locale

class PreferencesFragment() : BasePreferenceFragment<PrefsScreenPart, View, View.Presenter>(), View {
    override val fragmentTag = PreferencesFragment::class.qualifiedName ?: "PreferencesFragment"

    private var overrideLocalePreference: SwitchPreference? = null

    private var numberFormatPreference: Preference? = null
    private val numberFormatSummaryProvider = SummaryProvider<Preference> {
        presenter.provideNumberFormatSummary()
    }

    override fun setup(idRes: Int) {
        // intentionally left blank
    }

    override fun updateNumberFormat() {
        // hacky way to force updating the view
        numberFormatPreference?.summaryProvider = numberFormatSummaryProvider
    }

    override fun updateOverrideLocale(currentLocale: Locale) {
        overrideLocalePreference?.callChangeListener(currentLocale != Locale.getDefault())
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        overrideLocalePreference =
            preferenceScreen.findPreference<SwitchPreference?>(getString(R.string.prefs__pref__override_locale_key))?.apply {
                setSummaryOn(R.string.prefs__pref__override_locale_summary_on)
                setSummaryOff(R.string.prefs__pref__override_locale_summary_off)
                setOnPreferenceChangeListener { pref, newValue ->
                    presenter.onOverrideLocaleChanged(newValue as Boolean)
                    (pref as SwitchPreference).isChecked = newValue
                    return@setOnPreferenceChangeListener true
                }
            }

        numberFormatPreference =
            preferenceScreen.findPreference<Preference?>(getString(R.string.prefs__pref__number_format_key))
                ?.apply {
                    summaryProvider = numberFormatSummaryProvider
                    setOnPreferenceClickListener {
                        presenter.onNumberFormatClicked()
                        return@setOnPreferenceClickListener true
                    }
                }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(xml.preferences, rootKey)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_LANGUAGE_PICKER && resultCode == RESULT_OK) {
            val newValue: Language = data?.getParcelableExtra(EXTRA_RESULT) ?: return
            presenter.onNumberFormatSelected(newValue.locale)
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
