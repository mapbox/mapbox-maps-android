// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import androidx.annotation.ColorInt
import androidx.annotation.UiThread
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.Layer
import com.mapbox.maps.extension.style.layers.properties.*
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.types.*
import com.mapbox.maps.extension.style.utils.ColorUtils.colorIntToRgbaExpression
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorInt
import com.mapbox.maps.extension.style.utils.ColorUtils.rgbaExpressionToColorString
import com.mapbox.maps.extension.style.utils.silentUnwrap
import java.util.*

/**
 * An icon or a text label.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-symbol)
 *
 * @param layerId the ID of the layer
 * @param sourceId the ID of the source
 */
@UiThread
class SymbolLayer(override val layerId: String, val sourceId: String) : SymbolLayerDsl, Layer() {
  init {
    internalSourceId = sourceId
  }

  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  override fun sourceLayer(sourceLayer: String) = apply {
    val param = PropertyValue("source-layer", sourceLayer)
    setProperty(param)
  }

  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   */
  val sourceLayer: String?
    /**
     * Get the sourceLayer property
     *
     * @return sourceLayer
     */
    get() {
      return getPropertyValue("source-layer")
    }

  /**
   * A filter is a property at the layer level that determines which features should be rendered in a style layer.
   *
   * Filters are written as expressions, which give you fine-grained control over which features to include: the
   * style layer only displays the features that match the filter condition that you define.
   *
   * Note: Zoom expressions in filters are only evaluated at integer zoom levels. The `feature-state` expression
   * is not supported in filter expressions.
   *
   * @param filter the expression filter to set
   */
  override fun filter(filter: Expression) = apply {
    val propertyValue = PropertyValue("filter", filter)
    setProperty(propertyValue)
  }

  /**
   * A filter is a property at the layer level that determines which features should be rendered in a style layer.
   *
   * Filters are written as expressions, which give you fine-grained control over which features to include: the
   * style layer only displays the features that match the filter condition that you define.
   *
   * Note: Zoom expressions in filters are only evaluated at integer zoom levels. The `feature-state` expression
   * is not supported in filter expressions.
   */
  val filter: Expression?
    /**
     * Get the filter property
     *
     * @return filter
     */
    get() = getPropertyValue<Expression>("filter")

  /**
   * Whether this layer is displayed.
   */
  override val visibility: Visibility?
    /**
     * Whether this layer is displayed.
     *
     * @return VISIBILITY
     */
    get() {
      val property: String? = getPropertyValue("visibility")
      property?.let {
        return Visibility.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility
   */
  override fun visibility(visibility: Visibility) = apply {
    val propertyValue = PropertyValue("visibility", visibility)
    setProperty(propertyValue)
  }

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   */
  override val minZoom: Double?
    /**
     * Get the minzoom property
     *
     * @return minzoom
     */
    get() {
      return getPropertyValue("minzoom")
    }

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of minzoom
   */
  override fun minZoom(minZoom: Double) = apply {
    val param = PropertyValue("minzoom", minZoom)
    setProperty(param)
  }

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   */
  override val maxZoom: Double?
    /**
     * Get the maxzoom property
     *
     * @return maxzoom
     */
    get() {
      return getPropertyValue("maxzoom")
    }

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of maxzoom
   */
  override fun maxZoom(maxZoom: Double) = apply {
    val param = PropertyValue("maxzoom", maxZoom)
    setProperty(param)
  }

  // Property getters and setters

  /**
   * If true, the icon will be visible even if it collides with other previously drawn symbols.
   */
  val iconAllowOverlap: Boolean?
    /**
     * If true, the icon will be visible even if it collides with other previously drawn symbols.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("icon-allow-overlap")
    }

  /**
   * If true, the icon will be visible even if it collides with other previously drawn symbols.
   *
   * @param iconAllowOverlap value of iconAllowOverlap
   */
  override fun iconAllowOverlap(iconAllowOverlap: Boolean) = apply {
    val propertyValue = PropertyValue("icon-allow-overlap", iconAllowOverlap)
    setProperty(propertyValue)
  }

  /**
   * If true, the icon will be visible even if it collides with other previously drawn symbols.
   *
   * This is an Expression representation of "icon-allow-overlap".
   *
   * If true, the icon will be visible even if it collides with other previously drawn symbols.
   */
  val iconAllowOverlapAsExpression: Expression?
    /**
     * If true, the icon will be visible even if it collides with other previously drawn symbols.
     *
     * Get the IconAllowOverlap property as an Expression
     *
     * @return Boolean
     */
    get() {
      getPropertyValue<Expression>("icon-allow-overlap")?.let {
        return it
      }
      iconAllowOverlap?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * If true, the icon will be visible even if it collides with other previously drawn symbols.
   *
   * @param iconAllowOverlap value of iconAllowOverlap as Expression
   */
  override fun iconAllowOverlap(iconAllowOverlap: Expression) = apply {
    val propertyValue = PropertyValue("icon-allow-overlap", iconAllowOverlap)
    setProperty(propertyValue)
  }

  /**
   * Part of the icon placed closest to the anchor.
   */
  val iconAnchor: IconAnchor?
    /**
     * Part of the icon placed closest to the anchor.
     *
     * @return IconAnchor
     */
    get() {
      getPropertyValue<String?>("icon-anchor")?.let {
        return IconAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Part of the icon placed closest to the anchor.
   *
   * @param iconAnchor value of iconAnchor
   */
  override fun iconAnchor(iconAnchor: IconAnchor) = apply {
    val propertyValue = PropertyValue("icon-anchor", iconAnchor)
    setProperty(propertyValue)
  }

  /**
   * Part of the icon placed closest to the anchor.
   *
   * This is an Expression representation of "icon-anchor".
   *
   * Part of the icon placed closest to the anchor.
   */
  val iconAnchorAsExpression: Expression?
    /**
     * Part of the icon placed closest to the anchor.
     *
     * Get the IconAnchor property as an Expression
     *
     * @return IconAnchor
     */
    get() {
      getPropertyValue<Expression>("icon-anchor")?.let {
        return it
      }
      iconAnchor?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Part of the icon placed closest to the anchor.
   *
   * @param iconAnchor value of iconAnchor as Expression
   */
  override fun iconAnchor(iconAnchor: Expression) = apply {
    val propertyValue = PropertyValue("icon-anchor", iconAnchor)
    setProperty(propertyValue)
  }

  /**
   * If true, other symbols can be visible even if they collide with the icon.
   */
  val iconIgnorePlacement: Boolean?
    /**
     * If true, other symbols can be visible even if they collide with the icon.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("icon-ignore-placement")
    }

  /**
   * If true, other symbols can be visible even if they collide with the icon.
   *
   * @param iconIgnorePlacement value of iconIgnorePlacement
   */
  override fun iconIgnorePlacement(iconIgnorePlacement: Boolean) = apply {
    val propertyValue = PropertyValue("icon-ignore-placement", iconIgnorePlacement)
    setProperty(propertyValue)
  }

  /**
   * If true, other symbols can be visible even if they collide with the icon.
   *
   * This is an Expression representation of "icon-ignore-placement".
   *
   * If true, other symbols can be visible even if they collide with the icon.
   */
  val iconIgnorePlacementAsExpression: Expression?
    /**
     * If true, other symbols can be visible even if they collide with the icon.
     *
     * Get the IconIgnorePlacement property as an Expression
     *
     * @return Boolean
     */
    get() {
      getPropertyValue<Expression>("icon-ignore-placement")?.let {
        return it
      }
      iconIgnorePlacement?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * If true, other symbols can be visible even if they collide with the icon.
   *
   * @param iconIgnorePlacement value of iconIgnorePlacement as Expression
   */
  override fun iconIgnorePlacement(iconIgnorePlacement: Expression) = apply {
    val propertyValue = PropertyValue("icon-ignore-placement", iconIgnorePlacement)
    setProperty(propertyValue)
  }

  /**
   * Name of image in sprite to use for drawing an image background.
   */
  val iconImage: String?
    /**
     * Name of image in sprite to use for drawing an image background.
     *
     * @return String
     */
    get() {
      return getPropertyValue<String>("icon-image")
    }

  /**
   * Name of image in sprite to use for drawing an image background.
   *
   * @param iconImage value of iconImage
   */
  override fun iconImage(iconImage: String) = apply {
    val propertyValue = PropertyValue("icon-image", iconImage)
    setProperty(propertyValue)
  }

  /**
   * Name of image in sprite to use for drawing an image background.
   *
   * This is an Expression representation of "icon-image".
   *
   * Name of image in sprite to use for drawing an image background.
   */
  val iconImageAsExpression: Expression?
    /**
     * Name of image in sprite to use for drawing an image background.
     *
     * Get the IconImage property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("icon-image")?.let {
        return it
      }
      iconImage?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Name of image in sprite to use for drawing an image background.
   *
   * @param iconImage value of iconImage as Expression
   */
  override fun iconImage(iconImage: Expression) = apply {
    val propertyValue = PropertyValue("icon-image", iconImage)
    setProperty(propertyValue)
  }

  /**
   * If true, the icon may be flipped to prevent it from being rendered upside-down.
   */
  val iconKeepUpright: Boolean?
    /**
     * If true, the icon may be flipped to prevent it from being rendered upside-down.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("icon-keep-upright")
    }

  /**
   * If true, the icon may be flipped to prevent it from being rendered upside-down.
   *
   * @param iconKeepUpright value of iconKeepUpright
   */
  override fun iconKeepUpright(iconKeepUpright: Boolean) = apply {
    val propertyValue = PropertyValue("icon-keep-upright", iconKeepUpright)
    setProperty(propertyValue)
  }

  /**
   * If true, the icon may be flipped to prevent it from being rendered upside-down.
   *
   * This is an Expression representation of "icon-keep-upright".
   *
   * If true, the icon may be flipped to prevent it from being rendered upside-down.
   */
  val iconKeepUprightAsExpression: Expression?
    /**
     * If true, the icon may be flipped to prevent it from being rendered upside-down.
     *
     * Get the IconKeepUpright property as an Expression
     *
     * @return Boolean
     */
    get() {
      getPropertyValue<Expression>("icon-keep-upright")?.let {
        return it
      }
      iconKeepUpright?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * If true, the icon may be flipped to prevent it from being rendered upside-down.
   *
   * @param iconKeepUpright value of iconKeepUpright as Expression
   */
  override fun iconKeepUpright(iconKeepUpright: Expression) = apply {
    val propertyValue = PropertyValue("icon-keep-upright", iconKeepUpright)
    setProperty(propertyValue)
  }

  /**
   * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
   */
  val iconOffset: List<Double>?
    /**
     * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("icon-offset")
    }

  /**
   * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
   *
   * @param iconOffset value of iconOffset
   */
  override fun iconOffset(iconOffset: List<Double>) = apply {
    val propertyValue = PropertyValue("icon-offset", iconOffset)
    setProperty(propertyValue)
  }

  /**
   * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
   *
   * This is an Expression representation of "icon-offset".
   *
   * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
   */
  val iconOffsetAsExpression: Expression?
    /**
     * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
     *
     * Get the IconOffset property as an Expression
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("icon-offset")?.let {
        return it
      }
      iconOffset?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
   *
   * @param iconOffset value of iconOffset as Expression
   */
  override fun iconOffset(iconOffset: Expression) = apply {
    val propertyValue = PropertyValue("icon-offset", iconOffset)
    setProperty(propertyValue)
  }

  /**
   * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
   */
  val iconOptional: Boolean?
    /**
     * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("icon-optional")
    }

  /**
   * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
   *
   * @param iconOptional value of iconOptional
   */
  override fun iconOptional(iconOptional: Boolean) = apply {
    val propertyValue = PropertyValue("icon-optional", iconOptional)
    setProperty(propertyValue)
  }

  /**
   * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
   *
   * This is an Expression representation of "icon-optional".
   *
   * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
   */
  val iconOptionalAsExpression: Expression?
    /**
     * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
     *
     * Get the IconOptional property as an Expression
     *
     * @return Boolean
     */
    get() {
      getPropertyValue<Expression>("icon-optional")?.let {
        return it
      }
      iconOptional?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
   *
   * @param iconOptional value of iconOptional as Expression
   */
  override fun iconOptional(iconOptional: Expression) = apply {
    val propertyValue = PropertyValue("icon-optional", iconOptional)
    setProperty(propertyValue)
  }

  /**
   * Size of the additional area around the icon bounding box used for detecting symbol collisions.
   */
  val iconPadding: Double?
    /**
     * Size of the additional area around the icon bounding box used for detecting symbol collisions.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("icon-padding")
    }

  /**
   * Size of the additional area around the icon bounding box used for detecting symbol collisions.
   *
   * @param iconPadding value of iconPadding
   */
  override fun iconPadding(iconPadding: Double) = apply {
    val propertyValue = PropertyValue("icon-padding", iconPadding)
    setProperty(propertyValue)
  }

  /**
   * Size of the additional area around the icon bounding box used for detecting symbol collisions.
   *
   * This is an Expression representation of "icon-padding".
   *
   * Size of the additional area around the icon bounding box used for detecting symbol collisions.
   */
  val iconPaddingAsExpression: Expression?
    /**
     * Size of the additional area around the icon bounding box used for detecting symbol collisions.
     *
     * Get the IconPadding property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("icon-padding")?.let {
        return it
      }
      iconPadding?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Size of the additional area around the icon bounding box used for detecting symbol collisions.
   *
   * @param iconPadding value of iconPadding as Expression
   */
  override fun iconPadding(iconPadding: Expression) = apply {
    val propertyValue = PropertyValue("icon-padding", iconPadding)
    setProperty(propertyValue)
  }

  /**
   * Orientation of icon when map is pitched.
   */
  val iconPitchAlignment: IconPitchAlignment?
    /**
     * Orientation of icon when map is pitched.
     *
     * @return IconPitchAlignment
     */
    get() {
      getPropertyValue<String?>("icon-pitch-alignment")?.let {
        return IconPitchAlignment.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Orientation of icon when map is pitched.
   *
   * @param iconPitchAlignment value of iconPitchAlignment
   */
  override fun iconPitchAlignment(iconPitchAlignment: IconPitchAlignment) = apply {
    val propertyValue = PropertyValue("icon-pitch-alignment", iconPitchAlignment)
    setProperty(propertyValue)
  }

  /**
   * Orientation of icon when map is pitched.
   *
   * This is an Expression representation of "icon-pitch-alignment".
   *
   * Orientation of icon when map is pitched.
   */
  val iconPitchAlignmentAsExpression: Expression?
    /**
     * Orientation of icon when map is pitched.
     *
     * Get the IconPitchAlignment property as an Expression
     *
     * @return IconPitchAlignment
     */
    get() {
      getPropertyValue<Expression>("icon-pitch-alignment")?.let {
        return it
      }
      iconPitchAlignment?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Orientation of icon when map is pitched.
   *
   * @param iconPitchAlignment value of iconPitchAlignment as Expression
   */
  override fun iconPitchAlignment(iconPitchAlignment: Expression) = apply {
    val propertyValue = PropertyValue("icon-pitch-alignment", iconPitchAlignment)
    setProperty(propertyValue)
  }

  /**
   * Rotates the icon clockwise.
   */
  val iconRotate: Double?
    /**
     * Rotates the icon clockwise.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("icon-rotate")
    }

  /**
   * Rotates the icon clockwise.
   *
   * @param iconRotate value of iconRotate
   */
  override fun iconRotate(iconRotate: Double) = apply {
    val propertyValue = PropertyValue("icon-rotate", iconRotate)
    setProperty(propertyValue)
  }

  /**
   * Rotates the icon clockwise.
   *
   * This is an Expression representation of "icon-rotate".
   *
   * Rotates the icon clockwise.
   */
  val iconRotateAsExpression: Expression?
    /**
     * Rotates the icon clockwise.
     *
     * Get the IconRotate property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("icon-rotate")?.let {
        return it
      }
      iconRotate?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Rotates the icon clockwise.
   *
   * @param iconRotate value of iconRotate as Expression
   */
  override fun iconRotate(iconRotate: Expression) = apply {
    val propertyValue = PropertyValue("icon-rotate", iconRotate)
    setProperty(propertyValue)
  }

  /**
   * In combination with `symbol-placement`, determines the rotation behavior of icons.
   */
  val iconRotationAlignment: IconRotationAlignment?
    /**
     * In combination with `symbol-placement`, determines the rotation behavior of icons.
     *
     * @return IconRotationAlignment
     */
    get() {
      getPropertyValue<String?>("icon-rotation-alignment")?.let {
        return IconRotationAlignment.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * In combination with `symbol-placement`, determines the rotation behavior of icons.
   *
   * @param iconRotationAlignment value of iconRotationAlignment
   */
  override fun iconRotationAlignment(iconRotationAlignment: IconRotationAlignment) = apply {
    val propertyValue = PropertyValue("icon-rotation-alignment", iconRotationAlignment)
    setProperty(propertyValue)
  }

  /**
   * In combination with `symbol-placement`, determines the rotation behavior of icons.
   *
   * This is an Expression representation of "icon-rotation-alignment".
   *
   * In combination with `symbol-placement`, determines the rotation behavior of icons.
   */
  val iconRotationAlignmentAsExpression: Expression?
    /**
     * In combination with `symbol-placement`, determines the rotation behavior of icons.
     *
     * Get the IconRotationAlignment property as an Expression
     *
     * @return IconRotationAlignment
     */
    get() {
      getPropertyValue<Expression>("icon-rotation-alignment")?.let {
        return it
      }
      iconRotationAlignment?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * In combination with `symbol-placement`, determines the rotation behavior of icons.
   *
   * @param iconRotationAlignment value of iconRotationAlignment as Expression
   */
  override fun iconRotationAlignment(iconRotationAlignment: Expression) = apply {
    val propertyValue = PropertyValue("icon-rotation-alignment", iconRotationAlignment)
    setProperty(propertyValue)
  }

  /**
   * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
   */
  val iconSize: Double?
    /**
     * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("icon-size")
    }

  /**
   * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
   *
   * @param iconSize value of iconSize
   */
  override fun iconSize(iconSize: Double) = apply {
    val propertyValue = PropertyValue("icon-size", iconSize)
    setProperty(propertyValue)
  }

  /**
   * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
   *
   * This is an Expression representation of "icon-size".
   *
   * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
   */
  val iconSizeAsExpression: Expression?
    /**
     * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
     *
     * Get the IconSize property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("icon-size")?.let {
        return it
      }
      iconSize?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
   *
   * @param iconSize value of iconSize as Expression
   */
  override fun iconSize(iconSize: Expression) = apply {
    val propertyValue = PropertyValue("icon-size", iconSize)
    setProperty(propertyValue)
  }

  /**
   * Scales the icon to fit around the associated text.
   */
  val iconTextFit: IconTextFit?
    /**
     * Scales the icon to fit around the associated text.
     *
     * @return IconTextFit
     */
    get() {
      getPropertyValue<String?>("icon-text-fit")?.let {
        return IconTextFit.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Scales the icon to fit around the associated text.
   *
   * @param iconTextFit value of iconTextFit
   */
  override fun iconTextFit(iconTextFit: IconTextFit) = apply {
    val propertyValue = PropertyValue("icon-text-fit", iconTextFit)
    setProperty(propertyValue)
  }

  /**
   * Scales the icon to fit around the associated text.
   *
   * This is an Expression representation of "icon-text-fit".
   *
   * Scales the icon to fit around the associated text.
   */
  val iconTextFitAsExpression: Expression?
    /**
     * Scales the icon to fit around the associated text.
     *
     * Get the IconTextFit property as an Expression
     *
     * @return IconTextFit
     */
    get() {
      getPropertyValue<Expression>("icon-text-fit")?.let {
        return it
      }
      iconTextFit?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Scales the icon to fit around the associated text.
   *
   * @param iconTextFit value of iconTextFit as Expression
   */
  override fun iconTextFit(iconTextFit: Expression) = apply {
    val propertyValue = PropertyValue("icon-text-fit", iconTextFit)
    setProperty(propertyValue)
  }

  /**
   * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left.
   */
  val iconTextFitPadding: List<Double>?
    /**
     * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("icon-text-fit-padding")
    }

  /**
   * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left.
   *
   * @param iconTextFitPadding value of iconTextFitPadding
   */
  override fun iconTextFitPadding(iconTextFitPadding: List<Double>) = apply {
    val propertyValue = PropertyValue("icon-text-fit-padding", iconTextFitPadding)
    setProperty(propertyValue)
  }

  /**
   * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left.
   *
   * This is an Expression representation of "icon-text-fit-padding".
   *
   * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left.
   */
  val iconTextFitPaddingAsExpression: Expression?
    /**
     * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left.
     *
     * Get the IconTextFitPadding property as an Expression
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("icon-text-fit-padding")?.let {
        return it
      }
      iconTextFitPadding?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left.
   *
   * @param iconTextFitPadding value of iconTextFitPadding as Expression
   */
  override fun iconTextFitPadding(iconTextFitPadding: Expression) = apply {
    val propertyValue = PropertyValue("icon-text-fit-padding", iconTextFitPadding)
    setProperty(propertyValue)
  }

  /**
   * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
   */
  val symbolAvoidEdges: Boolean?
    /**
     * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("symbol-avoid-edges")
    }

  /**
   * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
   *
   * @param symbolAvoidEdges value of symbolAvoidEdges
   */
  override fun symbolAvoidEdges(symbolAvoidEdges: Boolean) = apply {
    val propertyValue = PropertyValue("symbol-avoid-edges", symbolAvoidEdges)
    setProperty(propertyValue)
  }

  /**
   * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
   *
   * This is an Expression representation of "symbol-avoid-edges".
   *
   * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
   */
  val symbolAvoidEdgesAsExpression: Expression?
    /**
     * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
     *
     * Get the SymbolAvoidEdges property as an Expression
     *
     * @return Boolean
     */
    get() {
      getPropertyValue<Expression>("symbol-avoid-edges")?.let {
        return it
      }
      symbolAvoidEdges?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
   *
   * @param symbolAvoidEdges value of symbolAvoidEdges as Expression
   */
  override fun symbolAvoidEdges(symbolAvoidEdges: Expression) = apply {
    val propertyValue = PropertyValue("symbol-avoid-edges", symbolAvoidEdges)
    setProperty(propertyValue)
  }

  /**
   * Label placement relative to its geometry.
   */
  val symbolPlacement: SymbolPlacement?
    /**
     * Label placement relative to its geometry.
     *
     * @return SymbolPlacement
     */
    get() {
      getPropertyValue<String?>("symbol-placement")?.let {
        return SymbolPlacement.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Label placement relative to its geometry.
   *
   * @param symbolPlacement value of symbolPlacement
   */
  override fun symbolPlacement(symbolPlacement: SymbolPlacement) = apply {
    val propertyValue = PropertyValue("symbol-placement", symbolPlacement)
    setProperty(propertyValue)
  }

  /**
   * Label placement relative to its geometry.
   *
   * This is an Expression representation of "symbol-placement".
   *
   * Label placement relative to its geometry.
   */
  val symbolPlacementAsExpression: Expression?
    /**
     * Label placement relative to its geometry.
     *
     * Get the SymbolPlacement property as an Expression
     *
     * @return SymbolPlacement
     */
    get() {
      getPropertyValue<Expression>("symbol-placement")?.let {
        return it
      }
      symbolPlacement?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Label placement relative to its geometry.
   *
   * @param symbolPlacement value of symbolPlacement as Expression
   */
  override fun symbolPlacement(symbolPlacement: Expression) = apply {
    val propertyValue = PropertyValue("symbol-placement", symbolPlacement)
    setProperty(propertyValue)
  }

  /**
   * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first.  When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
   */
  val symbolSortKey: Double?
    /**
     * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first.  When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("symbol-sort-key")
    }

  /**
   * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first.  When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
   *
   * @param symbolSortKey value of symbolSortKey
   */
  override fun symbolSortKey(symbolSortKey: Double) = apply {
    val propertyValue = PropertyValue("symbol-sort-key", symbolSortKey)
    setProperty(propertyValue)
  }

  /**
   * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first.  When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
   *
   * This is an Expression representation of "symbol-sort-key".
   *
   * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first.  When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
   */
  val symbolSortKeyAsExpression: Expression?
    /**
     * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first.  When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
     *
     * Get the SymbolSortKey property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("symbol-sort-key")?.let {
        return it
      }
      symbolSortKey?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first.  When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
   *
   * @param symbolSortKey value of symbolSortKey as Expression
   */
  override fun symbolSortKey(symbolSortKey: Expression) = apply {
    val propertyValue = PropertyValue("symbol-sort-key", symbolSortKey)
    setProperty(propertyValue)
  }

  /**
   * Distance between two symbol anchors.
   */
  val symbolSpacing: Double?
    /**
     * Distance between two symbol anchors.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("symbol-spacing")
    }

  /**
   * Distance between two symbol anchors.
   *
   * @param symbolSpacing value of symbolSpacing
   */
  override fun symbolSpacing(symbolSpacing: Double) = apply {
    val propertyValue = PropertyValue("symbol-spacing", symbolSpacing)
    setProperty(propertyValue)
  }

  /**
   * Distance between two symbol anchors.
   *
   * This is an Expression representation of "symbol-spacing".
   *
   * Distance between two symbol anchors.
   */
  val symbolSpacingAsExpression: Expression?
    /**
     * Distance between two symbol anchors.
     *
     * Get the SymbolSpacing property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("symbol-spacing")?.let {
        return it
      }
      symbolSpacing?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Distance between two symbol anchors.
   *
   * @param symbolSpacing value of symbolSpacing as Expression
   */
  override fun symbolSpacing(symbolSpacing: Expression) = apply {
    val propertyValue = PropertyValue("symbol-spacing", symbolSpacing)
    setProperty(propertyValue)
  }

  /**
   * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
   */
  val symbolZOrder: SymbolZOrder?
    /**
     * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
     *
     * @return SymbolZOrder
     */
    get() {
      getPropertyValue<String?>("symbol-z-order")?.let {
        return SymbolZOrder.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
   *
   * @param symbolZOrder value of symbolZOrder
   */
  override fun symbolZOrder(symbolZOrder: SymbolZOrder) = apply {
    val propertyValue = PropertyValue("symbol-z-order", symbolZOrder)
    setProperty(propertyValue)
  }

  /**
   * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
   *
   * This is an Expression representation of "symbol-z-order".
   *
   * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
   */
  val symbolZOrderAsExpression: Expression?
    /**
     * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
     *
     * Get the SymbolZOrder property as an Expression
     *
     * @return SymbolZOrder
     */
    get() {
      getPropertyValue<Expression>("symbol-z-order")?.let {
        return it
      }
      symbolZOrder?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
   *
   * @param symbolZOrder value of symbolZOrder as Expression
   */
  override fun symbolZOrder(symbolZOrder: Expression) = apply {
    val propertyValue = PropertyValue("symbol-z-order", symbolZOrder)
    setProperty(propertyValue)
  }

  /**
   * If true, the text will be visible even if it collides with other previously drawn symbols.
   */
  val textAllowOverlap: Boolean?
    /**
     * If true, the text will be visible even if it collides with other previously drawn symbols.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("text-allow-overlap")
    }

  /**
   * If true, the text will be visible even if it collides with other previously drawn symbols.
   *
   * @param textAllowOverlap value of textAllowOverlap
   */
  override fun textAllowOverlap(textAllowOverlap: Boolean) = apply {
    val propertyValue = PropertyValue("text-allow-overlap", textAllowOverlap)
    setProperty(propertyValue)
  }

  /**
   * If true, the text will be visible even if it collides with other previously drawn symbols.
   *
   * This is an Expression representation of "text-allow-overlap".
   *
   * If true, the text will be visible even if it collides with other previously drawn symbols.
   */
  val textAllowOverlapAsExpression: Expression?
    /**
     * If true, the text will be visible even if it collides with other previously drawn symbols.
     *
     * Get the TextAllowOverlap property as an Expression
     *
     * @return Boolean
     */
    get() {
      getPropertyValue<Expression>("text-allow-overlap")?.let {
        return it
      }
      textAllowOverlap?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * If true, the text will be visible even if it collides with other previously drawn symbols.
   *
   * @param textAllowOverlap value of textAllowOverlap as Expression
   */
  override fun textAllowOverlap(textAllowOverlap: Expression) = apply {
    val propertyValue = PropertyValue("text-allow-overlap", textAllowOverlap)
    setProperty(propertyValue)
  }

  /**
   * Part of the text placed closest to the anchor.
   */
  val textAnchor: TextAnchor?
    /**
     * Part of the text placed closest to the anchor.
     *
     * @return TextAnchor
     */
    get() {
      getPropertyValue<String?>("text-anchor")?.let {
        return TextAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Part of the text placed closest to the anchor.
   *
   * @param textAnchor value of textAnchor
   */
  override fun textAnchor(textAnchor: TextAnchor) = apply {
    val propertyValue = PropertyValue("text-anchor", textAnchor)
    setProperty(propertyValue)
  }

  /**
   * Part of the text placed closest to the anchor.
   *
   * This is an Expression representation of "text-anchor".
   *
   * Part of the text placed closest to the anchor.
   */
  val textAnchorAsExpression: Expression?
    /**
     * Part of the text placed closest to the anchor.
     *
     * Get the TextAnchor property as an Expression
     *
     * @return TextAnchor
     */
    get() {
      getPropertyValue<Expression>("text-anchor")?.let {
        return it
      }
      textAnchor?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Part of the text placed closest to the anchor.
   *
   * @param textAnchor value of textAnchor as Expression
   */
  override fun textAnchor(textAnchor: Expression) = apply {
    val propertyValue = PropertyValue("text-anchor", textAnchor)
    setProperty(propertyValue)
  }

  /**
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
   */
  val textField: Formatted?
    /**
     * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
     *
     * @return Formatted
     */
    get() {
      textFieldAsExpression?.let {
        return Formatted.fromExpression(it)
      }
      return null
    }

  /**
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
   *
   * @param textField value of textField
   */
  override fun textField(textField: Formatted) = apply {
    val propertyValue = PropertyValue("text-field", textField)
    setProperty(propertyValue)
  }

  /**
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
   *
   * This is an Expression representation of "text-field".
   *
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
   */
  val textFieldAsExpression: Expression?
    /**
     * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
     *
     * Get the TextField property as an Expression
     *
     * @return Formatted
     */
    get() {
      getPropertyValue<Expression>("text-field")?.let {
        return it
      }
      return null
    }

  /**
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
   *
   * @param textField value of textField as Expression
   */
  override fun textField(textField: Expression) = apply {
    val propertyValue = PropertyValue("text-field", textField)
    setProperty(propertyValue)
  }

  /**
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
   */
  val textFieldAsString: String?
    /**
     * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
     *
     * @return value of textField
     */
    get() {
      textField?.let {
        return it.getTextAsString()
      }
      return null
    }

  /**
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
   *
   * Set the TextField property as String.
   *
   * @param textField value of textField as String
   */
  override fun textField(textField: String) = apply {
    val propertyValue = PropertyValue("text-field", textField)
    setProperty(propertyValue)
  }

  /**
   * DSL for construct [Formatted] textField.
   */
  override fun textField(block: Formatted.() -> Unit) = apply {
    textField(Formatted().apply(block))
  }

  /**
   * Font stack to use for displaying text.
   */
  val textFont: List<String>?
    /**
     * Font stack to use for displaying text.
     *
     * @return List<String>
     */
    get() {
      return getPropertyValue<List<String>>("text-font")
    }

  /**
   * Font stack to use for displaying text.
   *
   * @param textFont value of textFont
   */
  override fun textFont(textFont: List<String>) = apply {
    val propertyValue = PropertyValue("text-font", textFont)
    setProperty(propertyValue)
  }

  /**
   * Font stack to use for displaying text.
   *
   * This is an Expression representation of "text-font".
   *
   * Font stack to use for displaying text.
   */
  val textFontAsExpression: Expression?
    /**
     * Font stack to use for displaying text.
     *
     * Get the TextFont property as an Expression
     *
     * @return List<String>
     */
    get() {
      getPropertyValue<Expression>("text-font")?.let {
        return it
      }
      textFont?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Font stack to use for displaying text.
   *
   * @param textFont value of textFont as Expression
   */
  override fun textFont(textFont: Expression) = apply {
    val propertyValue = PropertyValue("text-font", textFont)
    setProperty(propertyValue)
  }

  /**
   * If true, other symbols can be visible even if they collide with the text.
   */
  val textIgnorePlacement: Boolean?
    /**
     * If true, other symbols can be visible even if they collide with the text.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("text-ignore-placement")
    }

  /**
   * If true, other symbols can be visible even if they collide with the text.
   *
   * @param textIgnorePlacement value of textIgnorePlacement
   */
  override fun textIgnorePlacement(textIgnorePlacement: Boolean) = apply {
    val propertyValue = PropertyValue("text-ignore-placement", textIgnorePlacement)
    setProperty(propertyValue)
  }

  /**
   * If true, other symbols can be visible even if they collide with the text.
   *
   * This is an Expression representation of "text-ignore-placement".
   *
   * If true, other symbols can be visible even if they collide with the text.
   */
  val textIgnorePlacementAsExpression: Expression?
    /**
     * If true, other symbols can be visible even if they collide with the text.
     *
     * Get the TextIgnorePlacement property as an Expression
     *
     * @return Boolean
     */
    get() {
      getPropertyValue<Expression>("text-ignore-placement")?.let {
        return it
      }
      textIgnorePlacement?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * If true, other symbols can be visible even if they collide with the text.
   *
   * @param textIgnorePlacement value of textIgnorePlacement as Expression
   */
  override fun textIgnorePlacement(textIgnorePlacement: Expression) = apply {
    val propertyValue = PropertyValue("text-ignore-placement", textIgnorePlacement)
    setProperty(propertyValue)
  }

  /**
   * Text justification options.
   */
  val textJustify: TextJustify?
    /**
     * Text justification options.
     *
     * @return TextJustify
     */
    get() {
      getPropertyValue<String?>("text-justify")?.let {
        return TextJustify.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Text justification options.
   *
   * @param textJustify value of textJustify
   */
  override fun textJustify(textJustify: TextJustify) = apply {
    val propertyValue = PropertyValue("text-justify", textJustify)
    setProperty(propertyValue)
  }

  /**
   * Text justification options.
   *
   * This is an Expression representation of "text-justify".
   *
   * Text justification options.
   */
  val textJustifyAsExpression: Expression?
    /**
     * Text justification options.
     *
     * Get the TextJustify property as an Expression
     *
     * @return TextJustify
     */
    get() {
      getPropertyValue<Expression>("text-justify")?.let {
        return it
      }
      textJustify?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Text justification options.
   *
   * @param textJustify value of textJustify as Expression
   */
  override fun textJustify(textJustify: Expression) = apply {
    val propertyValue = PropertyValue("text-justify", textJustify)
    setProperty(propertyValue)
  }

  /**
   * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
   */
  val textKeepUpright: Boolean?
    /**
     * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("text-keep-upright")
    }

  /**
   * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
   *
   * @param textKeepUpright value of textKeepUpright
   */
  override fun textKeepUpright(textKeepUpright: Boolean) = apply {
    val propertyValue = PropertyValue("text-keep-upright", textKeepUpright)
    setProperty(propertyValue)
  }

  /**
   * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
   *
   * This is an Expression representation of "text-keep-upright".
   *
   * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
   */
  val textKeepUprightAsExpression: Expression?
    /**
     * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
     *
     * Get the TextKeepUpright property as an Expression
     *
     * @return Boolean
     */
    get() {
      getPropertyValue<Expression>("text-keep-upright")?.let {
        return it
      }
      textKeepUpright?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
   *
   * @param textKeepUpright value of textKeepUpright as Expression
   */
  override fun textKeepUpright(textKeepUpright: Expression) = apply {
    val propertyValue = PropertyValue("text-keep-upright", textKeepUpright)
    setProperty(propertyValue)
  }

  /**
   * Text tracking amount.
   */
  val textLetterSpacing: Double?
    /**
     * Text tracking amount.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("text-letter-spacing")
    }

  /**
   * Text tracking amount.
   *
   * @param textLetterSpacing value of textLetterSpacing
   */
  override fun textLetterSpacing(textLetterSpacing: Double) = apply {
    val propertyValue = PropertyValue("text-letter-spacing", textLetterSpacing)
    setProperty(propertyValue)
  }

  /**
   * Text tracking amount.
   *
   * This is an Expression representation of "text-letter-spacing".
   *
   * Text tracking amount.
   */
  val textLetterSpacingAsExpression: Expression?
    /**
     * Text tracking amount.
     *
     * Get the TextLetterSpacing property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("text-letter-spacing")?.let {
        return it
      }
      textLetterSpacing?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Text tracking amount.
   *
   * @param textLetterSpacing value of textLetterSpacing as Expression
   */
  override fun textLetterSpacing(textLetterSpacing: Expression) = apply {
    val propertyValue = PropertyValue("text-letter-spacing", textLetterSpacing)
    setProperty(propertyValue)
  }

  /**
   * Text leading value for multi-line text.
   */
  val textLineHeight: Double?
    /**
     * Text leading value for multi-line text.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("text-line-height")
    }

  /**
   * Text leading value for multi-line text.
   *
   * @param textLineHeight value of textLineHeight
   */
  override fun textLineHeight(textLineHeight: Double) = apply {
    val propertyValue = PropertyValue("text-line-height", textLineHeight)
    setProperty(propertyValue)
  }

  /**
   * Text leading value for multi-line text.
   *
   * This is an Expression representation of "text-line-height".
   *
   * Text leading value for multi-line text.
   */
  val textLineHeightAsExpression: Expression?
    /**
     * Text leading value for multi-line text.
     *
     * Get the TextLineHeight property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("text-line-height")?.let {
        return it
      }
      textLineHeight?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Text leading value for multi-line text.
   *
   * @param textLineHeight value of textLineHeight as Expression
   */
  override fun textLineHeight(textLineHeight: Expression) = apply {
    val propertyValue = PropertyValue("text-line-height", textLineHeight)
    setProperty(propertyValue)
  }

  /**
   * Maximum angle change between adjacent characters.
   */
  val textMaxAngle: Double?
    /**
     * Maximum angle change between adjacent characters.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("text-max-angle")
    }

  /**
   * Maximum angle change between adjacent characters.
   *
   * @param textMaxAngle value of textMaxAngle
   */
  override fun textMaxAngle(textMaxAngle: Double) = apply {
    val propertyValue = PropertyValue("text-max-angle", textMaxAngle)
    setProperty(propertyValue)
  }

  /**
   * Maximum angle change between adjacent characters.
   *
   * This is an Expression representation of "text-max-angle".
   *
   * Maximum angle change between adjacent characters.
   */
  val textMaxAngleAsExpression: Expression?
    /**
     * Maximum angle change between adjacent characters.
     *
     * Get the TextMaxAngle property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("text-max-angle")?.let {
        return it
      }
      textMaxAngle?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Maximum angle change between adjacent characters.
   *
   * @param textMaxAngle value of textMaxAngle as Expression
   */
  override fun textMaxAngle(textMaxAngle: Expression) = apply {
    val propertyValue = PropertyValue("text-max-angle", textMaxAngle)
    setProperty(propertyValue)
  }

  /**
   * The maximum line width for text wrapping.
   */
  val textMaxWidth: Double?
    /**
     * The maximum line width for text wrapping.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("text-max-width")
    }

  /**
   * The maximum line width for text wrapping.
   *
   * @param textMaxWidth value of textMaxWidth
   */
  override fun textMaxWidth(textMaxWidth: Double) = apply {
    val propertyValue = PropertyValue("text-max-width", textMaxWidth)
    setProperty(propertyValue)
  }

  /**
   * The maximum line width for text wrapping.
   *
   * This is an Expression representation of "text-max-width".
   *
   * The maximum line width for text wrapping.
   */
  val textMaxWidthAsExpression: Expression?
    /**
     * The maximum line width for text wrapping.
     *
     * Get the TextMaxWidth property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("text-max-width")?.let {
        return it
      }
      textMaxWidth?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The maximum line width for text wrapping.
   *
   * @param textMaxWidth value of textMaxWidth as Expression
   */
  override fun textMaxWidth(textMaxWidth: Expression) = apply {
    val propertyValue = PropertyValue("text-max-width", textMaxWidth)
    setProperty(propertyValue)
  }

  /**
   * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
   */
  val textOffset: List<Double>?
    /**
     * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("text-offset")
    }

  /**
   * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
   *
   * @param textOffset value of textOffset
   */
  override fun textOffset(textOffset: List<Double>) = apply {
    val propertyValue = PropertyValue("text-offset", textOffset)
    setProperty(propertyValue)
  }

  /**
   * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
   *
   * This is an Expression representation of "text-offset".
   *
   * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
   */
  val textOffsetAsExpression: Expression?
    /**
     * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
     *
     * Get the TextOffset property as an Expression
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("text-offset")?.let {
        return it
      }
      textOffset?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
   *
   * @param textOffset value of textOffset as Expression
   */
  override fun textOffset(textOffset: Expression) = apply {
    val propertyValue = PropertyValue("text-offset", textOffset)
    setProperty(propertyValue)
  }

  /**
   * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
   */
  val textOptional: Boolean?
    /**
     * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
     *
     * @return Boolean
     */
    get() {
      return getPropertyValue("text-optional")
    }

  /**
   * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
   *
   * @param textOptional value of textOptional
   */
  override fun textOptional(textOptional: Boolean) = apply {
    val propertyValue = PropertyValue("text-optional", textOptional)
    setProperty(propertyValue)
  }

  /**
   * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
   *
   * This is an Expression representation of "text-optional".
   *
   * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
   */
  val textOptionalAsExpression: Expression?
    /**
     * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
     *
     * Get the TextOptional property as an Expression
     *
     * @return Boolean
     */
    get() {
      getPropertyValue<Expression>("text-optional")?.let {
        return it
      }
      textOptional?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
   *
   * @param textOptional value of textOptional as Expression
   */
  override fun textOptional(textOptional: Expression) = apply {
    val propertyValue = PropertyValue("text-optional", textOptional)
    setProperty(propertyValue)
  }

  /**
   * Size of the additional area around the text bounding box used for detecting symbol collisions.
   */
  val textPadding: Double?
    /**
     * Size of the additional area around the text bounding box used for detecting symbol collisions.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("text-padding")
    }

  /**
   * Size of the additional area around the text bounding box used for detecting symbol collisions.
   *
   * @param textPadding value of textPadding
   */
  override fun textPadding(textPadding: Double) = apply {
    val propertyValue = PropertyValue("text-padding", textPadding)
    setProperty(propertyValue)
  }

  /**
   * Size of the additional area around the text bounding box used for detecting symbol collisions.
   *
   * This is an Expression representation of "text-padding".
   *
   * Size of the additional area around the text bounding box used for detecting symbol collisions.
   */
  val textPaddingAsExpression: Expression?
    /**
     * Size of the additional area around the text bounding box used for detecting symbol collisions.
     *
     * Get the TextPadding property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("text-padding")?.let {
        return it
      }
      textPadding?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Size of the additional area around the text bounding box used for detecting symbol collisions.
   *
   * @param textPadding value of textPadding as Expression
   */
  override fun textPadding(textPadding: Expression) = apply {
    val propertyValue = PropertyValue("text-padding", textPadding)
    setProperty(propertyValue)
  }

  /**
   * Orientation of text when map is pitched.
   */
  val textPitchAlignment: TextPitchAlignment?
    /**
     * Orientation of text when map is pitched.
     *
     * @return TextPitchAlignment
     */
    get() {
      getPropertyValue<String?>("text-pitch-alignment")?.let {
        return TextPitchAlignment.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Orientation of text when map is pitched.
   *
   * @param textPitchAlignment value of textPitchAlignment
   */
  override fun textPitchAlignment(textPitchAlignment: TextPitchAlignment) = apply {
    val propertyValue = PropertyValue("text-pitch-alignment", textPitchAlignment)
    setProperty(propertyValue)
  }

  /**
   * Orientation of text when map is pitched.
   *
   * This is an Expression representation of "text-pitch-alignment".
   *
   * Orientation of text when map is pitched.
   */
  val textPitchAlignmentAsExpression: Expression?
    /**
     * Orientation of text when map is pitched.
     *
     * Get the TextPitchAlignment property as an Expression
     *
     * @return TextPitchAlignment
     */
    get() {
      getPropertyValue<Expression>("text-pitch-alignment")?.let {
        return it
      }
      textPitchAlignment?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Orientation of text when map is pitched.
   *
   * @param textPitchAlignment value of textPitchAlignment as Expression
   */
  override fun textPitchAlignment(textPitchAlignment: Expression) = apply {
    val propertyValue = PropertyValue("text-pitch-alignment", textPitchAlignment)
    setProperty(propertyValue)
  }

  /**
   * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
   */
  val textRadialOffset: Double?
    /**
     * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("text-radial-offset")
    }

  /**
   * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
   *
   * @param textRadialOffset value of textRadialOffset
   */
  override fun textRadialOffset(textRadialOffset: Double) = apply {
    val propertyValue = PropertyValue("text-radial-offset", textRadialOffset)
    setProperty(propertyValue)
  }

  /**
   * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
   *
   * This is an Expression representation of "text-radial-offset".
   *
   * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
   */
  val textRadialOffsetAsExpression: Expression?
    /**
     * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
     *
     * Get the TextRadialOffset property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("text-radial-offset")?.let {
        return it
      }
      textRadialOffset?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
   *
   * @param textRadialOffset value of textRadialOffset as Expression
   */
  override fun textRadialOffset(textRadialOffset: Expression) = apply {
    val propertyValue = PropertyValue("text-radial-offset", textRadialOffset)
    setProperty(propertyValue)
  }

  /**
   * Rotates the text clockwise.
   */
  val textRotate: Double?
    /**
     * Rotates the text clockwise.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("text-rotate")
    }

  /**
   * Rotates the text clockwise.
   *
   * @param textRotate value of textRotate
   */
  override fun textRotate(textRotate: Double) = apply {
    val propertyValue = PropertyValue("text-rotate", textRotate)
    setProperty(propertyValue)
  }

  /**
   * Rotates the text clockwise.
   *
   * This is an Expression representation of "text-rotate".
   *
   * Rotates the text clockwise.
   */
  val textRotateAsExpression: Expression?
    /**
     * Rotates the text clockwise.
     *
     * Get the TextRotate property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("text-rotate")?.let {
        return it
      }
      textRotate?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Rotates the text clockwise.
   *
   * @param textRotate value of textRotate as Expression
   */
  override fun textRotate(textRotate: Expression) = apply {
    val propertyValue = PropertyValue("text-rotate", textRotate)
    setProperty(propertyValue)
  }

  /**
   * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
   */
  val textRotationAlignment: TextRotationAlignment?
    /**
     * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
     *
     * @return TextRotationAlignment
     */
    get() {
      getPropertyValue<String?>("text-rotation-alignment")?.let {
        return TextRotationAlignment.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
   *
   * @param textRotationAlignment value of textRotationAlignment
   */
  override fun textRotationAlignment(textRotationAlignment: TextRotationAlignment) = apply {
    val propertyValue = PropertyValue("text-rotation-alignment", textRotationAlignment)
    setProperty(propertyValue)
  }

  /**
   * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
   *
   * This is an Expression representation of "text-rotation-alignment".
   *
   * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
   */
  val textRotationAlignmentAsExpression: Expression?
    /**
     * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
     *
     * Get the TextRotationAlignment property as an Expression
     *
     * @return TextRotationAlignment
     */
    get() {
      getPropertyValue<Expression>("text-rotation-alignment")?.let {
        return it
      }
      textRotationAlignment?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
   *
   * @param textRotationAlignment value of textRotationAlignment as Expression
   */
  override fun textRotationAlignment(textRotationAlignment: Expression) = apply {
    val propertyValue = PropertyValue("text-rotation-alignment", textRotationAlignment)
    setProperty(propertyValue)
  }

  /**
   * Font size.
   */
  val textSize: Double?
    /**
     * Font size.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("text-size")
    }

  /**
   * Font size.
   *
   * @param textSize value of textSize
   */
  override fun textSize(textSize: Double) = apply {
    val propertyValue = PropertyValue("text-size", textSize)
    setProperty(propertyValue)
  }

  /**
   * Font size.
   *
   * This is an Expression representation of "text-size".
   *
   * Font size.
   */
  val textSizeAsExpression: Expression?
    /**
     * Font size.
     *
     * Get the TextSize property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("text-size")?.let {
        return it
      }
      textSize?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Font size.
   *
   * @param textSize value of textSize as Expression
   */
  override fun textSize(textSize: Expression) = apply {
    val propertyValue = PropertyValue("text-size", textSize)
    setProperty(propertyValue)
  }

  /**
   * Specifies how to capitalize text, similar to the CSS `text-transform` property.
   */
  val textTransform: TextTransform?
    /**
     * Specifies how to capitalize text, similar to the CSS `text-transform` property.
     *
     * @return TextTransform
     */
    get() {
      getPropertyValue<String?>("text-transform")?.let {
        return TextTransform.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Specifies how to capitalize text, similar to the CSS `text-transform` property.
   *
   * @param textTransform value of textTransform
   */
  override fun textTransform(textTransform: TextTransform) = apply {
    val propertyValue = PropertyValue("text-transform", textTransform)
    setProperty(propertyValue)
  }

  /**
   * Specifies how to capitalize text, similar to the CSS `text-transform` property.
   *
   * This is an Expression representation of "text-transform".
   *
   * Specifies how to capitalize text, similar to the CSS `text-transform` property.
   */
  val textTransformAsExpression: Expression?
    /**
     * Specifies how to capitalize text, similar to the CSS `text-transform` property.
     *
     * Get the TextTransform property as an Expression
     *
     * @return TextTransform
     */
    get() {
      getPropertyValue<Expression>("text-transform")?.let {
        return it
      }
      textTransform?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Specifies how to capitalize text, similar to the CSS `text-transform` property.
   *
   * @param textTransform value of textTransform as Expression
   */
  override fun textTransform(textTransform: Expression) = apply {
    val propertyValue = PropertyValue("text-transform", textTransform)
    setProperty(propertyValue)
  }

  /**
   * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
   */
  val textVariableAnchor: List<String>?
    /**
     * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
     *
     * @return List<String>
     */
    get() {
      return getPropertyValue<List<String>>("text-variable-anchor")
    }

  /**
   * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
   *
   * @param textVariableAnchor value of textVariableAnchor
   */
  override fun textVariableAnchor(textVariableAnchor: List<String>) = apply {
    val propertyValue = PropertyValue("text-variable-anchor", textVariableAnchor)
    setProperty(propertyValue)
  }

  /**
   * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
   *
   * This is an Expression representation of "text-variable-anchor".
   *
   * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
   */
  val textVariableAnchorAsExpression: Expression?
    /**
     * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
     *
     * Get the TextVariableAnchor property as an Expression
     *
     * @return List<String>
     */
    get() {
      getPropertyValue<Expression>("text-variable-anchor")?.let {
        return it
      }
      textVariableAnchor?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
   *
   * @param textVariableAnchor value of textVariableAnchor as Expression
   */
  override fun textVariableAnchor(textVariableAnchor: Expression) = apply {
    val propertyValue = PropertyValue("text-variable-anchor", textVariableAnchor)
    setProperty(propertyValue)
  }

  /**
   * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
   */
  val textWritingMode: List<String>?
    /**
     * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
     *
     * @return List<String>
     */
    get() {
      return getPropertyValue<List<String>>("text-writing-mode")
    }

  /**
   * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
   *
   * @param textWritingMode value of textWritingMode
   */
  override fun textWritingMode(textWritingMode: List<String>) = apply {
    val propertyValue = PropertyValue("text-writing-mode", textWritingMode)
    setProperty(propertyValue)
  }

  /**
   * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
   *
   * This is an Expression representation of "text-writing-mode".
   *
   * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
   */
  val textWritingModeAsExpression: Expression?
    /**
     * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
     *
     * Get the TextWritingMode property as an Expression
     *
     * @return List<String>
     */
    get() {
      getPropertyValue<Expression>("text-writing-mode")?.let {
        return it
      }
      textWritingMode?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
   *
   * @param textWritingMode value of textWritingMode as Expression
   */
  override fun textWritingMode(textWritingMode: Expression) = apply {
    val propertyValue = PropertyValue("text-writing-mode", textWritingMode)
    setProperty(propertyValue)
  }

  /**
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   */
  val iconColor: String?
    /**
     * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
     *
     * @return String
     */
    get() {
      iconColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * @param iconColor value of iconColor
   */
  override fun iconColor(iconColor: String) = apply {
    val propertyValue = PropertyValue("icon-color", iconColor)
    setProperty(propertyValue)
  }

  /**
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * This is an Expression representation of "icon-color".
   *
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   */
  val iconColorAsExpression: Expression?
    /**
     * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
     *
     * Get the IconColor property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("icon-color")?.let {
        return it
      }
      return null
    }

  /**
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * @param iconColor value of iconColor as Expression
   */
  override fun iconColor(iconColor: Expression) = apply {
    val propertyValue = PropertyValue("icon-color", iconColor)
    setProperty(propertyValue)
  }

  /**
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   */
  val iconColorAsColorInt: Int?
    /**
     * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      iconColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * @param iconColor value of iconColor
   */
  override fun iconColor(@ColorInt iconColor: Int) = apply {
    val propertyValue = PropertyValue("icon-color", colorIntToRgbaExpression(iconColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for IconColor.
   */
  val iconColorTransition: StyleTransition?
    /**
     * Get the IconColor property transition options
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("icon-color-transition")
    }

  /**
   * Set the IconColor property transition options
   *
   * @param options transition options for String
   */
  override fun iconColorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("icon-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [iconColorTransition].
   */
  override fun iconColorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    iconColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Fade out the halo towards the outside.
   */
  val iconHaloBlur: Double?
    /**
     * Fade out the halo towards the outside.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("icon-halo-blur")
    }

  /**
   * Fade out the halo towards the outside.
   *
   * @param iconHaloBlur value of iconHaloBlur
   */
  override fun iconHaloBlur(iconHaloBlur: Double) = apply {
    val propertyValue = PropertyValue("icon-halo-blur", iconHaloBlur)
    setProperty(propertyValue)
  }

  /**
   * Fade out the halo towards the outside.
   *
   * This is an Expression representation of "icon-halo-blur".
   *
   * Fade out the halo towards the outside.
   */
  val iconHaloBlurAsExpression: Expression?
    /**
     * Fade out the halo towards the outside.
     *
     * Get the IconHaloBlur property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("icon-halo-blur")?.let {
        return it
      }
      iconHaloBlur?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Fade out the halo towards the outside.
   *
   * @param iconHaloBlur value of iconHaloBlur as Expression
   */
  override fun iconHaloBlur(iconHaloBlur: Expression) = apply {
    val propertyValue = PropertyValue("icon-halo-blur", iconHaloBlur)
    setProperty(propertyValue)
  }

  /**
   * Transition options for IconHaloBlur.
   */
  val iconHaloBlurTransition: StyleTransition?
    /**
     * Get the IconHaloBlur property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("icon-halo-blur-transition")
    }

  /**
   * Set the IconHaloBlur property transition options
   *
   * @param options transition options for Double
   */
  override fun iconHaloBlurTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("icon-halo-blur-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [iconHaloBlurTransition].
   */
  override fun iconHaloBlurTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    iconHaloBlurTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   */
  val iconHaloColor: String?
    /**
     * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
     *
     * @return String
     */
    get() {
      iconHaloColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * @param iconHaloColor value of iconHaloColor
   */
  override fun iconHaloColor(iconHaloColor: String) = apply {
    val propertyValue = PropertyValue("icon-halo-color", iconHaloColor)
    setProperty(propertyValue)
  }

  /**
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * This is an Expression representation of "icon-halo-color".
   *
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   */
  val iconHaloColorAsExpression: Expression?
    /**
     * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
     *
     * Get the IconHaloColor property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("icon-halo-color")?.let {
        return it
      }
      return null
    }

  /**
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * @param iconHaloColor value of iconHaloColor as Expression
   */
  override fun iconHaloColor(iconHaloColor: Expression) = apply {
    val propertyValue = PropertyValue("icon-halo-color", iconHaloColor)
    setProperty(propertyValue)
  }

  /**
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   */
  val iconHaloColorAsColorInt: Int?
    /**
     * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      iconHaloColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * @param iconHaloColor value of iconHaloColor
   */
  override fun iconHaloColor(@ColorInt iconHaloColor: Int) = apply {
    val propertyValue = PropertyValue("icon-halo-color", colorIntToRgbaExpression(iconHaloColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for IconHaloColor.
   */
  val iconHaloColorTransition: StyleTransition?
    /**
     * Get the IconHaloColor property transition options
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("icon-halo-color-transition")
    }

  /**
   * Set the IconHaloColor property transition options
   *
   * @param options transition options for String
   */
  override fun iconHaloColorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("icon-halo-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [iconHaloColorTransition].
   */
  override fun iconHaloColorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    iconHaloColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Distance of halo to the icon outline.
   */
  val iconHaloWidth: Double?
    /**
     * Distance of halo to the icon outline.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("icon-halo-width")
    }

  /**
   * Distance of halo to the icon outline.
   *
   * @param iconHaloWidth value of iconHaloWidth
   */
  override fun iconHaloWidth(iconHaloWidth: Double) = apply {
    val propertyValue = PropertyValue("icon-halo-width", iconHaloWidth)
    setProperty(propertyValue)
  }

  /**
   * Distance of halo to the icon outline.
   *
   * This is an Expression representation of "icon-halo-width".
   *
   * Distance of halo to the icon outline.
   */
  val iconHaloWidthAsExpression: Expression?
    /**
     * Distance of halo to the icon outline.
     *
     * Get the IconHaloWidth property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("icon-halo-width")?.let {
        return it
      }
      iconHaloWidth?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Distance of halo to the icon outline.
   *
   * @param iconHaloWidth value of iconHaloWidth as Expression
   */
  override fun iconHaloWidth(iconHaloWidth: Expression) = apply {
    val propertyValue = PropertyValue("icon-halo-width", iconHaloWidth)
    setProperty(propertyValue)
  }

  /**
   * Transition options for IconHaloWidth.
   */
  val iconHaloWidthTransition: StyleTransition?
    /**
     * Get the IconHaloWidth property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("icon-halo-width-transition")
    }

  /**
   * Set the IconHaloWidth property transition options
   *
   * @param options transition options for Double
   */
  override fun iconHaloWidthTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("icon-halo-width-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [iconHaloWidthTransition].
   */
  override fun iconHaloWidthTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    iconHaloWidthTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity at which the icon will be drawn.
   */
  val iconOpacity: Double?
    /**
     * The opacity at which the icon will be drawn.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("icon-opacity")
    }

  /**
   * The opacity at which the icon will be drawn.
   *
   * @param iconOpacity value of iconOpacity
   */
  override fun iconOpacity(iconOpacity: Double) = apply {
    val propertyValue = PropertyValue("icon-opacity", iconOpacity)
    setProperty(propertyValue)
  }

  /**
   * The opacity at which the icon will be drawn.
   *
   * This is an Expression representation of "icon-opacity".
   *
   * The opacity at which the icon will be drawn.
   */
  val iconOpacityAsExpression: Expression?
    /**
     * The opacity at which the icon will be drawn.
     *
     * Get the IconOpacity property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("icon-opacity")?.let {
        return it
      }
      iconOpacity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The opacity at which the icon will be drawn.
   *
   * @param iconOpacity value of iconOpacity as Expression
   */
  override fun iconOpacity(iconOpacity: Expression) = apply {
    val propertyValue = PropertyValue("icon-opacity", iconOpacity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for IconOpacity.
   */
  val iconOpacityTransition: StyleTransition?
    /**
     * Get the IconOpacity property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("icon-opacity-transition")
    }

  /**
   * Set the IconOpacity property transition options
   *
   * @param options transition options for Double
   */
  override fun iconOpacityTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("icon-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [iconOpacityTransition].
   */
  override fun iconOpacityTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    iconOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   */
  val iconTranslate: List<Double>?
    /**
     * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("icon-translate")
    }

  /**
   * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   *
   * @param iconTranslate value of iconTranslate
   */
  override fun iconTranslate(iconTranslate: List<Double>) = apply {
    val propertyValue = PropertyValue("icon-translate", iconTranslate)
    setProperty(propertyValue)
  }

  /**
   * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   *
   * This is an Expression representation of "icon-translate".
   *
   * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   */
  val iconTranslateAsExpression: Expression?
    /**
     * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
     *
     * Get the IconTranslate property as an Expression
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("icon-translate")?.let {
        return it
      }
      iconTranslate?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   *
   * @param iconTranslate value of iconTranslate as Expression
   */
  override fun iconTranslate(iconTranslate: Expression) = apply {
    val propertyValue = PropertyValue("icon-translate", iconTranslate)
    setProperty(propertyValue)
  }

  /**
   * Transition options for IconTranslate.
   */
  val iconTranslateTransition: StyleTransition?
    /**
     * Get the IconTranslate property transition options
     *
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("icon-translate-transition")
    }

  /**
   * Set the IconTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  override fun iconTranslateTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("icon-translate-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [iconTranslateTransition].
   */
  override fun iconTranslateTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    iconTranslateTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Controls the frame of reference for `icon-translate`.
   */
  val iconTranslateAnchor: IconTranslateAnchor?
    /**
     * Controls the frame of reference for `icon-translate`.
     *
     * @return IconTranslateAnchor
     */
    get() {
      getPropertyValue<String?>("icon-translate-anchor")?.let {
        return IconTranslateAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Controls the frame of reference for `icon-translate`.
   *
   * @param iconTranslateAnchor value of iconTranslateAnchor
   */
  override fun iconTranslateAnchor(iconTranslateAnchor: IconTranslateAnchor) = apply {
    val propertyValue = PropertyValue("icon-translate-anchor", iconTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * Controls the frame of reference for `icon-translate`.
   *
   * This is an Expression representation of "icon-translate-anchor".
   *
   * Controls the frame of reference for `icon-translate`.
   */
  val iconTranslateAnchorAsExpression: Expression?
    /**
     * Controls the frame of reference for `icon-translate`.
     *
     * Get the IconTranslateAnchor property as an Expression
     *
     * @return IconTranslateAnchor
     */
    get() {
      getPropertyValue<Expression>("icon-translate-anchor")?.let {
        return it
      }
      iconTranslateAnchor?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Controls the frame of reference for `icon-translate`.
   *
   * @param iconTranslateAnchor value of iconTranslateAnchor as Expression
   */
  override fun iconTranslateAnchor(iconTranslateAnchor: Expression) = apply {
    val propertyValue = PropertyValue("icon-translate-anchor", iconTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * The color with which the text will be drawn.
   */
  val textColor: String?
    /**
     * The color with which the text will be drawn.
     *
     * @return String
     */
    get() {
      textColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The color with which the text will be drawn.
   *
   * @param textColor value of textColor
   */
  override fun textColor(textColor: String) = apply {
    val propertyValue = PropertyValue("text-color", textColor)
    setProperty(propertyValue)
  }

  /**
   * The color with which the text will be drawn.
   *
   * This is an Expression representation of "text-color".
   *
   * The color with which the text will be drawn.
   */
  val textColorAsExpression: Expression?
    /**
     * The color with which the text will be drawn.
     *
     * Get the TextColor property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("text-color")?.let {
        return it
      }
      return null
    }

  /**
   * The color with which the text will be drawn.
   *
   * @param textColor value of textColor as Expression
   */
  override fun textColor(textColor: Expression) = apply {
    val propertyValue = PropertyValue("text-color", textColor)
    setProperty(propertyValue)
  }

  /**
   * The color with which the text will be drawn.
   */
  val textColorAsColorInt: Int?
    /**
     * The color with which the text will be drawn.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      textColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The color with which the text will be drawn.
   *
   * @param textColor value of textColor
   */
  override fun textColor(@ColorInt textColor: Int) = apply {
    val propertyValue = PropertyValue("text-color", colorIntToRgbaExpression(textColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for TextColor.
   */
  val textColorTransition: StyleTransition?
    /**
     * Get the TextColor property transition options
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("text-color-transition")
    }

  /**
   * Set the TextColor property transition options
   *
   * @param options transition options for String
   */
  override fun textColorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("text-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [textColorTransition].
   */
  override fun textColorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    textColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The halo's fadeout distance towards the outside.
   */
  val textHaloBlur: Double?
    /**
     * The halo's fadeout distance towards the outside.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("text-halo-blur")
    }

  /**
   * The halo's fadeout distance towards the outside.
   *
   * @param textHaloBlur value of textHaloBlur
   */
  override fun textHaloBlur(textHaloBlur: Double) = apply {
    val propertyValue = PropertyValue("text-halo-blur", textHaloBlur)
    setProperty(propertyValue)
  }

  /**
   * The halo's fadeout distance towards the outside.
   *
   * This is an Expression representation of "text-halo-blur".
   *
   * The halo's fadeout distance towards the outside.
   */
  val textHaloBlurAsExpression: Expression?
    /**
     * The halo's fadeout distance towards the outside.
     *
     * Get the TextHaloBlur property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("text-halo-blur")?.let {
        return it
      }
      textHaloBlur?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The halo's fadeout distance towards the outside.
   *
   * @param textHaloBlur value of textHaloBlur as Expression
   */
  override fun textHaloBlur(textHaloBlur: Expression) = apply {
    val propertyValue = PropertyValue("text-halo-blur", textHaloBlur)
    setProperty(propertyValue)
  }

  /**
   * Transition options for TextHaloBlur.
   */
  val textHaloBlurTransition: StyleTransition?
    /**
     * Get the TextHaloBlur property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("text-halo-blur-transition")
    }

  /**
   * Set the TextHaloBlur property transition options
   *
   * @param options transition options for Double
   */
  override fun textHaloBlurTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("text-halo-blur-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [textHaloBlurTransition].
   */
  override fun textHaloBlurTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    textHaloBlurTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The color of the text's halo, which helps it stand out from backgrounds.
   */
  val textHaloColor: String?
    /**
     * The color of the text's halo, which helps it stand out from backgrounds.
     *
     * @return String
     */
    get() {
      textHaloColorAsExpression?.let {
        return rgbaExpressionToColorString(it)
      }
      return null
    }

  /**
   * The color of the text's halo, which helps it stand out from backgrounds.
   *
   * @param textHaloColor value of textHaloColor
   */
  override fun textHaloColor(textHaloColor: String) = apply {
    val propertyValue = PropertyValue("text-halo-color", textHaloColor)
    setProperty(propertyValue)
  }

  /**
   * The color of the text's halo, which helps it stand out from backgrounds.
   *
   * This is an Expression representation of "text-halo-color".
   *
   * The color of the text's halo, which helps it stand out from backgrounds.
   */
  val textHaloColorAsExpression: Expression?
    /**
     * The color of the text's halo, which helps it stand out from backgrounds.
     *
     * Get the TextHaloColor property as an Expression
     *
     * @return String
     */
    get() {
      getPropertyValue<Expression>("text-halo-color")?.let {
        return it
      }
      return null
    }

  /**
   * The color of the text's halo, which helps it stand out from backgrounds.
   *
   * @param textHaloColor value of textHaloColor as Expression
   */
  override fun textHaloColor(textHaloColor: Expression) = apply {
    val propertyValue = PropertyValue("text-halo-color", textHaloColor)
    setProperty(propertyValue)
  }

  /**
   * The color of the text's halo, which helps it stand out from backgrounds.
   */
  val textHaloColorAsColorInt: Int?
    /**
     * The color of the text's halo, which helps it stand out from backgrounds.
     *
     * @return int representation of a rgba string color
     */
    @ColorInt
    get() {
      textHaloColorAsExpression?.let {
        return rgbaExpressionToColorInt(it)
      }
      return null
    }

  /**
   * The color of the text's halo, which helps it stand out from backgrounds.
   *
   * @param textHaloColor value of textHaloColor
   */
  override fun textHaloColor(@ColorInt textHaloColor: Int) = apply {
    val propertyValue = PropertyValue("text-halo-color", colorIntToRgbaExpression(textHaloColor))
    setProperty(propertyValue)
  }

  /**
   * Transition options for TextHaloColor.
   */
  val textHaloColorTransition: StyleTransition?
    /**
     * Get the TextHaloColor property transition options
     *
     * @return transition options for String
     */
    get() {
      return getPropertyValue("text-halo-color-transition")
    }

  /**
   * Set the TextHaloColor property transition options
   *
   * @param options transition options for String
   */
  override fun textHaloColorTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("text-halo-color-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [textHaloColorTransition].
   */
  override fun textHaloColorTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    textHaloColorTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
   */
  val textHaloWidth: Double?
    /**
     * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("text-halo-width")
    }

  /**
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
   *
   * @param textHaloWidth value of textHaloWidth
   */
  override fun textHaloWidth(textHaloWidth: Double) = apply {
    val propertyValue = PropertyValue("text-halo-width", textHaloWidth)
    setProperty(propertyValue)
  }

  /**
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
   *
   * This is an Expression representation of "text-halo-width".
   *
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
   */
  val textHaloWidthAsExpression: Expression?
    /**
     * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
     *
     * Get the TextHaloWidth property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("text-halo-width")?.let {
        return it
      }
      textHaloWidth?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
   *
   * @param textHaloWidth value of textHaloWidth as Expression
   */
  override fun textHaloWidth(textHaloWidth: Expression) = apply {
    val propertyValue = PropertyValue("text-halo-width", textHaloWidth)
    setProperty(propertyValue)
  }

  /**
   * Transition options for TextHaloWidth.
   */
  val textHaloWidthTransition: StyleTransition?
    /**
     * Get the TextHaloWidth property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("text-halo-width-transition")
    }

  /**
   * Set the TextHaloWidth property transition options
   *
   * @param options transition options for Double
   */
  override fun textHaloWidthTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("text-halo-width-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [textHaloWidthTransition].
   */
  override fun textHaloWidthTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    textHaloWidthTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * The opacity at which the text will be drawn.
   */
  val textOpacity: Double?
    /**
     * The opacity at which the text will be drawn.
     *
     * @return Double
     */
    get() {
      return getPropertyValue("text-opacity")
    }

  /**
   * The opacity at which the text will be drawn.
   *
   * @param textOpacity value of textOpacity
   */
  override fun textOpacity(textOpacity: Double) = apply {
    val propertyValue = PropertyValue("text-opacity", textOpacity)
    setProperty(propertyValue)
  }

  /**
   * The opacity at which the text will be drawn.
   *
   * This is an Expression representation of "text-opacity".
   *
   * The opacity at which the text will be drawn.
   */
  val textOpacityAsExpression: Expression?
    /**
     * The opacity at which the text will be drawn.
     *
     * Get the TextOpacity property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("text-opacity")?.let {
        return it
      }
      textOpacity?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * The opacity at which the text will be drawn.
   *
   * @param textOpacity value of textOpacity as Expression
   */
  override fun textOpacity(textOpacity: Expression) = apply {
    val propertyValue = PropertyValue("text-opacity", textOpacity)
    setProperty(propertyValue)
  }

  /**
   * Transition options for TextOpacity.
   */
  val textOpacityTransition: StyleTransition?
    /**
     * Get the TextOpacity property transition options
     *
     * @return transition options for Double
     */
    get() {
      return getPropertyValue("text-opacity-transition")
    }

  /**
   * Set the TextOpacity property transition options
   *
   * @param options transition options for Double
   */
  override fun textOpacityTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("text-opacity-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [textOpacityTransition].
   */
  override fun textOpacityTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    textOpacityTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   */
  val textTranslate: List<Double>?
    /**
     * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
     *
     * @return List<Double>
     */
    get() {
      return getPropertyValue<List<Double>>("text-translate")
    }

  /**
   * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   *
   * @param textTranslate value of textTranslate
   */
  override fun textTranslate(textTranslate: List<Double>) = apply {
    val propertyValue = PropertyValue("text-translate", textTranslate)
    setProperty(propertyValue)
  }

  /**
   * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   *
   * This is an Expression representation of "text-translate".
   *
   * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   */
  val textTranslateAsExpression: Expression?
    /**
     * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
     *
     * Get the TextTranslate property as an Expression
     *
     * @return List<Double>
     */
    get() {
      getPropertyValue<Expression>("text-translate")?.let {
        return it
      }
      textTranslate?.let {
        return Expression.literal(it)
      }
      return null
    }

  /**
   * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   *
   * @param textTranslate value of textTranslate as Expression
   */
  override fun textTranslate(textTranslate: Expression) = apply {
    val propertyValue = PropertyValue("text-translate", textTranslate)
    setProperty(propertyValue)
  }

  /**
   * Transition options for TextTranslate.
   */
  val textTranslateTransition: StyleTransition?
    /**
     * Get the TextTranslate property transition options
     *
     * @return transition options for List<Double>
     */
    get() {
      return getPropertyValue("text-translate-transition")
    }

  /**
   * Set the TextTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  override fun textTranslateTransition(options: StyleTransition) = apply {
    val propertyValue = PropertyValue("text-translate-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [textTranslateTransition].
   */
  override fun textTranslateTransition(block: StyleTransition.Builder.() -> Unit) = apply {
    textTranslateTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Controls the frame of reference for `text-translate`.
   */
  val textTranslateAnchor: TextTranslateAnchor?
    /**
     * Controls the frame of reference for `text-translate`.
     *
     * @return TextTranslateAnchor
     */
    get() {
      getPropertyValue<String?>("text-translate-anchor")?.let {
        return TextTranslateAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
      }
      return null
    }

  /**
   * Controls the frame of reference for `text-translate`.
   *
   * @param textTranslateAnchor value of textTranslateAnchor
   */
  override fun textTranslateAnchor(textTranslateAnchor: TextTranslateAnchor) = apply {
    val propertyValue = PropertyValue("text-translate-anchor", textTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * Controls the frame of reference for `text-translate`.
   *
   * This is an Expression representation of "text-translate-anchor".
   *
   * Controls the frame of reference for `text-translate`.
   */
  val textTranslateAnchorAsExpression: Expression?
    /**
     * Controls the frame of reference for `text-translate`.
     *
     * Get the TextTranslateAnchor property as an Expression
     *
     * @return TextTranslateAnchor
     */
    get() {
      getPropertyValue<Expression>("text-translate-anchor")?.let {
        return it
      }
      textTranslateAnchor?.let {
        return Expression.literal(it.value)
      }
      return null
    }

  /**
   * Controls the frame of reference for `text-translate`.
   *
   * @param textTranslateAnchor value of textTranslateAnchor as Expression
   */
  override fun textTranslateAnchor(textTranslateAnchor: Expression) = apply {
    val propertyValue = PropertyValue("text-translate-anchor", textTranslateAnchor)
    setProperty(propertyValue)
  }

  /**
   * Get the type of this layer
   *
   * @return Type of the layer as [String]
   */
  override fun getType(): String {
    return "symbol"
  }
  /**
   * Static variables and methods.
   */
  companion object {
    // Default values for layer properties
    /**
     * Visibility of the layer.
     */
    val defaultVisibility: Visibility?
      /**
       * Get the default Visibility property
       *
       * @return VISIBILITY
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "visibility").silentUnwrap<String>()?.let {
          return Visibility.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
     *
     * Range:
     *       minimum: 0
     *       maximum: 24
     */
    val defaultMinZoom: Double?
      /**
       * Get the minzoom property
       *
       * @return minzoom
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("symbol", "minzoom").silentUnwrap()

    /**
     * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
     *
     * Range:
     *       minimum: 0
     *       maximum: 24
     */
    val defaultMaxZoom: Double?
      /**
       * Get the maxzoom property
       *
       * @return maxzoom
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("symbol", "maxzoom").silentUnwrap()

    /**
     * If true, the icon will be visible even if it collides with other previously drawn symbols.
     */
    val defaultIconAllowOverlap: Boolean?
      /**
       * If true, the icon will be visible even if it collides with other previously drawn symbols.
       *
       * Get the default value of IconAllowOverlap property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-allow-overlap").silentUnwrap()
      }

    /**
     * If true, the icon will be visible even if it collides with other previously drawn symbols.
     *
     * This is an Expression representation of "icon-allow-overlap".
     *
     * If true, the icon will be visible even if it collides with other previously drawn symbols.
     */
    val defaultIconAllowOverlapAsExpression: Expression?
      /**
       * Get default value of the IconAllowOverlap property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-allow-overlap").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconAllowOverlap?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Part of the icon placed closest to the anchor.
     */
    val defaultIconAnchor: IconAnchor?
      /**
       * Part of the icon placed closest to the anchor.
       *
       * Get the default value of IconAnchor property
       *
       * @return IconAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-anchor").silentUnwrap<String>()?.let {
          return IconAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Part of the icon placed closest to the anchor.
     *
     * This is an Expression representation of "icon-anchor".
     *
     * Part of the icon placed closest to the anchor.
     */
    val defaultIconAnchorAsExpression: Expression?
      /**
       * Get default value of the IconAnchor property as an Expression
       *
       * @return IconAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-anchor").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconAnchor?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * If true, other symbols can be visible even if they collide with the icon.
     */
    val defaultIconIgnorePlacement: Boolean?
      /**
       * If true, other symbols can be visible even if they collide with the icon.
       *
       * Get the default value of IconIgnorePlacement property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-ignore-placement").silentUnwrap()
      }

    /**
     * If true, other symbols can be visible even if they collide with the icon.
     *
     * This is an Expression representation of "icon-ignore-placement".
     *
     * If true, other symbols can be visible even if they collide with the icon.
     */
    val defaultIconIgnorePlacementAsExpression: Expression?
      /**
       * Get default value of the IconIgnorePlacement property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-ignore-placement").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconIgnorePlacement?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Name of image in sprite to use for drawing an image background.
     */
    val defaultIconImage: String?
      /**
       * Name of image in sprite to use for drawing an image background.
       *
       * Get the default value of IconImage property
       *
       * @return String
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-image").silentUnwrap()
      }

    /**
     * Name of image in sprite to use for drawing an image background.
     *
     * This is an Expression representation of "icon-image".
     *
     * Name of image in sprite to use for drawing an image background.
     */
    val defaultIconImageAsExpression: Expression?
      /**
       * Get default value of the IconImage property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-image").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconImage?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * If true, the icon may be flipped to prevent it from being rendered upside-down.
     */
    val defaultIconKeepUpright: Boolean?
      /**
       * If true, the icon may be flipped to prevent it from being rendered upside-down.
       *
       * Get the default value of IconKeepUpright property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-keep-upright").silentUnwrap()
      }

    /**
     * If true, the icon may be flipped to prevent it from being rendered upside-down.
     *
     * This is an Expression representation of "icon-keep-upright".
     *
     * If true, the icon may be flipped to prevent it from being rendered upside-down.
     */
    val defaultIconKeepUprightAsExpression: Expression?
      /**
       * Get default value of the IconKeepUpright property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-keep-upright").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconKeepUpright?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
     */
    val defaultIconOffset: List<Double>?
      /**
       * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
       *
       * Get the default value of IconOffset property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-offset").silentUnwrap()
      }

    /**
     * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
     *
     * This is an Expression representation of "icon-offset".
     *
     * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
     */
    val defaultIconOffsetAsExpression: Expression?
      /**
       * Get default value of the IconOffset property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-offset").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconOffset?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
     */
    val defaultIconOptional: Boolean?
      /**
       * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
       *
       * Get the default value of IconOptional property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-optional").silentUnwrap()
      }

    /**
     * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
     *
     * This is an Expression representation of "icon-optional".
     *
     * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
     */
    val defaultIconOptionalAsExpression: Expression?
      /**
       * Get default value of the IconOptional property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-optional").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconOptional?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Size of the additional area around the icon bounding box used for detecting symbol collisions.
     */
    val defaultIconPadding: Double?
      /**
       * Size of the additional area around the icon bounding box used for detecting symbol collisions.
       *
       * Get the default value of IconPadding property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-padding").silentUnwrap()
      }

    /**
     * Size of the additional area around the icon bounding box used for detecting symbol collisions.
     *
     * This is an Expression representation of "icon-padding".
     *
     * Size of the additional area around the icon bounding box used for detecting symbol collisions.
     */
    val defaultIconPaddingAsExpression: Expression?
      /**
       * Get default value of the IconPadding property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-padding").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconPadding?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Orientation of icon when map is pitched.
     */
    val defaultIconPitchAlignment: IconPitchAlignment?
      /**
       * Orientation of icon when map is pitched.
       *
       * Get the default value of IconPitchAlignment property
       *
       * @return IconPitchAlignment
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-pitch-alignment").silentUnwrap<String>()?.let {
          return IconPitchAlignment.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Orientation of icon when map is pitched.
     *
     * This is an Expression representation of "icon-pitch-alignment".
     *
     * Orientation of icon when map is pitched.
     */
    val defaultIconPitchAlignmentAsExpression: Expression?
      /**
       * Get default value of the IconPitchAlignment property as an Expression
       *
       * @return IconPitchAlignment
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-pitch-alignment").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconPitchAlignment?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * Rotates the icon clockwise.
     */
    val defaultIconRotate: Double?
      /**
       * Rotates the icon clockwise.
       *
       * Get the default value of IconRotate property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-rotate").silentUnwrap()
      }

    /**
     * Rotates the icon clockwise.
     *
     * This is an Expression representation of "icon-rotate".
     *
     * Rotates the icon clockwise.
     */
    val defaultIconRotateAsExpression: Expression?
      /**
       * Get default value of the IconRotate property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-rotate").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconRotate?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * In combination with `symbol-placement`, determines the rotation behavior of icons.
     */
    val defaultIconRotationAlignment: IconRotationAlignment?
      /**
       * In combination with `symbol-placement`, determines the rotation behavior of icons.
       *
       * Get the default value of IconRotationAlignment property
       *
       * @return IconRotationAlignment
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-rotation-alignment").silentUnwrap<String>()?.let {
          return IconRotationAlignment.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * In combination with `symbol-placement`, determines the rotation behavior of icons.
     *
     * This is an Expression representation of "icon-rotation-alignment".
     *
     * In combination with `symbol-placement`, determines the rotation behavior of icons.
     */
    val defaultIconRotationAlignmentAsExpression: Expression?
      /**
       * Get default value of the IconRotationAlignment property as an Expression
       *
       * @return IconRotationAlignment
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-rotation-alignment").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconRotationAlignment?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
     */
    val defaultIconSize: Double?
      /**
       * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
       *
       * Get the default value of IconSize property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-size").silentUnwrap()
      }

    /**
     * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
     *
     * This is an Expression representation of "icon-size".
     *
     * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
     */
    val defaultIconSizeAsExpression: Expression?
      /**
       * Get default value of the IconSize property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-size").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconSize?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Scales the icon to fit around the associated text.
     */
    val defaultIconTextFit: IconTextFit?
      /**
       * Scales the icon to fit around the associated text.
       *
       * Get the default value of IconTextFit property
       *
       * @return IconTextFit
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-text-fit").silentUnwrap<String>()?.let {
          return IconTextFit.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Scales the icon to fit around the associated text.
     *
     * This is an Expression representation of "icon-text-fit".
     *
     * Scales the icon to fit around the associated text.
     */
    val defaultIconTextFitAsExpression: Expression?
      /**
       * Get default value of the IconTextFit property as an Expression
       *
       * @return IconTextFit
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-text-fit").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconTextFit?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left.
     */
    val defaultIconTextFitPadding: List<Double>?
      /**
       * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left.
       *
       * Get the default value of IconTextFitPadding property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-text-fit-padding").silentUnwrap()
      }

    /**
     * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left.
     *
     * This is an Expression representation of "icon-text-fit-padding".
     *
     * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left.
     */
    val defaultIconTextFitPaddingAsExpression: Expression?
      /**
       * Get default value of the IconTextFitPadding property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-text-fit-padding").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconTextFitPadding?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
     */
    val defaultSymbolAvoidEdges: Boolean?
      /**
       * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
       *
       * Get the default value of SymbolAvoidEdges property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-avoid-edges").silentUnwrap()
      }

    /**
     * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
     *
     * This is an Expression representation of "symbol-avoid-edges".
     *
     * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
     */
    val defaultSymbolAvoidEdgesAsExpression: Expression?
      /**
       * Get default value of the SymbolAvoidEdges property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-avoid-edges").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultSymbolAvoidEdges?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Label placement relative to its geometry.
     */
    val defaultSymbolPlacement: SymbolPlacement?
      /**
       * Label placement relative to its geometry.
       *
       * Get the default value of SymbolPlacement property
       *
       * @return SymbolPlacement
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-placement").silentUnwrap<String>()?.let {
          return SymbolPlacement.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Label placement relative to its geometry.
     *
     * This is an Expression representation of "symbol-placement".
     *
     * Label placement relative to its geometry.
     */
    val defaultSymbolPlacementAsExpression: Expression?
      /**
       * Get default value of the SymbolPlacement property as an Expression
       *
       * @return SymbolPlacement
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-placement").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultSymbolPlacement?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first.  When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
     */
    val defaultSymbolSortKey: Double?
      /**
       * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first.  When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
       *
       * Get the default value of SymbolSortKey property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-sort-key").silentUnwrap()
      }

    /**
     * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first.  When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
     *
     * This is an Expression representation of "symbol-sort-key".
     *
     * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first.  When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
     */
    val defaultSymbolSortKeyAsExpression: Expression?
      /**
       * Get default value of the SymbolSortKey property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-sort-key").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultSymbolSortKey?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Distance between two symbol anchors.
     */
    val defaultSymbolSpacing: Double?
      /**
       * Distance between two symbol anchors.
       *
       * Get the default value of SymbolSpacing property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-spacing").silentUnwrap()
      }

    /**
     * Distance between two symbol anchors.
     *
     * This is an Expression representation of "symbol-spacing".
     *
     * Distance between two symbol anchors.
     */
    val defaultSymbolSpacingAsExpression: Expression?
      /**
       * Get default value of the SymbolSpacing property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-spacing").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultSymbolSpacing?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
     */
    val defaultSymbolZOrder: SymbolZOrder?
      /**
       * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
       *
       * Get the default value of SymbolZOrder property
       *
       * @return SymbolZOrder
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-z-order").silentUnwrap<String>()?.let {
          return SymbolZOrder.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
     *
     * This is an Expression representation of "symbol-z-order".
     *
     * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
     */
    val defaultSymbolZOrderAsExpression: Expression?
      /**
       * Get default value of the SymbolZOrder property as an Expression
       *
       * @return SymbolZOrder
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-z-order").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultSymbolZOrder?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * If true, the text will be visible even if it collides with other previously drawn symbols.
     */
    val defaultTextAllowOverlap: Boolean?
      /**
       * If true, the text will be visible even if it collides with other previously drawn symbols.
       *
       * Get the default value of TextAllowOverlap property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-allow-overlap").silentUnwrap()
      }

    /**
     * If true, the text will be visible even if it collides with other previously drawn symbols.
     *
     * This is an Expression representation of "text-allow-overlap".
     *
     * If true, the text will be visible even if it collides with other previously drawn symbols.
     */
    val defaultTextAllowOverlapAsExpression: Expression?
      /**
       * Get default value of the TextAllowOverlap property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-allow-overlap").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextAllowOverlap?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Part of the text placed closest to the anchor.
     */
    val defaultTextAnchor: TextAnchor?
      /**
       * Part of the text placed closest to the anchor.
       *
       * Get the default value of TextAnchor property
       *
       * @return TextAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-anchor").silentUnwrap<String>()?.let {
          return TextAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Part of the text placed closest to the anchor.
     *
     * This is an Expression representation of "text-anchor".
     *
     * Part of the text placed closest to the anchor.
     */
    val defaultTextAnchorAsExpression: Expression?
      /**
       * Get default value of the TextAnchor property as an Expression
       *
       * @return TextAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-anchor").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextAnchor?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
     */
    val defaultTextField: Formatted?
      /**
       * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
       *
       * Get the default value of TextField property
       *
       * @return Formatted
       */
      get() {
        defaultTextFieldAsExpression?.let {
          return Formatted.fromExpression(it)
        }
        return null
      }

    /**
     * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
     *
     * This is an Expression representation of "text-field".
     *
     * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
     */
    val defaultTextFieldAsExpression: Expression?
      /**
       * Get default value of the TextField property as an Expression
       *
       * @return Formatted
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-field").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
     */
    val defaultTextFieldAsString: String?
      /**
       * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
       *
       * Get the TextField property
       *
       * @return value of textField
       */
      get() {
        defaultTextField?.let {
          return it.getTextAsString()
        }
        return null
      }

    /**
     * Font stack to use for displaying text.
     */
    val defaultTextFont: List<String>?
      /**
       * Font stack to use for displaying text.
       *
       * Get the default value of TextFont property
       *
       * @return List<String>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-font").silentUnwrap()
      }

    /**
     * Font stack to use for displaying text.
     *
     * This is an Expression representation of "text-font".
     *
     * Font stack to use for displaying text.
     */
    val defaultTextFontAsExpression: Expression?
      /**
       * Get default value of the TextFont property as an Expression
       *
       * @return List<String>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-font").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextFont?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * If true, other symbols can be visible even if they collide with the text.
     */
    val defaultTextIgnorePlacement: Boolean?
      /**
       * If true, other symbols can be visible even if they collide with the text.
       *
       * Get the default value of TextIgnorePlacement property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-ignore-placement").silentUnwrap()
      }

    /**
     * If true, other symbols can be visible even if they collide with the text.
     *
     * This is an Expression representation of "text-ignore-placement".
     *
     * If true, other symbols can be visible even if they collide with the text.
     */
    val defaultTextIgnorePlacementAsExpression: Expression?
      /**
       * Get default value of the TextIgnorePlacement property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-ignore-placement").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextIgnorePlacement?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Text justification options.
     */
    val defaultTextJustify: TextJustify?
      /**
       * Text justification options.
       *
       * Get the default value of TextJustify property
       *
       * @return TextJustify
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-justify").silentUnwrap<String>()?.let {
          return TextJustify.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Text justification options.
     *
     * This is an Expression representation of "text-justify".
     *
     * Text justification options.
     */
    val defaultTextJustifyAsExpression: Expression?
      /**
       * Get default value of the TextJustify property as an Expression
       *
       * @return TextJustify
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-justify").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextJustify?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
     */
    val defaultTextKeepUpright: Boolean?
      /**
       * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
       *
       * Get the default value of TextKeepUpright property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-keep-upright").silentUnwrap()
      }

    /**
     * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
     *
     * This is an Expression representation of "text-keep-upright".
     *
     * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
     */
    val defaultTextKeepUprightAsExpression: Expression?
      /**
       * Get default value of the TextKeepUpright property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-keep-upright").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextKeepUpright?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Text tracking amount.
     */
    val defaultTextLetterSpacing: Double?
      /**
       * Text tracking amount.
       *
       * Get the default value of TextLetterSpacing property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-letter-spacing").silentUnwrap()
      }

    /**
     * Text tracking amount.
     *
     * This is an Expression representation of "text-letter-spacing".
     *
     * Text tracking amount.
     */
    val defaultTextLetterSpacingAsExpression: Expression?
      /**
       * Get default value of the TextLetterSpacing property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-letter-spacing").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextLetterSpacing?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Text leading value for multi-line text.
     */
    val defaultTextLineHeight: Double?
      /**
       * Text leading value for multi-line text.
       *
       * Get the default value of TextLineHeight property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-line-height").silentUnwrap()
      }

    /**
     * Text leading value for multi-line text.
     *
     * This is an Expression representation of "text-line-height".
     *
     * Text leading value for multi-line text.
     */
    val defaultTextLineHeightAsExpression: Expression?
      /**
       * Get default value of the TextLineHeight property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-line-height").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextLineHeight?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Maximum angle change between adjacent characters.
     */
    val defaultTextMaxAngle: Double?
      /**
       * Maximum angle change between adjacent characters.
       *
       * Get the default value of TextMaxAngle property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-max-angle").silentUnwrap()
      }

    /**
     * Maximum angle change between adjacent characters.
     *
     * This is an Expression representation of "text-max-angle".
     *
     * Maximum angle change between adjacent characters.
     */
    val defaultTextMaxAngleAsExpression: Expression?
      /**
       * Get default value of the TextMaxAngle property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-max-angle").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextMaxAngle?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * The maximum line width for text wrapping.
     */
    val defaultTextMaxWidth: Double?
      /**
       * The maximum line width for text wrapping.
       *
       * Get the default value of TextMaxWidth property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-max-width").silentUnwrap()
      }

    /**
     * The maximum line width for text wrapping.
     *
     * This is an Expression representation of "text-max-width".
     *
     * The maximum line width for text wrapping.
     */
    val defaultTextMaxWidthAsExpression: Expression?
      /**
       * Get default value of the TextMaxWidth property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-max-width").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextMaxWidth?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
     */
    val defaultTextOffset: List<Double>?
      /**
       * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
       *
       * Get the default value of TextOffset property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-offset").silentUnwrap()
      }

    /**
     * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
     *
     * This is an Expression representation of "text-offset".
     *
     * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
     */
    val defaultTextOffsetAsExpression: Expression?
      /**
       * Get default value of the TextOffset property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-offset").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextOffset?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
     */
    val defaultTextOptional: Boolean?
      /**
       * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
       *
       * Get the default value of TextOptional property
       *
       * @return Boolean
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-optional").silentUnwrap()
      }

    /**
     * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
     *
     * This is an Expression representation of "text-optional".
     *
     * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
     */
    val defaultTextOptionalAsExpression: Expression?
      /**
       * Get default value of the TextOptional property as an Expression
       *
       * @return Boolean
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-optional").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextOptional?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Size of the additional area around the text bounding box used for detecting symbol collisions.
     */
    val defaultTextPadding: Double?
      /**
       * Size of the additional area around the text bounding box used for detecting symbol collisions.
       *
       * Get the default value of TextPadding property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-padding").silentUnwrap()
      }

    /**
     * Size of the additional area around the text bounding box used for detecting symbol collisions.
     *
     * This is an Expression representation of "text-padding".
     *
     * Size of the additional area around the text bounding box used for detecting symbol collisions.
     */
    val defaultTextPaddingAsExpression: Expression?
      /**
       * Get default value of the TextPadding property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-padding").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextPadding?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Orientation of text when map is pitched.
     */
    val defaultTextPitchAlignment: TextPitchAlignment?
      /**
       * Orientation of text when map is pitched.
       *
       * Get the default value of TextPitchAlignment property
       *
       * @return TextPitchAlignment
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-pitch-alignment").silentUnwrap<String>()?.let {
          return TextPitchAlignment.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Orientation of text when map is pitched.
     *
     * This is an Expression representation of "text-pitch-alignment".
     *
     * Orientation of text when map is pitched.
     */
    val defaultTextPitchAlignmentAsExpression: Expression?
      /**
       * Get default value of the TextPitchAlignment property as an Expression
       *
       * @return TextPitchAlignment
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-pitch-alignment").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextPitchAlignment?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
     */
    val defaultTextRadialOffset: Double?
      /**
       * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
       *
       * Get the default value of TextRadialOffset property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-radial-offset").silentUnwrap()
      }

    /**
     * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
     *
     * This is an Expression representation of "text-radial-offset".
     *
     * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
     */
    val defaultTextRadialOffsetAsExpression: Expression?
      /**
       * Get default value of the TextRadialOffset property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-radial-offset").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextRadialOffset?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Rotates the text clockwise.
     */
    val defaultTextRotate: Double?
      /**
       * Rotates the text clockwise.
       *
       * Get the default value of TextRotate property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-rotate").silentUnwrap()
      }

    /**
     * Rotates the text clockwise.
     *
     * This is an Expression representation of "text-rotate".
     *
     * Rotates the text clockwise.
     */
    val defaultTextRotateAsExpression: Expression?
      /**
       * Get default value of the TextRotate property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-rotate").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextRotate?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
     */
    val defaultTextRotationAlignment: TextRotationAlignment?
      /**
       * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
       *
       * Get the default value of TextRotationAlignment property
       *
       * @return TextRotationAlignment
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-rotation-alignment").silentUnwrap<String>()?.let {
          return TextRotationAlignment.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
     *
     * This is an Expression representation of "text-rotation-alignment".
     *
     * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
     */
    val defaultTextRotationAlignmentAsExpression: Expression?
      /**
       * Get default value of the TextRotationAlignment property as an Expression
       *
       * @return TextRotationAlignment
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-rotation-alignment").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextRotationAlignment?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * Font size.
     */
    val defaultTextSize: Double?
      /**
       * Font size.
       *
       * Get the default value of TextSize property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-size").silentUnwrap()
      }

    /**
     * Font size.
     *
     * This is an Expression representation of "text-size".
     *
     * Font size.
     */
    val defaultTextSizeAsExpression: Expression?
      /**
       * Get default value of the TextSize property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-size").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextSize?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Specifies how to capitalize text, similar to the CSS `text-transform` property.
     */
    val defaultTextTransform: TextTransform?
      /**
       * Specifies how to capitalize text, similar to the CSS `text-transform` property.
       *
       * Get the default value of TextTransform property
       *
       * @return TextTransform
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-transform").silentUnwrap<String>()?.let {
          return TextTransform.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Specifies how to capitalize text, similar to the CSS `text-transform` property.
     *
     * This is an Expression representation of "text-transform".
     *
     * Specifies how to capitalize text, similar to the CSS `text-transform` property.
     */
    val defaultTextTransformAsExpression: Expression?
      /**
       * Get default value of the TextTransform property as an Expression
       *
       * @return TextTransform
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-transform").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextTransform?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
     */
    val defaultTextVariableAnchor: List<String>?
      /**
       * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
       *
       * Get the default value of TextVariableAnchor property
       *
       * @return List<String>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-variable-anchor").silentUnwrap()
      }

    /**
     * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
     *
     * This is an Expression representation of "text-variable-anchor".
     *
     * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
     */
    val defaultTextVariableAnchorAsExpression: Expression?
      /**
       * Get default value of the TextVariableAnchor property as an Expression
       *
       * @return List<String>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-variable-anchor").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextVariableAnchor?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
     */
    val defaultTextWritingMode: List<String>?
      /**
       * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
       *
       * Get the default value of TextWritingMode property
       *
       * @return List<String>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-writing-mode").silentUnwrap()
      }

    /**
     * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
     *
     * This is an Expression representation of "text-writing-mode".
     *
     * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
     */
    val defaultTextWritingModeAsExpression: Expression?
      /**
       * Get default value of the TextWritingMode property as an Expression
       *
       * @return List<String>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-writing-mode").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextWritingMode?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
     */
    val defaultIconColor: String?
      /**
       * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
       *
       * Get the default value of IconColor property
       *
       * @return String
       */
      get() {
        defaultIconColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
     *
     * This is an Expression representation of "icon-color".
     *
     * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
     */
    val defaultIconColorAsExpression: Expression?
      /**
       * Get default value of the IconColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
     */
    val defaultIconColorAsColorInt: Int?
      /**
       * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
       *
       * Get the default value of IconColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultIconColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for IconColor.
     */
    val defaultIconColorTransition: StyleTransition?
      /**
       * Get the IconColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-color-transition").silentUnwrap()

    /**
     * Fade out the halo towards the outside.
     */
    val defaultIconHaloBlur: Double?
      /**
       * Fade out the halo towards the outside.
       *
       * Get the default value of IconHaloBlur property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-blur").silentUnwrap()
      }

    /**
     * Fade out the halo towards the outside.
     *
     * This is an Expression representation of "icon-halo-blur".
     *
     * Fade out the halo towards the outside.
     */
    val defaultIconHaloBlurAsExpression: Expression?
      /**
       * Get default value of the IconHaloBlur property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-blur").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconHaloBlur?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for IconHaloBlur.
     */
    val defaultIconHaloBlurTransition: StyleTransition?
      /**
       * Get the IconHaloBlur property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-blur-transition").silentUnwrap()

    /**
     * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
     */
    val defaultIconHaloColor: String?
      /**
       * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
       *
       * Get the default value of IconHaloColor property
       *
       * @return String
       */
      get() {
        defaultIconHaloColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
     *
     * This is an Expression representation of "icon-halo-color".
     *
     * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
     */
    val defaultIconHaloColorAsExpression: Expression?
      /**
       * Get default value of the IconHaloColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
     */
    val defaultIconHaloColorAsColorInt: Int?
      /**
       * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
       *
       * Get the default value of IconHaloColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultIconHaloColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for IconHaloColor.
     */
    val defaultIconHaloColorTransition: StyleTransition?
      /**
       * Get the IconHaloColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-color-transition").silentUnwrap()

    /**
     * Distance of halo to the icon outline.
     */
    val defaultIconHaloWidth: Double?
      /**
       * Distance of halo to the icon outline.
       *
       * Get the default value of IconHaloWidth property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-width").silentUnwrap()
      }

    /**
     * Distance of halo to the icon outline.
     *
     * This is an Expression representation of "icon-halo-width".
     *
     * Distance of halo to the icon outline.
     */
    val defaultIconHaloWidthAsExpression: Expression?
      /**
       * Get default value of the IconHaloWidth property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-width").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconHaloWidth?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for IconHaloWidth.
     */
    val defaultIconHaloWidthTransition: StyleTransition?
      /**
       * Get the IconHaloWidth property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-halo-width-transition").silentUnwrap()

    /**
     * The opacity at which the icon will be drawn.
     */
    val defaultIconOpacity: Double?
      /**
       * The opacity at which the icon will be drawn.
       *
       * Get the default value of IconOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-opacity").silentUnwrap()
      }

    /**
     * The opacity at which the icon will be drawn.
     *
     * This is an Expression representation of "icon-opacity".
     *
     * The opacity at which the icon will be drawn.
     */
    val defaultIconOpacityAsExpression: Expression?
      /**
       * Get default value of the IconOpacity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-opacity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconOpacity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for IconOpacity.
     */
    val defaultIconOpacityTransition: StyleTransition?
      /**
       * Get the IconOpacity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-opacity-transition").silentUnwrap()

    /**
     * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
     */
    val defaultIconTranslate: List<Double>?
      /**
       * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
       *
       * Get the default value of IconTranslate property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate").silentUnwrap()
      }

    /**
     * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
     *
     * This is an Expression representation of "icon-translate".
     *
     * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
     */
    val defaultIconTranslateAsExpression: Expression?
      /**
       * Get default value of the IconTranslate property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconTranslate?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for IconTranslate.
     */
    val defaultIconTranslateTransition: StyleTransition?
      /**
       * Get the IconTranslate property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate-transition").silentUnwrap()

    /**
     * Controls the frame of reference for `icon-translate`.
     */
    val defaultIconTranslateAnchor: IconTranslateAnchor?
      /**
       * Controls the frame of reference for `icon-translate`.
       *
       * Get the default value of IconTranslateAnchor property
       *
       * @return IconTranslateAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate-anchor").silentUnwrap<String>()?.let {
          return IconTranslateAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Controls the frame of reference for `icon-translate`.
     *
     * This is an Expression representation of "icon-translate-anchor".
     *
     * Controls the frame of reference for `icon-translate`.
     */
    val defaultIconTranslateAnchorAsExpression: Expression?
      /**
       * Get default value of the IconTranslateAnchor property as an Expression
       *
       * @return IconTranslateAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate-anchor").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultIconTranslateAnchor?.let {
          return Expression.literal(it.value)
        }
        return null
      }

    /**
     * The color with which the text will be drawn.
     */
    val defaultTextColor: String?
      /**
       * The color with which the text will be drawn.
       *
       * Get the default value of TextColor property
       *
       * @return String
       */
      get() {
        defaultTextColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The color with which the text will be drawn.
     *
     * This is an Expression representation of "text-color".
     *
     * The color with which the text will be drawn.
     */
    val defaultTextColorAsExpression: Expression?
      /**
       * Get default value of the TextColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The color with which the text will be drawn.
     */
    val defaultTextColorAsColorInt: Int?
      /**
       * The color with which the text will be drawn.
       *
       * Get the default value of TextColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultTextColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for TextColor.
     */
    val defaultTextColorTransition: StyleTransition?
      /**
       * Get the TextColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-color-transition").silentUnwrap()

    /**
     * The halo's fadeout distance towards the outside.
     */
    val defaultTextHaloBlur: Double?
      /**
       * The halo's fadeout distance towards the outside.
       *
       * Get the default value of TextHaloBlur property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-blur").silentUnwrap()
      }

    /**
     * The halo's fadeout distance towards the outside.
     *
     * This is an Expression representation of "text-halo-blur".
     *
     * The halo's fadeout distance towards the outside.
     */
    val defaultTextHaloBlurAsExpression: Expression?
      /**
       * Get default value of the TextHaloBlur property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-blur").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextHaloBlur?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for TextHaloBlur.
     */
    val defaultTextHaloBlurTransition: StyleTransition?
      /**
       * Get the TextHaloBlur property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-blur-transition").silentUnwrap()

    /**
     * The color of the text's halo, which helps it stand out from backgrounds.
     */
    val defaultTextHaloColor: String?
      /**
       * The color of the text's halo, which helps it stand out from backgrounds.
       *
       * Get the default value of TextHaloColor property
       *
       * @return String
       */
      get() {
        defaultTextHaloColorAsExpression?.let {
          return rgbaExpressionToColorString(it)
        }
        return null
      }

    /**
     * The color of the text's halo, which helps it stand out from backgrounds.
     *
     * This is an Expression representation of "text-halo-color".
     *
     * The color of the text's halo, which helps it stand out from backgrounds.
     */
    val defaultTextHaloColorAsExpression: Expression?
      /**
       * Get default value of the TextHaloColor property as an Expression
       *
       * @return String
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-color").silentUnwrap<Expression>()?.let {
          return it
        }
        return null
      }

    /**
     * The color of the text's halo, which helps it stand out from backgrounds.
     */
    val defaultTextHaloColorAsColorInt: Int?
      /**
       * The color of the text's halo, which helps it stand out from backgrounds.
       *
       * Get the default value of TextHaloColor property as color int.
       *
       * @return int representation of a rgba string color
       */
      @ColorInt
      get() {
        defaultTextHaloColorAsExpression?.let {
          return rgbaExpressionToColorInt(it)
        }
        return null
      }

    /**
     * Transition options for TextHaloColor.
     */
    val defaultTextHaloColorTransition: StyleTransition?
      /**
       * Get the TextHaloColor property transition options
       *
       * @return transition options for String
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-color-transition").silentUnwrap()

    /**
     * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
     */
    val defaultTextHaloWidth: Double?
      /**
       * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
       *
       * Get the default value of TextHaloWidth property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-width").silentUnwrap()
      }

    /**
     * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
     *
     * This is an Expression representation of "text-halo-width".
     *
     * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
     */
    val defaultTextHaloWidthAsExpression: Expression?
      /**
       * Get default value of the TextHaloWidth property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-width").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextHaloWidth?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for TextHaloWidth.
     */
    val defaultTextHaloWidthTransition: StyleTransition?
      /**
       * Get the TextHaloWidth property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-halo-width-transition").silentUnwrap()

    /**
     * The opacity at which the text will be drawn.
     */
    val defaultTextOpacity: Double?
      /**
       * The opacity at which the text will be drawn.
       *
       * Get the default value of TextOpacity property
       *
       * @return Double
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-opacity").silentUnwrap()
      }

    /**
     * The opacity at which the text will be drawn.
     *
     * This is an Expression representation of "text-opacity".
     *
     * The opacity at which the text will be drawn.
     */
    val defaultTextOpacityAsExpression: Expression?
      /**
       * Get default value of the TextOpacity property as an Expression
       *
       * @return Double
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-opacity").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextOpacity?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for TextOpacity.
     */
    val defaultTextOpacityTransition: StyleTransition?
      /**
       * Get the TextOpacity property transition options
       *
       * @return transition options for Double
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-opacity-transition").silentUnwrap()

    /**
     * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
     */
    val defaultTextTranslate: List<Double>?
      /**
       * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
       *
       * Get the default value of TextTranslate property
       *
       * @return List<Double>
       */
      get() {
        return StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate").silentUnwrap()
      }

    /**
     * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
     *
     * This is an Expression representation of "text-translate".
     *
     * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
     */
    val defaultTextTranslateAsExpression: Expression?
      /**
       * Get default value of the TextTranslate property as an Expression
       *
       * @return List<Double>
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextTranslate?.let {
          return Expression.literal(it)
        }
        return null
      }

    /**
     * Transition options for TextTranslate.
     */
    val defaultTextTranslateTransition: StyleTransition?
      /**
       * Get the TextTranslate property transition options
       *
       * @return transition options for List<Double>
       */
      get() = StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate-transition").silentUnwrap()

    /**
     * Controls the frame of reference for `text-translate`.
     */
    val defaultTextTranslateAnchor: TextTranslateAnchor?
      /**
       * Controls the frame of reference for `text-translate`.
       *
       * Get the default value of TextTranslateAnchor property
       *
       * @return TextTranslateAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate-anchor").silentUnwrap<String>()?.let {
          return TextTranslateAnchor.valueOf(it.toUpperCase(Locale.US).replace('-', '_'))
        }
        return null
      }

    /**
     * Controls the frame of reference for `text-translate`.
     *
     * This is an Expression representation of "text-translate-anchor".
     *
     * Controls the frame of reference for `text-translate`.
     */
    val defaultTextTranslateAnchorAsExpression: Expression?
      /**
       * Get default value of the TextTranslateAnchor property as an Expression
       *
       * @return TextTranslateAnchor
       */
      get() {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate-anchor").silentUnwrap<Expression>()?.let {
          return it
        }
        defaultTextTranslateAnchor?.let {
          return Expression.literal(it.value)
        }
        return null
      }
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@LayersDsl
interface SymbolLayerDsl {
  /**
   * A source layer is an individual layer of data within a vector source.
   * A vector source can have multiple source layers.
   *
   * @param sourceLayer value of sourceLayer
   */
  fun sourceLayer(sourceLayer: String): SymbolLayer

  /**
   * A filter is a property at the layer level that determines which features should be rendered in a style layer.
   *
   * Filters are written as expressions, which give you fine-grained control over which features to include: the
   * style layer only displays the features that match the filter condition that you define.
   *
   * Note: Zoom expressions in filters are only evaluated at integer zoom levels. The `feature-state` expression
   * is not supported in filter expressions.
   *
   * @param filter the expression filter to set
   */
  fun filter(filter: Expression): SymbolLayer

  /**
   * Whether this layer is displayed.
   *
   * @param visibility value of Visibility
   */
  fun visibility(visibility: Visibility): SymbolLayer

  /**
   * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of minzoom
   */
  fun minZoom(minZoom: Double): SymbolLayer

  /**
   * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
   *
   * Range:
   *       minimum: 0
   *       maximum: 24
   *
   * @param value value of maxzoom
   */
  fun maxZoom(maxZoom: Double): SymbolLayer

  // Property getters and setters

  /**
   * If true, the icon will be visible even if it collides with other previously drawn symbols.
   *
   * @param iconAllowOverlap value of iconAllowOverlap
   */
  fun iconAllowOverlap(iconAllowOverlap: Boolean = false): SymbolLayer

  /**
   * If true, the icon will be visible even if it collides with other previously drawn symbols.
   *
   * @param iconAllowOverlap value of iconAllowOverlap as Expression
   */
  fun iconAllowOverlap(iconAllowOverlap: Expression): SymbolLayer

  /**
   * Part of the icon placed closest to the anchor.
   *
   * @param iconAnchor value of iconAnchor
   */
  fun iconAnchor(iconAnchor: IconAnchor = IconAnchor.CENTER): SymbolLayer

  /**
   * Part of the icon placed closest to the anchor.
   *
   * @param iconAnchor value of iconAnchor as Expression
   */
  fun iconAnchor(iconAnchor: Expression): SymbolLayer

  /**
   * If true, other symbols can be visible even if they collide with the icon.
   *
   * @param iconIgnorePlacement value of iconIgnorePlacement
   */
  fun iconIgnorePlacement(iconIgnorePlacement: Boolean = false): SymbolLayer

  /**
   * If true, other symbols can be visible even if they collide with the icon.
   *
   * @param iconIgnorePlacement value of iconIgnorePlacement as Expression
   */
  fun iconIgnorePlacement(iconIgnorePlacement: Expression): SymbolLayer

  /**
   * Name of image in sprite to use for drawing an image background.
   *
   * @param iconImage value of iconImage
   */
  fun iconImage(iconImage: String): SymbolLayer

  /**
   * Name of image in sprite to use for drawing an image background.
   *
   * @param iconImage value of iconImage as Expression
   */
  fun iconImage(iconImage: Expression): SymbolLayer

  /**
   * If true, the icon may be flipped to prevent it from being rendered upside-down.
   *
   * @param iconKeepUpright value of iconKeepUpright
   */
  fun iconKeepUpright(iconKeepUpright: Boolean = false): SymbolLayer

  /**
   * If true, the icon may be flipped to prevent it from being rendered upside-down.
   *
   * @param iconKeepUpright value of iconKeepUpright as Expression
   */
  fun iconKeepUpright(iconKeepUpright: Expression): SymbolLayer

  /**
   * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
   *
   * @param iconOffset value of iconOffset
   */
  fun iconOffset(iconOffset: List<Double> = listOf(0.0, 0.0)): SymbolLayer

  /**
   * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
   *
   * @param iconOffset value of iconOffset as Expression
   */
  fun iconOffset(iconOffset: Expression): SymbolLayer

  /**
   * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
   *
   * @param iconOptional value of iconOptional
   */
  fun iconOptional(iconOptional: Boolean = false): SymbolLayer

  /**
   * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
   *
   * @param iconOptional value of iconOptional as Expression
   */
  fun iconOptional(iconOptional: Expression): SymbolLayer

  /**
   * Size of the additional area around the icon bounding box used for detecting symbol collisions.
   *
   * @param iconPadding value of iconPadding
   */
  fun iconPadding(iconPadding: Double = 2.0): SymbolLayer

  /**
   * Size of the additional area around the icon bounding box used for detecting symbol collisions.
   *
   * @param iconPadding value of iconPadding as Expression
   */
  fun iconPadding(iconPadding: Expression): SymbolLayer

  /**
   * Orientation of icon when map is pitched.
   *
   * @param iconPitchAlignment value of iconPitchAlignment
   */
  fun iconPitchAlignment(iconPitchAlignment: IconPitchAlignment = IconPitchAlignment.AUTO): SymbolLayer

  /**
   * Orientation of icon when map is pitched.
   *
   * @param iconPitchAlignment value of iconPitchAlignment as Expression
   */
  fun iconPitchAlignment(iconPitchAlignment: Expression): SymbolLayer

  /**
   * Rotates the icon clockwise.
   *
   * @param iconRotate value of iconRotate
   */
  fun iconRotate(iconRotate: Double = 0.0): SymbolLayer

  /**
   * Rotates the icon clockwise.
   *
   * @param iconRotate value of iconRotate as Expression
   */
  fun iconRotate(iconRotate: Expression): SymbolLayer

  /**
   * In combination with `symbol-placement`, determines the rotation behavior of icons.
   *
   * @param iconRotationAlignment value of iconRotationAlignment
   */
  fun iconRotationAlignment(iconRotationAlignment: IconRotationAlignment = IconRotationAlignment.AUTO): SymbolLayer

  /**
   * In combination with `symbol-placement`, determines the rotation behavior of icons.
   *
   * @param iconRotationAlignment value of iconRotationAlignment as Expression
   */
  fun iconRotationAlignment(iconRotationAlignment: Expression): SymbolLayer

  /**
   * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
   *
   * @param iconSize value of iconSize
   */
  fun iconSize(iconSize: Double = 1.0): SymbolLayer

  /**
   * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
   *
   * @param iconSize value of iconSize as Expression
   */
  fun iconSize(iconSize: Expression): SymbolLayer

  /**
   * Scales the icon to fit around the associated text.
   *
   * @param iconTextFit value of iconTextFit
   */
  fun iconTextFit(iconTextFit: IconTextFit = IconTextFit.NONE): SymbolLayer

  /**
   * Scales the icon to fit around the associated text.
   *
   * @param iconTextFit value of iconTextFit as Expression
   */
  fun iconTextFit(iconTextFit: Expression): SymbolLayer

  /**
   * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left.
   *
   * @param iconTextFitPadding value of iconTextFitPadding
   */
  fun iconTextFitPadding(iconTextFitPadding: List<Double> = listOf(0.0, 0.0, 0.0, 0.0)): SymbolLayer

  /**
   * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left.
   *
   * @param iconTextFitPadding value of iconTextFitPadding as Expression
   */
  fun iconTextFitPadding(iconTextFitPadding: Expression): SymbolLayer

  /**
   * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
   *
   * @param symbolAvoidEdges value of symbolAvoidEdges
   */
  fun symbolAvoidEdges(symbolAvoidEdges: Boolean = false): SymbolLayer

  /**
   * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
   *
   * @param symbolAvoidEdges value of symbolAvoidEdges as Expression
   */
  fun symbolAvoidEdges(symbolAvoidEdges: Expression): SymbolLayer

  /**
   * Label placement relative to its geometry.
   *
   * @param symbolPlacement value of symbolPlacement
   */
  fun symbolPlacement(symbolPlacement: SymbolPlacement = SymbolPlacement.POINT): SymbolLayer

  /**
   * Label placement relative to its geometry.
   *
   * @param symbolPlacement value of symbolPlacement as Expression
   */
  fun symbolPlacement(symbolPlacement: Expression): SymbolLayer

  /**
   * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first.  When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
   *
   * @param symbolSortKey value of symbolSortKey
   */
  fun symbolSortKey(symbolSortKey: Double): SymbolLayer

  /**
   * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first.  When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
   *
   * @param symbolSortKey value of symbolSortKey as Expression
   */
  fun symbolSortKey(symbolSortKey: Expression): SymbolLayer

  /**
   * Distance between two symbol anchors.
   *
   * @param symbolSpacing value of symbolSpacing
   */
  fun symbolSpacing(symbolSpacing: Double = 250.0): SymbolLayer

  /**
   * Distance between two symbol anchors.
   *
   * @param symbolSpacing value of symbolSpacing as Expression
   */
  fun symbolSpacing(symbolSpacing: Expression): SymbolLayer

  /**
   * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
   *
   * @param symbolZOrder value of symbolZOrder
   */
  fun symbolZOrder(symbolZOrder: SymbolZOrder = SymbolZOrder.AUTO): SymbolLayer

  /**
   * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
   *
   * @param symbolZOrder value of symbolZOrder as Expression
   */
  fun symbolZOrder(symbolZOrder: Expression): SymbolLayer

  /**
   * If true, the text will be visible even if it collides with other previously drawn symbols.
   *
   * @param textAllowOverlap value of textAllowOverlap
   */
  fun textAllowOverlap(textAllowOverlap: Boolean = false): SymbolLayer

  /**
   * If true, the text will be visible even if it collides with other previously drawn symbols.
   *
   * @param textAllowOverlap value of textAllowOverlap as Expression
   */
  fun textAllowOverlap(textAllowOverlap: Expression): SymbolLayer

  /**
   * Part of the text placed closest to the anchor.
   *
   * @param textAnchor value of textAnchor
   */
  fun textAnchor(textAnchor: TextAnchor = TextAnchor.CENTER): SymbolLayer

  /**
   * Part of the text placed closest to the anchor.
   *
   * @param textAnchor value of textAnchor as Expression
   */
  fun textAnchor(textAnchor: Expression): SymbolLayer

  /**
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
   *
   * @param textField value of textField
   */
  fun textField(textField: Formatted): SymbolLayer

  /**
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
   *
   * @param textField value of textField as Expression
   */
  fun textField(textField: Expression): SymbolLayer

  /**
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
   *
   * Set the TextField property as String.
   *
   * @param textField value of textField as String
   */
  fun textField(textField: String): SymbolLayer

  /**
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
   *
   * DSL for construct [Formatted] textField.
   */
  fun textField(block: Formatted.() -> Unit): SymbolLayer

  /**
   * Font stack to use for displaying text.
   *
   * @param textFont value of textFont
   */
  fun textFont(textFont: List<String> = listOf("Open Sans Regular", "Arial Unicode MS Regular")): SymbolLayer

  /**
   * Font stack to use for displaying text.
   *
   * @param textFont value of textFont as Expression
   */
  fun textFont(textFont: Expression): SymbolLayer

  /**
   * If true, other symbols can be visible even if they collide with the text.
   *
   * @param textIgnorePlacement value of textIgnorePlacement
   */
  fun textIgnorePlacement(textIgnorePlacement: Boolean = false): SymbolLayer

  /**
   * If true, other symbols can be visible even if they collide with the text.
   *
   * @param textIgnorePlacement value of textIgnorePlacement as Expression
   */
  fun textIgnorePlacement(textIgnorePlacement: Expression): SymbolLayer

  /**
   * Text justification options.
   *
   * @param textJustify value of textJustify
   */
  fun textJustify(textJustify: TextJustify = TextJustify.CENTER): SymbolLayer

  /**
   * Text justification options.
   *
   * @param textJustify value of textJustify as Expression
   */
  fun textJustify(textJustify: Expression): SymbolLayer

  /**
   * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
   *
   * @param textKeepUpright value of textKeepUpright
   */
  fun textKeepUpright(textKeepUpright: Boolean = true): SymbolLayer

  /**
   * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
   *
   * @param textKeepUpright value of textKeepUpright as Expression
   */
  fun textKeepUpright(textKeepUpright: Expression): SymbolLayer

  /**
   * Text tracking amount.
   *
   * @param textLetterSpacing value of textLetterSpacing
   */
  fun textLetterSpacing(textLetterSpacing: Double = 0.0): SymbolLayer

  /**
   * Text tracking amount.
   *
   * @param textLetterSpacing value of textLetterSpacing as Expression
   */
  fun textLetterSpacing(textLetterSpacing: Expression): SymbolLayer

  /**
   * Text leading value for multi-line text.
   *
   * @param textLineHeight value of textLineHeight
   */
  fun textLineHeight(textLineHeight: Double = 1.2): SymbolLayer

  /**
   * Text leading value for multi-line text.
   *
   * @param textLineHeight value of textLineHeight as Expression
   */
  fun textLineHeight(textLineHeight: Expression): SymbolLayer

  /**
   * Maximum angle change between adjacent characters.
   *
   * @param textMaxAngle value of textMaxAngle
   */
  fun textMaxAngle(textMaxAngle: Double = 45.0): SymbolLayer

  /**
   * Maximum angle change between adjacent characters.
   *
   * @param textMaxAngle value of textMaxAngle as Expression
   */
  fun textMaxAngle(textMaxAngle: Expression): SymbolLayer

  /**
   * The maximum line width for text wrapping.
   *
   * @param textMaxWidth value of textMaxWidth
   */
  fun textMaxWidth(textMaxWidth: Double = 10.0): SymbolLayer

  /**
   * The maximum line width for text wrapping.
   *
   * @param textMaxWidth value of textMaxWidth as Expression
   */
  fun textMaxWidth(textMaxWidth: Expression): SymbolLayer

  /**
   * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
   *
   * @param textOffset value of textOffset
   */
  fun textOffset(textOffset: List<Double> = listOf(0.0, 0.0)): SymbolLayer

  /**
   * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
   *
   * @param textOffset value of textOffset as Expression
   */
  fun textOffset(textOffset: Expression): SymbolLayer

  /**
   * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
   *
   * @param textOptional value of textOptional
   */
  fun textOptional(textOptional: Boolean = false): SymbolLayer

  /**
   * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
   *
   * @param textOptional value of textOptional as Expression
   */
  fun textOptional(textOptional: Expression): SymbolLayer

  /**
   * Size of the additional area around the text bounding box used for detecting symbol collisions.
   *
   * @param textPadding value of textPadding
   */
  fun textPadding(textPadding: Double = 2.0): SymbolLayer

  /**
   * Size of the additional area around the text bounding box used for detecting symbol collisions.
   *
   * @param textPadding value of textPadding as Expression
   */
  fun textPadding(textPadding: Expression): SymbolLayer

  /**
   * Orientation of text when map is pitched.
   *
   * @param textPitchAlignment value of textPitchAlignment
   */
  fun textPitchAlignment(textPitchAlignment: TextPitchAlignment = TextPitchAlignment.AUTO): SymbolLayer

  /**
   * Orientation of text when map is pitched.
   *
   * @param textPitchAlignment value of textPitchAlignment as Expression
   */
  fun textPitchAlignment(textPitchAlignment: Expression): SymbolLayer

  /**
   * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
   *
   * @param textRadialOffset value of textRadialOffset
   */
  fun textRadialOffset(textRadialOffset: Double = 0.0): SymbolLayer

  /**
   * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
   *
   * @param textRadialOffset value of textRadialOffset as Expression
   */
  fun textRadialOffset(textRadialOffset: Expression): SymbolLayer

  /**
   * Rotates the text clockwise.
   *
   * @param textRotate value of textRotate
   */
  fun textRotate(textRotate: Double = 0.0): SymbolLayer

  /**
   * Rotates the text clockwise.
   *
   * @param textRotate value of textRotate as Expression
   */
  fun textRotate(textRotate: Expression): SymbolLayer

  /**
   * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
   *
   * @param textRotationAlignment value of textRotationAlignment
   */
  fun textRotationAlignment(textRotationAlignment: TextRotationAlignment = TextRotationAlignment.AUTO): SymbolLayer

  /**
   * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
   *
   * @param textRotationAlignment value of textRotationAlignment as Expression
   */
  fun textRotationAlignment(textRotationAlignment: Expression): SymbolLayer

  /**
   * Font size.
   *
   * @param textSize value of textSize
   */
  fun textSize(textSize: Double = 16.0): SymbolLayer

  /**
   * Font size.
   *
   * @param textSize value of textSize as Expression
   */
  fun textSize(textSize: Expression): SymbolLayer

  /**
   * Specifies how to capitalize text, similar to the CSS `text-transform` property.
   *
   * @param textTransform value of textTransform
   */
  fun textTransform(textTransform: TextTransform = TextTransform.NONE): SymbolLayer

  /**
   * Specifies how to capitalize text, similar to the CSS `text-transform` property.
   *
   * @param textTransform value of textTransform as Expression
   */
  fun textTransform(textTransform: Expression): SymbolLayer

  /**
   * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
   *
   * @param textVariableAnchor value of textVariableAnchor
   */
  fun textVariableAnchor(textVariableAnchor: List<String>): SymbolLayer

  /**
   * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
   *
   * @param textVariableAnchor value of textVariableAnchor as Expression
   */
  fun textVariableAnchor(textVariableAnchor: Expression): SymbolLayer

  /**
   * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
   *
   * @param textWritingMode value of textWritingMode
   */
  fun textWritingMode(textWritingMode: List<String>): SymbolLayer

  /**
   * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
   *
   * @param textWritingMode value of textWritingMode as Expression
   */
  fun textWritingMode(textWritingMode: Expression): SymbolLayer

  /**
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * @param iconColor value of iconColor
   */
  fun iconColor(iconColor: String = "#000000"): SymbolLayer

  /**
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * @param iconColor value of iconColor as Expression
   */
  fun iconColor(iconColor: Expression): SymbolLayer

  /**
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * @param iconColor value of iconColor
   */
  fun iconColor(@ColorInt iconColor: Int): SymbolLayer

  /**
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * Set the IconColor property transition options
   *
   * @param options transition options for String
   */
  fun iconColorTransition(options: StyleTransition): SymbolLayer

  /**
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * DSL for [iconColorTransition].
   */
  fun iconColorTransition(block: StyleTransition.Builder.() -> Unit): SymbolLayer

  /**
   * Fade out the halo towards the outside.
   *
   * @param iconHaloBlur value of iconHaloBlur
   */
  fun iconHaloBlur(iconHaloBlur: Double = 0.0): SymbolLayer

  /**
   * Fade out the halo towards the outside.
   *
   * @param iconHaloBlur value of iconHaloBlur as Expression
   */
  fun iconHaloBlur(iconHaloBlur: Expression): SymbolLayer

  /**
   * Fade out the halo towards the outside.
   *
   * Set the IconHaloBlur property transition options
   *
   * @param options transition options for Double
   */
  fun iconHaloBlurTransition(options: StyleTransition): SymbolLayer

  /**
   * Fade out the halo towards the outside.
   *
   * DSL for [iconHaloBlurTransition].
   */
  fun iconHaloBlurTransition(block: StyleTransition.Builder.() -> Unit): SymbolLayer

  /**
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * @param iconHaloColor value of iconHaloColor
   */
  fun iconHaloColor(iconHaloColor: String = "rgba(0, 0, 0, 0)"): SymbolLayer

  /**
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * @param iconHaloColor value of iconHaloColor as Expression
   */
  fun iconHaloColor(iconHaloColor: Expression): SymbolLayer

  /**
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * @param iconHaloColor value of iconHaloColor
   */
  fun iconHaloColor(@ColorInt iconHaloColor: Int): SymbolLayer

  /**
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * Set the IconHaloColor property transition options
   *
   * @param options transition options for String
   */
  fun iconHaloColorTransition(options: StyleTransition): SymbolLayer

  /**
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
   *
   * DSL for [iconHaloColorTransition].
   */
  fun iconHaloColorTransition(block: StyleTransition.Builder.() -> Unit): SymbolLayer

  /**
   * Distance of halo to the icon outline.
   *
   * @param iconHaloWidth value of iconHaloWidth
   */
  fun iconHaloWidth(iconHaloWidth: Double = 0.0): SymbolLayer

  /**
   * Distance of halo to the icon outline.
   *
   * @param iconHaloWidth value of iconHaloWidth as Expression
   */
  fun iconHaloWidth(iconHaloWidth: Expression): SymbolLayer

  /**
   * Distance of halo to the icon outline.
   *
   * Set the IconHaloWidth property transition options
   *
   * @param options transition options for Double
   */
  fun iconHaloWidthTransition(options: StyleTransition): SymbolLayer

  /**
   * Distance of halo to the icon outline.
   *
   * DSL for [iconHaloWidthTransition].
   */
  fun iconHaloWidthTransition(block: StyleTransition.Builder.() -> Unit): SymbolLayer

  /**
   * The opacity at which the icon will be drawn.
   *
   * @param iconOpacity value of iconOpacity
   */
  fun iconOpacity(iconOpacity: Double = 1.0): SymbolLayer

  /**
   * The opacity at which the icon will be drawn.
   *
   * @param iconOpacity value of iconOpacity as Expression
   */
  fun iconOpacity(iconOpacity: Expression): SymbolLayer

  /**
   * The opacity at which the icon will be drawn.
   *
   * Set the IconOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun iconOpacityTransition(options: StyleTransition): SymbolLayer

  /**
   * The opacity at which the icon will be drawn.
   *
   * DSL for [iconOpacityTransition].
   */
  fun iconOpacityTransition(block: StyleTransition.Builder.() -> Unit): SymbolLayer

  /**
   * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   *
   * @param iconTranslate value of iconTranslate
   */
  fun iconTranslate(iconTranslate: List<Double> = listOf(0.0, 0.0)): SymbolLayer

  /**
   * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   *
   * @param iconTranslate value of iconTranslate as Expression
   */
  fun iconTranslate(iconTranslate: Expression): SymbolLayer

  /**
   * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   *
   * Set the IconTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  fun iconTranslateTransition(options: StyleTransition): SymbolLayer

  /**
   * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   *
   * DSL for [iconTranslateTransition].
   */
  fun iconTranslateTransition(block: StyleTransition.Builder.() -> Unit): SymbolLayer

  /**
   * Controls the frame of reference for `icon-translate`.
   *
   * @param iconTranslateAnchor value of iconTranslateAnchor
   */
  fun iconTranslateAnchor(iconTranslateAnchor: IconTranslateAnchor = IconTranslateAnchor.MAP): SymbolLayer

  /**
   * Controls the frame of reference for `icon-translate`.
   *
   * @param iconTranslateAnchor value of iconTranslateAnchor as Expression
   */
  fun iconTranslateAnchor(iconTranslateAnchor: Expression): SymbolLayer

  /**
   * The color with which the text will be drawn.
   *
   * @param textColor value of textColor
   */
  fun textColor(textColor: String = "#000000"): SymbolLayer

  /**
   * The color with which the text will be drawn.
   *
   * @param textColor value of textColor as Expression
   */
  fun textColor(textColor: Expression): SymbolLayer

  /**
   * The color with which the text will be drawn.
   *
   * @param textColor value of textColor
   */
  fun textColor(@ColorInt textColor: Int): SymbolLayer

  /**
   * The color with which the text will be drawn.
   *
   * Set the TextColor property transition options
   *
   * @param options transition options for String
   */
  fun textColorTransition(options: StyleTransition): SymbolLayer

  /**
   * The color with which the text will be drawn.
   *
   * DSL for [textColorTransition].
   */
  fun textColorTransition(block: StyleTransition.Builder.() -> Unit): SymbolLayer

  /**
   * The halo's fadeout distance towards the outside.
   *
   * @param textHaloBlur value of textHaloBlur
   */
  fun textHaloBlur(textHaloBlur: Double = 0.0): SymbolLayer

  /**
   * The halo's fadeout distance towards the outside.
   *
   * @param textHaloBlur value of textHaloBlur as Expression
   */
  fun textHaloBlur(textHaloBlur: Expression): SymbolLayer

  /**
   * The halo's fadeout distance towards the outside.
   *
   * Set the TextHaloBlur property transition options
   *
   * @param options transition options for Double
   */
  fun textHaloBlurTransition(options: StyleTransition): SymbolLayer

  /**
   * The halo's fadeout distance towards the outside.
   *
   * DSL for [textHaloBlurTransition].
   */
  fun textHaloBlurTransition(block: StyleTransition.Builder.() -> Unit): SymbolLayer

  /**
   * The color of the text's halo, which helps it stand out from backgrounds.
   *
   * @param textHaloColor value of textHaloColor
   */
  fun textHaloColor(textHaloColor: String = "rgba(0, 0, 0, 0)"): SymbolLayer

  /**
   * The color of the text's halo, which helps it stand out from backgrounds.
   *
   * @param textHaloColor value of textHaloColor as Expression
   */
  fun textHaloColor(textHaloColor: Expression): SymbolLayer

  /**
   * The color of the text's halo, which helps it stand out from backgrounds.
   *
   * @param textHaloColor value of textHaloColor
   */
  fun textHaloColor(@ColorInt textHaloColor: Int): SymbolLayer

  /**
   * The color of the text's halo, which helps it stand out from backgrounds.
   *
   * Set the TextHaloColor property transition options
   *
   * @param options transition options for String
   */
  fun textHaloColorTransition(options: StyleTransition): SymbolLayer

  /**
   * The color of the text's halo, which helps it stand out from backgrounds.
   *
   * DSL for [textHaloColorTransition].
   */
  fun textHaloColorTransition(block: StyleTransition.Builder.() -> Unit): SymbolLayer

  /**
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
   *
   * @param textHaloWidth value of textHaloWidth
   */
  fun textHaloWidth(textHaloWidth: Double = 0.0): SymbolLayer

  /**
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
   *
   * @param textHaloWidth value of textHaloWidth as Expression
   */
  fun textHaloWidth(textHaloWidth: Expression): SymbolLayer

  /**
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
   *
   * Set the TextHaloWidth property transition options
   *
   * @param options transition options for Double
   */
  fun textHaloWidthTransition(options: StyleTransition): SymbolLayer

  /**
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
   *
   * DSL for [textHaloWidthTransition].
   */
  fun textHaloWidthTransition(block: StyleTransition.Builder.() -> Unit): SymbolLayer

  /**
   * The opacity at which the text will be drawn.
   *
   * @param textOpacity value of textOpacity
   */
  fun textOpacity(textOpacity: Double = 1.0): SymbolLayer

  /**
   * The opacity at which the text will be drawn.
   *
   * @param textOpacity value of textOpacity as Expression
   */
  fun textOpacity(textOpacity: Expression): SymbolLayer

  /**
   * The opacity at which the text will be drawn.
   *
   * Set the TextOpacity property transition options
   *
   * @param options transition options for Double
   */
  fun textOpacityTransition(options: StyleTransition): SymbolLayer

  /**
   * The opacity at which the text will be drawn.
   *
   * DSL for [textOpacityTransition].
   */
  fun textOpacityTransition(block: StyleTransition.Builder.() -> Unit): SymbolLayer

  /**
   * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   *
   * @param textTranslate value of textTranslate
   */
  fun textTranslate(textTranslate: List<Double> = listOf(0.0, 0.0)): SymbolLayer

  /**
   * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   *
   * @param textTranslate value of textTranslate as Expression
   */
  fun textTranslate(textTranslate: Expression): SymbolLayer

  /**
   * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   *
   * Set the TextTranslate property transition options
   *
   * @param options transition options for List<Double>
   */
  fun textTranslateTransition(options: StyleTransition): SymbolLayer

  /**
   * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
   *
   * DSL for [textTranslateTransition].
   */
  fun textTranslateTransition(block: StyleTransition.Builder.() -> Unit): SymbolLayer

  /**
   * Controls the frame of reference for `text-translate`.
   *
   * @param textTranslateAnchor value of textTranslateAnchor
   */
  fun textTranslateAnchor(textTranslateAnchor: TextTranslateAnchor = TextTranslateAnchor.MAP): SymbolLayer

  /**
   * Controls the frame of reference for `text-translate`.
   *
   * @param textTranslateAnchor value of textTranslateAnchor as Expression
   */
  fun textTranslateAnchor(textTranslateAnchor: Expression): SymbolLayer
}

/**
 * DSL function for creating a [SymbolLayer].
 */
fun symbolLayer(layerId: String, sourceId: String, block: SymbolLayerDsl.() -> Unit): SymbolLayer = SymbolLayer(layerId, sourceId).apply(block)

// End of generated file.