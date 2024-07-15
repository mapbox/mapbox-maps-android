// This file is generated.

package com.mapbox.maps.extension.compose.style.sources.generated

import androidx.compose.runtime.Immutable
import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.expressions.generated.Expression

/**
 * Influences the y direction of the tile coordinates. The global-mercator (aka Spherical Mercator) profile is assumed. Default value: "xyz".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
public data class SchemeValue(public val value: Value) {
  /**
   * Construct the SchemeValue with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: SchemeValue = SchemeValue(Value("Scheme.INITIAL"))

    /**
     * Default value for [SchemeValue], setting [DEFAULT] will result in setting the property value
     * defined by the rendering engine.
     */
    @JvmField
    public val DEFAULT: SchemeValue = SchemeValue(Value.nullValue())

    /**
     * Slippy map tilenames scheme.
     */
    @JvmField
    public val XYZ: SchemeValue = SchemeValue(Value("xyz"))

    /**
     * OSGeo spec scheme.
     */
    @JvmField
    public val TMS: SchemeValue = SchemeValue(Value("tms"))
  }
}

/**
 * The encoding used by this source. Mapbox Terrain RGB is used by default Default value: "mapbox".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
public data class EncodingValue(public val value: Value) {
  /**
   * Construct the EncodingValue with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: EncodingValue = EncodingValue(Value("Encoding.INITIAL"))

    /**
     * Default value for [EncodingValue], setting [DEFAULT] will result in setting the property value
     * defined by the rendering engine.
     */
    @JvmField
    public val DEFAULT: EncodingValue = EncodingValue(Value.nullValue())

    /**
     * Terrarium format PNG tiles. See https://aws.amazon.com/es/public-datasets/terrain/ for more info.
     */
    @JvmField
    public val TERRARIUM: EncodingValue = EncodingValue(Value("terrarium"))

    /**
     * Mapbox Terrain RGB tiles. See https://www.mapbox.com/help/access-elevation-data/#mapbox-terrain-rgb for more info.
     */
    @JvmField
    public val MAPBOX: EncodingValue = EncodingValue(Value("mapbox"))
  }
}
// End of generated file.