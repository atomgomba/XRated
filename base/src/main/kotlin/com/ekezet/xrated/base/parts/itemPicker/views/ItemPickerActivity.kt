package com.ekezet.xrated.base.parts.itemPicker.views

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import androidx.core.widget.doAfterTextChanged
import com.ekezet.xrated.base.R
import com.ekezet.xrated.base.parts.itemPicker.ItemPickerPart
import com.ekezet.xrated.base.parts.itemPicker.ItemPickerSpec.View
import com.ekezet.xrated.base.parts.itemPicker.Pickable
import com.ekezet.xrated.base.views.BaseActivity
import kotlinx.android.synthetic.main.activity_item_picker.*
import javax.inject.Inject

/**
 * @author kiri
 */
class ItemPickerActivity : BaseActivity<ItemPickerPart, View, View.Presenter>(), View {
    @Inject internal lateinit var adapter: ItemPickerAdapter

    override val items: ArrayList<Pickable>
        get() = intent.getParcelableArrayListExtra(EXTRA_ITEMS)!!

    override val selected: Pickable?
        get() = intent.getParcelableExtra(EXTRA_SELECTED)

    override var inputText: String
        get() = searchText.text.toString().trim()
        set(value) {
            searchText.setText(value)
        }

    override fun setup(idRes: Int) {
        super.setup(idRes)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = intent.getCharSequenceExtra(EXTRA_TITLE) ?: getString(R.string.item_picker__activity_title)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        searchText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == IME_ACTION_SEARCH) {
                presenter.onSearchInitiated(inputText)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        searchText.doAfterTextChanged {
            presenter.onSearchInitiated(it.toString())
        }
    }

    override fun setPickableItems(items: List<Pickable>) {
        adapter.setItems(items)
    }

    override fun showFiltered(query: CharSequence) {
        adapter.filter.filter(query)
    }

    override fun finishWithResult(item: Pickable?) {
        setResult(RESULT_OK, Intent().apply {
            putExtra(EXTRA_RESULT, item)
        })
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val EXTRA_RESULT = "${ItemPickerActivity::class.simpleName}.result"

        private val EXTRA_ITEMS = "${ItemPickerActivity::class.simpleName}.items"
        private val EXTRA_TITLE = "${ItemPickerActivity::class.simpleName}.title"
        private val EXTRA_SELECTED = "${ItemPickerActivity::class.simpleName}.selected"

        fun newIntent(
            context: Context,
            items: ArrayList<Pickable>,
            title: CharSequence? = null,
            selected: Pickable? = null
        ) =
            Intent(context, ItemPickerActivity::class.java).apply {
                putParcelableArrayListExtra(EXTRA_ITEMS, items)
                putExtra(EXTRA_TITLE, title)
                putExtra(EXTRA_SELECTED, selected)
            }
    }
}
