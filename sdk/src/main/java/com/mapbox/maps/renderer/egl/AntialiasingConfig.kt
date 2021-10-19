package com.mapbox.maps.renderer.egl

/**
 * Class describing multisample anti-aliasing (MSAA) option for rendering.
 */
sealed class AntialiasingConfig(
  /**
   * Number of samples per pixel.
   */
  val sampleCount: Int,
) {
  /**
   * MSAA is off.
   */
  object Off : AntialiasingConfig(0)

  /**
   * MSAA is on. Higher [sampleCount] values result in better rendering quality, but may reduce overall performance.
   *
   * @param sampleCount passing 2 will enable MSAA x2 if it's supported, 4 stands for MSAA x4 etc.
   */
  class On(sampleCount: Int) : AntialiasingConfig(sampleCount)

  /**
   * Equals method.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other == null || javaClass != other.javaClass) {
      return false
    }

    val that = other as AntialiasingConfig?

    if (sampleCount != that?.sampleCount) {
      return false
    }

    return true
  }

  /**
   * Hash code method.
   */
  override fun hashCode(): Int {
    return 31 + sampleCount
  }
}