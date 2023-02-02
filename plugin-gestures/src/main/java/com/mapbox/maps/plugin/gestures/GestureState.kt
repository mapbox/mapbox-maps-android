package com.mapbox.maps.plugin.gestures

import com.mapbox.android.gestures.AndroidGesturesManager

internal class GestureState(private val gesturesManager: AndroidGesturesManager) {
  enum class Type {
    DoubleTap,
    Scale,
    ScaleQuickZoom,
    Shove,
  }

  private val rememberedGestureSettings = mutableMapOf<Type, Boolean>()

  fun saveAndDisable(gesture: Type): Boolean {
    return when (gesture) {
      Type.DoubleTap -> {
        gesturesManager.moveGestureDetector.isEnabled.also {
          gesturesManager.moveGestureDetector.isEnabled = false
        }
      }
      Type.Scale -> {
        gesturesManager.rotateGestureDetector.isEnabled.also {
          gesturesManager.rotateGestureDetector.isEnabled = false
        }
      }
      Type.ScaleQuickZoom -> {
        gesturesManager.moveGestureDetector.isEnabled.also {
          gesturesManager.moveGestureDetector.isEnabled = false
        }
      }
      Type.Shove -> {
        gesturesManager.moveGestureDetector.isEnabled.also {
          gesturesManager.moveGestureDetector.isEnabled = false
        }
      }
    }.also {
      rememberedGestureSettings[gesture] = it
    }
  }

  fun restore(gesture: Type) {
    rememberedGestureSettings.remove(gesture)?.let {
      when (gesture) {
        Type.DoubleTap -> gesturesManager.moveGestureDetector.isEnabled = it
        Type.Scale -> gesturesManager.rotateGestureDetector.isEnabled = it
        Type.ScaleQuickZoom -> gesturesManager.moveGestureDetector.isEnabled = it
        Type.Shove -> gesturesManager.moveGestureDetector.isEnabled = it
      }
    }
  }

  fun peek(gesture: Type): Boolean? {
    return rememberedGestureSettings[gesture]
  }
}