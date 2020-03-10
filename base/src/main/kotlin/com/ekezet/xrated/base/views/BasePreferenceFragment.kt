package com.ekezet.xrated.base.views

import android.os.Bundle
import android.view.View
import com.ekezet.xrated.base.arch.IFragmentPresenter
import com.ekezet.xrated.base.arch.IFragmentView
import com.ekezet.xrated.base.arch.IPresenter
import com.ekezet.xrated.base.arch.IView
import com.ekezet.xrated.base.arch.Part
import javax.inject.Inject

/**
 * @author kiri
 */
abstract class BasePreferenceFragment<A : Part<V, VP, *>, V : IView, VP : IPresenter<V>> : DaggerPreferenceFragmentCompat(), IFragmentView {
    @Inject internal lateinit var part: A

    protected lateinit var presenter: VP
        private set

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val presenter = presenter
        if (presenter is IFragmentPresenter<*>) {
            presenter.onViewCreated()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = part.presenter
        @Suppress("UNCHECKED_CAST")
        part.bootWithView(this as V, lifecycle)
    }

    override fun getAndroidContext() = requireContext()
}
