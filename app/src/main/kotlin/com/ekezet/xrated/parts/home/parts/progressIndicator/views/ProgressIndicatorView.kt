package com.ekezet.xrated.parts.home.parts.progressIndicator.views

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style.FILL
import android.graphics.PorterDuff.Mode
import android.graphics.drawable.BitmapDrawable
import androidx.core.animation.doOnEnd
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.ekezet.xrated.R
import com.ekezet.xrated.base.di.ActivityScope
import com.ekezet.xrated.base.views.BaseView
import com.ekezet.xrated.parts.home.parts.progressIndicator.ProgressIndicatorSpec.View
import timber.log.Timber
import javax.inject.Inject

/**
 * @author kiri
 */
@ActivityScope
class ProgressIndicatorView @Inject constructor(context: Context) : BaseView(context), View {
    private var fadeAnimator: ValueAnimator? = null
    private val progressAnimator = ValueAnimator.ofFloat(0F, 1F).apply {
        duration = 1500L
        repeatCount = INFINITE
        repeatMode = REVERSE
        interpolator = LinearOutSlowInInterpolator()
        val resources = context.resources
        addUpdateListener {
            canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR)
            val pos = (width - lineWidth) * animatedFraction
            canvas.drawRect(pos, 0F, pos + lineWidth, height.toFloat(), paint)
            background = BitmapDrawable(resources, bitmap)
        }
    }

    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas
    private val paint: Paint = Paint().apply {
        style = FILL
        color = context.getColor(R.color.colorAccent)
    }
    private var lineWidth = 0

    override fun startLoading() {
        if (!::bitmap.isInitialized) {
            lineWidth = width / 4
            bitmap = Bitmap.createBitmap(width, height, ARGB_8888)
            canvas = Canvas(bitmap)
        }
        val progress = if (fadeAnimator?.isRunning == true) {
            fadeAnimator?.animatedFraction ?: 0F
        } else {
            0F
        }
        fadeAnimator = ValueAnimator.ofFloat(progress, 1F).apply {
            addUpdateListener {
                alpha = animatedFraction
            }
            doOnEnd {
                progressAnimator.start()
            }
        }
        fadeAnimator?.start()
    }

    override fun finishLoading() {
        if (!::bitmap.isInitialized) {
            Timber.w("Loading not started so cannot finish")
            return
        }
        val progress = if (fadeAnimator?.isRunning == true) {
            fadeAnimator?.animatedFraction ?: 1F
        } else {
            1F
        }
        fadeAnimator = ValueAnimator.ofFloat(0F, progress).apply {
            startDelay = 500L
            addUpdateListener {
                alpha = 1F - animatedFraction
            }
            doOnEnd {
                progressAnimator.end()
                clearCanvas()
            }
        }
        fadeAnimator?.start()
    }

    private fun clearCanvas() {
        canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR)
        background = BitmapDrawable(resources, bitmap)
    } }
