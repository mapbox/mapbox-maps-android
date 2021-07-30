package com.mapbox.maps.renderer

import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLES20
import androidx.annotation.CallSuper

abstract class Widget {

  private var isInit = false
  var width = 0
  var height = 0

  @CallSuper
  open fun onSizeChanged(width: Int, height: Int) {
    this.width = width
    this.height = height
  }

  abstract fun initialize()

  // TODO make params customizable
  @CallSuper
  open fun render() {
    if (!isInit) {
      isInit = true
      initialize()
    }
    GLES20.glUseProgram(0)
    GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)
    GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
  }

  abstract fun deinitialize()
}