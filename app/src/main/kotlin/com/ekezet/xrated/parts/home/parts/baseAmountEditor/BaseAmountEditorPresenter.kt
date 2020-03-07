package com.ekezet.xrated.parts.home.parts.baseAmountEditor

import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.R
import com.ekezet.xrated.base.BasePresenter
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.base.utils.PrefsManager
import com.ekezet.xrated.parts.home.parts.baseAmountEditor.BaseAmountEditorSpec.Interactor
import com.ekezet.xrated.parts.home.parts.baseAmountEditor.BaseAmountEditorSpec.View
import java.text.NumberFormat
import javax.inject.Inject
import kotlin.math.absoluteValue

/**
 * @author kiri
 */
@ActivityScope
class BaseAmountEditorPresenter @Inject constructor(
    private val prefsManager: PrefsManager
) : BasePresenter<View, Interactor, Nothing>(), View.Presenter,
    Interactor.Presenter {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        view.setup(R.layout.view_base_amount_editor)
    }

    override fun onUserChangedBaseAmount() {
        val amount = try {
            view.baseAmount.toString().trim().toFloat().absoluteValue
        } catch (e: NumberFormatException) {
            1F
        }
        interactor!!.changeBaseAmount(amount)
        view.clearBaseAmountEditFocus()
    }

    override fun onBaseAmountLoaded(amount: Float) {
        view.baseAmount = amount.format()
    }

    private fun Float.format(): CharSequence =
        NumberFormat.getNumberInstance(prefsManager.numFormatLocale).format(this)

    override fun onBaseCurrencyChanged(baseCurrency: CharSequence) {
        view.baseCurrency = baseCurrency
    }
}
