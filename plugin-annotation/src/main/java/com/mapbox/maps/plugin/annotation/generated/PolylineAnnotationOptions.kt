// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import androidx.annotation.ColorInt
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mapbox.geojson.Feature
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxAnnotationException
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.annotation.AnnotationManager
import com.mapbox.maps.plugin.annotation.AnnotationOptions

/**
 * Builder class from which a polylineAnnotation is created.
 */
class PolylineAnnotationOptions : AnnotationOptions<LineString, PolylineAnnotation> {
  private var isDraggable: Boolean = false
  private var data: JsonElement? = null
  private var geometry: LineString? = null

  /**
   * The display of lines when joining. Default value: "miter".
   */
  var lineJoin: LineJoin? = null

  /**
   * Set line-join to initialise the polylineAnnotation with.
   *
   * The display of lines when joining.
   *
   * @param lineJoin the line-join value
   * @return this
   */
  fun withLineJoin(lineJoin: LineJoin): PolylineAnnotationOptions {
    this.lineJoin = lineJoin
    return this
  }

  /**
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   */
  var lineSortKey: Double? = null

  /**
   * Set line-sort-key to initialise the polylineAnnotation with.
   *
   * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
   *
   * @param lineSortKey the line-sort-key value
   * @return this
   */
  fun withLineSortKey(lineSortKey: Double): PolylineAnnotationOptions {
    this.lineSortKey = lineSortKey
    return this
  }

  /**
   * Vertical offset from ground, in meters. Defaults to 0. Not supported for globe projection at the moment.
   */
  var lineZOffset: Double? = null

  /**
   * Set line-z-offset to initialise the polylineAnnotation with.
   *
   * Vertical offset from ground, in meters. Defaults to 0. Not supported for globe projection at the moment.
   *
   * @param lineZOffset the line-z-offset value
   * @return this
   */
  fun withLineZOffset(lineZOffset: Double): PolylineAnnotationOptions {
    this.lineZOffset = lineZOffset
    return this
  }

  /**
   * Blur applied to the line, in pixels. Default value: 0. Minimum value: 0.
   */
  var lineBlur: Double? = null

  /**
   * Set line-blur to initialise the polylineAnnotation with.
   *
   * Blur applied to the line, in density-independent pixels. The unit of lineBlur is in pixels.
   *
   * @param lineBlur the line-blur value
   * @return this
   */
  fun withLineBlur(lineBlur: Double): PolylineAnnotationOptions {
    this.lineBlur = lineBlur
    return this
  }

  /**
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color. Default value: "rgba(0, 0, 0, 0)".
   */
  var lineBorderColor: String? = null

  /**
   * Set line-border-color to initialise the polylineAnnotation with.
   *
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
   *
   * @param lineBorderColor the line-border-color value
   * @return this
   */
  fun withLineBorderColor(lineBorderColor: String): PolylineAnnotationOptions {
    this.lineBorderColor = lineBorderColor
    return this
  }

  /**
   * Set line-border-color to initialise the polylineAnnotation with.
   *
   * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
   *
   * @param lineBorderColor the line-border-color value with ColorInt format
   * @return this
   */
  fun withLineBorderColor(@ColorInt lineBorderColor: Int): PolylineAnnotationOptions {
    this.lineBorderColor = ColorUtils.colorToRgbaString(lineBorderColor)
    return this
  }

  /**
   * The width of the line border. A value of zero means no border. Default value: 0. Minimum value: 0.
   */
  var lineBorderWidth: Double? = null

  /**
   * Set line-border-width to initialise the polylineAnnotation with.
   *
   * The width of the line border. A value of zero means no border.
   *
   * @param lineBorderWidth the line-border-width value
   * @return this
   */
  fun withLineBorderWidth(lineBorderWidth: Double): PolylineAnnotationOptions {
    this.lineBorderWidth = lineBorderWidth
    return this
  }

  /**
   * The color with which the line will be drawn. Default value: "#000000".
   */
  var lineColor: String? = null

  /**
   * Set line-color to initialise the polylineAnnotation with.
   *
   * The color with which the line will be drawn.
   *
   * @param lineColor the line-color value
   * @return this
   */
  fun withLineColor(lineColor: String): PolylineAnnotationOptions {
    this.lineColor = lineColor
    return this
  }

  /**
   * Set line-color to initialise the polylineAnnotation with.
   *
   * The color with which the line will be drawn.
   *
   * @param lineColor the line-color value with ColorInt format
   * @return this
   */
  fun withLineColor(@ColorInt lineColor: Int): PolylineAnnotationOptions {
    this.lineColor = ColorUtils.colorToRgbaString(lineColor)
    return this
  }

  /**
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap. Default value: 0. Minimum value: 0.
   */
  var lineGapWidth: Double? = null

  /**
   * Set line-gap-width to initialise the polylineAnnotation with.
   *
   * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap. The unit of lineGapWidth is in density-independent pixels.
   *
   * @param lineGapWidth the line-gap-width value
   * @return this
   */
  fun withLineGapWidth(lineGapWidth: Double): PolylineAnnotationOptions {
    this.lineGapWidth = lineGapWidth
    return this
  }

  /**
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset. Default value: 0.
   */
  var lineOffset: Double? = null

  /**
   * Set line-offset to initialise the polylineAnnotation with.
   *
   * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset. The unit of lineOffset is in density-independent pixels.
   *
   * @param lineOffset the line-offset value
   * @return this
   */
  fun withLineOffset(lineOffset: Double): PolylineAnnotationOptions {
    this.lineOffset = lineOffset
    return this
  }

  /**
   * The opacity at which the line will be drawn. Default value: 1. Value range: [0, 1]
   */
  var lineOpacity: Double? = null

  /**
   * Set line-opacity to initialise the polylineAnnotation with.
   *
   * The opacity at which the line will be drawn.
   *
   * @param lineOpacity the line-opacity value
   * @return this
   */
  fun withLineOpacity(lineOpacity: Double): PolylineAnnotationOptions {
    this.lineOpacity = lineOpacity
    return this
  }

  /**
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   */
  var linePattern: String? = null

  /**
   * Set line-pattern to initialise the polylineAnnotation with.
   *
   * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
   *
   * @param linePattern the line-pattern value
   * @return this
   */
  fun withLinePattern(linePattern: String): PolylineAnnotationOptions {
    this.linePattern = linePattern
    return this
  }

  /**
   * Stroke thickness. Default value: 1. Minimum value: 0.
   */
  var lineWidth: Double? = null

  /**
   * Set line-width to initialise the polylineAnnotation with.
   *
   * Stroke thickness. The unit of lineWidth is in density-independent pixels.
   *
   * @param lineWidth the line-width value
   * @return this
   */
  fun withLineWidth(lineWidth: Double): PolylineAnnotationOptions {
    this.lineWidth = lineWidth
    return this
  }

  /**
   * Set a list of Point for the line, which represents the locations of the line on the map
   *
   * @param point a list of the locations of the line in a longitude and latitude pairs
   * @return this
   */
  fun withPoints(points: List<Point>): PolylineAnnotationOptions {
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
   * Set the geometry of the polylineAnnotation, which represents the location of the polylineAnnotation on the map
   *
   * @param geometry the location of the polylineAnnotation
   * @return this
   */
  fun withGeometry(geometry: LineString): PolylineAnnotationOptions {
    this.geometry = geometry
    return this
  }

  /**
   * Get the geometry of the polylineAnnotation, which represents the location of the polylineAnnotation on the map
   *
   * @return the location of the polylineAnnotation
   */
  fun getGeometry(): LineString? {
    return geometry
  }

  /**
   * Returns whether this polylineAnnotation is draggable, meaning it can be dragged across the screen when touched and moved.
   *
   * @return draggable when touched
   */
  fun getDraggable(): Boolean {
    return isDraggable
  }

  /**
   * Set whether this polylineAnnotation should be draggable,
   * meaning it can be dragged across the screen when touched and moved.
   *
   * @param draggable should be draggable
   */
  fun withDraggable(draggable: Boolean): PolylineAnnotationOptions {
    isDraggable = draggable
    return this
  }

  /**
   * Set the arbitrary json data of the annotation.
   *
   * @param jsonElement the arbitrary json element data
   */
  fun withData(jsonElement: JsonElement): PolylineAnnotationOptions {
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
    id: String,
    annotationManager: AnnotationManager<LineString, PolylineAnnotation, *, *, *, *, *>
  ): PolylineAnnotation {
    if (geometry == null) {
      throw MapboxAnnotationException("geometry field is required")
    }
    val jsonObject = JsonObject()
    lineJoin?.let {
      jsonObject.addProperty(PROPERTY_LINE_JOIN, it.value)
    }
    lineSortKey?.let {
      jsonObject.addProperty(PROPERTY_LINE_SORT_KEY, it)
    }
    lineZOffset?.let {
      jsonObject.addProperty(PROPERTY_LINE_Z_OFFSET, it)
    }
    lineBlur?.let {
      jsonObject.addProperty(PROPERTY_LINE_BLUR, it)
    }
    lineBorderColor?.let {
      jsonObject.addProperty(PROPERTY_LINE_BORDER_COLOR, it)
    }
    lineBorderWidth?.let {
      jsonObject.addProperty(PROPERTY_LINE_BORDER_WIDTH, it)
    }
    lineColor?.let {
      jsonObject.addProperty(PROPERTY_LINE_COLOR, it)
    }
    lineGapWidth?.let {
      jsonObject.addProperty(PROPERTY_LINE_GAP_WIDTH, it)
    }
    lineOffset?.let {
      jsonObject.addProperty(PROPERTY_LINE_OFFSET, it)
    }
    lineOpacity?.let {
      jsonObject.addProperty(PROPERTY_LINE_OPACITY, it)
    }
    linePattern?.let {
      jsonObject.addProperty(PROPERTY_LINE_PATTERN, it)
    }
    lineWidth?.let {
      jsonObject.addProperty(PROPERTY_LINE_WIDTH, it)
    }
    val polylineAnnotation = PolylineAnnotation(id, annotationManager, jsonObject, geometry!!)
    polylineAnnotation.isDraggable = isDraggable
    polylineAnnotation.setData(data)
    return polylineAnnotation
  }

  /**
   * Static variables and methods.
   */
  companion object {

    /** The property for line-join */
    const val PROPERTY_LINE_JOIN = "line-join"

    /** The property for line-sort-key */
    const val PROPERTY_LINE_SORT_KEY = "line-sort-key"

    /** The property for line-z-offset */
    const val PROPERTY_LINE_Z_OFFSET = "line-z-offset"

    /** The property for line-blur */
    const val PROPERTY_LINE_BLUR = "line-blur"

    /** The property for line-border-color */
    const val PROPERTY_LINE_BORDER_COLOR = "line-border-color"

    /** The property for line-border-width */
    const val PROPERTY_LINE_BORDER_WIDTH = "line-border-width"

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
     * Creates PolylineAnnotationOptions out of a Feature.
     *
     * @param feature feature to be converted
     */
    fun fromFeature(feature: Feature): PolylineAnnotationOptions? {
      if (feature.geometry() == null) {
        throw MapboxAnnotationException("geometry field is required")
      }
      if (feature.geometry() !is LineString) {

        return null
      }

      val options = PolylineAnnotationOptions()
      options.geometry = feature.geometry() as (LineString)
      if (feature.hasProperty(PROPERTY_LINE_JOIN)) {
        options.lineJoin = LineJoin.valueOf(feature.getProperty(PROPERTY_LINE_JOIN).asString)
      }
      if (feature.hasProperty(PROPERTY_LINE_SORT_KEY)) {
        options.lineSortKey = feature.getProperty(PROPERTY_LINE_SORT_KEY).asDouble
      }
      if (feature.hasProperty(PROPERTY_LINE_Z_OFFSET)) {
        options.lineZOffset = feature.getProperty(PROPERTY_LINE_Z_OFFSET).asDouble
      }
      if (feature.hasProperty(PROPERTY_LINE_BLUR)) {
        options.lineBlur = feature.getProperty(PROPERTY_LINE_BLUR).asDouble
      }
      if (feature.hasProperty(PROPERTY_LINE_BORDER_COLOR)) {
        options.lineBorderColor = feature.getProperty(PROPERTY_LINE_BORDER_COLOR).asString
      }
      if (feature.hasProperty(PROPERTY_LINE_BORDER_WIDTH)) {
        options.lineBorderWidth = feature.getProperty(PROPERTY_LINE_BORDER_WIDTH).asDouble
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