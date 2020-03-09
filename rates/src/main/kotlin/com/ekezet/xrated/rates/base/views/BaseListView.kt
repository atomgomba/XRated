package com.ekezet.xrated.rates.base.views

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import androidx.core.view.isInvisible
import com.ekezet.xrated.base.views.BaseView
import com.ekezet.xrated.rates.R
import com.ekezet.xrated.rates.base.BaseListSpec.View
import com.ekezet.xrated.rates.base.items.viewmodels.ExchangeRateListItem
import com.ekezet.xrated.rates.base.items.views.ExchangeRatesAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.view_rates_list.view.*

/**
 * @author kiri
 */
@SuppressLint("ViewConstructor")
open class BaseListView constructor(
    context: Context,
    private val adapter: ExchangeRatesAdapter,
    private val presenter: View.Presenter<out View>
) : BaseView(context), View {
    private val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

    override fun setup(idRes: Int) {
        super.setup(idRes)
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = null
        recyclerView.adapter = adapter
        swipeRefresh.setOnRefreshListener {
            presenter.onSwipeRefresh()
        }
    }

    override fun setEmptyText(text: CharSequence) {
        emptyText.text = text
    }

    override fun showItems(items: List<ExchangeRateListItem>) {
        adapter.setItems(items)
    }

    override fun copyToClipboard(label: CharSequence, text: CharSequence) {
        clipboardManager.setPrimaryClip(ClipData.newPlainText(label, text))
        Snackbar.make(recyclerView, context.getString(R.string.rates__copied_to_clipboard, text), Snackbar.LENGTH_SHORT).show()
    }

    override fun showList() {
        swipeRefresh.isInvisible = false
        emptyText.isInvisible = true
    }

    override fun hideList() {
        swipeRefresh.isInvisible = true
        emptyText.isInvisible = false
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun forceRefreshList() {
        adapter.notifyDataSetChanged()
    }
}
