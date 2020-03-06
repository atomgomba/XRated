package com.ekezet.xrated.di

import com.ekezet.xrated.App
import com.ekezet.xrated.base.di.BaseModule
import com.ekezet.xrated.parts.home.di.HomeModule
import com.ekezet.xrated.rates.di.RatesModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author kiri
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        BaseModule::class,
        RatesModule::class,
        HomeModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<App>
}
