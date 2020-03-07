package com.ekezet.xrated.prefs.parts.prefsScreen.di

import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.prefs.parts.prefsScreen.views.PreferencesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author kiri
 */
@Module
abstract class PrefsScreenModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [PrefsScreenActivityModule::class])
    abstract fun contributePreferencesActivityInjector(): PreferencesActivity
}
