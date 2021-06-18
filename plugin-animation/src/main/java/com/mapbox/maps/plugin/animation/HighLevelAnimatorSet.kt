package com.mapbox.maps.plugin.animation

import android.animation.AnimatorSet

internal data class HighLevelAnimatorSet(
  val owner: String?,
  val animatorSet: AnimatorSet
) : Cancelable {

  var started = false

  override fun cancel() {
    animatorSet.cancel()
  }
}