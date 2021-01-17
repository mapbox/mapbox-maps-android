package com.mapbox.maps.plugin.location

import android.animation.Animator
import android.animation.AnimatorSet
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator

internal class MapboxAnimatorSetProvider private constructor() {

  fun startAnimation(
    animators: List<Animator>,
    interpolator: Interpolator,
    duration: Long
  ) {
    AnimatorSet().apply {
      this.playTogether(animators)
      this.interpolator = interpolator
      this.duration = duration
      this.start()
    }
  }

  companion object {
    val instance = MapboxAnimatorSetProvider()
    val animatorInstance: Interpolator = LinearInterpolator()
  }
}