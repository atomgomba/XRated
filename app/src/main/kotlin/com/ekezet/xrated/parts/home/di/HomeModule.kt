package com.ekezet.xrated.parts.home.di

import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.parts.home.views.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author kiri
 */
@Module
abstract class HomeModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun contributeMainActivityInjector(): HomeActivity
}
