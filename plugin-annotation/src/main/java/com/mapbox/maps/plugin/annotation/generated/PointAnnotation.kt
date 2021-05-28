// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import android.graphics.Bitmap
import androidx.annotation.ColorInt
import com.google.gson.*
import com.mapbox.android.gestures.MoveDistancesObject
import com.mapbox.geojson.*
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.annotation.Annotation
import com.mapbox.maps.plugin.annotation.AnnotationManager
import com.mapbox.maps.plugin.annotation.AnnotationType
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate

/**
 * Class for PointAnnotation
 */
class PointAnnotation(
  id: Long,
  /** The annotation manger that manipulate this annotation */
  private val annotationManager: AnnotationManager<Point, PointAnnotation, *, *, *, *, *>,
  jsonObject: JsonObject,
  geometry: Point
) : Annotation<Point>(id, jsonObject, geometry) {

  init {
    jsonObject.addProperty(ID_KEY, id)
  }

  /**
   * Get the type of this annotation
   */
  override fun getType(): AnnotationType {
    return AnnotationType.PointAnnotation
  }

  /**
   * The Point of the pointAnnotation, which represents the location of the pointAnnotation on the map
   *
   * To update the pointAnnotation on the map use {@link PointAnnotationManager#update(Annotation)}.
   */
  var point: Point
    get() {
      return geometry
    }
    set(value) {
      geometry = value
    }

  /**
   * The bitmap image for this Symbol
   *
   * Will not take effect if [iconImage] has been set.
   */
  var iconImageBitmap: Bitmap? = null
    /**
     * Set the iconImageBitmap property
     *
     * @param value the iconBitmap
     */
    set(value) {
      value?.let {
        field = it
        if (iconImage == null) {
          iconImage = ICON_DEFAULT_NAME_PREFIX + it.generationId
        }
      }
    }
  // Property accessors
  /**
   * The iconAnchor property
   *
   * Part of the icon placed closest to the anchor.
   */
  var iconAnchor: IconAnchor?
    /**
     * Get the iconAnchor property
     *
     * @return property wrapper value around IconAnchor
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)
      value?.let {
        return IconAnchor.valueOf(it.asString)
      }
      return null
    }
    /**
     * Set the iconAnchor property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for IconAnchor
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_ICON_ANCHOR, it.value)
      }
    }

  /**
   * The iconImage property
   *
   * Name of image in sprite to use for drawing an image background.
   */
  var iconImage: String?
    /**
     * Get the iconImage property
     *
     * @return property wrapper value around String
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the iconImage property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for String
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_ICON_IMAGE, it)
      }
    }

  /**
   * The iconOffset property
   *
   * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of {@link PropertyFactory#iconSize} to obtain the final offset in density-independent pixels. When combined with {@link PropertyFactory#iconRotate} the offset will be as if the rotated direction was up.
   */
  var iconOffset: List<Double>?
    /**
     * Get the iconOffset property
     *
     * @return [Point] value for List<Double>
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)
      value?.let {
        return (it as JsonArray).map { it.toString().toDouble() }
      }
      return null
    }
    /**
     * Set the iconOffset property.
     * To update the pointAnnotation on the map use {@link PointAnnotationManager#update(Annotation)}.
     */
    set(value) {
      val jsonArray = JsonArray()
      value?.let {
        it.forEach { jsonArray.add(it) }
        jsonObject.add(PointAnnotationOptions.PROPERTY_ICON_OFFSET, jsonArray)
      }
    }

  /**
   * The iconRotate property
   *
   * Rotates the icon clockwise.
   */
  var iconRotate: Double?
    /**
     * Get the iconRotate property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the iconRotate property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_ICON_ROTATE, it)
      }
    }

  /**
   * The iconSize property
   *
   * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by {@link PropertyFactory#iconSize}. 1 is the original size; 3 triples the size of the image.
   */
  var iconSize: Double?
    /**
     * Get the iconSize property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the iconSize property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_ICON_SIZE, it)
      }
    }

  /**
   * The symbolSortKey property
   *
   * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first.  When {@link PropertyFactory#iconAllowOverlap} or {@link PropertyFactory#textAllowOverlap} is `false`, features with a lower sort key will have priority during placement. When {@link PropertyFactory#iconAllowOverlap} or {@link PropertyFactory#textAllowOverlap} is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
   */
  var symbolSortKey: Double?
    /**
     * Get the symbolSortKey property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the symbolSortKey property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY, it)
      }
    }

  /**
   * The textAnchor property
   *
   * Part of the text placed closest to the anchor.
   */
  var textAnchor: TextAnchor?
    /**
     * Get the textAnchor property
     *
     * @return property wrapper value around TextAnchor
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)
      value?.let {
        return TextAnchor.valueOf(it.asString)
      }
      return null
    }
    /**
     * Set the textAnchor property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for TextAnchor
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR, it.value)
      }
    }

  /**
   * The textField property
   *
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options.
   */
  var textField: String?
    /**
     * Get the textField property
     *
     * @return property wrapper value around String
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the textField property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for String
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_TEXT_FIELD, it)
      }
    }

  /**
   * The textJustify property
   *
   * Text justification options.
   */
  var textJustify: TextJustify?
    /**
     * Get the textJustify property
     *
     * @return property wrapper value around TextJustify
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)
      value?.let {
        return TextJustify.valueOf(it.asString)
      }
      return null
    }
    /**
     * Set the textJustify property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for TextJustify
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY, it.value)
      }
    }

  /**
   * The textLetterSpacing property
   *
   * Text tracking amount.
   */
  var textLetterSpacing: Double?
    /**
     * Get the textLetterSpacing property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textLetterSpacing property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING, it)
      }
    }

  /**
   * The textMaxWidth property
   *
   * The maximum line width for text wrapping.
   */
  var textMaxWidth: Double?
    /**
     * Get the textMaxWidth property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textMaxWidth property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH, it)
      }
    }

  /**
   * The textOffset property
   *
   * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
   */
  var textOffset: List<Double>?
    /**
     * Get the textOffset property
     *
     * @return [Point] value for List<Double>
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)
      value?.let {
        return (it as JsonArray).map { it.toString().toDouble() }
      }
      return null
    }
    /**
     * Set the textOffset property.
     * To update the pointAnnotation on the map use {@link PointAnnotationManager#update(Annotation)}.
     */
    set(value) {
      val jsonArray = JsonArray()
      value?.let {
        it.forEach { jsonArray.add(it) }
        jsonObject.add(PointAnnotationOptions.PROPERTY_TEXT_OFFSET, jsonArray)
      }
    }

  /**
   * The textRadialOffset property
   *
   * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with {@link PropertyFactory#textVariableAnchor}, which defaults to using the two-dimensional {@link PropertyFactory#textOffset} if present.
   */
  var textRadialOffset: Double?
    /**
     * Get the textRadialOffset property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textRadialOffset property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET, it)
      }
    }

  /**
   * The textRotate property
   *
   * Rotates the text clockwise.
   */
  var textRotate: Double?
    /**
     * Get the textRotate property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textRotate property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_TEXT_ROTATE, it)
      }
    }

  /**
   * The textSize property
   *
   * Font size.
   */
  var textSize: Double?
    /**
     * Get the textSize property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textSize property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_TEXT_SIZE, it)
      }
    }

  /**
   * The textTransform property
   *
   * Specifies how to capitalize text, similar to the CSS {@link PropertyFactory#textTransform} property.
   */
  var textTransform: TextTransform?
    /**
     * Get the textTransform property
     *
     * @return property wrapper value around TextTransform
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)
      value?.let {
        return TextTransform.valueOf(it.asString)
      }
      return null
    }
    /**
     * Set the textTransform property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for TextTransform
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM, it.value)
      }
    }

  /**
   * The iconColor property in Int
   * The color of the icon. This can only be used with sdf icons.
   */
  var iconColorInt: Int?
    /**
     * Get the iconColor property
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the iconColor property
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(@ColorInt value) {
      value?.let {
        jsonObject.addProperty(
          PointAnnotationOptions.PROPERTY_ICON_COLOR, ColorUtils.colorToRgbaString(value)
        )
      }
    }

  /**
   * The iconColor property in String
   *
   * The color of the icon. This can only be used with sdf icons.
   */
  var iconColorString: String?
    /**
     * Get the iconColor property
     * @return color value for String
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the iconColor property
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(value) {
      jsonObject.addProperty(PointAnnotationOptions.PROPERTY_ICON_COLOR, value)
    }

  /**
   * The iconHaloBlur property
   *
   * Fade out the halo towards the outside.
   */
  var iconHaloBlur: Double?
    /**
     * Get the iconHaloBlur property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the iconHaloBlur property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR, it)
      }
    }

  /**
   * The iconHaloColor property in Int
   * The color of the icon's halo. Icon halos can only be used with SDF icons.
   */
  var iconHaloColorInt: Int?
    /**
     * Get the iconHaloColor property
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the iconHaloColor property
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(@ColorInt value) {
      value?.let {
        jsonObject.addProperty(
          PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR, ColorUtils.colorToRgbaString(value)
        )
      }
    }

  /**
   * The iconHaloColor property in String
   *
   * The color of the icon's halo. Icon halos can only be used with SDF icons.
   */
  var iconHaloColorString: String?
    /**
     * Get the iconHaloColor property
     * @return color value for String
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the iconHaloColor property
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(value) {
      jsonObject.addProperty(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR, value)
    }

  /**
   * The iconHaloWidth property
   *
   * Distance of halo to the icon outline.
   */
  var iconHaloWidth: Double?
    /**
     * Get the iconHaloWidth property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the iconHaloWidth property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH, it)
      }
    }

  /**
   * The iconOpacity property
   *
   * The opacity at which the icon will be drawn.
   */
  var iconOpacity: Double?
    /**
     * Get the iconOpacity property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the iconOpacity property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_ICON_OPACITY, it)
      }
    }

  /**
   * The textColor property in Int
   * The color with which the text will be drawn.
   */
  var textColorInt: Int?
    /**
     * Get the textColor property
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the textColor property
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(@ColorInt value) {
      value?.let {
        jsonObject.addProperty(
          PointAnnotationOptions.PROPERTY_TEXT_COLOR, ColorUtils.colorToRgbaString(value)
        )
      }
    }

  /**
   * The textColor property in String
   *
   * The color with which the text will be drawn.
   */
  var textColorString: String?
    /**
     * Get the textColor property
     * @return color value for String
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the textColor property
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(value) {
      jsonObject.addProperty(PointAnnotationOptions.PROPERTY_TEXT_COLOR, value)
    }

  /**
   * The textHaloBlur property
   *
   * The halo's fadeout distance towards the outside.
   */
  var textHaloBlur: Double?
    /**
     * Get the textHaloBlur property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textHaloBlur property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR, it)
      }
    }

  /**
   * The textHaloColor property in Int
   * The color of the text's halo, which helps it stand out from backgrounds.
   */
  var textHaloColorInt: Int?
    /**
     * Get the textHaloColor property
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the textHaloColor property
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(@ColorInt value) {
      value?.let {
        jsonObject.addProperty(
          PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR, ColorUtils.colorToRgbaString(value)
        )
      }
    }

  /**
   * The textHaloColor property in String
   *
   * The color of the text's halo, which helps it stand out from backgrounds.
   */
  var textHaloColorString: String?
    /**
     * Get the textHaloColor property
     * @return color value for String
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the textHaloColor property
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param color value for String
     */
    set(value) {
      jsonObject.addProperty(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR, value)
    }

  /**
   * The textHaloWidth property
   *
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
   */
  var textHaloWidth: Double?
    /**
     * Get the textHaloWidth property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textHaloWidth property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH, it)
      }
    }

  /**
   * The textOpacity property
   *
   * The opacity at which the text will be drawn.
   */
  var textOpacity: Double?
    /**
     * Get the textOpacity property
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textOpacity property
     *
     * To update the pointAnnotation on the map use {@link pointAnnotationManager#update(Annotation)}.
     *
     * @param value constant property value for Double
     */
    set(value) {
      value?.let {
        jsonObject.addProperty(PointAnnotationOptions.PROPERTY_TEXT_OPACITY, it)
      }
    }

  /**
   * Get the offset geometry for the touch point
   */
  override fun getOffsetGeometry(
    mapCameraManagerDelegate: MapCameraManagerDelegate,
    moveDistancesObject: MoveDistancesObject,
    touchAreaShiftX: Int,
    touchAreaShiftY: Int
  ): Point? {
    val point = mapCameraManagerDelegate.coordinateForPixel(
      ScreenCoordinate(
        (moveDistancesObject.currentX - touchAreaShiftX).toDouble(),
        (moveDistancesObject.currentY - touchAreaShiftY).toDouble(),
      )
    )
    if (point.latitude() > MAX_MERCATOR_LATITUDE || point.latitude() < MIN_MERCATOR_LATITUDE) {
      return null
    }
    return point
  }

  /**
   * Set the used data-driven properties
   */
  override fun setUsedDataDrivenProperties() {
    jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_IMAGE)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_OFFSET)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_ROTATE)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_SIZE)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_FIELD)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_SIZE)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_COLOR)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_OPACITY)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_COLOR)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)
    }
    jsonObject.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)?.let {
      annotationManager.enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)
    }
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /** the Id key for annotation */
    const val ID_KEY: String = "PointAnnotation"
    /** the default name for icon */
    const val ICON_DEFAULT_NAME_PREFIX = "icon_default_name_"
  }
}