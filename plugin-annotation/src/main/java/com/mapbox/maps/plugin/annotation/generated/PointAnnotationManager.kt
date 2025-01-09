// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import android.graphics.Bitmap
import androidx.annotation.ColorInt
import com.google.gson.JsonArray
import com.mapbox.geojson.*
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.StyleManager
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.extension.style.utils.TypeUtils
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationManagerImpl
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.AnnotationType
import com.mapbox.maps.plugin.annotation.ClusterAnnotationManager
import com.mapbox.maps.plugin.annotation.OnClusterClickListener
import com.mapbox.maps.plugin.annotation.OnClusterLongClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation.Companion.ICON_DEFAULT_NAME_PREFIX
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import java.util.Locale
import java.util.concurrent.atomic.AtomicLong

/**
 * The pointAnnotation manager allows to add pointAnnotations to a map.
 */
class PointAnnotationManager(
  delegateProvider: MapDelegateProvider,
  annotationConfig: AnnotationConfig? = null
) :
  AnnotationManagerImpl<Point, PointAnnotation, PointAnnotationOptions, OnPointAnnotationDragListener, OnPointAnnotationClickListener, OnPointAnnotationLongClickListener, OnPointAnnotationInteractionListener, SymbolLayer>(
    delegateProvider, annotationConfig, ID_GENERATOR.incrementAndGet(), "pointAnnotation", ::SymbolLayer
  ),
  ClusterAnnotationManager {

  /**
   * The Added clusterClickListeners
   */
  override val clusterClickListeners: MutableList<OnClusterClickListener> = mutableListOf()

  /**
   * The Added clusterLongClickListeners
   */
  override val clusterLongClickListeners: MutableList<OnClusterLongClickListener> = mutableListOf()

  @OptIn(MapboxExperimental::class)
  override fun setDataDrivenPropertyIsUsed(property: String) {
    when (property) {
      PointAnnotationOptions.PROPERTY_ICON_ANCHOR -> {
        layer.iconAnchor(get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR))
        dragLayer.iconAnchor(get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR))
      }
      PointAnnotationOptions.PROPERTY_ICON_IMAGE -> {
        layer.iconImage(get(PointAnnotationOptions.PROPERTY_ICON_IMAGE))
        dragLayer.iconImage(get(PointAnnotationOptions.PROPERTY_ICON_IMAGE))
      }
      PointAnnotationOptions.PROPERTY_ICON_OFFSET -> {
        layer.iconOffset(get(PointAnnotationOptions.PROPERTY_ICON_OFFSET))
        dragLayer.iconOffset(get(PointAnnotationOptions.PROPERTY_ICON_OFFSET))
      }
      PointAnnotationOptions.PROPERTY_ICON_ROTATE -> {
        layer.iconRotate(get(PointAnnotationOptions.PROPERTY_ICON_ROTATE))
        dragLayer.iconRotate(get(PointAnnotationOptions.PROPERTY_ICON_ROTATE))
      }
      PointAnnotationOptions.PROPERTY_ICON_SIZE -> {
        layer.iconSize(get(PointAnnotationOptions.PROPERTY_ICON_SIZE))
        dragLayer.iconSize(get(PointAnnotationOptions.PROPERTY_ICON_SIZE))
      }
      PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT -> {
        layer.iconTextFit(get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT))
        dragLayer.iconTextFit(get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT))
      }
      PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING -> {
        layer.iconTextFitPadding(get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING))
        dragLayer.iconTextFitPadding(get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING))
      }
      PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY -> {
        layer.symbolSortKey(get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY))
        dragLayer.symbolSortKey(get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY))
      }
      PointAnnotationOptions.PROPERTY_TEXT_ANCHOR -> {
        layer.textAnchor(get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR))
        dragLayer.textAnchor(get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR))
      }
      PointAnnotationOptions.PROPERTY_TEXT_FIELD -> {
        layer.textField(get(PointAnnotationOptions.PROPERTY_TEXT_FIELD))
        dragLayer.textField(get(PointAnnotationOptions.PROPERTY_TEXT_FIELD))
      }
      PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY -> {
        layer.textJustify(get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY))
        dragLayer.textJustify(get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY))
      }
      PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING -> {
        layer.textLetterSpacing(get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING))
        dragLayer.textLetterSpacing(get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING))
      }
      PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT -> {
        layer.textLineHeight(get(PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT))
        dragLayer.textLineHeight(get(PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT))
      }
      PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH -> {
        layer.textMaxWidth(get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH))
        dragLayer.textMaxWidth(get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH))
      }
      PointAnnotationOptions.PROPERTY_TEXT_OFFSET -> {
        layer.textOffset(get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET))
        dragLayer.textOffset(get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET))
      }
      PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET -> {
        layer.textRadialOffset(get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET))
        dragLayer.textRadialOffset(get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET))
      }
      PointAnnotationOptions.PROPERTY_TEXT_ROTATE -> {
        layer.textRotate(get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE))
        dragLayer.textRotate(get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE))
      }
      PointAnnotationOptions.PROPERTY_TEXT_SIZE -> {
        layer.textSize(get(PointAnnotationOptions.PROPERTY_TEXT_SIZE))
        dragLayer.textSize(get(PointAnnotationOptions.PROPERTY_TEXT_SIZE))
      }
      PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM -> {
        layer.textTransform(get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM))
        dragLayer.textTransform(get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM))
      }
      PointAnnotationOptions.PROPERTY_ICON_COLOR -> {
        layer.iconColor(get(PointAnnotationOptions.PROPERTY_ICON_COLOR))
        dragLayer.iconColor(get(PointAnnotationOptions.PROPERTY_ICON_COLOR))
      }
      PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH -> {
        layer.iconEmissiveStrength(get(PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH))
        dragLayer.iconEmissiveStrength(get(PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH))
      }
      PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR -> {
        layer.iconHaloBlur(get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR))
        dragLayer.iconHaloBlur(get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR))
      }
      PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR -> {
        layer.iconHaloColor(get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR))
        dragLayer.iconHaloColor(get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR))
      }
      PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH -> {
        layer.iconHaloWidth(get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH))
        dragLayer.iconHaloWidth(get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH))
      }
      PointAnnotationOptions.PROPERTY_ICON_IMAGE_CROSS_FADE -> {
        layer.iconImageCrossFade(get(PointAnnotationOptions.PROPERTY_ICON_IMAGE_CROSS_FADE))
        dragLayer.iconImageCrossFade(get(PointAnnotationOptions.PROPERTY_ICON_IMAGE_CROSS_FADE))
      }
      PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY -> {
        layer.iconOcclusionOpacity(get(PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY))
        dragLayer.iconOcclusionOpacity(get(PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY))
      }
      PointAnnotationOptions.PROPERTY_ICON_OPACITY -> {
        layer.iconOpacity(get(PointAnnotationOptions.PROPERTY_ICON_OPACITY))
        dragLayer.iconOpacity(get(PointAnnotationOptions.PROPERTY_ICON_OPACITY))
      }
      PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET -> {
        layer.symbolZOffset(get(PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET))
        dragLayer.symbolZOffset(get(PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET))
      }
      PointAnnotationOptions.PROPERTY_TEXT_COLOR -> {
        layer.textColor(get(PointAnnotationOptions.PROPERTY_TEXT_COLOR))
        dragLayer.textColor(get(PointAnnotationOptions.PROPERTY_TEXT_COLOR))
      }
      PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH -> {
        layer.textEmissiveStrength(get(PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH))
        dragLayer.textEmissiveStrength(get(PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH))
      }
      PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR -> {
        layer.textHaloBlur(get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR))
        dragLayer.textHaloBlur(get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR))
      }
      PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR -> {
        layer.textHaloColor(get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR))
        dragLayer.textHaloColor(get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR))
      }
      PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH -> {
        layer.textHaloWidth(get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH))
        dragLayer.textHaloWidth(get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH))
      }
      PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY -> {
        layer.textOcclusionOpacity(get(PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY))
        dragLayer.textOcclusionOpacity(get(PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY))
      }
      PointAnnotationOptions.PROPERTY_TEXT_OPACITY -> {
        layer.textOpacity(get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY))
        dragLayer.textOpacity(get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY))
      }
    }
  }

  /**
   * Create a list of pointAnnotations on the map.
   *
   * PointAnnotations are going to be created only for features with a matching geometry.
   *
   * All supported properties are:
   * PointAnnotationOptions.PROPERTY_ICON_ANCHOR - IconAnchor
   * PointAnnotationOptions.PROPERTY_ICON_IMAGE - String
   * PointAnnotationOptions.PROPERTY_ICON_OFFSET - List<Double>
   * PointAnnotationOptions.PROPERTY_ICON_ROTATE - Double
   * PointAnnotationOptions.PROPERTY_ICON_SIZE - Double
   * PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT - IconTextFit
   * PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING - List<Double>
   * PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY - Double
   * PointAnnotationOptions.PROPERTY_TEXT_ANCHOR - TextAnchor
   * PointAnnotationOptions.PROPERTY_TEXT_FIELD - String
   * PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY - TextJustify
   * PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING - Double
   * PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT - Double
   * PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH - Double
   * PointAnnotationOptions.PROPERTY_TEXT_OFFSET - List<Double>
   * PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET - Double
   * PointAnnotationOptions.PROPERTY_TEXT_ROTATE - Double
   * PointAnnotationOptions.PROPERTY_TEXT_SIZE - Double
   * PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM - TextTransform
   * PointAnnotationOptions.PROPERTY_ICON_COLOR - String
   * PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH - Double
   * PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR - Double
   * PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR - String
   * PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH - Double
   * PointAnnotationOptions.PROPERTY_ICON_IMAGE_CROSS_FADE - Double
   * PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY - Double
   * PointAnnotationOptions.PROPERTY_ICON_OPACITY - Double
   * PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET - Double
   * PointAnnotationOptions.PROPERTY_TEXT_COLOR - String
   * PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH - Double
   * PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR - Double
   * PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR - String
   * PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH - Double
   * PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY - Double
   * PointAnnotationOptions.PROPERTY_TEXT_OPACITY - Double
   * Learn more about above properties in the )[The online documentation](https://www.mapbox.com/mapbox-gl-js/style-spec/).
   *
   * Out of spec properties:
   * "is-draggable" - Boolean, true if the pointAnnotation should be draggable, false otherwise
   *
   * @param json the GeoJSON defining the list of pointAnnotations to build
   * @return the list of built pointAnnotations
   */
  fun create(json: String): List<PointAnnotation> {
    return create(FeatureCollection.fromJson(json))
  }

  /**
   * Create a list of pointAnnotations on the map.
   *
   * PointAnnotations are going to be created only for features with a matching geometry.
   *
   * All supported properties are:
   * PointAnnotationOptions.PROPERTY_ICON_ANCHOR - IconAnchor
   * PointAnnotationOptions.PROPERTY_ICON_IMAGE - String
   * PointAnnotationOptions.PROPERTY_ICON_OFFSET - List<Double>
   * PointAnnotationOptions.PROPERTY_ICON_ROTATE - Double
   * PointAnnotationOptions.PROPERTY_ICON_SIZE - Double
   * PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT - IconTextFit
   * PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING - List<Double>
   * PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY - Double
   * PointAnnotationOptions.PROPERTY_TEXT_ANCHOR - TextAnchor
   * PointAnnotationOptions.PROPERTY_TEXT_FIELD - String
   * PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY - TextJustify
   * PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING - Double
   * PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT - Double
   * PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH - Double
   * PointAnnotationOptions.PROPERTY_TEXT_OFFSET - List<Double>
   * PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET - Double
   * PointAnnotationOptions.PROPERTY_TEXT_ROTATE - Double
   * PointAnnotationOptions.PROPERTY_TEXT_SIZE - Double
   * PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM - TextTransform
   * PointAnnotationOptions.PROPERTY_ICON_COLOR - String
   * PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH - Double
   * PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR - Double
   * PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR - String
   * PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH - Double
   * PointAnnotationOptions.PROPERTY_ICON_IMAGE_CROSS_FADE - Double
   * PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY - Double
   * PointAnnotationOptions.PROPERTY_ICON_OPACITY - Double
   * PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET - Double
   * PointAnnotationOptions.PROPERTY_TEXT_COLOR - String
   * PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH - Double
   * PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR - Double
   * PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR - String
   * PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH - Double
   * PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY - Double
   * PointAnnotationOptions.PROPERTY_TEXT_OPACITY - Double
   * Learn more about above properties in the )[The online documentation](https://www.mapbox.com/mapbox-gl-js/style-spec/).
   *
   * Out of spec properties:
   * "is-draggable" - Boolean, true if the pointAnnotation should be draggable, false otherwise
   *
   * @param featureCollection the featureCollection defining the list of pointAnnotations to build
   * @return the list of built pointAnnotations
   */
  fun create(featureCollection: FeatureCollection): List<PointAnnotation> {
    featureCollection.features()?.let { features ->
      val options = features.mapNotNull {
        PointAnnotationOptions.fromFeature(it)
      }
      return create(options)
    }
    return listOf()
  }

  /**
   * Get the key of the id of the annotation.
   *
   * @return the key of the id of the annotation
   */
  override fun getAnnotationIdKey(): String {
    return PointAnnotation.ID_KEY
  }

  // Property accessors
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
      field = value
      if (value != null) {
        if (field != value) {
          if (iconImage == null || iconImage!!.startsWith(ICON_DEFAULT_NAME_PREFIX)) {
            // User does not set iconImage, update iconImage to this new bitmap
            val imageId = ICON_DEFAULT_NAME_PREFIX + value.hashCode()
            iconImage = imageId
            addStyleImage(imageId, value)
          }
        }
      } else {
        iconImage = null
      }
    }

  /**
   * The IconAllowOverlap property
   *
   * If true, the icon will be visible even if it collides with other previously drawn symbols. Default value: false.
   */
  var iconAllowOverlap: Boolean?
    /**
     * Get the IconAllowOverlap property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.iconAllowOverlap
    }
    /**
     * Set the IconAllowOverlap property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-allow-overlap").value
      }
      setLayerProperty(wrappedValue, "icon-allow-overlap")
    }

  /**
   * The default iconAnchor for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Part of the icon placed closest to the anchor. Default value: "center".
   */
  var iconAnchor: IconAnchor?
    /**
     * Get the iconAnchor property.
     *
     * @return property wrapper value around IconAnchor
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)
      value?.let {
        return IconAnchor.valueOf(it.asString.uppercase(Locale.US))
      }
      return null
    }
    /**
     * Set the iconAnchor property.
     *
     * @param value constant property value for IconAnchor
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_ICON_ANCHOR, value.value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The IconIgnorePlacement property
   *
   * If true, other symbols can be visible even if they collide with the icon. Default value: false.
   */
  var iconIgnorePlacement: Boolean?
    /**
     * Get the IconIgnorePlacement property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.iconIgnorePlacement
    }
    /**
     * Set the IconIgnorePlacement property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-ignore-placement").value
      }
      setLayerProperty(wrappedValue, "icon-ignore-placement")
    }

  /**
   * The default iconImage for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Name of image in sprite to use for drawing an image background.
   */
  var iconImage: String?
    /**
     * Get the iconImage property.
     *
     * @return property wrapper value around String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the iconImage property.
     *
     * @param value constant property value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_ICON_IMAGE, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_IMAGE)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_IMAGE)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The IconKeepUpright property
   *
   * If true, the icon may be flipped to prevent it from being rendered upside-down. Default value: false.
   */
  var iconKeepUpright: Boolean?
    /**
     * Get the IconKeepUpright property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.iconKeepUpright
    }
    /**
     * Set the IconKeepUpright property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-keep-upright").value
      }
      setLayerProperty(wrappedValue, "icon-keep-upright")
    }

  /**
   * The default iconOffset for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up. Default value: [0,0].
   */
  var iconOffset: List<Double>?
    /**
     * Get the iconOffset property.
     *
     * @return [Point] value for List<Double>
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)
      return (value as? JsonArray)?.map { it.toString().toDouble() }
    }
    /**
     * Set the iconOffset property.
     */
    set(value) {
      if (!value.isNullOrEmpty()) {
        val jsonArray = JsonArray(value.size)
        value.forEach { jsonArray.add(it) }
        dataDrivenPropertyDefaultValues.add(PointAnnotationOptions.PROPERTY_ICON_OFFSET, jsonArray)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_OFFSET)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_OFFSET)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The IconOptional property
   *
   * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not. Default value: false.
   */
  var iconOptional: Boolean?
    /**
     * Get the IconOptional property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.iconOptional
    }
    /**
     * Set the IconOptional property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-optional").value
      }
      setLayerProperty(wrappedValue, "icon-optional")
    }

  /**
   * The IconPadding property
   *
   * Size of the additional area around the icon bounding box used for detecting symbol collisions. Default value: 2. Minimum value: 0. The unit of iconPadding is in pixels.
   */
  var iconPadding: Double?
    /**
     * Get the IconPadding property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.iconPadding
    }
    /**
     * Set the IconPadding property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-padding").value
      }
      setLayerProperty(wrappedValue, "icon-padding")
    }

  /**
   * The IconPitchAlignment property
   *
   * Orientation of icon when map is pitched. Default value: "auto".
   */
  var iconPitchAlignment: IconPitchAlignment?
    /**
     * Get the IconPitchAlignment property
     *
     * @return property wrapper value around IconPitchAlignment
     */
    get(): IconPitchAlignment? {
      return layer.iconPitchAlignment
    }
    /**
     * Set the IconPitchAlignment property
     * @param value property wrapper value around IconPitchAlignment
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-pitch-alignment").value
      }
      setLayerProperty(wrappedValue, "icon-pitch-alignment")
    }

  /**
   * The default iconRotate for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Rotates the icon clockwise. Default value: 0. The unit of iconRotate is in degrees.
   */
  var iconRotate: Double?
    /**
     * Get the iconRotate property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the iconRotate property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_ICON_ROTATE, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_ROTATE)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_ROTATE)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The IconRotationAlignment property
   *
   * In combination with `symbol-placement`, determines the rotation behavior of icons. Default value: "auto".
   */
  var iconRotationAlignment: IconRotationAlignment?
    /**
     * Get the IconRotationAlignment property
     *
     * @return property wrapper value around IconRotationAlignment
     */
    get(): IconRotationAlignment? {
      return layer.iconRotationAlignment
    }
    /**
     * Set the IconRotationAlignment property
     * @param value property wrapper value around IconRotationAlignment
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-rotation-alignment").value
      }
      setLayerProperty(wrappedValue, "icon-rotation-alignment")
    }

  /**
   * The default iconSize for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image. Default value: 1. Minimum value: 0. The unit of iconSize is in factor of the original icon size.
   */
  var iconSize: Double?
    /**
     * Get the iconSize property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the iconSize property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_ICON_SIZE, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_SIZE)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_SIZE)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The IconSizeScaleRange property
   *
   * Defines the minimum and maximum scaling factors for icon related properties like `icon-size`, `icon-halo-width`, `icon-halo-blur` Default value: [0.8,2]. Value range: [0.1, 10]
   */
  @MapboxExperimental
  var iconSizeScaleRange: List<Double>?
    /**
     * Get the IconSizeScaleRange property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer.iconSizeScaleRange
    }
    /**
     * Set the IconSizeScaleRange property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-size-scale-range").value
      }
      setLayerProperty(wrappedValue, "icon-size-scale-range")
    }

  /**
   * The default iconTextFit for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Scales the icon to fit around the associated text. Default value: "none".
   */
  var iconTextFit: IconTextFit?
    /**
     * Get the iconTextFit property.
     *
     * @return property wrapper value around IconTextFit
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT)
      value?.let {
        return IconTextFit.valueOf(it.asString.uppercase(Locale.US))
      }
      return null
    }
    /**
     * Set the iconTextFit property.
     *
     * @param value constant property value for IconTextFit
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT, value.value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default iconTextFitPadding for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left. Default value: [0,0,0,0]. The unit of iconTextFitPadding is in pixels.
   */
  var iconTextFitPadding: List<Double>?
    /**
     * Get the iconTextFitPadding property.
     *
     * @return [Point] value for List<Double>
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING)
      return (value as? JsonArray)?.map { it.toString().toDouble() }
    }
    /**
     * Set the iconTextFitPadding property.
     */
    set(value) {
      if (!value.isNullOrEmpty()) {
        val jsonArray = JsonArray(value.size)
        value.forEach { jsonArray.add(it) }
        dataDrivenPropertyDefaultValues.add(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING, jsonArray)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The SymbolAvoidEdges property
   *
   * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries. Default value: false.
   */
  var symbolAvoidEdges: Boolean?
    /**
     * Get the SymbolAvoidEdges property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.symbolAvoidEdges
    }
    /**
     * Set the SymbolAvoidEdges property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-avoid-edges").value
      }
      setLayerProperty(wrappedValue, "symbol-avoid-edges")
    }

  /**
   * The SymbolElevationReference property
   *
   * Selects the base of symbol-elevation. Default value: "ground".
   */
  @MapboxExperimental
  var symbolElevationReference: SymbolElevationReference?
    /**
     * Get the SymbolElevationReference property
     *
     * @return property wrapper value around SymbolElevationReference
     */
    get(): SymbolElevationReference? {
      return layer.symbolElevationReference
    }
    /**
     * Set the SymbolElevationReference property
     * @param value property wrapper value around SymbolElevationReference
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-elevation-reference").value
      }
      setLayerProperty(wrappedValue, "symbol-elevation-reference")
    }

  /**
   * The SymbolPlacement property
   *
   * Label placement relative to its geometry. Default value: "point".
   */
  var symbolPlacement: SymbolPlacement?
    /**
     * Get the SymbolPlacement property
     *
     * @return property wrapper value around SymbolPlacement
     */
    get(): SymbolPlacement? {
      return layer.symbolPlacement
    }
    /**
     * Set the SymbolPlacement property
     * @param value property wrapper value around SymbolPlacement
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-placement").value
      }
      setLayerProperty(wrappedValue, "symbol-placement")
    }

  /**
   * The default symbolSortKey for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first. When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
   */
  var symbolSortKey: Double?
    /**
     * Get the symbolSortKey property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the symbolSortKey property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The SymbolSpacing property
   *
   * Distance between two symbol anchors. Default value: 250. Minimum value: 1. The unit of symbolSpacing is in pixels.
   */
  var symbolSpacing: Double?
    /**
     * Get the SymbolSpacing property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.symbolSpacing
    }
    /**
     * Set the SymbolSpacing property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-spacing").value
      }
      setLayerProperty(wrappedValue, "symbol-spacing")
    }

  /**
   * The SymbolZElevate property
   *
   * Position symbol on buildings (both fill extrusions and models) rooftops. In order to have minimal impact on performance, this is supported only when `fill-extrusion-height` is not zoom-dependent and remains unchanged. For fading in buildings when zooming in, fill-extrusion-vertical-scale should be used and symbols would raise with building rooftops. Symbols are sorted by elevation, except in cases when `viewport-y` sorting or `symbol-sort-key` are applied. Default value: false.
   */
  var symbolZElevate: Boolean?
    /**
     * Get the SymbolZElevate property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.symbolZElevate
    }
    /**
     * Set the SymbolZElevate property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-z-elevate").value
      }
      setLayerProperty(wrappedValue, "symbol-z-elevate")
    }

  /**
   * The SymbolZOrder property
   *
   * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`. Default value: "auto".
   */
  var symbolZOrder: SymbolZOrder?
    /**
     * Get the SymbolZOrder property
     *
     * @return property wrapper value around SymbolZOrder
     */
    get(): SymbolZOrder? {
      return layer.symbolZOrder
    }
    /**
     * Set the SymbolZOrder property
     * @param value property wrapper value around SymbolZOrder
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "symbol-z-order").value
      }
      setLayerProperty(wrappedValue, "symbol-z-order")
    }

  /**
   * The TextAllowOverlap property
   *
   * If true, the text will be visible even if it collides with other previously drawn symbols. Default value: false.
   */
  var textAllowOverlap: Boolean?
    /**
     * Get the TextAllowOverlap property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.textAllowOverlap
    }
    /**
     * Set the TextAllowOverlap property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-allow-overlap").value
      }
      setLayerProperty(wrappedValue, "text-allow-overlap")
    }

  /**
   * The default textAnchor for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Part of the text placed closest to the anchor. Default value: "center".
   */
  var textAnchor: TextAnchor?
    /**
     * Get the textAnchor property.
     *
     * @return property wrapper value around TextAnchor
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)
      value?.let {
        return TextAnchor.valueOf(it.asString.uppercase(Locale.US))
      }
      return null
    }
    /**
     * Set the textAnchor property.
     *
     * @param value constant property value for TextAnchor
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR, value.value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default textField for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored. Default value: "".
   */
  var textField: String?
    /**
     * Get the textField property.
     *
     * @return property wrapper value around String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the textField property.
     *
     * @param value constant property value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_FIELD, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_FIELD)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_FIELD)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The TextFont property
   *
   * Font stack to use for displaying text.
   */
  var textFont: List<String>?
    /**
     * Get the TextFont property
     *
     * @return property wrapper value around List<String>
     */
    get(): List<String>? {
      return layer.textFont
    }
    /**
     * Set the TextFont property
     * @param value property wrapper value around List<String>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-font").value
      }
      setLayerProperty(wrappedValue, "text-font")
    }

  /**
   * The TextIgnorePlacement property
   *
   * If true, other symbols can be visible even if they collide with the text. Default value: false.
   */
  var textIgnorePlacement: Boolean?
    /**
     * Get the TextIgnorePlacement property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.textIgnorePlacement
    }
    /**
     * Set the TextIgnorePlacement property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-ignore-placement").value
      }
      setLayerProperty(wrappedValue, "text-ignore-placement")
    }

  /**
   * The default textJustify for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Text justification options. Default value: "center".
   */
  var textJustify: TextJustify?
    /**
     * Get the textJustify property.
     *
     * @return property wrapper value around TextJustify
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)
      value?.let {
        return TextJustify.valueOf(it.asString.uppercase(Locale.US))
      }
      return null
    }
    /**
     * Set the textJustify property.
     *
     * @param value constant property value for TextJustify
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY, value.value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The TextKeepUpright property
   *
   * If true, the text may be flipped vertically to prevent it from being rendered upside-down. Default value: true.
   */
  var textKeepUpright: Boolean?
    /**
     * Get the TextKeepUpright property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.textKeepUpright
    }
    /**
     * Set the TextKeepUpright property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-keep-upright").value
      }
      setLayerProperty(wrappedValue, "text-keep-upright")
    }

  /**
   * The default textLetterSpacing for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Text tracking amount. Default value: 0. The unit of textLetterSpacing is in ems.
   */
  var textLetterSpacing: Double?
    /**
     * Get the textLetterSpacing property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textLetterSpacing property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default textLineHeight for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Text leading value for multi-line text. Default value: 1.2. The unit of textLineHeight is in ems.
   */
  var textLineHeight: Double?
    /**
     * Get the textLineHeight property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textLineHeight property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The TextMaxAngle property
   *
   * Maximum angle change between adjacent characters. Default value: 45. The unit of textMaxAngle is in degrees.
   */
  var textMaxAngle: Double?
    /**
     * Get the TextMaxAngle property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.textMaxAngle
    }
    /**
     * Set the TextMaxAngle property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-max-angle").value
      }
      setLayerProperty(wrappedValue, "text-max-angle")
    }

  /**
   * The default textMaxWidth for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * The maximum line width for text wrapping. Default value: 10. Minimum value: 0. The unit of textMaxWidth is in ems.
   */
  var textMaxWidth: Double?
    /**
     * Get the textMaxWidth property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textMaxWidth property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default textOffset for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position. Default value: [0,0]. The unit of textOffset is in ems.
   */
  var textOffset: List<Double>?
    /**
     * Get the textOffset property.
     *
     * @return [Point] value for List<Double>
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)
      return (value as? JsonArray)?.map { it.toString().toDouble() }
    }
    /**
     * Set the textOffset property.
     */
    set(value) {
      if (!value.isNullOrEmpty()) {
        val jsonArray = JsonArray(value.size)
        value.forEach { jsonArray.add(it) }
        dataDrivenPropertyDefaultValues.add(PointAnnotationOptions.PROPERTY_TEXT_OFFSET, jsonArray)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The TextOptional property
   *
   * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not. Default value: false.
   */
  var textOptional: Boolean?
    /**
     * Get the TextOptional property
     *
     * @return property wrapper value around Boolean
     */
    get(): Boolean? {
      return layer.textOptional
    }
    /**
     * Set the TextOptional property
     * @param value property wrapper value around Boolean
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-optional").value
      }
      setLayerProperty(wrappedValue, "text-optional")
    }

  /**
   * The TextPadding property
   *
   * Size of the additional area around the text bounding box used for detecting symbol collisions. Default value: 2. Minimum value: 0. The unit of textPadding is in pixels.
   */
  var textPadding: Double?
    /**
     * Get the TextPadding property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.textPadding
    }
    /**
     * Set the TextPadding property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-padding").value
      }
      setLayerProperty(wrappedValue, "text-padding")
    }

  /**
   * The TextPitchAlignment property
   *
   * Orientation of text when map is pitched. Default value: "auto".
   */
  var textPitchAlignment: TextPitchAlignment?
    /**
     * Get the TextPitchAlignment property
     *
     * @return property wrapper value around TextPitchAlignment
     */
    get(): TextPitchAlignment? {
      return layer.textPitchAlignment
    }
    /**
     * Set the TextPitchAlignment property
     * @param value property wrapper value around TextPitchAlignment
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-pitch-alignment").value
      }
      setLayerProperty(wrappedValue, "text-pitch-alignment")
    }

  /**
   * The default textRadialOffset for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present. Default value: 0. The unit of textRadialOffset is in ems.
   */
  var textRadialOffset: Double?
    /**
     * Get the textRadialOffset property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textRadialOffset property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default textRotate for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Rotates the text clockwise. Default value: 0. The unit of textRotate is in degrees.
   */
  var textRotate: Double?
    /**
     * Get the textRotate property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textRotate property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_ROTATE, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The TextRotationAlignment property
   *
   * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text. Default value: "auto".
   */
  var textRotationAlignment: TextRotationAlignment?
    /**
     * Get the TextRotationAlignment property
     *
     * @return property wrapper value around TextRotationAlignment
     */
    get(): TextRotationAlignment? {
      return layer.textRotationAlignment
    }
    /**
     * Set the TextRotationAlignment property
     * @param value property wrapper value around TextRotationAlignment
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-rotation-alignment").value
      }
      setLayerProperty(wrappedValue, "text-rotation-alignment")
    }

  /**
   * The default textSize for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Font size. Default value: 16. Minimum value: 0. The unit of textSize is in pixels.
   */
  var textSize: Double?
    /**
     * Get the textSize property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textSize property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_SIZE, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_SIZE)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_SIZE)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The TextSizeScaleRange property
   *
   * Defines the minimum and maximum scaling factors for text related properties like `text-size`, `text-max-width`, `text-halo-width`, `font-size` Default value: [0.8,2]. Value range: [0.1, 10]
   */
  @MapboxExperimental
  var textSizeScaleRange: List<Double>?
    /**
     * Get the TextSizeScaleRange property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer.textSizeScaleRange
    }
    /**
     * Set the TextSizeScaleRange property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-size-scale-range").value
      }
      setLayerProperty(wrappedValue, "text-size-scale-range")
    }

  /**
   * The default textTransform for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Specifies how to capitalize text, similar to the CSS `text-transform` property. Default value: "none".
   */
  var textTransform: TextTransform?
    /**
     * Get the textTransform property.
     *
     * @return property wrapper value around TextTransform
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)
      value?.let {
        return TextTransform.valueOf(it.asString.uppercase(Locale.US))
      }
      return null
    }
    /**
     * Set the textTransform property.
     *
     * @param value constant property value for TextTransform
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM, value.value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The TextVariableAnchor property
   *
   * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
   */
  var textVariableAnchor: List<String>?
    /**
     * Get the TextVariableAnchor property
     *
     * @return property wrapper value around List<String>
     */
    get(): List<String>? {
      return layer.textVariableAnchor
    }
    /**
     * Set the TextVariableAnchor property
     * @param value property wrapper value around List<String>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-variable-anchor").value
      }
      setLayerProperty(wrappedValue, "text-variable-anchor")
    }

  /**
   * The TextWritingMode property
   *
   * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
   */
  var textWritingMode: List<String>?
    /**
     * Get the TextWritingMode property
     *
     * @return property wrapper value around List<String>
     */
    get(): List<String>? {
      return layer.textWritingMode
    }
    /**
     * Set the TextWritingMode property
     * @param value property wrapper value around List<String>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-writing-mode").value
      }
      setLayerProperty(wrappedValue, "text-writing-mode")
    }

  /**
   * The default iconColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color int.
   *
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). Default value: "#000000".
   */
  var iconColorInt: Int?
    /**
     * Get the iconColor property.
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the iconColor property.
     *
     * @param value color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(
          PointAnnotationOptions.PROPERTY_ICON_COLOR, ColorUtils.colorToRgbaString(value)
        )
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default iconColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color string.
   *
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). Default value: "#000000".
   */
  var iconColorString: String?
    /**
     * Get the iconColor property.
     * @return color value for String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the iconColor property.
     *
     * @param value color value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_ICON_COLOR, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The IconColorSaturation property
   *
   * Increase or reduce the saturation of the symbol icon. Default value: 0. Value range: [-1, 1]
   */
  var iconColorSaturation: Double?
    /**
     * Get the IconColorSaturation property
     *
     * @return property wrapper value around Double
     */
    get(): Double? {
      return layer.iconColorSaturation
    }
    /**
     * Set the IconColorSaturation property
     * @param value property wrapper value around Double
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-color-saturation").value
      }
      setLayerProperty(wrappedValue, "icon-color-saturation")
    }

  /**
   * The default iconEmissiveStrength for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Controls the intensity of light emitted on the source features. Default value: 1. Minimum value: 0. The unit of iconEmissiveStrength is in intensity.
   */
  var iconEmissiveStrength: Double?
    /**
     * Get the iconEmissiveStrength property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the iconEmissiveStrength property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default iconHaloBlur for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Fade out the halo towards the outside. Default value: 0. Minimum value: 0. The unit of iconHaloBlur is in pixels.
   */
  var iconHaloBlur: Double?
    /**
     * Get the iconHaloBlur property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the iconHaloBlur property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default iconHaloColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color int.
   *
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). Default value: "rgba(0, 0, 0, 0)".
   */
  var iconHaloColorInt: Int?
    /**
     * Get the iconHaloColor property.
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the iconHaloColor property.
     *
     * @param value color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(
          PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR, ColorUtils.colorToRgbaString(value)
        )
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default iconHaloColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color string.
   *
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). Default value: "rgba(0, 0, 0, 0)".
   */
  var iconHaloColorString: String?
    /**
     * Get the iconHaloColor property.
     * @return color value for String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the iconHaloColor property.
     *
     * @param value color value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default iconHaloWidth for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Distance of halo to the icon outline. Default value: 0. Minimum value: 0. The unit of iconHaloWidth is in pixels.
   */
  var iconHaloWidth: Double?
    /**
     * Get the iconHaloWidth property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the iconHaloWidth property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default iconImageCrossFade for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Controls the transition progress between the image variants of icon-image. Zero means the first variant is used, one is the second, and in between they are blended together. Default value: 0. Value range: [0, 1]
   */
  var iconImageCrossFade: Double?
    /**
     * Get the iconImageCrossFade property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE_CROSS_FADE)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the iconImageCrossFade property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_ICON_IMAGE_CROSS_FADE, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_IMAGE_CROSS_FADE)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_IMAGE_CROSS_FADE)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default iconOcclusionOpacity for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * The opacity at which the icon will be drawn in case of being depth occluded. Absent value means full occlusion against terrain only. Default value: 0. Value range: [0, 1]
   */
  var iconOcclusionOpacity: Double?
    /**
     * Get the iconOcclusionOpacity property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the iconOcclusionOpacity property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default iconOpacity for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * The opacity at which the icon will be drawn. Default value: 1. Value range: [0, 1]
   */
  var iconOpacity: Double?
    /**
     * Get the iconOpacity property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the iconOpacity property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_ICON_OPACITY, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_ICON_OPACITY)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_ICON_OPACITY)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The IconTranslate property
   *
   * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up. Default value: [0,0]. The unit of iconTranslate is in pixels.
   */
  var iconTranslate: List<Double>?
    /**
     * Get the IconTranslate property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer.iconTranslate
    }
    /**
     * Set the IconTranslate property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate").value
      }
      setLayerProperty(wrappedValue, "icon-translate")
    }

  /**
   * The IconTranslateAnchor property
   *
   * Controls the frame of reference for `icon-translate`. Default value: "map".
   */
  var iconTranslateAnchor: IconTranslateAnchor?
    /**
     * Get the IconTranslateAnchor property
     *
     * @return property wrapper value around IconTranslateAnchor
     */
    get(): IconTranslateAnchor? {
      return layer.iconTranslateAnchor
    }
    /**
     * Set the IconTranslateAnchor property
     * @param value property wrapper value around IconTranslateAnchor
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "icon-translate-anchor").value
      }
      setLayerProperty(wrappedValue, "icon-translate-anchor")
    }

  /**
   * The default symbolZOffset for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Specifies an uniform elevation from the ground, in meters. Default value: 0. Minimum value: 0.
   */
  @MapboxExperimental
  var symbolZOffset: Double?
    /**
     * Get the symbolZOffset property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the symbolZOffset property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default textColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color int.
   *
   * The color with which the text will be drawn. Default value: "#000000".
   */
  var textColorInt: Int?
    /**
     * Get the textColor property.
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the textColor property.
     *
     * @param value color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(
          PointAnnotationOptions.PROPERTY_TEXT_COLOR, ColorUtils.colorToRgbaString(value)
        )
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default textColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color string.
   *
   * The color with which the text will be drawn. Default value: "#000000".
   */
  var textColorString: String?
    /**
     * Get the textColor property.
     * @return color value for String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the textColor property.
     *
     * @param value color value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_COLOR, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default textEmissiveStrength for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Controls the intensity of light emitted on the source features. Default value: 1. Minimum value: 0. The unit of textEmissiveStrength is in intensity.
   */
  var textEmissiveStrength: Double?
    /**
     * Get the textEmissiveStrength property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textEmissiveStrength property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default textHaloBlur for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * The halo's fadeout distance towards the outside. Default value: 0. Minimum value: 0. The unit of textHaloBlur is in pixels.
   */
  var textHaloBlur: Double?
    /**
     * Get the textHaloBlur property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textHaloBlur property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default textHaloColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color int.
   *
   * The color of the text's halo, which helps it stand out from backgrounds. Default value: "rgba(0, 0, 0, 0)".
   */
  var textHaloColorInt: Int?
    /**
     * Get the textHaloColor property.
     * @return color value for String
     */
    @ColorInt
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)
      value?.let {
        ColorUtils.rgbaToColor(it.asString)?.let {
          return it
        }
      }
      return null
    }
    /**
     * Set the textHaloColor property.
     *
     * @param value color value for String
     */
    set(@ColorInt value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(
          PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR, ColorUtils.colorToRgbaString(value)
        )
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default textHaloColor for all annotations added to this annotation manager if not overwritten by individual annotation settings in color string.
   *
   * The color of the text's halo, which helps it stand out from backgrounds. Default value: "rgba(0, 0, 0, 0)".
   */
  var textHaloColorString: String?
    /**
     * Get the textHaloColor property.
     * @return color value for String
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)
      value?.let {
        return it.asString.toString()
      }
      return null
    }
    /**
     * Set the textHaloColor property.
     *
     * @param value color value for String
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default textHaloWidth for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size. Default value: 0. Minimum value: 0. The unit of textHaloWidth is in pixels.
   */
  var textHaloWidth: Double?
    /**
     * Get the textHaloWidth property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textHaloWidth property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default textOcclusionOpacity for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * The opacity at which the text will be drawn in case of being depth occluded. Absent value means full occlusion against terrain only. Default value: 0. Value range: [0, 1]
   */
  var textOcclusionOpacity: Double?
    /**
     * Get the textOcclusionOpacity property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textOcclusionOpacity property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The default textOpacity for all annotations added to this annotation manager if not overwritten by individual annotation settings.
   *
   * The opacity at which the text will be drawn. Default value: 1. Value range: [0, 1]
   */
  var textOpacity: Double?
    /**
     * Get the textOpacity property.
     *
     * @return property wrapper value around Double
     */
    get() {
      val value = dataDrivenPropertyDefaultValues.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)
      value?.let {
        return it.asString.toDouble()
      }
      return null
    }
    /**
     * Set the textOpacity property.
     *
     * @param value constant property value for Double
     */
    set(value) {
      if (value != null) {
        dataDrivenPropertyDefaultValues.addProperty(PointAnnotationOptions.PROPERTY_TEXT_OPACITY, value)
        enableDataDrivenProperty(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)
      } else {
        dataDrivenPropertyDefaultValues.remove(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)
      }
      // Update child annotation property if not being set.
      update(annotations)
    }

  /**
   * The TextTranslate property
   *
   * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up. Default value: [0,0]. The unit of textTranslate is in pixels.
   */
  var textTranslate: List<Double>?
    /**
     * Get the TextTranslate property
     *
     * @return property wrapper value around List<Double>
     */
    get(): List<Double>? {
      return layer.textTranslate
    }
    /**
     * Set the TextTranslate property
     * @param value property wrapper value around List<Double>
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate").value
      }
      setLayerProperty(wrappedValue, "text-translate")
    }

  /**
   * The TextTranslateAnchor property
   *
   * Controls the frame of reference for `text-translate`. Default value: "map".
   */
  var textTranslateAnchor: TextTranslateAnchor?
    /**
     * Get the TextTranslateAnchor property
     *
     * @return property wrapper value around TextTranslateAnchor
     */
    get(): TextTranslateAnchor? {
      return layer.textTranslateAnchor
    }
    /**
     * Set the TextTranslateAnchor property
     * @param value property wrapper value around TextTranslateAnchor
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "text-translate-anchor").value
      }
      setLayerProperty(wrappedValue, "text-translate-anchor")
    }

  /**
   * The Slot property
   *
   * The slot this layer is assigned to. If specified, and a slot with that name exists, it will be placed at that position in the layer order.
   */
  var slot: String?
    /**
     * Get the Slot property
     *
     * @return property wrapper value around String
     */
    get(): String? {
      return layer.slot
    }
    /**
     * Set the Slot property
     * @param value property wrapper value around String
     */
    set(value) {
      val wrappedValue = if (value != null) {
        TypeUtils.wrapToValue(value)
      } else {
        StyleManager.getStyleLayerPropertyDefaultValue("symbol", "slot").value
      }
      setLayerProperty(wrappedValue, "slot")
    }

  /**
   * The filter on the managed pointAnnotations.
   */
  override var layerFilter: Expression?
    /**
     * Get filter of the managed pointAnnotations.
     *
     * @return expression
     */
    get() = layer.filter
    /**
     * Set filter on the managed pointAnnotations.
     *
     * @param value expression
     */
    set(value) {
      value?.let {
        layer.filter(it)
        dragLayer.filter(it)
      }
    }

  init {
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_ANCHOR] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_IMAGE] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_OFFSET] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_ROTATE] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_SIZE] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_ANCHOR] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_FIELD] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_OFFSET] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_ROTATE] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_SIZE] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_COLOR] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_IMAGE_CROSS_FADE] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_ICON_OPACITY] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_COLOR] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY] = false
    dataDrivenPropertyUsageMap[PointAnnotationOptions.PROPERTY_TEXT_OPACITY] = false

    // Show all icons and texts by default.
    iconAllowOverlap = true
    textAllowOverlap = true
    iconIgnorePlacement = true
    textIgnorePlacement = true
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /** The generator for id */
    var ID_GENERATOR = AtomicLong(0)
  }
}

/**
 * Extension function to create a PointAnnotationManager instance.
 */
fun AnnotationPlugin.createPointAnnotationManager(
  annotationConfig: AnnotationConfig? = null
): PointAnnotationManager {
  return createAnnotationManager(AnnotationType.PointAnnotation, annotationConfig) as PointAnnotationManager
}