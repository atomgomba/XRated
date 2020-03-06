package com.ekezet.xrated.parts.home.parts.infoPage

import androidx.lifecycle.LifecycleOwner
import com.ekezet.xrated.BuildConfig
import com.ekezet.xrated.R
import com.ekezet.xrated.base.BasePresenter
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.parts.home.parts.infoPage.InfoPageSpec.Interactor
import com.ekezet.xrated.parts.home.parts.infoPage.InfoPageSpec.View
import com.ekezet.xrated.rates.di.UPDATE_HOUR
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Named

/**
 * @author kiri
 */
@ActivityScope
class InfoPagePresenter @Inject constructor(
    @Named(UPDATE_HOUR) private val updateHour: Int
) : BasePresenter<View, Interactor, Nothing>(), View.Presenter,
    Interactor.Presenter {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        view.setup(R.layout.view_info_page)
        view.version = "%s-%s (%d)".format(BuildConfig.VERSION_NAME, BuildConfig.BUILD_TYPE, BuildConfig.VERSION_CODE)
        view.buildId = "%s #%s".format(t(R.string.build_id), t(R.string.build_number))
        view.updateHour = updateHour
    }

    override fun onClearCacheButtonClicked() {
        interactor!!.clearCache()
    }

    override fun onDatesAvailable(publishedAt: DateTime?, updatedAt: DateTime?) {
        view.publishedAt = publishedAt?.toString("YYYY-MM-dd") ?: t(R.string.common__not_available)
        view.downloadedAt = updatedAt?.toString("YYYY-MM-dd HH:mm:ss") ?: t(R.string.common__not_available)
    }
}
