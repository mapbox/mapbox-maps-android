// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import androidx.annotation.ColorInt
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.annotation.AnnotationManager
import com.mapbox.maps.plugin.annotation.AnnotationOptions

/**
 * Builder class from which a circle is created.
 */
class CircleOptions : AnnotationOptions<Point, Circle> {
  private var isDraggable: Boolean = false
  private var data: JsonElement? = null
  private var geometry: Point? = null

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  var circleSortKey: Double? = null

  /**
   * Set circle-sort-key to initialise the circle with.
   * <p>
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   * </p>
   * @param circleSortKey the circle-sort-key value
   * @return this
   */
  fun withCircleSortKey(circleSortKey: Double): CircleOptions {
    this.circleSortKey = circleSortKey
    return this
  }

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
   */
  var circleBlur: Double = 0.0

  /**
   * Set circle-blur to initialise the circle with.
   * <p>
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
   * </p>
   * @param circleBlur the circle-blur value
   * @return this
   */
  fun withCircleBlur(circleBlur: Double): CircleOptions {
    this.circleBlur = circleBlur
    return this
  }

  /**
   * The fill color of the circle.
   */
  var circleColor: String = "rgba(0, 0, 0, 1)"

  /**
   * Set circle-color to initialise the circle with.
   * <p>
   * The fill color of the circle.
   * </p>
   * @param circleColor the circle-color value
   * @return this
   */
  fun withCircleColor(circleColor: String): CircleOptions {
    this.circleColor = circleColor
    return this
  }

  /**
   * Set circle-color to initialise the circle with.
   * <p>
   * The fill color of the circle.
   * </p>
   * @param circleColor the circle-color value with ColorInt format
   * @return this
   */
  fun withCircleColor(@ColorInt circleColor: Int): CircleOptions {
    this.circleColor = ColorUtils.colorToRgbaString(circleColor)
    return this
  }

  /**
   * The opacity at which the circle will be drawn.
   */
  var circleOpacity: Double = 1.0

  /**
   * Set circle-opacity to initialise the circle with.
   * <p>
   * The opacity at which the circle will be drawn.
   * </p>
   * @param circleOpacity the circle-opacity value
   * @return this
   */
  fun withCircleOpacity(circleOpacity: Double): CircleOptions {
    this.circleOpacity = circleOpacity
    return this
  }

  /**
   * Circle radius.
   */
  var circleRadius: Double = 5.0

  /**
   * Set circle-radius to initialise the circle with.
   * <p>
   * Circle radius.
   * </p>
   * @param circleRadius the circle-radius value
   * @return this
   */
  fun withCircleRadius(circleRadius: Double): CircleOptions {
    this.circleRadius = circleRadius
    return this
  }

  /**
   * The stroke color of the circle.
   */
  var circleStrokeColor: String = "rgba(0, 0, 0, 1)"

  /**
   * Set circle-stroke-color to initialise the circle with.
   * <p>
   * The stroke color of the circle.
   * </p>
   * @param circleStrokeColor the circle-stroke-color value
   * @return this
   */
  fun withCircleStrokeColor(circleStrokeColor: String): CircleOptions {
    this.circleStrokeColor = circleStrokeColor
    return this
  }

  /**
   * Set circle-stroke-color to initialise the circle with.
   * <p>
   * The stroke color of the circle.
   * </p>
   * @param circleStrokeColor the circle-stroke-color value with ColorInt format
   * @return this
   */
  fun withCircleStrokeColor(@ColorInt circleStrokeColor: Int): CircleOptions {
    this.circleStrokeColor = ColorUtils.colorToRgbaString(circleStrokeColor)
    return this
  }

  /**
   * The opacity of the circle's stroke.
   */
  var circleStrokeOpacity: Double = 1.0

  /**
   * Set circle-stroke-opacity to initialise the circle with.
   * <p>
   * The opacity of the circle's stroke.
   * </p>
   * @param circleStrokeOpacity the circle-stroke-opacity value
   * @return this
   */
  fun withCircleStrokeOpacity(circleStrokeOpacity: Double): CircleOptions {
    this.circleStrokeOpacity = circleStrokeOpacity
    return this
  }

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
   */
  var circleStrokeWidth: Double = 0.0

  /**
   * Set circle-stroke-width to initialise the circle with.
   * <p>
   * The width of the circle's stroke. Strokes are placed outside of the {@link PropertyFactory#circleRadius}.
   * </p>
   * @param circleStrokeWidth the circle-stroke-width value
   * @return this
   */
  fun withCircleStrokeWidth(circleStrokeWidth: Double): CircleOptions {
    this.circleStrokeWidth = circleStrokeWidth
    return this
  }

  /**
   * Set the Point of the circle, which represents the location of the circle on the map
   *
   * @param point the location of the circle in a longitude and latitude pair
   * @return this
   */
  fun withPoint(point: Point): CircleOptions {
    geometry = point
    return this
  }

  /**
   * Get the Point of the circle, which represents the location of the circle on the map
   *
   * @return the location of the circle in a longitude and latitude pair
   */
  fun getPoint(): Point? {
    return geometry
  }

  /**
   * Set the geometry of the circle, which represents the location of the circle on the map
   *
   * @param geometry the location of the circle
   * @return this
   */
  fun withGeometry(geometry: Point): CircleOptions {
    this.geometry = geometry
    return this
  }

  /**
   * Get the geometry of the circle, which represents the location of the circle on the map
   *
   * @return the location of the circle
   */
  fun getGeometry(): Point? {
    return geometry
  }

  /**
   * Returns whether this circle is draggable, meaning it can be dragged across the screen when touched and moved.
   *
   * @return draggable when touched
   */
  fun getDraggable(): Boolean {
    return isDraggable
  }

  /**
   * Set whether this circle should be draggable,
   * meaning it can be dragged across the screen when touched and moved.
   *
   * @param draggable should be draggable
   */
  fun withDraggable(draggable: Boolean): CircleOptions {
    isDraggable = draggable
    return this
  }

  /**
   * Set the arbitrary json data of the annotation.
   *
   * @param jsonElement the arbitrary json element data
   */
  fun withData(jsonElement: JsonElement): CircleOptions {
    this.data = jsonElement
    return this
  }

  /**
   * Get the arbitrary json data of the annotation.
   *
   * @return the arbitrary json object data if set, else null
   */
  fun getData(): JsonElement? {
    return data
  }

  /**
   * Build an annotation
   *
   * @param id: the id for this annotation
   * @param annotationManager: the annotationManager that manage this annotation
   *
   * @return the annotation that is built
   */
  override fun build(
    id: Long,
    annotationManager: AnnotationManager<Point, Circle, *, *, *, *>
  ): Circle {
    if (geometry == null) {
      throw RuntimeException("geometry field is required")
    }
    val jsonObject = JsonObject()
    jsonObject.addProperty(PROPERTY_CIRCLE_SORT_KEY, circleSortKey)
    jsonObject.addProperty(PROPERTY_CIRCLE_BLUR, circleBlur)
    jsonObject.addProperty(PROPERTY_CIRCLE_COLOR, circleColor)
    jsonObject.addProperty(PROPERTY_CIRCLE_OPACITY, circleOpacity)
    jsonObject.addProperty(PROPERTY_CIRCLE_RADIUS, circleRadius)
    jsonObject.addProperty(PROPERTY_CIRCLE_STROKE_COLOR, circleStrokeColor)
    jsonObject.addProperty(PROPERTY_CIRCLE_STROKE_OPACITY, circleStrokeOpacity)
    jsonObject.addProperty(PROPERTY_CIRCLE_STROKE_WIDTH, circleStrokeWidth)
    val circle = Circle(id, annotationManager, jsonObject, geometry!!)
    circle.isDraggable = isDraggable
    circle.setData(data)
    return circle
  }

  /**
   * Static variables and methods.
   */
  companion object {

    /** The property for circle-sort-key */
    const val PROPERTY_CIRCLE_SORT_KEY = "circle-sort-key"

    /** The property for circle-blur */
    const val PROPERTY_CIRCLE_BLUR = "circle-blur"

    /** The property for circle-color */
    const val PROPERTY_CIRCLE_COLOR = "circle-color"

    /** The property for circle-opacity */
    const val PROPERTY_CIRCLE_OPACITY = "circle-opacity"

    /** The property for circle-radius */
    const val PROPERTY_CIRCLE_RADIUS = "circle-radius"

    /** The property for circle-stroke-color */
    const val PROPERTY_CIRCLE_STROKE_COLOR = "circle-stroke-color"

    /** The property for circle-stroke-opacity */
    const val PROPERTY_CIRCLE_STROKE_OPACITY = "circle-stroke-opacity"

    /** The property for circle-stroke-width */
    const val PROPERTY_CIRCLE_STROKE_WIDTH = "circle-stroke-width"

    /** The property for is-draggable */
    private const val PROPERTY_IS_DRAGGABLE = "is-draggable"

    /**
     * Creates CircleOptions out of a Feature.
     *
     * @param feature feature to be converted
     */
    fun fromFeature(feature: Feature): CircleOptions? {
      if (feature.geometry() == null) {
        throw RuntimeException("geometry field is required")
      }
      if (feature.geometry() !is Point) {

        return null
      }

      val options = CircleOptions()
      options.geometry = feature.geometry() as (Point)
      if (feature.hasProperty(PROPERTY_CIRCLE_SORT_KEY)) {
        options.circleSortKey = feature.getProperty(PROPERTY_CIRCLE_SORT_KEY).asDouble
      }
      if (feature.hasProperty(PROPERTY_CIRCLE_BLUR)) {
        options.circleBlur = feature.getProperty(PROPERTY_CIRCLE_BLUR).asDouble
      }
      if (feature.hasProperty(PROPERTY_CIRCLE_COLOR)) {
        options.circleColor = feature.getProperty(PROPERTY_CIRCLE_COLOR).asString
      }
      if (feature.hasProperty(PROPERTY_CIRCLE_OPACITY)) {
        options.circleOpacity = feature.getProperty(PROPERTY_CIRCLE_OPACITY).asDouble
      }
      if (feature.hasProperty(PROPERTY_CIRCLE_RADIUS)) {
        options.circleRadius = feature.getProperty(PROPERTY_CIRCLE_RADIUS).asDouble
      }
      if (feature.hasProperty(PROPERTY_CIRCLE_STROKE_COLOR)) {
        options.circleStrokeColor = feature.getProperty(PROPERTY_CIRCLE_STROKE_COLOR).asString
      }
      if (feature.hasProperty(PROPERTY_CIRCLE_STROKE_OPACITY)) {
        options.circleStrokeOpacity = feature.getProperty(PROPERTY_CIRCLE_STROKE_OPACITY).asDouble
      }
      if (feature.hasProperty(PROPERTY_CIRCLE_STROKE_WIDTH)) {
        options.circleStrokeWidth = feature.getProperty(PROPERTY_CIRCLE_STROKE_WIDTH).asDouble
      }
      if (feature.hasProperty(PROPERTY_IS_DRAGGABLE)) {
        options.isDraggable = feature.getProperty(PROPERTY_IS_DRAGGABLE).asBoolean
      }
      return options
    }
  }
}