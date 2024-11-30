package com.eltex.lab14.presentation.animator

import android.animation.ObjectAnimator
import android.view.View
import com.eltex.lab14.Constants

object ButtonAnimator {
    fun animationRotate(view: View) {
        val rotate = ObjectAnimator.ofFloat(view, Constants.VIEW_ROTATION_ANIMATION_NAME, 0f, 360f)
        rotate.duration = 500 // Длительность анимации
        rotate.start()
    }
}