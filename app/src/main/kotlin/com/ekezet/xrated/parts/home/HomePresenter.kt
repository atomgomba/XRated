package com.ekezet.xrated.parts.home

import androidx.annotation.IdRes
import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.R
import com.ekezet.xrated.base.BasePresenter
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.di.MENU
import com.ekezet.xrated.parts.home.HomeSpec.Interactor
import com.ekezet.xrated.parts.home.HomeSpec.Router
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
) : BasePresenter<View, Interactor, Router>(),
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
        leaveInfoPage()
    }

    override fun onPreferencesClicked() {
        router!!.startPreferencesActivity()
    }

    override fun onBaseAmountChanged() {
        leaveInfoPage()
    }

    override fun onNavigationItemSelected(menuId: Int) {
        view.pageIndex = pageIndexMap[menuId] ?: 0
        onPageIdSelected(menuId)
    }

    override fun onUserScrolledToPage(index: Int) {
        val pageId = pageIdMap[index] ?: 0
        bottomMenuView.activateMenuItem(pageId)
    }

    private fun leaveInfoPage() {
        if (view.pageIndex != pageIndexMap[R.id.navInfo]) {
            return
        }
        view.pageIndex = if (interactor!!.hasFavorites()) {
            pageIndexMap[R.id.navFavorites] ?: 1
        } else {
            pageIndexMap[R.id.navDailyRates] ?: 1
        }
    }

    private fun onPageIdSelected(@IdRes pageId: Int) {
        if (pageId == R.id.navInfo) {
            view.expandToolbar()
        }
    }
}
