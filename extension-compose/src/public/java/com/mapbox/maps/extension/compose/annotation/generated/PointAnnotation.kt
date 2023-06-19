// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.annotation.internal.generated.PointAnnotationManagerNode
import com.mapbox.maps.extension.compose.annotation.internal.generated.PointAnnotationNode
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.internal.RootMapNode
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

/**
 * Composable function to add a [PointAnnotation] to the Map.
 *
 * @param point The Point of the pointAnnotation, which represents the location of the pointAnnotation on the map.
 * @param iconImageBitmap The bitmap image for this Symbol. Not it will not take effect if [iconImage] has been set.
 * @param iconAnchor Part of the icon placed closest to the anchor.
 * @param iconImage Name of image in sprite to use for drawing an image background.
 * @param iconOffset Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of {@link PropertyFactory#iconSize} to obtain the final offset in density-independent pixels. When combined with {@link PropertyFactory#iconRotate} the offset will be as if the rotated direction was up.
 * @param iconRotate Rotates the icon clockwise. The unit of iconRotate is in degrees.
 * @param iconSize Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by {@link PropertyFactory#iconSize}. 1 is the original size; 3 triples the size of the image. The unit of iconSize is in factor of the original icon size.
 * @param iconTextFit Scales the icon to fit around the associated text.
 * @param iconTextFitPadding Size of the additional area added to dimensions determined by {@link Property.ICON_TEXT_FIT}, in clockwise order: top, right, bottom, left. The unit of iconTextFitPadding is in density-independent pixels.
 * @param textAnchor Part of the text placed closest to the anchor.
 * @param textField Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
 * @param textJustify Text justification options.
 * @param textLetterSpacing Text tracking amount. The unit of textLetterSpacing is in ems.
 * @param textLineHeight Text leading value for multi-line text. The unit of textLineHeight is in ems.
 * @param textMaxWidth The maximum line width for text wrapping. The unit of textMaxWidth is in ems.
 * @param textOffset Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position. The unit of textOffset is in ems.
 * @param textRadialOffset Radial offset of text, in the direction of the symbol's anchor. Useful in combination with {@link PropertyFactory#textVariableAnchor}, which defaults to using the two-dimensional {@link PropertyFactory#textOffset} if present. The unit of textRadialOffset is in ems.
 * @param textRotate Rotates the text clockwise. The unit of textRotate is in degrees.
 * @param textSize Font size. The unit of textSize is in density-independent pixels.
 * @param textTransform Specifies how to capitalize text, similar to the CSS {@link PropertyFactory#textTransform} property.
 * @param iconColorInt The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). The property is set as Color Int.
 * @param iconColorString The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). The property is set as Color String.
 * @param iconEmissiveStrength Emission strength. The unit of iconEmissiveStrength is in intensity.
 * @param iconHaloBlur Fade out the halo towards the outside. The unit of iconHaloBlur is in density-independent pixels.
 * @param iconHaloColorInt The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). The property is set as Color Int.
 * @param iconHaloColorString The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). The property is set as Color String.
 * @param iconHaloWidth Distance of halo to the icon outline. The unit of iconHaloWidth is in density-independent pixels.
 * @param iconImageCrossFade Controls the transition progress between the image variants of icon-image. Zero means the first variant is used, one is the second, and in between they are blended together.
 * @param iconOpacity The opacity at which the icon will be drawn.
 * @param textColorInt The color with which the text will be drawn. The property is set as Color Int.
 * @param textColorString The color with which the text will be drawn. The property is set as Color String.
 * @param textEmissiveStrength Emission strength. The unit of textEmissiveStrength is in intensity.
 * @param textHaloBlur The halo's fadeout distance towards the outside. The unit of textHaloBlur is in density-independent pixels.
 * @param textHaloColorInt The color of the text's halo, which helps it stand out from backgrounds. The property is set as Color Int.
 * @param textHaloColorString The color of the text's halo, which helps it stand out from backgrounds. The property is set as Color String.
 * @param textHaloWidth Distance of halo to the font outline. Max text halo width is 1/4 of the font-size. The unit of textHaloWidth is in density-independent pixels.
 * @param textOpacity The opacity at which the text will be drawn.
 * @param onClick Callback to be invoked when the [PointAnnotation] is clicked. The clicked [PointAnnotation] will be passed as parameter.
 */
@Composable
@MapboxMapComposable
public fun PointAnnotation(
  point: Point,
  iconImageBitmap: Bitmap? = null,
  iconAnchor: IconAnchor? = null,
  iconImage: String? = null,
  iconOffset: List<Double>? = null,
  iconRotate: Double? = null,
  iconSize: Double? = null,
  iconTextFit: IconTextFit? = null,
  iconTextFitPadding: List<Double>? = null,
  textAnchor: TextAnchor? = null,
  textField: String? = null,
  textJustify: TextJustify? = null,
  textLetterSpacing: Double? = null,
  textLineHeight: Double? = null,
  textMaxWidth: Double? = null,
  textOffset: List<Double>? = null,
  textRadialOffset: Double? = null,
  textRotate: Double? = null,
  textSize: Double? = null,
  textTransform: TextTransform? = null,
  iconColorInt: Int? = null,
  iconColorString: String? = null,
  iconEmissiveStrength: Double? = null,
  iconHaloBlur: Double? = null,
  iconHaloColorInt: Int? = null,
  iconHaloColorString: String? = null,
  iconHaloWidth: Double? = null,
  iconImageCrossFade: Double? = null,
  iconOpacity: Double? = null,
  textColorInt: Int? = null,
  textColorString: String? = null,
  textEmissiveStrength: Double? = null,
  textHaloBlur: Double? = null,
  textHaloColorInt: Int? = null,
  textHaloColorString: String? = null,
  textHaloWidth: Double? = null,
  textOpacity: Double? = null,
  onClick: (PointAnnotation) -> Boolean = { false },
) {
  val mapApplier = currentComposer.applier as? MapApplier
    ?: throw IllegalStateException("Illegal use of PointAnnotation inside unsupported composable function")
  ComposeNode<PointAnnotationNode, MapApplier>(
    factory = {
      val annotationManager = when (val currentNode = mapApplier.current) {
        // not reachable now as we don't allow inserting annotation node under annotation cluster node.
        is PointAnnotationManagerNode -> currentNode.annotationManager
        is RootMapNode -> mapApplier.mapView.annotations.createPointAnnotationManager()
        else -> throw IllegalArgumentException("Illegal use of PointAnnotation inside an incompatible node: $currentNode.")
      }
      val annotationOptions: PointAnnotationOptions = PointAnnotationOptions()
        .withPoint(point)
      iconImageBitmap?.let {
        annotationOptions.withIconImage(it)
      }
      iconAnchor?.let {
        annotationOptions.withIconAnchor(it)
      }
      iconImage?.let {
        annotationOptions.withIconImage(it)
      }
      iconOffset?.let {
        annotationOptions.withIconOffset(it)
      }
      iconRotate?.let {
        annotationOptions.withIconRotate(it)
      }
      iconSize?.let {
        annotationOptions.withIconSize(it)
      }
      iconTextFit?.let {
        annotationOptions.withIconTextFit(it)
      }
      iconTextFitPadding?.let {
        annotationOptions.withIconTextFitPadding(it)
      }
      textAnchor?.let {
        annotationOptions.withTextAnchor(it)
      }
      textField?.let {
        annotationOptions.withTextField(it)
      }
      textJustify?.let {
        annotationOptions.withTextJustify(it)
      }
      textLetterSpacing?.let {
        annotationOptions.withTextLetterSpacing(it)
      }
      textLineHeight?.let {
        annotationOptions.withTextLineHeight(it)
      }
      textMaxWidth?.let {
        annotationOptions.withTextMaxWidth(it)
      }
      textOffset?.let {
        annotationOptions.withTextOffset(it)
      }
      textRadialOffset?.let {
        annotationOptions.withTextRadialOffset(it)
      }
      textRotate?.let {
        annotationOptions.withTextRotate(it)
      }
      textSize?.let {
        annotationOptions.withTextSize(it)
      }
      textTransform?.let {
        annotationOptions.withTextTransform(it)
      }
      iconColorInt?.let {
        annotationOptions.withIconColor(it)
      }
      iconColorString?.let {
        annotationOptions.withIconColor(it)
      }
      iconEmissiveStrength?.let {
        annotationOptions.withIconEmissiveStrength(it)
      }
      iconHaloBlur?.let {
        annotationOptions.withIconHaloBlur(it)
      }
      iconHaloColorInt?.let {
        annotationOptions.withIconHaloColor(it)
      }
      iconHaloColorString?.let {
        annotationOptions.withIconHaloColor(it)
      }
      iconHaloWidth?.let {
        annotationOptions.withIconHaloWidth(it)
      }
      iconImageCrossFade?.let {
        annotationOptions.withIconImageCrossFade(it)
      }
      iconOpacity?.let {
        annotationOptions.withIconOpacity(it)
      }
      textColorInt?.let {
        annotationOptions.withTextColor(it)
      }
      textColorString?.let {
        annotationOptions.withTextColor(it)
      }
      textEmissiveStrength?.let {
        annotationOptions.withTextEmissiveStrength(it)
      }
      textHaloBlur?.let {
        annotationOptions.withTextHaloBlur(it)
      }
      textHaloColorInt?.let {
        annotationOptions.withTextHaloColor(it)
      }
      textHaloColorString?.let {
        annotationOptions.withTextHaloColor(it)
      }
      textHaloWidth?.let {
        annotationOptions.withTextHaloWidth(it)
      }
      textOpacity?.let {
        annotationOptions.withTextOpacity(it)
      }

      val annotation = annotationManager.create(annotationOptions)
      PointAnnotationNode(annotationManager, annotation, onClick)
    },
    update = {
      update(onClick) {
        onClicked = it
      }
      update(point) {
        annotation.point = it
        annotationManager.update(annotation)
      }
      update(iconImageBitmap) {
        annotation.iconImageBitmap = it
        annotationManager.update(annotation)
      }
      update(iconAnchor) {
        annotation.iconAnchor = it
        annotationManager.update(annotation)
      }
      update(iconImage) {
        annotation.iconImage = it
        annotationManager.update(annotation)
      }
      update(iconOffset) {
        annotation.iconOffset = it
        annotationManager.update(annotation)
      }
      update(iconRotate) {
        annotation.iconRotate = it
        annotationManager.update(annotation)
      }
      update(iconSize) {
        annotation.iconSize = it
        annotationManager.update(annotation)
      }
      update(iconTextFit) {
        annotation.iconTextFit = it
        annotationManager.update(annotation)
      }
      update(iconTextFitPadding) {
        annotation.iconTextFitPadding = it
        annotationManager.update(annotation)
      }
      update(textAnchor) {
        annotation.textAnchor = it
        annotationManager.update(annotation)
      }
      update(textField) {
        annotation.textField = it
        annotationManager.update(annotation)
      }
      update(textJustify) {
        annotation.textJustify = it
        annotationManager.update(annotation)
      }
      update(textLetterSpacing) {
        annotation.textLetterSpacing = it
        annotationManager.update(annotation)
      }
      update(textLineHeight) {
        annotation.textLineHeight = it
        annotationManager.update(annotation)
      }
      update(textMaxWidth) {
        annotation.textMaxWidth = it
        annotationManager.update(annotation)
      }
      update(textOffset) {
        annotation.textOffset = it
        annotationManager.update(annotation)
      }
      update(textRadialOffset) {
        annotation.textRadialOffset = it
        annotationManager.update(annotation)
      }
      update(textRotate) {
        annotation.textRotate = it
        annotationManager.update(annotation)
      }
      update(textSize) {
        annotation.textSize = it
        annotationManager.update(annotation)
      }
      update(textTransform) {
        annotation.textTransform = it
        annotationManager.update(annotation)
      }
      update(iconColorInt) {
        annotation.iconColorInt = it
        annotationManager.update(annotation)
      }
      update(iconColorString) {
        annotation.iconColorString = it
        annotationManager.update(annotation)
      }
      update(iconEmissiveStrength) {
        annotation.iconEmissiveStrength = it
        annotationManager.update(annotation)
      }
      update(iconHaloBlur) {
        annotation.iconHaloBlur = it
        annotationManager.update(annotation)
      }
      update(iconHaloColorInt) {
        annotation.iconHaloColorInt = it
        annotationManager.update(annotation)
      }
      update(iconHaloColorString) {
        annotation.iconHaloColorString = it
        annotationManager.update(annotation)
      }
      update(iconHaloWidth) {
        annotation.iconHaloWidth = it
        annotationManager.update(annotation)
      }
      update(iconImageCrossFade) {
        annotation.iconImageCrossFade = it
        annotationManager.update(annotation)
      }
      update(iconOpacity) {
        annotation.iconOpacity = it
        annotationManager.update(annotation)
      }
      update(textColorInt) {
        annotation.textColorInt = it
        annotationManager.update(annotation)
      }
      update(textColorString) {
        annotation.textColorString = it
        annotationManager.update(annotation)
      }
      update(textEmissiveStrength) {
        annotation.textEmissiveStrength = it
        annotationManager.update(annotation)
      }
      update(textHaloBlur) {
        annotation.textHaloBlur = it
        annotationManager.update(annotation)
      }
      update(textHaloColorInt) {
        annotation.textHaloColorInt = it
        annotationManager.update(annotation)
      }
      update(textHaloColorString) {
        annotation.textHaloColorString = it
        annotationManager.update(annotation)
      }
      update(textHaloWidth) {
        annotation.textHaloWidth = it
        annotationManager.update(annotation)
      }
      update(textOpacity) {
        annotation.textOpacity = it
        annotationManager.update(annotation)
      }
    }
  )
}