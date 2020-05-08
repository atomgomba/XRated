package com.ekezet.xrated

import com.crashlytics.android.Crashlytics
import com.ekezet.xrated.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author kiri
 */
class App : DaggerApplication() {
    private val appInjector = DaggerAppComponent.factory()
        .create(this)

    override fun onCreate() {
        super.onCreate()

        @Suppress("ConstantConditionIf")
        Timber.plant(
            if (BuildConfig.DEBUG) {
                MainDebugTree()
            } else {
                ProductionTree()
            }
        )
    }

    override fun applicationInjector(): AndroidInjector<App> = appInjector

    private inner class MainDebugTree : Timber.DebugTree(), CoroutineScope {
        override val coroutineContext = Dispatchers.Main

        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            launch {
                super.log(priority, tag, message, t)
            }
        }

        override fun createStackElementTag(element: StackTraceElement) = with(element) {
            "($fileName:$lineNumber)"
        }
    }

    private inner class ProductionTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            Crashlytics.log("[$priority] $tag: $message")
            t?.let {
                Crashlytics.logException(t)
            }
        }
    }
}
