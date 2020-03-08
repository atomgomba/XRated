package com.ekezet.xrated.prefs.parts.prefsScreen.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.ekezet.xrated.prefs.R
import dagger.android.support.DaggerAppCompatActivity

/**
 * @author kiri
 */
class PreferencesActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settingsFragmentContainer, PreferencesFragment::class.java, null)
            .commit()
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
