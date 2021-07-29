package com.mapbox.maps.renderer

import androidx.annotation.CallSuper

abstract class Widget {

  private var isInit = false

  abstract fun initialize()

  // TODO make params customizable
  @CallSuper
  open fun render() {
    if (!isInit) {
      initialize()
    }
  }

  abstract fun deinitialize()
}