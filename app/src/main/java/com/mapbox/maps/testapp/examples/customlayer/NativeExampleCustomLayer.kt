package com.mapbox.maps.testapp.examples.customlayer

import androidx.annotation.Keep
import com.mapbox.maps.CustomLayerHost
import com.mapbox.maps.CustomLayerRenderParameters

@Keep
class NativeExampleCustomLayer : CustomLayerHost {
  external override fun initialize()

  external override fun render(parameters: CustomLayerRenderParameters)

  external override fun contextLost()

  external override fun deinitialize()

  external fun setColor(red: Float, green: Float, blue: Float, alpha: Float)

  companion object {
    init {
      System.loadLibrary("example-cpp-custom-layer")
    }
  }
}