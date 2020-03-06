package com.ekezet.xrated.base.views

import android.os.Bundle
import androidx.annotation.CallSuper
import com.ekezet.xrated.base.arch.IPresenter
import com.ekezet.xrated.base.arch.IView
import com.ekezet.xrated.base.arch.Part
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * @author kiri
 */
open class BaseActivity<A : Part<V, VP, *>, V : IView, VP : IPresenter<V>> : DaggerAppCompatActivity(), IView {
    @Inject internal lateinit var part: A

    protected lateinit var presenter: VP
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = part.presenter
        @Suppress("UNCHECKED_CAST")
        part.bootWithView(this as V, lifecycle)
    }

    @CallSuper override fun setup(idRes: Int) {
        setContentView(idRes)
    }

    override fun getAndroidContext() = this
}
