package com.ekezet.xrated.parts.home.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.ekezet.xrated.R
import com.ekezet.xrated.base.arch.AnyPart
import com.ekezet.xrated.base.views.BaseActivity
import com.ekezet.xrated.parts.home.HomePart
import com.ekezet.xrated.parts.home.HomeSpec.View
import com.ekezet.xrated.parts.home.parts.progressIndicator.ProgressIndicatorPart
import com.ekezet.xrated.viewmodels.NavigationItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : BaseActivity<HomePart, View, View.Presenter>(), View {
    @Inject internal lateinit var menuPagerAdapter: HomePagerAdapter

    override var pageIndex: Int
        get() = viewPager.currentItem
        set(value) {
            viewPager.setCurrentItem(value, true)
        }

    private lateinit var baseCurrencyMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }

    override fun setup(idRes: Int) {
        super.setup(idRes)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(R.string.home__activity_title)
        viewPager.adapter = menuPagerAdapter
        viewPager.registerOnPageChangeCallback(MyOnPageChangeCallback())
    }

    override fun mountBottomMenu(bottomMenu: AnyPart) {
        bottomMenu.bootWithContainer(bottomMenuContainer, lifecycle)
    }

    override fun mountBaseAmountEditor(baseAmountEditor: AnyPart) {
        baseAmountEditor.bootWithContainer(baseAmountEditorContainer, lifecycle)
    }

    override fun mountProgressIndicator(progressIndicator: ProgressIndicatorPart) {
        progressIndicator.bootWithContainer(progressIndicatorContainer, lifecycle)
    }

    override fun setNavigationItems(items: List<NavigationItem>) {
        viewPager.offscreenPageLimit = items.size
        menuPagerAdapter.setPages(items.map { it.part })
    }

    override fun showMessage(text: CharSequence) {
        Snackbar.make(viewPager, text, Snackbar.LENGTH_SHORT).show()
    }

    override fun showError(text: CharSequence) {
        Snackbar.make(viewPager, text, Snackbar.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_options, menu)
        baseCurrencyMenuItem = menu.findItem(R.id.menuBaseCurrency)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        presenter.onPrepareOptionsMenu()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuBaseCurrency -> {
                presenter.onBaseCurrencyClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setBaseCurrency(currencyCode: String) {
        if (::baseCurrencyMenuItem.isInitialized) {
            baseCurrencyMenuItem.title = currencyCode
        }
    }

    private inner class MyOnPageChangeCallback : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            presenter.onUserScrolledToPage(position)
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }
}
