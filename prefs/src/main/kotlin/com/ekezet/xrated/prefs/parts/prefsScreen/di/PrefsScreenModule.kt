package com.ekezet.xrated.prefs.parts.prefsScreen.di

import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.base.di.FragmentScope
import com.ekezet.xrated.base.parts.itemPicker.di.ItemPickerModule
import com.ekezet.xrated.prefs.parts.prefsScreen.views.PreferencesActivity
import com.ekezet.xrated.prefs.parts.prefsScreen.views.PreferencesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author kiri
 */
@Module(
    includes = [
        ItemPickerModule::class
    ]
)
abstract class PrefsScreenModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributePreferencesActivityInjector(): PreferencesActivity

    @FragmentScope
    @ContributesAndroidInjector(modules = [PrefsFragmentModule::class])
    abstract fun contributePreferencesFragmentInjector(): PreferencesFragment
}
