package com.mapbox.maps.renderer.egl

/**
 * Class describing multisample anti-aliasing (MSAA) option for rendering.
 */
sealed class ConfigMSAA(
  /**
   * Number of sample buffers, when MSAA is enabled will be 1 and 0 otherwise.
   */
  val sampleBuffers: Int,
  /**
   * Number of samples per pixel.
   */
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

    val that = other as ConfigMSAA?

    if (sampleBuffers != that?.sampleBuffers) {
      return false
    }

    if (samples != that.samples) {
      return false
    }

    return true
  }

  /**
   * Hash code method.
   */
  override fun hashCode(): Int {
    var result = sampleBuffers
    result = 31 * result + samples
    return result
  }
}