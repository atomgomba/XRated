package com.ekezet.xrated.base.parts.itemPicker.di

import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.base.parts.itemPicker.views.ItemPickerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author kiri
 */
@Module
abstract class ItemPickerModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [ItemPickerActivityModule::class])
    abstract fun contributeItemPickerActivityInjector(): ItemPickerActivity
}
