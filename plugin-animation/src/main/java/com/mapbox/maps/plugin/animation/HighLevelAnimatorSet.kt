package com.mapbox.maps.plugin.animation

import android.animation.AnimatorSet
import com.mapbox.maps.threading.AnimationThreadController

internal data class HighLevelAnimatorSet(
  val owner: String?,
  val animatorSet: AnimatorSet
) : Cancelable {

  override fun cancel() {
    AnimationThreadController.postOnAnimatorThread {
      animatorSet.cancel()
    }
  }
}