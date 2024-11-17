package com.eltex.lab14.presentation.animator

import android.animation.ObjectAnimator
import android.view.View

class MyAnimator {
    companion object {
        fun animationRotate(view: View) {
            val rotate = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f)
            rotate.duration = 500 // Длительность анимации
            rotate.start()
        }
    }
}