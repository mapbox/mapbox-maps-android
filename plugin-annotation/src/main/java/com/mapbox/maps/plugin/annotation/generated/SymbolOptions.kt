// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import android.graphics.Bitmap
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.TextJustify
import com.mapbox.maps.extension.style.layers.properties.generated.TextTransform
import com.mapbox.maps.plugin.annotation.AnnotationManager
import com.mapbox.maps.plugin.annotation.AnnotationOptions
import com.mapbox.maps.plugin.annotation.ConvertUtils.convertDoubleArray
import com.mapbox.maps.plugin.annotation.ConvertUtils.convertStringArray
import com.mapbox.maps.plugin.annotation.ConvertUtils.toDoubleArray
import com.mapbox.maps.plugin.annotation.ConvertUtils.toStringArray

/**
 * Builder class from which a symbol is created.
 */
class SymbolOptions : AnnotationOptions<Point, Symbol> {
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
  fun withIconImage(iconImageBitmap: Bitmap): SymbolOptions {
    this.iconImageBitmap = iconImageBitmap
    return this
  }

  /**
   * Part of the icon placed closest to the anchor.
   */
  var iconAnchor: IconAnchor = IconAnchor.CENTER

  /**
   * Set icon-anchor to initialise the symbol with.
   * <p>
   * Part of the icon placed closest to the anchor.
   * </p>
   * @param iconAnchor the icon-anchor value
   * @return this
   */
  fun withIconAnchor(iconAnchor: IconAnchor): SymbolOptions {
    this.iconAnchor = iconAnchor
    return this
  }

  /**
   * Name of image in sprite to use for drawing an image background.
   */
  var iconImage: String? = null

  /**
   * Set icon-image to initialise the symbol with.
   * <p>
   * Name of image in sprite to use for drawing an image background.
   * </p>
   * @param iconImage the icon-image value
   * @return this
   */
  fun withIconImage(iconImage: String): SymbolOptions {
    this.iconImage = iconImage
    return this
  }

  /**
   * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
   */
  var iconOffset: List<Double> = listOf(0.0, 0.0)

  /**
   * Set icon-offset to initialise the symbol with.
   * <p>
   * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of {@link PropertyFactory#iconSize} to obtain the final offset in density-independent pixels. When combined with {@link PropertyFactory#iconRotate} the offset will be as if the rotated direction was up.
   * </p>
   * @param iconOffset the icon-offset value
   * @return this
   */
  fun withIconOffset(iconOffset: List<Double>): SymbolOptions {
    this.iconOffset = iconOffset
    return this
  }

  /**
   * Rotates the icon clockwise.
   */
  var iconRotate: Double = 0.0

  /**
   * Set icon-rotate to initialise the symbol with.
   * <p>
   * Rotates the icon clockwise.
   * </p>
   * @param iconRotate the icon-rotate value
   * @return this
   */
  fun withIconRotate(iconRotate: Double): SymbolOptions {
    this.iconRotate = iconRotate
    return this
  }

  /**
   * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
   */
  var iconSize: Double = 1.0

  /**
   * Set icon-size to initialise the symbol with.
   * <p>
   * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by {@link PropertyFactory#iconSize}. 1 is the original size; 3 triples the size of the image.
   * </p>
   * @param iconSize the icon-size value
   * @return this
   */
  fun withIconSize(iconSize: Double): SymbolOptions {
    this.iconSize = iconSize
    return this
  }

  /**
   * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first.  When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
   */
  var symbolSortKey: Double? = null

  /**
   * Set symbol-sort-key to initialise the symbol with.
   * <p>
   * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first.  When {@link PropertyFactory#iconAllowOverlap} or {@link PropertyFactory#textAllowOverlap} is `false`, features with a lower sort key will have priority during placement. When {@link PropertyFactory#iconAllowOverlap} or {@link PropertyFactory#textAllowOverlap} is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
   * </p>
   * @param symbolSortKey the symbol-sort-key value
   * @return this
   */
  fun withSymbolSortKey(symbolSortKey: Double): SymbolOptions {
    this.symbolSortKey = symbolSortKey
    return this
  }

  /**
   * Part of the text placed closest to the anchor.
   */
  var textAnchor: TextAnchor = TextAnchor.CENTER

  /**
   * Set text-anchor to initialise the symbol with.
   * <p>
   * Part of the text placed closest to the anchor.
   * </p>
   * @param textAnchor the text-anchor value
   * @return this
   */
  fun withTextAnchor(textAnchor: TextAnchor): SymbolOptions {
    this.textAnchor = textAnchor
    return this
  }

  /**
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options.
   */
  var textField: String? = null

  /**
   * Set text-field to initialise the symbol with.
   * <p>
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options.
   * </p>
   * @param textField the text-field value
   * @return this
   */
  fun withTextField(textField: String): SymbolOptions {
    this.textField = textField
    return this
  }

  /**
   * Font stack to use for displaying text.
   */
  var textFont: List<String> = listOf("Open Sans Regular", "Arial Unicode MS Regular")

  /**
   * Set text-font to initialise the symbol with.
   * <p>
   * Font stack to use for displaying text.
   * </p>
   * @param textFont the text-font value
   * @return this
   */
  fun withTextFont(textFont: List<String>): SymbolOptions {
    this.textFont = textFont
    return this
  }

  /**
   * Text justification options.
   */
  var textJustify: TextJustify = TextJustify.CENTER

  /**
   * Set text-justify to initialise the symbol with.
   * <p>
   * Text justification options.
   * </p>
   * @param textJustify the text-justify value
   * @return this
   */
  fun withTextJustify(textJustify: TextJustify): SymbolOptions {
    this.textJustify = textJustify
    return this
  }

  /**
   * Text tracking amount.
   */
  var textLetterSpacing: Double = 0.0

  /**
   * Set text-letter-spacing to initialise the symbol with.
   * <p>
   * Text tracking amount.
   * </p>
   * @param textLetterSpacing the text-letter-spacing value
   * @return this
   */
  fun withTextLetterSpacing(textLetterSpacing: Double): SymbolOptions {
    this.textLetterSpacing = textLetterSpacing
    return this
  }

  /**
   * The maximum line width for text wrapping.
   */
  var textMaxWidth: Double = 10.0

  /**
   * Set text-max-width to initialise the symbol with.
   * <p>
   * The maximum line width for text wrapping.
   * </p>
   * @param textMaxWidth the text-max-width value
   * @return this
   */
  fun withTextMaxWidth(textMaxWidth: Double): SymbolOptions {
    this.textMaxWidth = textMaxWidth
    return this
  }

  /**
   * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
   */
  var textOffset: List<Double> = listOf(0.0, 0.0)

  /**
   * Set text-offset to initialise the symbol with.
   * <p>
   * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
   * </p>
   * @param textOffset the text-offset value
   * @return this
   */
  fun withTextOffset(textOffset: List<Double>): SymbolOptions {
    this.textOffset = textOffset
    return this
  }

  /**
   * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
   */
  var textRadialOffset: Double = 0.0

  /**
   * Set text-radial-offset to initialise the symbol with.
   * <p>
   * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with {@link PropertyFactory#textVariableAnchor}, which defaults to using the two-dimensional {@link PropertyFactory#textOffset} if present.
   * </p>
   * @param textRadialOffset the text-radial-offset value
   * @return this
   */
  fun withTextRadialOffset(textRadialOffset: Double): SymbolOptions {
    this.textRadialOffset = textRadialOffset
    return this
  }

  /**
   * Rotates the text clockwise.
   */
  var textRotate: Double = 0.0

  /**
   * Set text-rotate to initialise the symbol with.
   * <p>
   * Rotates the text clockwise.
   * </p>
   * @param textRotate the text-rotate value
   * @return this
   */
  fun withTextRotate(textRotate: Double): SymbolOptions {
    this.textRotate = textRotate
    return this
  }

  /**
   * Font size.
   */
  var textSize: Double = 16.0

  /**
   * Set text-size to initialise the symbol with.
   * <p>
   * Font size.
   * </p>
   * @param textSize the text-size value
   * @return this
   */
  fun withTextSize(textSize: Double): SymbolOptions {
    this.textSize = textSize
    return this
  }

  /**
   * Specifies how to capitalize text, similar to the CSS `text-transform` property.
   */
  var textTransform: TextTransform = TextTransform.NONE

  /**
   * Set text-transform to initialise the symbol with.
   * <p>
   * Specifies how to capitalize text, similar to the CSS {@link PropertyFactory#textTransform} property.
   * </p>
   * @param textTransform the text-transform value
   * @return this
   */
  fun withTextTransform(textTransform: TextTransform): SymbolOptions {
    this.textTransform = textTransform
    return this
  }

  /**
   * The color of the icon. This can only be used with sdf icons.
   */
  var iconColor: String = "rgba(0, 0, 0, 1)"

  /**
   * Set icon-color to initialise the symbol with.
   * <p>
   * The color of the icon. This can only be used with sdf icons.
   * </p>
   * @param iconColor the icon-color value
   * @return this
   */
  fun withIconColor(iconColor: String): SymbolOptions {
    this.iconColor = iconColor
    return this
  }

  /**
   * Fade out the halo towards the outside.
   */
  var iconHaloBlur: Double = 0.0

  /**
   * Set icon-halo-blur to initialise the symbol with.
   * <p>
   * Fade out the halo towards the outside.
   * </p>
   * @param iconHaloBlur the icon-halo-blur value
   * @return this
   */
  fun withIconHaloBlur(iconHaloBlur: Double): SymbolOptions {
    this.iconHaloBlur = iconHaloBlur
    return this
  }

  /**
   * The color of the icon's halo. Icon halos can only be used with SDF icons.
   */
  var iconHaloColor: String = "rgba(0, 0, 0, 1)"

  /**
   * Set icon-halo-color to initialise the symbol with.
   * <p>
   * The color of the icon's halo. Icon halos can only be used with SDF icons.
   * </p>
   * @param iconHaloColor the icon-halo-color value
   * @return this
   */
  fun withIconHaloColor(iconHaloColor: String): SymbolOptions {
    this.iconHaloColor = iconHaloColor
    return this
  }

  /**
   * Distance of halo to the icon outline.
   */
  var iconHaloWidth: Double = 0.0

  /**
   * Set icon-halo-width to initialise the symbol with.
   * <p>
   * Distance of halo to the icon outline.
   * </p>
   * @param iconHaloWidth the icon-halo-width value
   * @return this
   */
  fun withIconHaloWidth(iconHaloWidth: Double): SymbolOptions {
    this.iconHaloWidth = iconHaloWidth
    return this
  }

  /**
   * The opacity at which the icon will be drawn.
   */
  var iconOpacity: Double = 1.0

  /**
   * Set icon-opacity to initialise the symbol with.
   * <p>
   * The opacity at which the icon will be drawn.
   * </p>
   * @param iconOpacity the icon-opacity value
   * @return this
   */
  fun withIconOpacity(iconOpacity: Double): SymbolOptions {
    this.iconOpacity = iconOpacity
    return this
  }

  /**
   * The color with which the text will be drawn.
   */
  var textColor: String = "rgba(0, 0, 0, 1)"

  /**
   * Set text-color to initialise the symbol with.
   * <p>
   * The color with which the text will be drawn.
   * </p>
   * @param textColor the text-color value
   * @return this
   */
  fun withTextColor(textColor: String): SymbolOptions {
    this.textColor = textColor
    return this
  }

  /**
   * The halo's fadeout distance towards the outside.
   */
  var textHaloBlur: Double = 0.0

  /**
   * Set text-halo-blur to initialise the symbol with.
   * <p>
   * The halo's fadeout distance towards the outside.
   * </p>
   * @param textHaloBlur the text-halo-blur value
   * @return this
   */
  fun withTextHaloBlur(textHaloBlur: Double): SymbolOptions {
    this.textHaloBlur = textHaloBlur
    return this
  }

  /**
   * The color of the text's halo, which helps it stand out from backgrounds.
   */
  var textHaloColor: String = "rgba(0, 0, 0, 1)"

  /**
   * Set text-halo-color to initialise the symbol with.
   * <p>
   * The color of the text's halo, which helps it stand out from backgrounds.
   * </p>
   * @param textHaloColor the text-halo-color value
   * @return this
   */
  fun withTextHaloColor(textHaloColor: String): SymbolOptions {
    this.textHaloColor = textHaloColor
    return this
  }

  /**
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
   */
  var textHaloWidth: Double = 0.0

  /**
   * Set text-halo-width to initialise the symbol with.
   * <p>
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
   * </p>
   * @param textHaloWidth the text-halo-width value
   * @return this
   */
  fun withTextHaloWidth(textHaloWidth: Double): SymbolOptions {
    this.textHaloWidth = textHaloWidth
    return this
  }

  /**
   * The opacity at which the text will be drawn.
   */
  var textOpacity: Double = 1.0

  /**
   * Set text-opacity to initialise the symbol with.
   * <p>
   * The opacity at which the text will be drawn.
   * </p>
   * @param textOpacity the text-opacity value
   * @return this
   */
  fun withTextOpacity(textOpacity: Double): SymbolOptions {
    this.textOpacity = textOpacity
    return this
  }

  /**
   * Set the Point of the symbol, which represents the location of the symbol on the map
   *
   * @param point the location of the symbol in a longitude and latitude pair
   * @return this
   */
  fun withPoint(point: Point): SymbolOptions {
    geometry = point
    return this
  }

  /**
   * Get the Point of the symbol, which represents the location of the symbol on the map
   *
   * @return the location of the symbol in a longitude and latitude pair
   */
  fun getPoint(): Point? {
    return geometry
  }

  /**
   * Set the geometry of the symbol, which represents the location of the symbol on the map
   *
   * @param geometry the location of the symbol
   * @return this
   */
  fun withGeometry(geometry: Point): SymbolOptions {
    this.geometry = geometry
    return this
  }

  /**
   * Get the geometry of the symbol, which represents the location of the symbol on the map
   *
   * @return the location of the symbol
   */
  fun getGeometry(): Point? {
    return geometry
  }

  /**
   * Returns whether this symbol is draggable, meaning it can be dragged across the screen when touched and moved.
   *
   * @return draggable when touched
   */
  fun getDraggable(): Boolean {
    return isDraggable
  }

  /**
   * Set whether this symbol should be draggable,
   * meaning it can be dragged across the screen when touched and moved.
   *
   * @param draggable should be draggable
   */
  fun withDraggable(draggable: Boolean): SymbolOptions {
    isDraggable = draggable
    return this
  }

  /**
   * Set the arbitrary json data of the annotation.
   *
   * @param jsonElement the arbitrary json element data
   */
  fun withData(jsonElement: JsonElement): SymbolOptions {
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
    annotationManager: AnnotationManager<Point, Symbol, *, *, *, *>
  ): Symbol {
    if (geometry == null) {
      throw RuntimeException("geometry field is required")
    }
    val jsonObject = JsonObject()
    jsonObject.addProperty(PROPERTY_ICON_ANCHOR, iconAnchor.value)
    jsonObject.addProperty(PROPERTY_ICON_IMAGE, iconImage)
    jsonObject.add(PROPERTY_ICON_OFFSET, convertDoubleArray(iconOffset))
    jsonObject.addProperty(PROPERTY_ICON_ROTATE, iconRotate)
    jsonObject.addProperty(PROPERTY_ICON_SIZE, iconSize)
    jsonObject.addProperty(PROPERTY_SYMBOL_SORT_KEY, symbolSortKey)
    jsonObject.addProperty(PROPERTY_TEXT_ANCHOR, textAnchor.value)
    jsonObject.addProperty(PROPERTY_TEXT_FIELD, textField)
    jsonObject.add(PROPERTY_TEXT_FONT, convertStringArray(textFont))
    jsonObject.addProperty(PROPERTY_TEXT_JUSTIFY, textJustify.value)
    jsonObject.addProperty(PROPERTY_TEXT_LETTER_SPACING, textLetterSpacing)
    jsonObject.addProperty(PROPERTY_TEXT_MAX_WIDTH, textMaxWidth)
    jsonObject.add(PROPERTY_TEXT_OFFSET, convertDoubleArray(textOffset))
    jsonObject.addProperty(PROPERTY_TEXT_RADIAL_OFFSET, textRadialOffset)
    jsonObject.addProperty(PROPERTY_TEXT_ROTATE, textRotate)
    jsonObject.addProperty(PROPERTY_TEXT_SIZE, textSize)
    jsonObject.addProperty(PROPERTY_TEXT_TRANSFORM, textTransform.value)
    jsonObject.addProperty(PROPERTY_ICON_COLOR, iconColor)
    jsonObject.addProperty(PROPERTY_ICON_HALO_BLUR, iconHaloBlur)
    jsonObject.addProperty(PROPERTY_ICON_HALO_COLOR, iconHaloColor)
    jsonObject.addProperty(PROPERTY_ICON_HALO_WIDTH, iconHaloWidth)
    jsonObject.addProperty(PROPERTY_ICON_OPACITY, iconOpacity)
    jsonObject.addProperty(PROPERTY_TEXT_COLOR, textColor)
    jsonObject.addProperty(PROPERTY_TEXT_HALO_BLUR, textHaloBlur)
    jsonObject.addProperty(PROPERTY_TEXT_HALO_COLOR, textHaloColor)
    jsonObject.addProperty(PROPERTY_TEXT_HALO_WIDTH, textHaloWidth)
    jsonObject.addProperty(PROPERTY_TEXT_OPACITY, textOpacity)
    val symbol = Symbol(id, annotationManager, jsonObject, geometry!!)
    iconImageBitmap?.let {
      symbol.iconImageBitmap = iconImageBitmap
    }
    symbol.isDraggable = isDraggable
    symbol.setData(data)
    return symbol
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

    /** The property for symbol-sort-key */
    const val PROPERTY_SYMBOL_SORT_KEY = "symbol-sort-key"

    /** The property for text-anchor */
    const val PROPERTY_TEXT_ANCHOR = "text-anchor"

    /** The property for text-field */
    const val PROPERTY_TEXT_FIELD = "text-field"

    /** The property for text-font */
    const val PROPERTY_TEXT_FONT = "text-font"

    /** The property for text-justify */
    const val PROPERTY_TEXT_JUSTIFY = "text-justify"

    /** The property for text-letter-spacing */
    const val PROPERTY_TEXT_LETTER_SPACING = "text-letter-spacing"

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

    /** The property for icon-halo-blur */
    const val PROPERTY_ICON_HALO_BLUR = "icon-halo-blur"

    /** The property for icon-halo-color */
    const val PROPERTY_ICON_HALO_COLOR = "icon-halo-color"

    /** The property for icon-halo-width */
    const val PROPERTY_ICON_HALO_WIDTH = "icon-halo-width"

    /** The property for icon-opacity */
    const val PROPERTY_ICON_OPACITY = "icon-opacity"

    /** The property for text-color */
    const val PROPERTY_TEXT_COLOR = "text-color"

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
     * Creates SymbolOptions out of a Feature.
     *
     * @param feature feature to be converted
     */
    fun fromFeature(feature: Feature): SymbolOptions? {
      if (feature.geometry() == null) {
        throw RuntimeException("geometry field is required")
      }
      if (feature.geometry() !is Point) {

        return null
      }

      val options = SymbolOptions()
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
      if (feature.hasProperty(PROPERTY_SYMBOL_SORT_KEY)) {
        options.symbolSortKey = feature.getProperty(PROPERTY_SYMBOL_SORT_KEY).asDouble
      }
      if (feature.hasProperty(PROPERTY_TEXT_ANCHOR)) {
        options.textAnchor = TextAnchor.valueOf(feature.getProperty(PROPERTY_TEXT_ANCHOR).asString)
      }
      if (feature.hasProperty(PROPERTY_TEXT_FIELD)) {
        options.textField = feature.getProperty(PROPERTY_TEXT_FIELD).asString
      }
      if (feature.hasProperty(PROPERTY_TEXT_FONT)) {
        options.textFont = toStringArray(feature.getProperty(PROPERTY_TEXT_FONT).asJsonArray)
      }
      if (feature.hasProperty(PROPERTY_TEXT_JUSTIFY)) {
        options.textJustify = TextJustify.valueOf(feature.getProperty(PROPERTY_TEXT_JUSTIFY).asString)
      }
      if (feature.hasProperty(PROPERTY_TEXT_LETTER_SPACING)) {
        options.textLetterSpacing = feature.getProperty(PROPERTY_TEXT_LETTER_SPACING).asDouble
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
      if (feature.hasProperty(PROPERTY_ICON_HALO_BLUR)) {
        options.iconHaloBlur = feature.getProperty(PROPERTY_ICON_HALO_BLUR).asDouble
      }
      if (feature.hasProperty(PROPERTY_ICON_HALO_COLOR)) {
        options.iconHaloColor = feature.getProperty(PROPERTY_ICON_HALO_COLOR).asString
      }
      if (feature.hasProperty(PROPERTY_ICON_HALO_WIDTH)) {
        options.iconHaloWidth = feature.getProperty(PROPERTY_ICON_HALO_WIDTH).asDouble
      }
      if (feature.hasProperty(PROPERTY_ICON_OPACITY)) {
        options.iconOpacity = feature.getProperty(PROPERTY_ICON_OPACITY).asDouble
      }
      if (feature.hasProperty(PROPERTY_TEXT_COLOR)) {
        options.textColor = feature.getProperty(PROPERTY_TEXT_COLOR).asString
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