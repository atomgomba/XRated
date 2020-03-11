package com.ekezet.xrated.di

import android.content.Context
import com.ekezet.xrated.App
import com.ekezet.xrated.base.di.APP
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * @author kiri
 */
@Module
object AppModule {
    @Provides @Named(APP) fun provideAppContext(app: App): Context = app.applicationContext
}
