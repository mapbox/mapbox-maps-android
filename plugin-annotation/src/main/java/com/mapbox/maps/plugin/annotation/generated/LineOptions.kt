// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mapbox.geojson.Feature
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.plugin.annotation.AnnotationManager
import com.mapbox.maps.plugin.annotation.AnnotationOptions

/**
 * Builder class from which a line is created.
 */
class LineOptions : AnnotationOptions<LineString, Line> {
  private var isDraggable: Boolean = false
  private var data: JsonElement? = null
  private var geometry: LineString? = null

  /**
   * The display of lines when joining.
   */
  var lineJoin: LineJoin = LineJoin.MITER

  /**
   * Set line-join to initialise the line with.
   * <p>
   * The display of lines when joining.
   * </p>
   * @param lineJoin the line-join value
   * @return this
   */
  fun withLineJoin(lineJoin: LineJoin): LineOptions {
    this.lineJoin = lineJoin
    return this
  }

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  var lineSortKey: Double? = null

  /**
   * Set line-sort-key to initialise the line with.
   * <p>
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   * </p>
   * @param lineSortKey the line-sort-key value
   * @return this
   */
  fun withLineSortKey(lineSortKey: Double): LineOptions {
    this.lineSortKey = lineSortKey
    return this
  }

  /**
   * Blur applied to the line, in pixels.
   */
  var lineBlur: Double = 0.0

  /**
   * Set line-blur to initialise the line with.
   * <p>
   * Blur applied to the line, in density-independent pixels.
   * </p>
   * @param lineBlur the line-blur value
   * @return this
   */
  fun withLineBlur(lineBlur: Double): LineOptions {
    this.lineBlur = lineBlur
    return this
  }

  /**
   * The color with which the line will be drawn.
   */
  var lineColor: String = "rgba(0, 0, 0, 1)"

  /**
   * Set line-color to initialise the line with.
   * <p>
   * The color with which the line will be drawn.
   * </p>
   * @param lineColor the line-color value
   * @return this
   */
  fun withLineColor(lineColor: String): LineOptions {
    this.lineColor = lineColor
    return this
  }

  /**
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
   */
  var lineGapWidth: Double = 0.0

  /**
   * Set line-gap-width to initialise the line with.
   * <p>
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
   * </p>
   * @param lineGapWidth the line-gap-width value
   * @return this
   */
  fun withLineGapWidth(lineGapWidth: Double): LineOptions {
    this.lineGapWidth = lineGapWidth
    return this
  }

  /**
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
   */
  var lineOffset: Double = 0.0

  /**
   * Set line-offset to initialise the line with.
   * <p>
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
   * </p>
   * @param lineOffset the line-offset value
   * @return this
   */
  fun withLineOffset(lineOffset: Double): LineOptions {
    this.lineOffset = lineOffset
    return this
  }

  /**
   * The opacity at which the line will be drawn.
   */
  var lineOpacity: Double = 1.0

  /**
   * Set line-opacity to initialise the line with.
   * <p>
   * The opacity at which the line will be drawn.
   * </p>
   * @param lineOpacity the line-opacity value
   * @return this
   */
  fun withLineOpacity(lineOpacity: Double): LineOptions {
    this.lineOpacity = lineOpacity
    return this
  }

  /**
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  var linePattern: String? = null

  /**
   * Set line-pattern to initialise the line with.
   * <p>
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   * </p>
   * @param linePattern the line-pattern value
   * @return this
   */
  fun withLinePattern(linePattern: String): LineOptions {
    this.linePattern = linePattern
    return this
  }

  /**
   * Stroke thickness.
   */
  var lineWidth: Double = 1.0

  /**
   * Set line-width to initialise the line with.
   * <p>
   * Stroke thickness.
   * </p>
   * @param lineWidth the line-width value
   * @return this
   */
  fun withLineWidth(lineWidth: Double): LineOptions {
    this.lineWidth = lineWidth
    return this
  }

  /**
   * Set a list of Point for the line, which represents the locations of the line on the map
   *
   * @param point a list of the locations of the line in a longitude and latitude pairs
   * @return this
   */
  fun withPoints(points: List<Point>): LineOptions {
    geometry = LineString.fromLngLats(points)
    return this
  }

  /**
   * Get a list of Point for the line, which represents the locations of the line on the map
   *
   * @return a list of the locations of the line in a longitude and latitude pairs
   */
  fun getPoints(): List<Point> {
    return geometry?.coordinates() as List<Point>
  }

  /**
   * Set the geometry of the line, which represents the location of the line on the map
   *
   * @param geometry the location of the line
   * @return this
   */
  fun withGeometry(geometry: LineString): LineOptions {
    this.geometry = geometry
    return this
  }

  /**
   * Get the geometry of the line, which represents the location of the line on the map
   *
   * @return the location of the line
   */
  fun getGeometry(): LineString? {
    return geometry
  }

  /**
   * Returns whether this line is draggable, meaning it can be dragged across the screen when touched and moved.
   *
   * @return draggable when touched
   */
  fun getDraggable(): Boolean {
    return isDraggable
  }

  /**
   * Set whether this line should be draggable,
   * meaning it can be dragged across the screen when touched and moved.
   *
   * @param draggable should be draggable
   */
  fun withDraggable(draggable: Boolean): LineOptions {
    isDraggable = draggable
    return this
  }

  /**
   * Set the arbitrary json data of the annotation.
   *
   * @param jsonElement the arbitrary json element data
   */
  fun withData(jsonElement: JsonElement): LineOptions {
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
    annotationManager: AnnotationManager<LineString, Line, *, *, *, *>
  ): Line {
    if (geometry == null) {
      throw RuntimeException("geometry field is required")
    }
    val jsonObject = JsonObject()
    jsonObject.addProperty(PROPERTY_LINE_JOIN, lineJoin.value)
    jsonObject.addProperty(PROPERTY_LINE_SORT_KEY, lineSortKey)
    jsonObject.addProperty(PROPERTY_LINE_BLUR, lineBlur)
    jsonObject.addProperty(PROPERTY_LINE_COLOR, lineColor)
    jsonObject.addProperty(PROPERTY_LINE_GAP_WIDTH, lineGapWidth)
    jsonObject.addProperty(PROPERTY_LINE_OFFSET, lineOffset)
    jsonObject.addProperty(PROPERTY_LINE_OPACITY, lineOpacity)
    jsonObject.addProperty(PROPERTY_LINE_PATTERN, linePattern)
    jsonObject.addProperty(PROPERTY_LINE_WIDTH, lineWidth)
    val line = Line(id, annotationManager, jsonObject, geometry!!)
    line.isDraggable = isDraggable
    line.setData(data)
    return line
  }

  /**
   * Static variables and methods.
   */
  companion object {

    /** The property for line-join */
    const val PROPERTY_LINE_JOIN = "line-join"

    /** The property for line-sort-key */
    const val PROPERTY_LINE_SORT_KEY = "line-sort-key"

    /** The property for line-blur */
    const val PROPERTY_LINE_BLUR = "line-blur"

    /** The property for line-color */
    const val PROPERTY_LINE_COLOR = "line-color"

    /** The property for line-gap-width */
    const val PROPERTY_LINE_GAP_WIDTH = "line-gap-width"

    /** The property for line-offset */
    const val PROPERTY_LINE_OFFSET = "line-offset"

    /** The property for line-opacity */
    const val PROPERTY_LINE_OPACITY = "line-opacity"

    /** The property for line-pattern */
    const val PROPERTY_LINE_PATTERN = "line-pattern"

    /** The property for line-width */
    const val PROPERTY_LINE_WIDTH = "line-width"

    /** The property for is-draggable */
    private const val PROPERTY_IS_DRAGGABLE = "is-draggable"

    /**
     * Creates LineOptions out of a Feature.
     *
     * @param feature feature to be converted
     */
    fun fromFeature(feature: Feature): LineOptions? {
      if (feature.geometry() == null) {
        throw RuntimeException("geometry field is required")
      }
      if (feature.geometry() !is LineString) {

        return null
      }

      val options = LineOptions()
      options.geometry = feature.geometry() as (LineString)
      if (feature.hasProperty(PROPERTY_LINE_JOIN)) {
        options.lineJoin = LineJoin.valueOf(feature.getProperty(PROPERTY_LINE_JOIN).asString)
      }
      if (feature.hasProperty(PROPERTY_LINE_SORT_KEY)) {
        options.lineSortKey = feature.getProperty(PROPERTY_LINE_SORT_KEY).asDouble
      }
      if (feature.hasProperty(PROPERTY_LINE_BLUR)) {
        options.lineBlur = feature.getProperty(PROPERTY_LINE_BLUR).asDouble
      }
      if (feature.hasProperty(PROPERTY_LINE_COLOR)) {
        options.lineColor = feature.getProperty(PROPERTY_LINE_COLOR).asString
      }
      if (feature.hasProperty(PROPERTY_LINE_GAP_WIDTH)) {
        options.lineGapWidth = feature.getProperty(PROPERTY_LINE_GAP_WIDTH).asDouble
      }
      if (feature.hasProperty(PROPERTY_LINE_OFFSET)) {
        options.lineOffset = feature.getProperty(PROPERTY_LINE_OFFSET).asDouble
      }
      if (feature.hasProperty(PROPERTY_LINE_OPACITY)) {
        options.lineOpacity = feature.getProperty(PROPERTY_LINE_OPACITY).asDouble
      }
      if (feature.hasProperty(PROPERTY_LINE_PATTERN)) {
        options.linePattern = feature.getProperty(PROPERTY_LINE_PATTERN).asString
      }
      if (feature.hasProperty(PROPERTY_LINE_WIDTH)) {
        options.lineWidth = feature.getProperty(PROPERTY_LINE_WIDTH).asDouble
      }
      if (feature.hasProperty(PROPERTY_IS_DRAGGABLE)) {
        options.isDraggable = feature.getProperty(PROPERTY_IS_DRAGGABLE).asBoolean
      }
      return options
    }
  }
}