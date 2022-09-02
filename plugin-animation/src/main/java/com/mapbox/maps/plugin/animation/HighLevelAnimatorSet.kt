package com.mapbox.maps.plugin.animation

import android.animation.AnimatorSet
import com.mapbox.maps.threading.AnimationThreadController

internal data class HighLevelAnimatorSet(
  val owner: String?,
  val animatorSet: AnimatorSet
) : Cancelable {

  var started = false

  override fun cancel() {
    AnimationThreadController.postOnAnimatorThread {
      animatorSet.cancel()
    }
  }
}