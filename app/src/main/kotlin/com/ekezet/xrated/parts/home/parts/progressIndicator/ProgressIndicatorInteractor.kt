package com.ekezet.xrated.parts.home.parts.progressIndicator

import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.base.BaseInteractor
import com.ekezet.xrated.base.api.ApiResponse.LoadingResponse
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.parts.home.parts.progressIndicator.ProgressIndicatorSpec.Interactor
import com.ekezet.xrated.rates.data.RatesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class ProgressIndicatorInteractor @Inject constructor(
    private val ratesRepository: RatesRepository
) : BaseInteractor<Interactor.Presenter>(), Interactor {
    override val coroutineContext = Dispatchers.Main

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        launch {
            ratesRepository.exchangeRatesChannel.consumeEach {
                if (it is LoadingResponse) {
                    presenter.onLoadingStarted()
                } else {
                    presenter.onLoadingFinished()
                }
            }
        }
    }
}
