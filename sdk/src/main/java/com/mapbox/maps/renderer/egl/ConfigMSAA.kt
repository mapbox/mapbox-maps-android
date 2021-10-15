package com.mapbox.maps.renderer.egl

/**
 * Class describing multisample anti-aliasing (MSAA) option for rendering.
 */
sealed class ConfigMSAA(
  val sampleBuffers: Int,
  val samples: Int,
) {
  /**
   * MSAA is off.
   */
  object Off : ConfigMSAA(0, 0)

  /**
   * MSAA is on. Higher [samples] values result in better rendering quality, but may reduce overall performance.
   *
   * @param samples passing 2 will enable MSAA x2 if it's supported, 4 stands for MSAA x4 etc.
   */
  class On(samples: Int) : ConfigMSAA(1, samples)
}