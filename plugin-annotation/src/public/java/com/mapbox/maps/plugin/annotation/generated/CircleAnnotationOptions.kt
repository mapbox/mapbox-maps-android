// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import androidx.annotation.ColorInt
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxAnnotationException
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.annotation.AnnotationManager
import com.mapbox.maps.plugin.annotation.AnnotationOptions

/**
 * Builder class from which a circleAnnotation is created.
 */
class CircleAnnotationOptions : AnnotationOptions<Point, CircleAnnotation> {
  private var isDraggable: Boolean = false
  private var data: JsonElement? = null
  private var geometry: Point? = null

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  var circleSortKey: Double? = null

  /**
   * Set circle-sort-key to initialise the circleAnnotation with.
   *
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * @param circleSortKey the circle-sort-key value
   * @return this
   */
  fun withCircleSortKey(circleSortKey: Double): CircleAnnotationOptions {
    this.circleSortKey = circleSortKey
    return this
  }

  /**
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
   */
  var circleBlur: Double? = null

  /**
   * Set circle-blur to initialise the circleAnnotation with.
   *
   * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
   *
   * @param circleBlur the circle-blur value
   * @return this
   */
  fun withCircleBlur(circleBlur: Double): CircleAnnotationOptions {
    this.circleBlur = circleBlur
    return this
  }

  /**
   * The fill color of the circle.
   */
  var circleColor: String? = null

  /**
   * Set circle-color to initialise the circleAnnotation with.
   *
   * The fill color of the circle.
   *
   * @param circleColor the circle-color value
   * @return this
   */
  fun withCircleColor(circleColor: String): CircleAnnotationOptions {
    this.circleColor = circleColor
    return this
  }

  /**
   * Set circle-color to initialise the circleAnnotation with.
   *
   * The fill color of the circle.
   *
   * @param circleColor the circle-color value with ColorInt format
   * @return this
   */
  fun withCircleColor(@ColorInt circleColor: Int): CircleAnnotationOptions {
    this.circleColor = ColorUtils.colorToRgbaString(circleColor)
    return this
  }

  /**
   * The opacity at which the circle will be drawn.
   */
  var circleOpacity: Double? = null

  /**
   * Set circle-opacity to initialise the circleAnnotation with.
   *
   * The opacity at which the circle will be drawn.
   *
   * @param circleOpacity the circle-opacity value
   * @return this
   */
  fun withCircleOpacity(circleOpacity: Double): CircleAnnotationOptions {
    this.circleOpacity = circleOpacity
    return this
  }

  /**
   * Circle radius.
   */
  var circleRadius: Double? = null

  /**
   * Set circle-radius to initialise the circleAnnotation with.
   *
   * Circle radius. The unit of circleRadius is in density-independent pixels.
   *
   * @param circleRadius the circle-radius value
   * @return this
   */
  fun withCircleRadius(circleRadius: Double): CircleAnnotationOptions {
    this.circleRadius = circleRadius
    return this
  }

  /**
   * The stroke color of the circle.
   */
  var circleStrokeColor: String? = null

  /**
   * Set circle-stroke-color to initialise the circleAnnotation with.
   *
   * The stroke color of the circle.
   *
   * @param circleStrokeColor the circle-stroke-color value
   * @return this
   */
  fun withCircleStrokeColor(circleStrokeColor: String): CircleAnnotationOptions {
    this.circleStrokeColor = circleStrokeColor
    return this
  }

  /**
   * Set circle-stroke-color to initialise the circleAnnotation with.
   *
   * The stroke color of the circle.
   *
   * @param circleStrokeColor the circle-stroke-color value with ColorInt format
   * @return this
   */
  fun withCircleStrokeColor(@ColorInt circleStrokeColor: Int): CircleAnnotationOptions {
    this.circleStrokeColor = ColorUtils.colorToRgbaString(circleStrokeColor)
    return this
  }

  /**
   * The opacity of the circle's stroke.
   */
  var circleStrokeOpacity: Double? = null

  /**
   * Set circle-stroke-opacity to initialise the circleAnnotation with.
   *
   * The opacity of the circle's stroke.
   *
   * @param circleStrokeOpacity the circle-stroke-opacity value
   * @return this
   */
  fun withCircleStrokeOpacity(circleStrokeOpacity: Double): CircleAnnotationOptions {
    this.circleStrokeOpacity = circleStrokeOpacity
    return this
  }

  /**
   * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
   */
  var circleStrokeWidth: Double? = null

  /**
   * Set circle-stroke-width to initialise the circleAnnotation with.
   *
   * The width of the circle's stroke. Strokes are placed outside of the {@link PropertyFactory#circleRadius}. The unit of circleStrokeWidth is in density-independent pixels.
   *
   * @param circleStrokeWidth the circle-stroke-width value
   * @return this
   */
  fun withCircleStrokeWidth(circleStrokeWidth: Double): CircleAnnotationOptions {
    this.circleStrokeWidth = circleStrokeWidth
    return this
  }

  /**
   * Set the Point of the circleAnnotation, which represents the location of the circleAnnotation on the map
   *
   * @param point the location of the circleAnnotation in a longitude and latitude pair
   * @return this
   */
  fun withPoint(point: Point): CircleAnnotationOptions {
    geometry = point
    return this
  }

  /**
   * Get the Point of the circleAnnotation, which represents the location of the circleAnnotation on the map
   *
   * @return the location of the circleAnnotation in a longitude and latitude pair
   */
  fun getPoint(): Point? {
    return geometry
  }

  /**
   * Set the geometry of the circleAnnotation, which represents the location of the circleAnnotation on the map
   *
   * @param geometry the location of the circleAnnotation
   * @return this
   */
  fun withGeometry(geometry: Point): CircleAnnotationOptions {
    this.geometry = geometry
    return this
  }

  /**
   * Get the geometry of the circleAnnotation, which represents the location of the circleAnnotation on the map
   *
   * @return the location of the circleAnnotation
   */
  fun getGeometry(): Point? {
    return geometry
  }

  /**
   * Returns whether this circleAnnotation is draggable, meaning it can be dragged across the screen when touched and moved.
   *
   * @return draggable when touched
   */
  fun getDraggable(): Boolean {
    return isDraggable
  }

  /**
   * Set whether this circleAnnotation should be draggable,
   * meaning it can be dragged across the screen when touched and moved.
   *
   * @param draggable should be draggable
   */
  fun withDraggable(draggable: Boolean): CircleAnnotationOptions {
    isDraggable = draggable
    return this
  }

  /**
   * Set the arbitrary json data of the annotation.
   *
   * @param jsonElement the arbitrary json element data
   */
  fun withData(jsonElement: JsonElement): CircleAnnotationOptions {
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
    annotationManager: AnnotationManager<Point, CircleAnnotation, *, *, *, *, *>
  ): CircleAnnotation {
    if (geometry == null) {
      throw MapboxAnnotationException("geometry field is required")
    }
    val jsonObject = JsonObject()
    circleSortKey?.let {
      jsonObject.addProperty(PROPERTY_CIRCLE_SORT_KEY, it)
    }
    circleBlur?.let {
      jsonObject.addProperty(PROPERTY_CIRCLE_BLUR, it)
    }
    circleColor?.let {
      jsonObject.addProperty(PROPERTY_CIRCLE_COLOR, it)
    }
    circleOpacity?.let {
      jsonObject.addProperty(PROPERTY_CIRCLE_OPACITY, it)
    }
    circleRadius?.let {
      jsonObject.addProperty(PROPERTY_CIRCLE_RADIUS, it)
    }
    circleStrokeColor?.let {
      jsonObject.addProperty(PROPERTY_CIRCLE_STROKE_COLOR, it)
    }
    circleStrokeOpacity?.let {
      jsonObject.addProperty(PROPERTY_CIRCLE_STROKE_OPACITY, it)
    }
    circleStrokeWidth?.let {
      jsonObject.addProperty(PROPERTY_CIRCLE_STROKE_WIDTH, it)
    }
    val circleAnnotation = CircleAnnotation(id, annotationManager, jsonObject, geometry!!)
    circleAnnotation.isDraggable = isDraggable
    circleAnnotation.setData(data)
    return circleAnnotation
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
     * Creates CircleAnnotationOptions out of a Feature.
     *
     * @param feature feature to be converted
     */
    fun fromFeature(feature: Feature): CircleAnnotationOptions? {
      if (feature.geometry() == null) {
        throw MapboxAnnotationException("geometry field is required")
      }
      if (feature.geometry() !is Point) {

        return null
      }

      val options = CircleAnnotationOptions()
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