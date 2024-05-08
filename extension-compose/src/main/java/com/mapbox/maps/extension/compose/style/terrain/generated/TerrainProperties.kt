// This file is generated.
@file:Suppress("ktlint:filename")
package com.mapbox.maps.extension.compose.style.terrain.generated

import androidx.compose.runtime.Immutable
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.internal.ComposeTypeUtils
import com.mapbox.maps.extension.style.expressions.generated.Expression

/**
 * Exaggerates the elevation of the terrain by multiplying the data from the DEM with this value.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Exaggeration(public val value: Value) {
  /**
   * Construct the Exaggeration with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the Exaggeration with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "exaggeration"

    /**
     * Default value for [Exaggeration], setting default will result in restoring the property value defined in the style.
     */
    public val default: Exaggeration = Exaggeration(Value.nullValue())
  }
}
// End of generated file.