// This file is generated.

package com.mapbox.maps.extension.compose.style.atmosphere.generated

import androidx.compose.runtime.Immutable
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.internal.ComposeTypeUtils
import com.mapbox.maps.extension.style.expressions.generated.Expression

/**
 * The color of the atmosphere region immediately below the horizon and within the `range` and above the horizon and within `horizon-blend`. Using opacity is recommended only for smoothly transitioning fog on/off as anything less than 100% opacity results in more tiles loaded and drawn.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Color(public val value: Value) {
  /**
   * Construct the Color with [Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the Color with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "color"

    /**
     * Default value for [Color], setting default will result in restoring the property value defined in the style.
     */
    public val default: Color = Color(Value.nullValue())
  }
}

/**
 * The color of the atmosphere region above the horizon, `high-color` extends further above the horizon than the `color` property and its spread can be controlled with `horizon-blend`. The opacity can be set to `0` to remove the high atmosphere color contribution.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class HighColor(public val value: Value) {
  /**
   * Construct the HighColor with [Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the HighColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "high-color"

    /**
     * Default value for [HighColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: HighColor = HighColor(Value.nullValue())
  }
}

/**
 * Horizon blend applies a smooth fade from the color of the atmosphere to the color of space. A value of zero leaves a sharp transition from atmosphere to space. Increasing the value blends the color of atmosphere into increasingly high angles of the sky.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class HorizonBlend(public val value: Value) {
  /**
   * Construct the HorizonBlend with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the HorizonBlend with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "horizon-blend"

    /**
     * Default value for [HorizonBlend], setting default will result in restoring the property value defined in the style.
     */
    public val default: HorizonBlend = HorizonBlend(Value.nullValue())
  }
}

/**
 * The start and end distance range in which fog fades from fully transparent to fully opaque. The distance to the point at the center of the map is defined as zero, so that negative range values are closer to the camera, and positive values are farther away.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Range(public val value: Value) {
  /**
   * Construct the Range with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the Range with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "range"

    /**
     * Default value for [Range], setting default will result in restoring the property value defined in the style.
     */
    public val default: Range = Range(Value.nullValue())
  }
}

/**
 * The color of the region above the horizon and after the end of the `horizon-blend` contribution. The opacity can be set to `0` to have a transparent background.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SpaceColor(public val value: Value) {
  /**
   * Construct the SpaceColor with [Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the SpaceColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "space-color"

    /**
     * Default value for [SpaceColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: SpaceColor = SpaceColor(Value.nullValue())
  }
}

/**
 * A value controlling the star intensity where `0` will show no stars and `1` will show stars at their maximum intensity.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class StarIntensity(public val value: Value) {
  /**
   * Construct the StarIntensity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the StarIntensity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "star-intensity"

    /**
     * Default value for [StarIntensity], setting default will result in restoring the property value defined in the style.
     */
    public val default: StarIntensity = StarIntensity(Value.nullValue())
  }
}

/**
 * An array of two number values, specifying the vertical range, measured in meters, over which the fog should gradually fade out. When both parameters are set to zero, the fog will be rendered without any vertical constraints.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class VerticalRange(public val value: Value) {
  /**
   * Construct the VerticalRange with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the VerticalRange with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "vertical-range"

    /**
     * Default value for [VerticalRange], setting default will result in restoring the property value defined in the style.
     */
    public val default: VerticalRange = VerticalRange(Value.nullValue())
  }
}
// End of generated file.