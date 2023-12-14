// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import android.graphics.Bitmap
import androidx.annotation.ColorInt
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxAnnotationException
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.IconTextFit
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.TextJustify
import com.mapbox.maps.extension.style.layers.properties.generated.TextTransform
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.annotation.AnnotationManager
import com.mapbox.maps.plugin.annotation.AnnotationOptions
import com.mapbox.maps.plugin.annotation.ConvertUtils.convertDoubleArray
import com.mapbox.maps.plugin.annotation.ConvertUtils.toDoubleArray

/**
 * Builder class from which a pointAnnotation is created.
 */
class PointAnnotationOptions : AnnotationOptions<Point, PointAnnotation> {
  private var isDraggable: Boolean = false
  private var data: JsonElement? = null
  private var geometry: Point? = null
  private var iconImageBitmap: Bitmap? = null

  /**
   * Set bitmap icon-image to initialise the symbol.
   *
   * Will not take effect if a String iconImage name has been set.
   *
   * @param iconImage the bitmap image
   * @return this
   */
  fun withIconImage(iconImageBitmap: Bitmap): PointAnnotationOptions {
    this.iconImageBitmap = iconImageBitmap
    return this
  }

  /**
   * Part of the icon placed closest to the anchor.
   */
  var iconAnchor: IconAnchor? = null

  /**
   * Set icon-anchor to initialise the pointAnnotation with.
   *
   * Part of the icon placed closest to the anchor.
   *
   * @param iconAnchor the icon-anchor value
   * @return this
   */
  fun withIconAnchor(iconAnchor: IconAnchor): PointAnnotationOptions {
    this.iconAnchor = iconAnchor
    return this
  }

  /**
   * Name of image in sprite to use for drawing an image background.
   */
  var iconImage: String? = null

  /**
   * Set icon-image to initialise the pointAnnotation with.
   *
   * Name of image in sprite to use for drawing an image background.
   *
   * @param iconImage the icon-image value
   * @return this
   */
  fun withIconImage(iconImage: String): PointAnnotationOptions {
    this.iconImage = iconImage
    return this
  }

  /**
   * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
   */
  var iconOffset: List<Double>? = null

  /**
   * Set icon-offset to initialise the pointAnnotation with.
   *
   * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of {@link PropertyFactory#iconSize} to obtain the final offset in density-independent pixels. When combined with {@link PropertyFactory#iconRotate} the offset will be as if the rotated direction was up.
   *
   * @param iconOffset the icon-offset value
   * @return this
   */
  fun withIconOffset(iconOffset: List<Double>): PointAnnotationOptions {
    this.iconOffset = iconOffset
    return this
  }

  /**
   * Rotates the icon clockwise.
   */
  var iconRotate: Double? = null

  /**
   * Set icon-rotate to initialise the pointAnnotation with.
   *
   * Rotates the icon clockwise. The unit of iconRotate is in degrees.
   *
   * @param iconRotate the icon-rotate value
   * @return this
   */
  fun withIconRotate(iconRotate: Double): PointAnnotationOptions {
    this.iconRotate = iconRotate
    return this
  }

  /**
   * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
   */
  var iconSize: Double? = null

  /**
   * Set icon-size to initialise the pointAnnotation with.
   *
   * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by {@link PropertyFactory#iconSize}. 1 is the original size; 3 triples the size of the image. The unit of iconSize is in factor of the original icon size.
   *
   * @param iconSize the icon-size value
   * @return this
   */
  fun withIconSize(iconSize: Double): PointAnnotationOptions {
    this.iconSize = iconSize
    return this
  }

  /**
   * Scales the icon to fit around the associated text.
   */
  var iconTextFit: IconTextFit? = null

  /**
   * Set icon-text-fit to initialise the pointAnnotation with.
   *
   * Scales the icon to fit around the associated text.
   *
   * @param iconTextFit the icon-text-fit value
   * @return this
   */
  fun withIconTextFit(iconTextFit: IconTextFit): PointAnnotationOptions {
    this.iconTextFit = iconTextFit
    return this
  }

  /**
   * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left.
   */
  var iconTextFitPadding: List<Double>? = null

  /**
   * Set icon-text-fit-padding to initialise the pointAnnotation with.
   *
   * Size of the additional area added to dimensions determined by {@link Property.ICON_TEXT_FIT}, in clockwise order: top, right, bottom, left. The unit of iconTextFitPadding is in density-independent pixels.
   *
   * @param iconTextFitPadding the icon-text-fit-padding value
   * @return this
   */
  fun withIconTextFitPadding(iconTextFitPadding: List<Double>): PointAnnotationOptions {
    this.iconTextFitPadding = iconTextFitPadding
    return this
  }

  /**
   * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first. When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
   */
  var symbolSortKey: Double? = null

  /**
   * Set symbol-sort-key to initialise the pointAnnotation with.
   *
   * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first. When {@link PropertyFactory#iconAllowOverlap} or {@link PropertyFactory#textAllowOverlap} is `false`, features with a lower sort key will have priority during placement. When {@link PropertyFactory#iconAllowOverlap} or {@link PropertyFactory#textAllowOverlap} is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
   *
   * @param symbolSortKey the symbol-sort-key value
   * @return this
   */
  fun withSymbolSortKey(symbolSortKey: Double): PointAnnotationOptions {
    this.symbolSortKey = symbolSortKey
    return this
  }

  /**
   * Part of the text placed closest to the anchor.
   */
  var textAnchor: TextAnchor? = null

  /**
   * Set text-anchor to initialise the pointAnnotation with.
   *
   * Part of the text placed closest to the anchor.
   *
   * @param textAnchor the text-anchor value
   * @return this
   */
  fun withTextAnchor(textAnchor: TextAnchor): PointAnnotationOptions {
    this.textAnchor = textAnchor
    return this
  }

  /**
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
   */
  var textField: String? = null

  /**
   * Set text-field to initialise the pointAnnotation with.
   *
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
   *
   * @param textField the text-field value
   * @return this
   */
  fun withTextField(textField: String): PointAnnotationOptions {
    this.textField = textField
    return this
  }

  /**
   * Text justification options.
   */
  var textJustify: TextJustify? = null

  /**
   * Set text-justify to initialise the pointAnnotation with.
   *
   * Text justification options.
   *
   * @param textJustify the text-justify value
   * @return this
   */
  fun withTextJustify(textJustify: TextJustify): PointAnnotationOptions {
    this.textJustify = textJustify
    return this
  }

  /**
   * Text tracking amount.
   */
  var textLetterSpacing: Double? = null

  /**
   * Set text-letter-spacing to initialise the pointAnnotation with.
   *
   * Text tracking amount. The unit of textLetterSpacing is in ems.
   *
   * @param textLetterSpacing the text-letter-spacing value
   * @return this
   */
  fun withTextLetterSpacing(textLetterSpacing: Double): PointAnnotationOptions {
    this.textLetterSpacing = textLetterSpacing
    return this
  }

  /**
   * Text leading value for multi-line text.
   */
  var textLineHeight: Double? = null

  /**
   * Set text-line-height to initialise the pointAnnotation with.
   *
   * Text leading value for multi-line text. The unit of textLineHeight is in ems.
   *
   * @param textLineHeight the text-line-height value
   * @return this
   */
  fun withTextLineHeight(textLineHeight: Double): PointAnnotationOptions {
    this.textLineHeight = textLineHeight
    return this
  }

  /**
   * The maximum line width for text wrapping.
   */
  var textMaxWidth: Double? = null

  /**
   * Set text-max-width to initialise the pointAnnotation with.
   *
   * The maximum line width for text wrapping. The unit of textMaxWidth is in ems.
   *
   * @param textMaxWidth the text-max-width value
   * @return this
   */
  fun withTextMaxWidth(textMaxWidth: Double): PointAnnotationOptions {
    this.textMaxWidth = textMaxWidth
    return this
  }

  /**
   * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
   */
  var textOffset: List<Double>? = null

  /**
   * Set text-offset to initialise the pointAnnotation with.
   *
   * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position. The unit of textOffset is in ems.
   *
   * @param textOffset the text-offset value
   * @return this
   */
  fun withTextOffset(textOffset: List<Double>): PointAnnotationOptions {
    this.textOffset = textOffset
    return this
  }

  /**
   * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
   */
  var textRadialOffset: Double? = null

  /**
   * Set text-radial-offset to initialise the pointAnnotation with.
   *
   * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with {@link PropertyFactory#textVariableAnchor}, which defaults to using the two-dimensional {@link PropertyFactory#textOffset} if present. The unit of textRadialOffset is in ems.
   *
   * @param textRadialOffset the text-radial-offset value
   * @return this
   */
  fun withTextRadialOffset(textRadialOffset: Double): PointAnnotationOptions {
    this.textRadialOffset = textRadialOffset
    return this
  }

  /**
   * Rotates the text clockwise.
   */
  var textRotate: Double? = null

  /**
   * Set text-rotate to initialise the pointAnnotation with.
   *
   * Rotates the text clockwise. The unit of textRotate is in degrees.
   *
   * @param textRotate the text-rotate value
   * @return this
   */
  fun withTextRotate(textRotate: Double): PointAnnotationOptions {
    this.textRotate = textRotate
    return this
  }

  /**
   * Font size.
   */
  var textSize: Double? = null

  /**
   * Set text-size to initialise the pointAnnotation with.
   *
   * Font size. The unit of textSize is in density-independent pixels.
   *
   * @param textSize the text-size value
   * @return this
   */
  fun withTextSize(textSize: Double): PointAnnotationOptions {
    this.textSize = textSize
    return this
  }

  /**
   * Specifies how to capitalize text, similar to the CSS `text-transform` property.
   */
  var textTransform: TextTransform? = null

  /**
   * Set text-transform to initialise the pointAnnotation with.
   *
   * Specifies how to capitalize text, similar to the CSS {@link PropertyFactory#textTransform} property.
   *
   * @param textTransform the text-transform value
   * @return this
   */
  fun withTextTransform(textTransform: TextTransform): PointAnnotationOptions {
    this.textTransform = textTransform
    return this
  }

  /**
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   */
  var iconColor: String? = null

  /**
   * Set icon-color to initialise the pointAnnotation with.
   *
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * @param iconColor the icon-color value
   * @return this
   */
  fun withIconColor(iconColor: String): PointAnnotationOptions {
    this.iconColor = iconColor
    return this
  }

  /**
   * Set icon-color to initialise the pointAnnotation with.
   *
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * @param iconColor the icon-color value with ColorInt format
   * @return this
   */
  fun withIconColor(@ColorInt iconColor: Int): PointAnnotationOptions {
    this.iconColor = ColorUtils.colorToRgbaString(iconColor)
    return this
  }

  /**
   * Controls the intensity of light emitted on the source features.
   */
  var iconEmissiveStrength: Double? = null

  /**
   * Set icon-emissive-strength to initialise the pointAnnotation with.
   *
   * Controls the intensity of light emitted on the source features. The unit of iconEmissiveStrength is in intensity.
   *
   * @param iconEmissiveStrength the icon-emissive-strength value
   * @return this
   */
  fun withIconEmissiveStrength(iconEmissiveStrength: Double): PointAnnotationOptions {
    this.iconEmissiveStrength = iconEmissiveStrength
    return this
  }

  /**
   * Fade out the halo towards the outside.
   */
  var iconHaloBlur: Double? = null

  /**
   * Set icon-halo-blur to initialise the pointAnnotation with.
   *
   * Fade out the halo towards the outside. The unit of iconHaloBlur is in density-independent pixels.
   *
   * @param iconHaloBlur the icon-halo-blur value
   * @return this
   */
  fun withIconHaloBlur(iconHaloBlur: Double): PointAnnotationOptions {
    this.iconHaloBlur = iconHaloBlur
    return this
  }

  /**
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   */
  var iconHaloColor: String? = null

  /**
   * Set icon-halo-color to initialise the pointAnnotation with.
   *
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * @param iconHaloColor the icon-halo-color value
   * @return this
   */
  fun withIconHaloColor(iconHaloColor: String): PointAnnotationOptions {
    this.iconHaloColor = iconHaloColor
    return this
  }

  /**
   * Set icon-halo-color to initialise the pointAnnotation with.
   *
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * @param iconHaloColor the icon-halo-color value with ColorInt format
   * @return this
   */
  fun withIconHaloColor(@ColorInt iconHaloColor: Int): PointAnnotationOptions {
    this.iconHaloColor = ColorUtils.colorToRgbaString(iconHaloColor)
    return this
  }

  /**
   * Distance of halo to the icon outline.
   */
  var iconHaloWidth: Double? = null

  /**
   * Set icon-halo-width to initialise the pointAnnotation with.
   *
   * Distance of halo to the icon outline. The unit of iconHaloWidth is in density-independent pixels.
   *
   * @param iconHaloWidth the icon-halo-width value
   * @return this
   */
  fun withIconHaloWidth(iconHaloWidth: Double): PointAnnotationOptions {
    this.iconHaloWidth = iconHaloWidth
    return this
  }

  /**
   * Controls the transition progress between the image variants of icon-image. Zero means the first variant is used, one is the second, and in between they are blended together.
   */
  var iconImageCrossFade: Double? = null

  /**
   * Set icon-image-cross-fade to initialise the pointAnnotation with.
   *
   * Controls the transition progress between the image variants of icon-image. Zero means the first variant is used, one is the second, and in between they are blended together.
   *
   * @param iconImageCrossFade the icon-image-cross-fade value
   * @return this
   */
  fun withIconImageCrossFade(iconImageCrossFade: Double): PointAnnotationOptions {
    this.iconImageCrossFade = iconImageCrossFade
    return this
  }

  /**
   * The opacity at which the icon will be drawn.
   */
  var iconOpacity: Double? = null

  /**
   * Set icon-opacity to initialise the pointAnnotation with.
   *
   * The opacity at which the icon will be drawn.
   *
   * @param iconOpacity the icon-opacity value
   * @return this
   */
  fun withIconOpacity(iconOpacity: Double): PointAnnotationOptions {
    this.iconOpacity = iconOpacity
    return this
  }

  /**
   * The color with which the text will be drawn.
   */
  var textColor: String? = null

  /**
   * Set text-color to initialise the pointAnnotation with.
   *
   * The color with which the text will be drawn.
   *
   * @param textColor the text-color value
   * @return this
   */
  fun withTextColor(textColor: String): PointAnnotationOptions {
    this.textColor = textColor
    return this
  }

  /**
   * Set text-color to initialise the pointAnnotation with.
   *
   * The color with which the text will be drawn.
   *
   * @param textColor the text-color value with ColorInt format
   * @return this
   */
  fun withTextColor(@ColorInt textColor: Int): PointAnnotationOptions {
    this.textColor = ColorUtils.colorToRgbaString(textColor)
    return this
  }

  /**
   * Controls the intensity of light emitted on the source features.
   */
  var textEmissiveStrength: Double? = null

  /**
   * Set text-emissive-strength to initialise the pointAnnotation with.
   *
   * Controls the intensity of light emitted on the source features. The unit of textEmissiveStrength is in intensity.
   *
   * @param textEmissiveStrength the text-emissive-strength value
   * @return this
   */
  fun withTextEmissiveStrength(textEmissiveStrength: Double): PointAnnotationOptions {
    this.textEmissiveStrength = textEmissiveStrength
    return this
  }

  /**
   * The halo's fadeout distance towards the outside.
   */
  var textHaloBlur: Double? = null

  /**
   * Set text-halo-blur to initialise the pointAnnotation with.
   *
   * The halo's fadeout distance towards the outside. The unit of textHaloBlur is in density-independent pixels.
   *
   * @param textHaloBlur the text-halo-blur value
   * @return this
   */
  fun withTextHaloBlur(textHaloBlur: Double): PointAnnotationOptions {
    this.textHaloBlur = textHaloBlur
    return this
  }

  /**
   * The color of the text's halo, which helps it stand out from backgrounds.
   */
  var textHaloColor: String? = null

  /**
   * Set text-halo-color to initialise the pointAnnotation with.
   *
   * The color of the text's halo, which helps it stand out from backgrounds.
   *
   * @param textHaloColor the text-halo-color value
   * @return this
   */
  fun withTextHaloColor(textHaloColor: String): PointAnnotationOptions {
    this.textHaloColor = textHaloColor
    return this
  }

  /**
   * Set text-halo-color to initialise the pointAnnotation with.
   *
   * The color of the text's halo, which helps it stand out from backgrounds.
   *
   * @param textHaloColor the text-halo-color value with ColorInt format
   * @return this
   */
  fun withTextHaloColor(@ColorInt textHaloColor: Int): PointAnnotationOptions {
    this.textHaloColor = ColorUtils.colorToRgbaString(textHaloColor)
    return this
  }

  /**
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
   */
  var textHaloWidth: Double? = null

  /**
   * Set text-halo-width to initialise the pointAnnotation with.
   *
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size. The unit of textHaloWidth is in density-independent pixels.
   *
   * @param textHaloWidth the text-halo-width value
   * @return this
   */
  fun withTextHaloWidth(textHaloWidth: Double): PointAnnotationOptions {
    this.textHaloWidth = textHaloWidth
    return this
  }

  /**
   * The opacity at which the text will be drawn.
   */
  var textOpacity: Double? = null

  /**
   * Set text-opacity to initialise the pointAnnotation with.
   *
   * The opacity at which the text will be drawn.
   *
   * @param textOpacity the text-opacity value
   * @return this
   */
  fun withTextOpacity(textOpacity: Double): PointAnnotationOptions {
    this.textOpacity = textOpacity
    return this
  }

  /**
   * Set the Point of the pointAnnotation, which represents the location of the pointAnnotation on the map
   *
   * @param point the location of the pointAnnotation in a longitude and latitude pair
   * @return this
   */
  fun withPoint(point: Point): PointAnnotationOptions {
    geometry = point
    return this
  }

  /**
   * Get the Point of the pointAnnotation, which represents the location of the pointAnnotation on the map
   *
   * @return the location of the pointAnnotation in a longitude and latitude pair
   */
  fun getPoint(): Point? {
    return geometry
  }

  /**
   * Set the geometry of the pointAnnotation, which represents the location of the pointAnnotation on the map
   *
   * @param geometry the location of the pointAnnotation
   * @return this
   */
  fun withGeometry(geometry: Point): PointAnnotationOptions {
    this.geometry = geometry
    return this
  }

  /**
   * Get the geometry of the pointAnnotation, which represents the location of the pointAnnotation on the map
   *
   * @return the location of the pointAnnotation
   */
  fun getGeometry(): Point? {
    return geometry
  }

  /**
   * Returns whether this pointAnnotation is draggable, meaning it can be dragged across the screen when touched and moved.
   *
   * @return draggable when touched
   */
  fun getDraggable(): Boolean {
    return isDraggable
  }

  /**
   * Set whether this pointAnnotation should be draggable,
   * meaning it can be dragged across the screen when touched and moved.
   *
   * @param draggable should be draggable
   */
  fun withDraggable(draggable: Boolean): PointAnnotationOptions {
    isDraggable = draggable
    return this
  }

  /**
   * Set the arbitrary json data of the annotation.
   *
   * @param jsonElement the arbitrary json element data
   */
  fun withData(jsonElement: JsonElement): PointAnnotationOptions {
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
    annotationManager: AnnotationManager<Point, PointAnnotation, *, *, *, *, *>
  ): PointAnnotation {
    if (geometry == null) {
      throw MapboxAnnotationException("geometry field is required")
    }
    val jsonObject = JsonObject()
    iconAnchor?.let {
      jsonObject.addProperty(PROPERTY_ICON_ANCHOR, it.value)
    }
    iconImage?.let {
      jsonObject.addProperty(PROPERTY_ICON_IMAGE, it)
    }
    iconOffset?.let {
      jsonObject.add(PROPERTY_ICON_OFFSET, convertDoubleArray(it))
    }
    iconRotate?.let {
      jsonObject.addProperty(PROPERTY_ICON_ROTATE, it)
    }
    iconSize?.let {
      jsonObject.addProperty(PROPERTY_ICON_SIZE, it)
    }
    iconTextFit?.let {
      jsonObject.addProperty(PROPERTY_ICON_TEXT_FIT, it.value)
    }
    iconTextFitPadding?.let {
      jsonObject.add(PROPERTY_ICON_TEXT_FIT_PADDING, convertDoubleArray(it))
    }
    symbolSortKey?.let {
      jsonObject.addProperty(PROPERTY_SYMBOL_SORT_KEY, it)
    }
    textAnchor?.let {
      jsonObject.addProperty(PROPERTY_TEXT_ANCHOR, it.value)
    }
    textField?.let {
      jsonObject.addProperty(PROPERTY_TEXT_FIELD, it)
    }
    textJustify?.let {
      jsonObject.addProperty(PROPERTY_TEXT_JUSTIFY, it.value)
    }
    textLetterSpacing?.let {
      jsonObject.addProperty(PROPERTY_TEXT_LETTER_SPACING, it)
    }
    textLineHeight?.let {
      jsonObject.addProperty(PROPERTY_TEXT_LINE_HEIGHT, it)
    }
    textMaxWidth?.let {
      jsonObject.addProperty(PROPERTY_TEXT_MAX_WIDTH, it)
    }
    textOffset?.let {
      jsonObject.add(PROPERTY_TEXT_OFFSET, convertDoubleArray(it))
    }
    textRadialOffset?.let {
      jsonObject.addProperty(PROPERTY_TEXT_RADIAL_OFFSET, it)
    }
    textRotate?.let {
      jsonObject.addProperty(PROPERTY_TEXT_ROTATE, it)
    }
    textSize?.let {
      jsonObject.addProperty(PROPERTY_TEXT_SIZE, it)
    }
    textTransform?.let {
      jsonObject.addProperty(PROPERTY_TEXT_TRANSFORM, it.value)
    }
    iconColor?.let {
      jsonObject.addProperty(PROPERTY_ICON_COLOR, it)
    }
    iconEmissiveStrength?.let {
      jsonObject.addProperty(PROPERTY_ICON_EMISSIVE_STRENGTH, it)
    }
    iconHaloBlur?.let {
      jsonObject.addProperty(PROPERTY_ICON_HALO_BLUR, it)
    }
    iconHaloColor?.let {
      jsonObject.addProperty(PROPERTY_ICON_HALO_COLOR, it)
    }
    iconHaloWidth?.let {
      jsonObject.addProperty(PROPERTY_ICON_HALO_WIDTH, it)
    }
    iconImageCrossFade?.let {
      jsonObject.addProperty(PROPERTY_ICON_IMAGE_CROSS_FADE, it)
    }
    iconOpacity?.let {
      jsonObject.addProperty(PROPERTY_ICON_OPACITY, it)
    }
    textColor?.let {
      jsonObject.addProperty(PROPERTY_TEXT_COLOR, it)
    }
    textEmissiveStrength?.let {
      jsonObject.addProperty(PROPERTY_TEXT_EMISSIVE_STRENGTH, it)
    }
    textHaloBlur?.let {
      jsonObject.addProperty(PROPERTY_TEXT_HALO_BLUR, it)
    }
    textHaloColor?.let {
      jsonObject.addProperty(PROPERTY_TEXT_HALO_COLOR, it)
    }
    textHaloWidth?.let {
      jsonObject.addProperty(PROPERTY_TEXT_HALO_WIDTH, it)
    }
    textOpacity?.let {
      jsonObject.addProperty(PROPERTY_TEXT_OPACITY, it)
    }
    val pointAnnotation = PointAnnotation(id, annotationManager, jsonObject, geometry!!)
    iconImageBitmap?.let {
      pointAnnotation.iconImageBitmap = iconImageBitmap
    }
    pointAnnotation.isDraggable = isDraggable
    pointAnnotation.setData(data)
    return pointAnnotation
  }

  /**
   * Static variables and methods.
   */
  companion object {

    /** The property for icon-anchor */
    const val PROPERTY_ICON_ANCHOR = "icon-anchor"

    /** The property for icon-image */
    const val PROPERTY_ICON_IMAGE = "icon-image"

    /** The property for icon-offset */
    const val PROPERTY_ICON_OFFSET = "icon-offset"

    /** The property for icon-rotate */
    const val PROPERTY_ICON_ROTATE = "icon-rotate"

    /** The property for icon-size */
    const val PROPERTY_ICON_SIZE = "icon-size"

    /** The property for icon-text-fit */
    const val PROPERTY_ICON_TEXT_FIT = "icon-text-fit"

    /** The property for icon-text-fit-padding */
    const val PROPERTY_ICON_TEXT_FIT_PADDING = "icon-text-fit-padding"

    /** The property for symbol-sort-key */
    const val PROPERTY_SYMBOL_SORT_KEY = "symbol-sort-key"

    /** The property for text-anchor */
    const val PROPERTY_TEXT_ANCHOR = "text-anchor"

    /** The property for text-field */
    const val PROPERTY_TEXT_FIELD = "text-field"

    /** The property for text-justify */
    const val PROPERTY_TEXT_JUSTIFY = "text-justify"

    /** The property for text-letter-spacing */
    const val PROPERTY_TEXT_LETTER_SPACING = "text-letter-spacing"

    /** The property for text-line-height */
    const val PROPERTY_TEXT_LINE_HEIGHT = "text-line-height"

    /** The property for text-max-width */
    const val PROPERTY_TEXT_MAX_WIDTH = "text-max-width"

    /** The property for text-offset */
    const val PROPERTY_TEXT_OFFSET = "text-offset"

    /** The property for text-radial-offset */
    const val PROPERTY_TEXT_RADIAL_OFFSET = "text-radial-offset"

    /** The property for text-rotate */
    const val PROPERTY_TEXT_ROTATE = "text-rotate"

    /** The property for text-size */
    const val PROPERTY_TEXT_SIZE = "text-size"

    /** The property for text-transform */
    const val PROPERTY_TEXT_TRANSFORM = "text-transform"

    /** The property for icon-color */
    const val PROPERTY_ICON_COLOR = "icon-color"

    /** The property for icon-emissive-strength */
    const val PROPERTY_ICON_EMISSIVE_STRENGTH = "icon-emissive-strength"

    /** The property for icon-halo-blur */
    const val PROPERTY_ICON_HALO_BLUR = "icon-halo-blur"

    /** The property for icon-halo-color */
    const val PROPERTY_ICON_HALO_COLOR = "icon-halo-color"

    /** The property for icon-halo-width */
    const val PROPERTY_ICON_HALO_WIDTH = "icon-halo-width"

    /** The property for icon-image-cross-fade */
    const val PROPERTY_ICON_IMAGE_CROSS_FADE = "icon-image-cross-fade"

    /** The property for icon-opacity */
    const val PROPERTY_ICON_OPACITY = "icon-opacity"

    /** The property for text-color */
    const val PROPERTY_TEXT_COLOR = "text-color"

    /** The property for text-emissive-strength */
    const val PROPERTY_TEXT_EMISSIVE_STRENGTH = "text-emissive-strength"

    /** The property for text-halo-blur */
    const val PROPERTY_TEXT_HALO_BLUR = "text-halo-blur"

    /** The property for text-halo-color */
    const val PROPERTY_TEXT_HALO_COLOR = "text-halo-color"

    /** The property for text-halo-width */
    const val PROPERTY_TEXT_HALO_WIDTH = "text-halo-width"

    /** The property for text-opacity */
    const val PROPERTY_TEXT_OPACITY = "text-opacity"

    /** The property for is-draggable */
    private const val PROPERTY_IS_DRAGGABLE = "is-draggable"

    /**
     * Creates PointAnnotationOptions out of a Feature.
     *
     * @param feature feature to be converted
     */
    fun fromFeature(feature: Feature): PointAnnotationOptions? {
      if (feature.geometry() == null) {
        throw MapboxAnnotationException("geometry field is required")
      }
      if (feature.geometry() !is Point) {

        return null
      }

      val options = PointAnnotationOptions()
      options.geometry = feature.geometry() as (Point)
      if (feature.hasProperty(PROPERTY_ICON_ANCHOR)) {
        options.iconAnchor = IconAnchor.valueOf(feature.getProperty(PROPERTY_ICON_ANCHOR).asString)
      }
      if (feature.hasProperty(PROPERTY_ICON_IMAGE)) {
        options.iconImage = feature.getProperty(PROPERTY_ICON_IMAGE).asString
      }
      if (feature.hasProperty(PROPERTY_ICON_OFFSET)) {
        options.iconOffset = toDoubleArray(feature.getProperty(PROPERTY_ICON_OFFSET).asJsonArray)
      }
      if (feature.hasProperty(PROPERTY_ICON_ROTATE)) {
        options.iconRotate = feature.getProperty(PROPERTY_ICON_ROTATE).asDouble
      }
      if (feature.hasProperty(PROPERTY_ICON_SIZE)) {
        options.iconSize = feature.getProperty(PROPERTY_ICON_SIZE).asDouble
      }
      if (feature.hasProperty(PROPERTY_ICON_TEXT_FIT)) {
        options.iconTextFit = IconTextFit.valueOf(feature.getProperty(PROPERTY_ICON_TEXT_FIT).asString)
      }
      if (feature.hasProperty(PROPERTY_ICON_TEXT_FIT_PADDING)) {
        options.iconTextFitPadding = toDoubleArray(feature.getProperty(PROPERTY_ICON_TEXT_FIT_PADDING).asJsonArray)
      }
      if (feature.hasProperty(PROPERTY_SYMBOL_SORT_KEY)) {
        options.symbolSortKey = feature.getProperty(PROPERTY_SYMBOL_SORT_KEY).asDouble
      }
      if (feature.hasProperty(PROPERTY_TEXT_ANCHOR)) {
        options.textAnchor = TextAnchor.valueOf(feature.getProperty(PROPERTY_TEXT_ANCHOR).asString)
      }
      if (feature.hasProperty(PROPERTY_TEXT_FIELD)) {
        options.textField = feature.getProperty(PROPERTY_TEXT_FIELD).asString
      }
      if (feature.hasProperty(PROPERTY_TEXT_JUSTIFY)) {
        options.textJustify = TextJustify.valueOf(feature.getProperty(PROPERTY_TEXT_JUSTIFY).asString)
      }
      if (feature.hasProperty(PROPERTY_TEXT_LETTER_SPACING)) {
        options.textLetterSpacing = feature.getProperty(PROPERTY_TEXT_LETTER_SPACING).asDouble
      }
      if (feature.hasProperty(PROPERTY_TEXT_LINE_HEIGHT)) {
        options.textLineHeight = feature.getProperty(PROPERTY_TEXT_LINE_HEIGHT).asDouble
      }
      if (feature.hasProperty(PROPERTY_TEXT_MAX_WIDTH)) {
        options.textMaxWidth = feature.getProperty(PROPERTY_TEXT_MAX_WIDTH).asDouble
      }
      if (feature.hasProperty(PROPERTY_TEXT_OFFSET)) {
        options.textOffset = toDoubleArray(feature.getProperty(PROPERTY_TEXT_OFFSET).asJsonArray)
      }
      if (feature.hasProperty(PROPERTY_TEXT_RADIAL_OFFSET)) {
        options.textRadialOffset = feature.getProperty(PROPERTY_TEXT_RADIAL_OFFSET).asDouble
      }
      if (feature.hasProperty(PROPERTY_TEXT_ROTATE)) {
        options.textRotate = feature.getProperty(PROPERTY_TEXT_ROTATE).asDouble
      }
      if (feature.hasProperty(PROPERTY_TEXT_SIZE)) {
        options.textSize = feature.getProperty(PROPERTY_TEXT_SIZE).asDouble
      }
      if (feature.hasProperty(PROPERTY_TEXT_TRANSFORM)) {
        options.textTransform = TextTransform.valueOf(feature.getProperty(PROPERTY_TEXT_TRANSFORM).asString)
      }
      if (feature.hasProperty(PROPERTY_ICON_COLOR)) {
        options.iconColor = feature.getProperty(PROPERTY_ICON_COLOR).asString
      }
      if (feature.hasProperty(PROPERTY_ICON_EMISSIVE_STRENGTH)) {
        options.iconEmissiveStrength = feature.getProperty(PROPERTY_ICON_EMISSIVE_STRENGTH).asDouble
      }
      if (feature.hasProperty(PROPERTY_ICON_HALO_BLUR)) {
        options.iconHaloBlur = feature.getProperty(PROPERTY_ICON_HALO_BLUR).asDouble
      }
      if (feature.hasProperty(PROPERTY_ICON_HALO_COLOR)) {
        options.iconHaloColor = feature.getProperty(PROPERTY_ICON_HALO_COLOR).asString
      }
      if (feature.hasProperty(PROPERTY_ICON_HALO_WIDTH)) {
        options.iconHaloWidth = feature.getProperty(PROPERTY_ICON_HALO_WIDTH).asDouble
      }
      if (feature.hasProperty(PROPERTY_ICON_IMAGE_CROSS_FADE)) {
        options.iconImageCrossFade = feature.getProperty(PROPERTY_ICON_IMAGE_CROSS_FADE).asDouble
      }
      if (feature.hasProperty(PROPERTY_ICON_OPACITY)) {
        options.iconOpacity = feature.getProperty(PROPERTY_ICON_OPACITY).asDouble
      }
      if (feature.hasProperty(PROPERTY_TEXT_COLOR)) {
        options.textColor = feature.getProperty(PROPERTY_TEXT_COLOR).asString
      }
      if (feature.hasProperty(PROPERTY_TEXT_EMISSIVE_STRENGTH)) {
        options.textEmissiveStrength = feature.getProperty(PROPERTY_TEXT_EMISSIVE_STRENGTH).asDouble
      }
      if (feature.hasProperty(PROPERTY_TEXT_HALO_BLUR)) {
        options.textHaloBlur = feature.getProperty(PROPERTY_TEXT_HALO_BLUR).asDouble
      }
      if (feature.hasProperty(PROPERTY_TEXT_HALO_COLOR)) {
        options.textHaloColor = feature.getProperty(PROPERTY_TEXT_HALO_COLOR).asString
      }
      if (feature.hasProperty(PROPERTY_TEXT_HALO_WIDTH)) {
        options.textHaloWidth = feature.getProperty(PROPERTY_TEXT_HALO_WIDTH).asDouble
      }
      if (feature.hasProperty(PROPERTY_TEXT_OPACITY)) {
        options.textOpacity = feature.getProperty(PROPERTY_TEXT_OPACITY).asDouble
      }
      if (feature.hasProperty(PROPERTY_IS_DRAGGABLE)) {
        options.isDraggable = feature.getProperty(PROPERTY_IS_DRAGGABLE).asBoolean
      }
      return options
    }
  }
}