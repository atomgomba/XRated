package com.ekezet.xrated.prefs.parts.prefsScreen.views

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.Preference.SummaryProvider
import com.ekezet.xrated.base.parts.itemPicker.views.ItemPickerActivity.Companion.EXTRA_RESULT
import com.ekezet.xrated.base.views.BasePreferenceFragment
import com.ekezet.xrated.prefs.R
import com.ekezet.xrated.prefs.R.string
import com.ekezet.xrated.prefs.R.xml
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenPart
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.Companion.REQUEST_CODE_LANGUAGE_PICKER
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.View
import com.ekezet.xrated.prefs.parts.prefsScreen.data.Language

class PreferencesFragment() : BasePreferenceFragment<PrefsScreenPart, View, View.Presenter>(), View {
    override val fragmentTag = PreferencesFragment::class.qualifiedName ?: "PreferencesFragment"

    private var numberFormatPreference: Preference? = null
    private val numberFormatSummaryProvider = SummaryProvider<Preference> {
        presenter.provideNumberFormatSummary()
    }

    override fun setup(idRes: Int) {
        // intentionally left blank
    }

    override fun updateNumberFormat(language: String) {
        numberFormatPreference?.summaryProvider = numberFormatSummaryProvider
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        numberFormatPreference =
            preferenceScreen.findPreference<Preference?>(getString(R.string.prefs__pref__number_format_key))
                ?.apply {
                    summaryProvider = numberFormatSummaryProvider
                    setOnPreferenceClickListener {
                        presenter.onNumberFormatClicked()
                        return@setOnPreferenceClickListener true
                    }
                }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(xml.preferences, rootKey)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_LANGUAGE_PICKER && resultCode == RESULT_OK) {
            val newValue: Language = data?.getParcelableExtra(EXTRA_RESULT) ?: return
            presenter.onNumberFormatSelected(newValue.isoCode2)
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
