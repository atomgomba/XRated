package com.ekezet.xrated.base.arch

import android.content.Context
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

/**
 * @author kiri
 */
open class Part<V : IView, VP : IPresenter<V>, IP : IPresenter<V>>(
    internal var view: V?,
    internal var interactor: IInteractor<IP>? = null,
    var presenter: VP,
    internal var router: IRouter? = null
) {
    private fun boot(lifecycle: Lifecycle? = null, context: Context? = null) {
        val bootView = view
            ?: throw IllegalArgumentException("Cannot boot without a view. Please use bootWithView() method.")
        presenter.onBoot(this)
        interactor?.onBoot(presenter as IP)
        router?.onBoot(context ?: bootView.getAndroidContext())
        lifecycle?.addObserver(presenter)
        if (interactor != null) {
            lifecycle?.addObserver(interactor as LifecycleObserver)
        }
    }

    fun bootWithView(view: V, lifecycle: Lifecycle? = null) {
        this.view = view
        boot(lifecycle)
    }

    fun bootWithContainer(container: ViewGroup, lifecycle: Lifecycle? = null) {
        container.addView(view as android.view.View)
        boot(lifecycle)
    }
}
