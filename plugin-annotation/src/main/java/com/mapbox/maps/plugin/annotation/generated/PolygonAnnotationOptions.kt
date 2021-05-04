// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import androidx.annotation.ColorInt
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.annotation.AnnotationManager
import com.mapbox.maps.plugin.annotation.AnnotationOptions

/**
 * Builder class from which a polygonAnnotation is created.
 */
class PolygonAnnotationOptions : AnnotationOptions<Polygon, PolygonAnnotation> {
  private var isDraggable: Boolean = false
  private var data: JsonElement? = null
  private var geometry: Polygon? = null

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  var fillSortKey: Double? = null

  /**
   * Set fill-sort-key to initialise the polygonAnnotation with.
   *
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * @param fillSortKey the fill-sort-key value
   * @return this
   */
  fun withFillSortKey(fillSortKey: Double): PolygonAnnotationOptions {
    this.fillSortKey = fillSortKey
    return this
  }

  /**
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   */
  var fillColor: String? = null

  /**
   * Set fill-color to initialise the polygonAnnotation with.
   *
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   *
   * @param fillColor the fill-color value
   * @return this
   */
  fun withFillColor(fillColor: String): PolygonAnnotationOptions {
    this.fillColor = fillColor
    return this
  }

  /**
   * Set fill-color to initialise the polygonAnnotation with.
   *
   * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
   *
   * @param fillColor the fill-color value with ColorInt format
   * @return this
   */
  fun withFillColor(@ColorInt fillColor: Int): PolygonAnnotationOptions {
    this.fillColor = ColorUtils.colorToRgbaString(fillColor)
    return this
  }

  /**
   * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
   */
  var fillOpacity: Double? = null

  /**
   * Set fill-opacity to initialise the polygonAnnotation with.
   *
   * The opacity of the entire fill layer. In contrast to the {@link PropertyFactory#fillColor}, this value will also affect the 1px stroke around the fill, if the stroke is used.
   *
   * @param fillOpacity the fill-opacity value
   * @return this
   */
  fun withFillOpacity(fillOpacity: Double): PolygonAnnotationOptions {
    this.fillOpacity = fillOpacity
    return this
  }

  /**
   * The outline color of the fill. Matches the value of `fill-color` if unspecified.
   */
  var fillOutlineColor: String? = null

  /**
   * Set fill-outline-color to initialise the polygonAnnotation with.
   *
   * The outline color of the fill. Matches the value of {@link PropertyFactory#fillColor} if unspecified.
   *
   * @param fillOutlineColor the fill-outline-color value
   * @return this
   */
  fun withFillOutlineColor(fillOutlineColor: String): PolygonAnnotationOptions {
    this.fillOutlineColor = fillOutlineColor
    return this
  }

  /**
   * Set fill-outline-color to initialise the polygonAnnotation with.
   *
   * The outline color of the fill. Matches the value of {@link PropertyFactory#fillColor} if unspecified.
   *
   * @param fillOutlineColor the fill-outline-color value with ColorInt format
   * @return this
   */
  fun withFillOutlineColor(@ColorInt fillOutlineColor: Int): PolygonAnnotationOptions {
    this.fillOutlineColor = ColorUtils.colorToRgbaString(fillOutlineColor)
    return this
  }

  /**
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  var fillPattern: String? = null

  /**
   * Set fill-pattern to initialise the polygonAnnotation with.
   *
   * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * @param fillPattern the fill-pattern value
   * @return this
   */
  fun withFillPattern(fillPattern: String): PolygonAnnotationOptions {
    this.fillPattern = fillPattern
    return this
  }

  /**
   * Set a list of lists of Point for the fill, which represents the locations of the fill on the map
   *
   * @param points a list of a lists of the locations of the line in a longitude and latitude pairs
   * @return this
   */
  fun withPoints(points: List<List<Point>>): PolygonAnnotationOptions {
    geometry = Polygon.fromLngLats(points)
    return this
  }

  /**
   * Get a list of lists of Point for the fill, which represents the locations of the fill on the map
   *
   * @return a list of a lists of the locations of the line in a longitude and latitude pairs
   */
  fun getPoints(): List<List<Point>> {
    return geometry?.coordinates() as List<List<Point>>
  }

  /**
   * Set the geometry of the polygonAnnotation, which represents the location of the polygonAnnotation on the map
   *
   * @param geometry the location of the polygonAnnotation
   * @return this
   */
  fun withGeometry(geometry: Polygon): PolygonAnnotationOptions {
    this.geometry = geometry
    return this
  }

  /**
   * Get the geometry of the polygonAnnotation, which represents the location of the polygonAnnotation on the map
   *
   * @return the location of the polygonAnnotation
   */
  fun getGeometry(): Polygon? {
    return geometry
  }

  /**
   * Returns whether this polygonAnnotation is draggable, meaning it can be dragged across the screen when touched and moved.
   *
   * @return draggable when touched
   */
  fun getDraggable(): Boolean {
    return isDraggable
  }

  /**
   * Set whether this polygonAnnotation should be draggable,
   * meaning it can be dragged across the screen when touched and moved.
   *
   * @param draggable should be draggable
   */
  fun withDraggable(draggable: Boolean): PolygonAnnotationOptions {
    isDraggable = draggable
    return this
  }

  /**
   * Set the arbitrary json data of the annotation.
   *
   * @param jsonElement the arbitrary json element data
   */
  fun withData(jsonElement: JsonElement): PolygonAnnotationOptions {
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
    annotationManager: AnnotationManager<Polygon, PolygonAnnotation, *, *, *, *>
  ): PolygonAnnotation {
    if (geometry == null) {
      throw RuntimeException("geometry field is required")
    }
    val jsonObject = JsonObject()
    fillSortKey?.let {
      jsonObject.addProperty(PROPERTY_FILL_SORT_KEY, it)
    }
    fillColor?.let {
      jsonObject.addProperty(PROPERTY_FILL_COLOR, it)
    }
    fillOpacity?.let {
      jsonObject.addProperty(PROPERTY_FILL_OPACITY, it)
    }
    fillOutlineColor?.let {
      jsonObject.addProperty(PROPERTY_FILL_OUTLINE_COLOR, it)
    }
    fillPattern?.let {
      jsonObject.addProperty(PROPERTY_FILL_PATTERN, it)
    }
    val polygonAnnotation = PolygonAnnotation(id, annotationManager, jsonObject, geometry!!)
    polygonAnnotation.isDraggable = isDraggable
    polygonAnnotation.setData(data)
    return polygonAnnotation
  }

  /**
   * Static variables and methods.
   */
  companion object {

    /** The property for fill-sort-key */
    const val PROPERTY_FILL_SORT_KEY = "fill-sort-key"

    /** The property for fill-color */
    const val PROPERTY_FILL_COLOR = "fill-color"

    /** The property for fill-opacity */
    const val PROPERTY_FILL_OPACITY = "fill-opacity"

    /** The property for fill-outline-color */
    const val PROPERTY_FILL_OUTLINE_COLOR = "fill-outline-color"

    /** The property for fill-pattern */
    const val PROPERTY_FILL_PATTERN = "fill-pattern"

    /** The property for is-draggable */
    private const val PROPERTY_IS_DRAGGABLE = "is-draggable"

    /**
     * Creates PolygonAnnotationOptions out of a Feature.
     *
     * @param feature feature to be converted
     */
    fun fromFeature(feature: Feature): PolygonAnnotationOptions? {
      if (feature.geometry() == null) {
        throw RuntimeException("geometry field is required")
      }
      if (feature.geometry() !is Polygon) {
        return null
      }

      val options = PolygonAnnotationOptions()
      options.geometry = feature.geometry() as (Polygon)
      if (feature.hasProperty(PROPERTY_FILL_SORT_KEY)) {
        options.fillSortKey = feature.getProperty(PROPERTY_FILL_SORT_KEY).asDouble
      }
      if (feature.hasProperty(PROPERTY_FILL_COLOR)) {
        options.fillColor = feature.getProperty(PROPERTY_FILL_COLOR).asString
      }
      if (feature.hasProperty(PROPERTY_FILL_OPACITY)) {
        options.fillOpacity = feature.getProperty(PROPERTY_FILL_OPACITY).asDouble
      }
      if (feature.hasProperty(PROPERTY_FILL_OUTLINE_COLOR)) {
        options.fillOutlineColor = feature.getProperty(PROPERTY_FILL_OUTLINE_COLOR).asString
      }
      if (feature.hasProperty(PROPERTY_FILL_PATTERN)) {
        options.fillPattern = feature.getProperty(PROPERTY_FILL_PATTERN).asString
      }
      if (feature.hasProperty(PROPERTY_IS_DRAGGABLE)) {
        options.isDraggable = feature.getProperty(PROPERTY_IS_DRAGGABLE).asBoolean
      }
      return options
    }
  }
}