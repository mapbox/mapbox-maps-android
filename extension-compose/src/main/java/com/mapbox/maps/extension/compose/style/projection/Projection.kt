package com.mapbox.maps.extension.compose.style.projection

import androidx.compose.runtime.Immutable
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName

/**
 * The projection to be used for rendering the map.
 *
 * @param value the [ProjectionName] wrapped in [Value].
 */
@Immutable
@MapboxExperimental
public data class Projection private constructor(public val value: Value) {
  /**
   * Public companion object.
   */
  public companion object {

    /**
     * Default value for [Projection], setting default will result in restoring the property value defined in the style.
     */
    public val default: Projection = Projection(Value.nullValue())

    /**
     * The globe projection.
     */
    @JvmField
    public val GLOBE: Projection = Projection(
      Value.valueOf(
        hashMapOf(
          "name" to Value(ProjectionName.GLOBE.value)
        )
      )
    )

    /**
     * The Mercator projection.
     */
    @JvmField
    public val MERCATOR: Projection = Projection(
      Value.valueOf(
        hashMapOf(
          "name" to Value(ProjectionName.MERCATOR.value)
        )
      )
    )
  }
}