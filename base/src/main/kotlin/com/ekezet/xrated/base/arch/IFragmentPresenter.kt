package com.ekezet.xrated.base.arch

/**
 * @author kiri
 */
interface IFragmentPresenter<V : IView> : IPresenter<V> {
    fun onViewCreated() {}
}
