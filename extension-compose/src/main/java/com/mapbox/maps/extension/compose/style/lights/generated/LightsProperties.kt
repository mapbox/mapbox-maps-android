// ktlint-disable filename
// This file is generated.

package com.mapbox.maps.extension.compose.style.lights.generated

import androidx.compose.runtime.Immutable
import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.expressions.generated.Expression

/**
 *  Whether extruded geometries are lit relative to the map or viewport. Default value: "viewport".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
public data class AnchorValue(public val value: Value) {
  /**
   * Construct the Anchor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
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
     * Default value for [AnchorValue], setting default will result in setting the property value
     * defined by the rendering engine.
     */
    @JvmField
    public val DEFAULT: AnchorValue = AnchorValue(Value.nullValue())

    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: AnchorValue = AnchorValue(Value.nullValue())

    /**
     * The position of the light source is aligned to the rotation of the map.
     */
    @JvmField
    public val MAP: AnchorValue = AnchorValue(Value("map"))

    /**
     * The position of the light source is aligned to the rotation of the viewport. If terrain is enabled, performance regressions may occur in certain scenarios, particularly on lower-end hardware. Ensure that you test your target scenarios on the appropriate hardware to verify performance.
     */
    @JvmField
    public val VIEWPORT: AnchorValue = AnchorValue(Value("viewport"))
  }
}
// End of generated file.