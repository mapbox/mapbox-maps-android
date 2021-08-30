package com.mapbox.maps.plugin

import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import java.lang.RuntimeException

/**
 * Describes projection map is using.
 *
 * MapboxMap supports Mercator and Globe projections.
 */
@MapboxExperimental
sealed class MapProjection(
  private val projectionName: String
) {

  /**
   * Mercator projection.
   *
   * @see [Mercator projection description](https://en.wikipedia.org/wiki/Mercator_projection)
   */
  object Mercator: MapProjection(MERCATOR_PROJECTION_NAME)

  /**
   * Globe projection. Map surface is represented as the globe sphere.
   *
   * @see [TRANSITION_ZOOM_LEVEL] for more details what projection will be used depending on current zoom level.
   */
  object Globe: MapProjection(GLOBE_PROJECTION_NAME)

  fun toValue() = Value.valueOf(hashMapOf(NAME_KEY to Value(projectionName)))

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * Zoom level threshold where MapboxMap will automatically switch projection
     * from [MapProjection.Mercator] to [MapProjection.Globe] or vice-versa
     * if [MapProjectionDelegate.setMapProjection] was configured to use [MapProjection.Globe].
     *
     * If MapboxMap is using [MapProjection.Globe] and current map zoom level is < [TRANSITION_ZOOM_LEVEL] -
     * map will use [MapProjection.Mercator] and [MapProjectionDelegate.getMapProjection] will return [MapProjection.Mercator].
     *
     * If MapboxMap is using [MapProjection.Globe] and current map zoom level is > [TRANSITION_ZOOM_LEVEL] -
     * map will use [MapProjection.Globe] and [MapProjectionDelegate.getMapProjection] will return [MapProjection.Globe].
     *
     * If MapboxMap is using [MapProjection.Mercator] - map will use [MapProjection.Mercator] for any zoom level and
     * [MapProjectionDelegate.getMapProjection] will return [MapProjection.Mercator].
     */
    const val TRANSITION_ZOOM_LEVEL = 5.0

    /**
     * Constructs [MapProjection] from [Value].
     *
     * @throws [RuntimeException] if given [value] could not be casted to [MapProjection].
     */
    fun fromValue(value: Value): MapProjection {
      @Suppress("UNCHECKED_CAST")
      val map = value.contents as? HashMap<String, Value>
      return when(map?.get(NAME_KEY)?.contents as? String) {
        MERCATOR_PROJECTION_NAME -> Mercator
        GLOBE_PROJECTION_NAME -> Globe
        else -> throw RuntimeException("Could not cast given Value to valid MapProjection!")
      }
    }

    private const val NAME_KEY = "name"
    private const val MERCATOR_PROJECTION_NAME = "mercator"
    private const val GLOBE_PROJECTION_NAME = "globe"
  }
}