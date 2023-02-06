package com.mapbox.maps.plugin.gestures

import com.mapbox.android.gestures.AndroidGesturesManager

internal class GestureState(private val gesturesManager: AndroidGesturesManager) {
  enum class Type {
    DoubleTap,
    Scale,
    ScaleQuickZoom,
    Shove,
  }

  private val savedGestureEnabledMap = mutableMapOf<Type, Boolean>()

  fun saveAndDisable(gesture: Type): Boolean =
    when (gesture) {
      Type.Scale -> gesturesManager.rotateGestureDetector
      else -> gesturesManager.moveGestureDetector
    }.let { detector ->
      val state = detector.isEnabled
      savedGestureEnabledMap[gesture] = state
      detector.isEnabled = false
      state
    }

  fun restore(gesture: Type) {
    savedGestureEnabledMap.remove(gesture)?.let { state ->
      when (gesture) {
        Type.Scale -> gesturesManager.rotateGestureDetector
        else -> gesturesManager.moveGestureDetector
      }.isEnabled = state
    }
  }

  fun peek(gesture: Type): Boolean? {
    return savedGestureEnabledMap[gesture]
  }
}