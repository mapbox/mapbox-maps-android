package com.mapbox.maps.renderer.egl

sealed class ConfigMSAA(
  val sampleBuffers: Int,
  val samples: Int
) {
  object Off: ConfigMSAA(0, 0)
  object On2X: ConfigMSAA(1, 2)
  object On4X: ConfigMSAA(1, 4)
  object On8X: ConfigMSAA(1, 8)
}
