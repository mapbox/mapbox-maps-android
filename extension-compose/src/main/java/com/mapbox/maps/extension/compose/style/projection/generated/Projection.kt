// This file is generated.

package com.mapbox.maps.extension.compose.style.projection.generated

import androidx.compose.runtime.Immutable
import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName

/**
 * The projection to be used for rendering the map.
 *
 * @param value the [Projection] wrapped in [Value].
 */
@Immutable
public data class Projection internal constructor(
  public val value: Value
) {

  private constructor(name: ProjectionName) : this(
    Value.valueOf(hashMapOf("name" to Value(name.value))),
  )

  /**
   * The projection name of the [Projection] or null if the resulted name is not a [String].
   */
  @Suppress("UNCHECKED_CAST")
  val projectionNameOrNull: String?
    get() = (value.contents as? HashMap<String, Value>)?.get("name")?.contents as? String

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
    internal val INITIAL: Projection = Projection(Value.valueOf("Projection.INITIAL"))

    /**
     * Default value for [Projection], setting default will result in restoring the property value defined in the rendering engine.
     */
    @JvmField
    public val DEFAULT: Projection = Projection(Value.nullValue())

    /**
     * The Mercator projection is the default projection.
     */
    @JvmField
    public val MERCATOR: Projection = Projection(ProjectionName.MERCATOR)

    /**
     * A globe projection.
     */
    @JvmField
    public val GLOBE: Projection = Projection(ProjectionName.GLOBE)
  }
}
// End of generated file.