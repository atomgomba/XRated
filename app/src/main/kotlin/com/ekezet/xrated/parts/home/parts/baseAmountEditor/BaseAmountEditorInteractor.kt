package com.ekezet.xrated.parts.home.parts.baseAmountEditor

import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.base.BaseInteractor
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.base.utils.PrefsManager
import com.ekezet.xrated.base.utils.PrefsManager.Companion.PREF_BASE_AMOUNT
import com.ekezet.xrated.base.utils.PrefsManager.Companion.PREF_BASE_CURRENCY
import com.ekezet.xrated.base.utils.PrefsManager.Companion.PREF_NUM_FORMAT_LANGUAGE
import com.ekezet.xrated.parts.home.parts.baseAmountEditor.BaseAmountEditorSpec.Interactor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class BaseAmountEditorInteractor @Inject constructor(
    private val prefsManager: PrefsManager
) : BaseInteractor<Interactor.Presenter>(), Interactor {
    override val coroutineContext = Dispatchers.Main

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        launch {
            presenter.onBaseAmountLoaded(prefsManager.baseAmount)
            presenter.onBaseCurrencyChanged(prefsManager.baseCurrency)
            prefsManager.changesChannel.consumeEach {
                if (it in listOf(PREF_BASE_AMOUNT, PREF_NUM_FORMAT_LANGUAGE)) {
                    presenter.onBaseAmountLoaded(prefsManager.baseAmount)
                } else if (it == PREF_BASE_CURRENCY) {
                    presenter.onBaseCurrencyChanged(prefsManager.baseCurrency)
                }
            }
        }
    }

    override fun changeBaseAmount(amount: Float) {
        val baseAmount = if (0 < amount) {
            amount
        } else {
            1F
        }
        prefsManager.baseAmount = baseAmount
    }
}
