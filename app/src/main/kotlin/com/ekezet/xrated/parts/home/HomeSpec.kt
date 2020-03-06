package com.ekezet.xrated.parts.home

import androidx.annotation.IdRes
import com.ekezet.xrated.base.arch.AnyPart
import com.ekezet.xrated.base.arch.IInteractor
import com.ekezet.xrated.base.arch.IPresenter
import com.ekezet.xrated.base.arch.IView
import com.ekezet.xrated.base.arch.Part
import com.ekezet.xrated.parts.home.HomeSpec.Interactor
import com.ekezet.xrated.parts.home.HomeSpec.View
import com.ekezet.xrated.parts.home.parts.progressIndicator.ProgressIndicatorPart
import com.ekezet.xrated.viewmodels.NavigationItem

/**
 * @author kiri
 */
interface HomeSpec {
    interface View : IView {
        var pageIndex: Int

        fun mountBottomMenu(bottomMenu: AnyPart)
        fun mountBaseAmountEditor(baseAmountEditor: AnyPart)
        fun mountProgressIndicator(progressIndicator: ProgressIndicatorPart)
        fun setNavigationItems(items: List<NavigationItem>)
        fun setBaseCurrency(currencyCode: String)
        fun showMessage(text: CharSequence)
        fun showError(text: CharSequence)

        interface Presenter : IPresenter<View> {
            fun onPrepareOptionsMenu()
            fun onBaseCurrencyClicked()
            fun onUserScrolledToPage(index: Int)
        }
    }

    interface Interactor : IInteractor<Interactor.Presenter> {
        fun fetchBaseCurrency()
        fun hasFavorites(): Boolean

        interface Presenter : IPresenter<View> {
            fun onLoadingError(throwable: Throwable)
            fun onBaseCurrencyChanged(baseCurrency: String)
            fun onBaseAmountChanged()
            fun onNavigationItemSelected(@IdRes menuId: Int)
        }
    }
}

typealias HomePart = Part<View, View.Presenter, Interactor.Presenter>
