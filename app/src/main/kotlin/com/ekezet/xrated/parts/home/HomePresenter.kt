package com.ekezet.xrated.parts.home

import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.R
import com.ekezet.xrated.base.BasePresenter
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.di.MENU
import com.ekezet.xrated.parts.home.HomeSpec.Interactor
import com.ekezet.xrated.parts.home.HomeSpec.View
import com.ekezet.xrated.parts.home.parts.baseAmountEditor.BaseAmountEditorPart
import com.ekezet.xrated.parts.home.parts.bottomMenu.BottomMenuPart
import com.ekezet.xrated.parts.home.parts.bottomMenu.BottomMenuSpec
import com.ekezet.xrated.parts.home.parts.progressIndicator.ProgressIndicatorPart
import com.ekezet.xrated.viewmodels.NavigationItem
import javax.inject.Inject
import javax.inject.Named

/**
 * @author kiri
 */
@ActivityScope
class HomePresenter @Inject constructor(
    private val bottomMenu: BottomMenuPart,
    private val baseAmountEditor: BaseAmountEditorPart,
    @Named(MENU) private val menuItems: List<NavigationItem>,
    private val bottomMenuView: BottomMenuSpec.View,
    private val progressIndicator: ProgressIndicatorPart
) : BasePresenter<View, Interactor, Nothing>(),
    View.Presenter, Interactor.Presenter {
    private val pageIndexMap: MutableMap<Int, Int> = HashMap()
    private val pageIdMap: MutableMap<Int, Int> = HashMap()

    init {
        menuItems.forEachIndexed { index, navigationItem ->
            pageIndexMap[navigationItem.menuId] = index
            pageIdMap[index] = navigationItem.menuId
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        with(view) {
            setup(R.layout.activity_home)
            mountBottomMenu(bottomMenu)
            mountBaseAmountEditor(baseAmountEditor)
            mountProgressIndicator(progressIndicator)
            setNavigationItems(menuItems)
        }
    }

    override fun onLoadingError(throwable: Throwable) {
        view.showError(throwable.message ?: t(R.string.common__unknown_error))
    }

    override fun onBaseCurrencyChanged(baseCurrency: String) {
        view.setBaseCurrency(baseCurrency)
    }

    override fun onPrepareOptionsMenu() {
        interactor!!.fetchBaseCurrency()
    }

    override fun onBaseCurrencyClicked() {
        view.showMessage(t(R.string.home__toolbar__select_base_currency_hint))
    }

    override fun onBaseAmountChanged() {
        if (view.pageIndex == pageIndexMap[R.id.navInfo]) {
            if (interactor!!.hasFavorites()) {
                view.pageIndex = pageIndexMap[R.id.navFavorites] ?: 1
            } else {
                view.pageIndex = pageIndexMap[R.id.navDailyRates] ?: 1
            }
        }
    }

    override fun onNavigationItemSelected(menuId: Int) {
        view.pageIndex = pageIndexMap[menuId] ?: 0
    }

    override fun onUserScrolledToPage(index: Int) {
        bottomMenuView.activateMenuItem(pageIdMap[index] ?: 0)
    }
}
