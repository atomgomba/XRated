package com.ekezet.xrated.prefs.parts.prefsScreen.views

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import com.ekezet.xrated.base.views.BaseActivity
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenPart
import com.ekezet.xrated.prefs.parts.prefsScreen.PrefsScreenSpec.View

/**
 * @author kiri
 */
class PreferencesActivity : BaseActivity<PrefsScreenPart, View, View.Presenter>(), View {
    override var localeName: CharSequence
        get() = TODO("not implemented")
        set(value) {}

    override fun setup(idRes: Int) {
        super.setup(idRes)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, PreferencesActivity::class.java)
    }
}
