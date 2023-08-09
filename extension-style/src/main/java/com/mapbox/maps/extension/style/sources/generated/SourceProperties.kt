// This file is generated.

package com.mapbox.maps.extension.style.sources.generated

/**
 * Influences the y direction of the tile coordinates. The global-mercator (aka Spherical Mercator) profile is assumed.
 *
 * @param value
 */
class Scheme private constructor(val value: String) {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is Scheme &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "Scheme(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * Slippy map tilenames scheme.
     */
    @JvmField
    val XYZ = Scheme("xyz")
    /**
     * OSGeo spec scheme.
     */
    @JvmField
    val TMS = Scheme("tms")

    /**
     * Utility function to get [Scheme] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): Scheme {
      return when (value) {
        "XYZ" -> XYZ
        "TMS" -> TMS
        else -> throw RuntimeException("Scheme.valueOf does not support [$value]")
      }
    }
  }
}

/**
 * The encoding used by this source. Mapbox Terrain RGB is used by default
 *
 * @param value
 */
class Encoding private constructor(val value: String) {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is Encoding &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "Encoding(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * Terrarium format PNG tiles. See https://aws.amazon.com/es/public-datasets/terrain/ for more info.
     */
    @JvmField
    val TERRARIUM = Encoding("terrarium")
    /**
     * Mapbox Terrain RGB tiles. See https://www.mapbox.com/help/access-elevation-data/#mapbox-terrain-rgb for more info.
     */
    @JvmField
    val MAPBOX = Encoding("mapbox")

    /**
     * Utility function to get [Encoding] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): Encoding {
      return when (value) {
        "TERRARIUM" -> TERRARIUM
        "MAPBOX" -> MAPBOX
        else -> throw RuntimeException("Encoding.valueOf does not support [$value]")
      }
    }
  }
}

// End of generated file.