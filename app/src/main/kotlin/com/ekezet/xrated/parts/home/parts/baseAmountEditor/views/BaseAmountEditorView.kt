package com.ekezet.xrated.parts.home.parts.baseAmountEditor.views

import android.annotation.SuppressLint
import android.content.Context
import android.view.inputmethod.EditorInfo
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.base.views.BaseView
import com.ekezet.xrated.parts.home.parts.baseAmountEditor.BaseAmountEditorSpec.View
import kotlinx.android.synthetic.main.view_base_amount_editor.view.*
import javax.inject.Inject

/**
 * @author kiri
 */
@SuppressLint("ViewConstructor")
@ActivityScope
class BaseAmountEditorView @Inject constructor(
    context: Context,
    private val presenter: View.Presenter
) : BaseView(context), View {
    override var baseAmount: CharSequence
        get() = baseAmountEdit.text.toString()
        set(value) {
            baseAmountEdit.setText(value)
        }

    override var baseCurrency: CharSequence
        get() = baseCurrencyText.text
        set(value) {
            baseCurrencyText.text = value
        }

    override fun clearBaseAmountEditFocus() {
        baseAmountEdit.clearFocus()
    }

    override fun setup(idRes: Int) {
        super.setup(idRes)
        setBaseAmountButton.setOnClickListener {
            presenter.onUserChangedBaseAmount()
        }
        baseAmountEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.onUserChangedBaseAmount()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}
