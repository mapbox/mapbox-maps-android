package com.mapbox.maps.plugin.animation

import android.animation.AnimatorSet
import com.mapbox.common.Cancelable
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.threading.AnimationThreadController

internal data class HighLevelAnimatorSet(
  val owner: String?,
  val animatorSet: AnimatorSet
) : Cancelable {

  @OptIn(MapboxExperimental::class)
  override fun cancel() {
    AnimationThreadController.postOnAnimatorThread {
      animatorSet.cancel()
    }
  }
}