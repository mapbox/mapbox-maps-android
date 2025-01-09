// This file is generated.

package com.mapbox.maps.extension.compose.annotation.generated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.annotation.IconImage
import com.mapbox.maps.extension.compose.annotation.internal.generated.PointAnnotationNode
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.IconTextFit
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.TextJustify
import com.mapbox.maps.extension.style.layers.properties.generated.TextTransform
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation

/**
 * The state holder for [PointAnnotation] properties.
 */
@Stable
public class PointAnnotationState private constructor(
  initialIconAnchor: IconAnchor?,
  initialIconImage: IconImage?,
  initialIconOffset: List<Double>?,
  initialIconRotate: Double?,
  initialIconSize: Double?,
  initialIconTextFit: IconTextFit?,
  initialIconTextFitPadding: List<Double>?,
  initialTextAnchor: TextAnchor?,
  initialTextField: String?,
  initialTextJustify: TextJustify?,
  initialTextLetterSpacing: Double?,
  initialTextLineHeight: Double?,
  initialTextMaxWidth: Double?,
  initialTextOffset: List<Double>?,
  initialTextRadialOffset: Double?,
  initialTextRotate: Double?,
  initialTextSize: Double?,
  initialTextTransform: TextTransform?,
  initialIconColor: Color?,
  initialIconEmissiveStrength: Double?,
  initialIconHaloBlur: Double?,
  initialIconHaloColor: Color?,
  initialIconHaloWidth: Double?,
  initialIconImageCrossFade: Double?,
  initialIconOcclusionOpacity: Double?,
  initialIconOpacity: Double?,
  initialSymbolZOffset: Double?,
  initialTextColor: Color?,
  initialTextEmissiveStrength: Double?,
  initialTextHaloBlur: Double?,
  initialTextHaloColor: Color?,
  initialTextHaloWidth: Double?,
  initialTextOcclusionOpacity: Double?,
  initialTextOpacity: Double?,
  initialPointAnnotationInteractionsState: PointAnnotationInteractionsState,
) {

  public constructor() : this(
    initialIconAnchor = null,
    initialIconImage = null,
    initialIconOffset = null,
    initialIconRotate = null,
    initialIconSize = null,
    initialIconTextFit = null,
    initialIconTextFitPadding = null,
    initialTextAnchor = null,
    initialTextField = null,
    initialTextJustify = null,
    initialTextLetterSpacing = null,
    initialTextLineHeight = null,
    initialTextMaxWidth = null,
    initialTextOffset = null,
    initialTextRadialOffset = null,
    initialTextRotate = null,
    initialTextSize = null,
    initialTextTransform = null,
    initialIconColor = null,
    initialIconEmissiveStrength = null,
    initialIconHaloBlur = null,
    initialIconHaloColor = null,
    initialIconHaloWidth = null,
    initialIconImageCrossFade = null,
    initialIconOcclusionOpacity = null,
    initialIconOpacity = null,
    initialSymbolZOffset = null,
    initialTextColor = null,
    initialTextEmissiveStrength = null,
    initialTextHaloBlur = null,
    initialTextHaloColor = null,
    initialTextHaloWidth = null,
    initialTextOcclusionOpacity = null,
    initialTextOpacity = null,
    initialPointAnnotationInteractionsState = PointAnnotationInteractionsState(),
)

  /**
  * All interactions with [PointAnnotation]
  */
  public var interactionsState: PointAnnotationInteractionsState by mutableStateOf(initialPointAnnotationInteractionsState)
  /**
   * Part of the icon placed closest to the anchor. Default value: "center".
   */
  public var iconAnchor: IconAnchor? by mutableStateOf(initialIconAnchor)
  /**
   * Name of image in sprite to use for drawing an image background.
   */
  public var iconImage: IconImage? by mutableStateOf(initialIconImage)
  /**
   * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up. Default value: [0,0].
   */
  public var iconOffset: List<Double>? by mutableStateOf(initialIconOffset)
  /**
   * Rotates the icon clockwise. Default value: 0. The unit of iconRotate is in degrees.
   */
  public var iconRotate: Double? by mutableStateOf(initialIconRotate)
  /**
   * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image. Default value: 1. Minimum value: 0. The unit of iconSize is in factor of the original icon size.
   */
  public var iconSize: Double? by mutableStateOf(initialIconSize)
  /**
   * Scales the icon to fit around the associated text. Default value: "none".
   */
  public var iconTextFit: IconTextFit? by mutableStateOf(initialIconTextFit)
  /**
   * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left. Default value: [0,0,0,0]. The unit of iconTextFitPadding is in pixels.
   */
  public var iconTextFitPadding: List<Double>? by mutableStateOf(initialIconTextFitPadding)
  /**
   * Part of the text placed closest to the anchor. Default value: "center".
   */
  public var textAnchor: TextAnchor? by mutableStateOf(initialTextAnchor)
  /**
   * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored. Default value: "".
   */
  public var textField: String? by mutableStateOf(initialTextField)
  /**
   * Text justification options. Default value: "center".
   */
  public var textJustify: TextJustify? by mutableStateOf(initialTextJustify)
  /**
   * Text tracking amount. Default value: 0. The unit of textLetterSpacing is in ems.
   */
  public var textLetterSpacing: Double? by mutableStateOf(initialTextLetterSpacing)
  /**
   * Text leading value for multi-line text. Default value: 1.2. The unit of textLineHeight is in ems.
   */
  public var textLineHeight: Double? by mutableStateOf(initialTextLineHeight)
  /**
   * The maximum line width for text wrapping. Default value: 10. Minimum value: 0. The unit of textMaxWidth is in ems.
   */
  public var textMaxWidth: Double? by mutableStateOf(initialTextMaxWidth)
  /**
   * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position. Default value: [0,0]. The unit of textOffset is in ems.
   */
  public var textOffset: List<Double>? by mutableStateOf(initialTextOffset)
  /**
   * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present. Default value: 0. The unit of textRadialOffset is in ems.
   */
  public var textRadialOffset: Double? by mutableStateOf(initialTextRadialOffset)
  /**
   * Rotates the text clockwise. Default value: 0. The unit of textRotate is in degrees.
   */
  public var textRotate: Double? by mutableStateOf(initialTextRotate)
  /**
   * Font size. Default value: 16. Minimum value: 0. The unit of textSize is in pixels.
   */
  public var textSize: Double? by mutableStateOf(initialTextSize)
  /**
   * Specifies how to capitalize text, similar to the CSS `text-transform` property. Default value: "none".
   */
  public var textTransform: TextTransform? by mutableStateOf(initialTextTransform)
  /**
   * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). Default value: "#000000".
   */
  public var iconColor: Color? by mutableStateOf(initialIconColor)
  /**
   * Controls the intensity of light emitted on the source features. Default value: 1. Minimum value: 0. The unit of iconEmissiveStrength is in intensity.
   */
  public var iconEmissiveStrength: Double? by mutableStateOf(initialIconEmissiveStrength)
  /**
   * Fade out the halo towards the outside. Default value: 0. Minimum value: 0. The unit of iconHaloBlur is in pixels.
   */
  public var iconHaloBlur: Double? by mutableStateOf(initialIconHaloBlur)
  /**
   * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/). Default value: "rgba(0, 0, 0, 0)".
   */
  public var iconHaloColor: Color? by mutableStateOf(initialIconHaloColor)
  /**
   * Distance of halo to the icon outline. Default value: 0. Minimum value: 0. The unit of iconHaloWidth is in pixels.
   */
  public var iconHaloWidth: Double? by mutableStateOf(initialIconHaloWidth)
  /**
   * Controls the transition progress between the image variants of icon-image. Zero means the first variant is used, one is the second, and in between they are blended together. Default value: 0. Value range: [0, 1]
   */
  public var iconImageCrossFade: Double? by mutableStateOf(initialIconImageCrossFade)
  /**
   * The opacity at which the icon will be drawn in case of being depth occluded. Absent value means full occlusion against terrain only. Default value: 0. Value range: [0, 1]
   */
  public var iconOcclusionOpacity: Double? by mutableStateOf(initialIconOcclusionOpacity)
  /**
   * The opacity at which the icon will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var iconOpacity: Double? by mutableStateOf(initialIconOpacity)
  /**
   * Specifies an uniform elevation from the ground, in meters. Default value: 0. Minimum value: 0.
   */
  @MapboxExperimental
  public var symbolZOffset: Double? by mutableStateOf(initialSymbolZOffset)
  /**
   * The color with which the text will be drawn. Default value: "#000000".
   */
  public var textColor: Color? by mutableStateOf(initialTextColor)
  /**
   * Controls the intensity of light emitted on the source features. Default value: 1. Minimum value: 0. The unit of textEmissiveStrength is in intensity.
   */
  public var textEmissiveStrength: Double? by mutableStateOf(initialTextEmissiveStrength)
  /**
   * The halo's fadeout distance towards the outside. Default value: 0. Minimum value: 0. The unit of textHaloBlur is in pixels.
   */
  public var textHaloBlur: Double? by mutableStateOf(initialTextHaloBlur)
  /**
   * The color of the text's halo, which helps it stand out from backgrounds. Default value: "rgba(0, 0, 0, 0)".
   */
  public var textHaloColor: Color? by mutableStateOf(initialTextHaloColor)
  /**
   * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size. Default value: 0. Minimum value: 0. The unit of textHaloWidth is in pixels.
   */
  public var textHaloWidth: Double? by mutableStateOf(initialTextHaloWidth)
  /**
   * The opacity at which the text will be drawn in case of being depth occluded. Absent value means full occlusion against terrain only. Default value: 0. Value range: [0, 1]
   */
  public var textOcclusionOpacity: Double? by mutableStateOf(initialTextOcclusionOpacity)
  /**
   * The opacity at which the text will be drawn. Default value: 1. Value range: [0, 1]
   */
  public var textOpacity: Double? by mutableStateOf(initialTextOpacity)

  @Composable
  private fun UpdateIconAnchor(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.iconAnchor = iconAnchor
      }
    )
  }
  @Composable
  private fun UpdateIconImage(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        iconImage?.let { iconImage ->
          iconImage.bitmap?.let {
            annotation.iconImageBitmap = it
          }
          iconImage.imageId?.let {
            annotation.iconImage = it
          }
        }
      }
    )
  }
  @Composable
  private fun UpdateIconOffset(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.iconOffset = iconOffset
      }
    )
  }
  @Composable
  private fun UpdateIconRotate(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.iconRotate = iconRotate
      }
    )
  }
  @Composable
  private fun UpdateIconSize(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.iconSize = iconSize
      }
    )
  }
  @Composable
  private fun UpdateIconTextFit(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.iconTextFit = iconTextFit
      }
    )
  }
  @Composable
  private fun UpdateIconTextFitPadding(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.iconTextFitPadding = iconTextFitPadding
      }
    )
  }
  @Composable
  private fun UpdateTextAnchor(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textAnchor = textAnchor
      }
    )
  }
  @Composable
  private fun UpdateTextField(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textField = textField
      }
    )
  }
  @Composable
  private fun UpdateTextJustify(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textJustify = textJustify
      }
    )
  }
  @Composable
  private fun UpdateTextLetterSpacing(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textLetterSpacing = textLetterSpacing
      }
    )
  }
  @Composable
  private fun UpdateTextLineHeight(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textLineHeight = textLineHeight
      }
    )
  }
  @Composable
  private fun UpdateTextMaxWidth(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textMaxWidth = textMaxWidth
      }
    )
  }
  @Composable
  private fun UpdateTextOffset(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textOffset = textOffset
      }
    )
  }
  @Composable
  private fun UpdateTextRadialOffset(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textRadialOffset = textRadialOffset
      }
    )
  }
  @Composable
  private fun UpdateTextRotate(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textRotate = textRotate
      }
    )
  }
  @Composable
  private fun UpdateTextSize(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textSize = textSize
      }
    )
  }
  @Composable
  private fun UpdateTextTransform(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textTransform = textTransform
      }
    )
  }
  @Composable
  private fun UpdateIconColor(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.iconColorInt = iconColor?.toArgb()
      }
    )
  }
  @Composable
  private fun UpdateIconEmissiveStrength(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.iconEmissiveStrength = iconEmissiveStrength
      }
    )
  }
  @Composable
  private fun UpdateIconHaloBlur(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.iconHaloBlur = iconHaloBlur
      }
    )
  }
  @Composable
  private fun UpdateIconHaloColor(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.iconHaloColorInt = iconHaloColor?.toArgb()
      }
    )
  }
  @Composable
  private fun UpdateIconHaloWidth(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.iconHaloWidth = iconHaloWidth
      }
    )
  }
  @Composable
  private fun UpdateIconImageCrossFade(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.iconImageCrossFade = iconImageCrossFade
      }
    )
  }
  @Composable
  private fun UpdateIconOcclusionOpacity(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.iconOcclusionOpacity = iconOcclusionOpacity
      }
    )
  }
  @Composable
  private fun UpdateIconOpacity(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.iconOpacity = iconOpacity
      }
    )
  }
  @Composable
  private fun UpdateSymbolZOffset(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.symbolZOffset = symbolZOffset
      }
    )
  }
  @Composable
  private fun UpdateTextColor(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textColorInt = textColor?.toArgb()
      }
    )
  }
  @Composable
  private fun UpdateTextEmissiveStrength(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textEmissiveStrength = textEmissiveStrength
      }
    )
  }
  @Composable
  private fun UpdateTextHaloBlur(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textHaloBlur = textHaloBlur
      }
    )
  }
  @Composable
  private fun UpdateTextHaloColor(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textHaloColorInt = textHaloColor?.toArgb()
      }
    )
  }
  @Composable
  private fun UpdateTextHaloWidth(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textHaloWidth = textHaloWidth
      }
    )
  }
  @Composable
  private fun UpdateTextOcclusionOpacity(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textOcclusionOpacity = textOcclusionOpacity
      }
    )
  }
  @Composable
  private fun UpdateTextOpacity(
    annotationNode: PointAnnotationNode
  ) {
    annotationNode.annotationManager.update(
      annotationNode.annotation.also { annotation ->
        annotation.textOpacity = textOpacity
      }
    )
  }

  @Composable
  internal fun UpdateProperties(
    annotationNode: PointAnnotationNode,
  ) {
    UpdateIconAnchor(annotationNode)
    UpdateIconImage(annotationNode)
    UpdateIconOffset(annotationNode)
    UpdateIconRotate(annotationNode)
    UpdateIconSize(annotationNode)
    UpdateIconTextFit(annotationNode)
    UpdateIconTextFitPadding(annotationNode)
    UpdateTextAnchor(annotationNode)
    UpdateTextField(annotationNode)
    UpdateTextJustify(annotationNode)
    UpdateTextLetterSpacing(annotationNode)
    UpdateTextLineHeight(annotationNode)
    UpdateTextMaxWidth(annotationNode)
    UpdateTextOffset(annotationNode)
    UpdateTextRadialOffset(annotationNode)
    UpdateTextRotate(annotationNode)
    UpdateTextSize(annotationNode)
    UpdateTextTransform(annotationNode)
    UpdateIconColor(annotationNode)
    UpdateIconEmissiveStrength(annotationNode)
    UpdateIconHaloBlur(annotationNode)
    UpdateIconHaloColor(annotationNode)
    UpdateIconHaloWidth(annotationNode)
    UpdateIconImageCrossFade(annotationNode)
    UpdateIconOcclusionOpacity(annotationNode)
    UpdateIconOpacity(annotationNode)
    UpdateSymbolZOffset(annotationNode)
    UpdateTextColor(annotationNode)
    UpdateTextEmissiveStrength(annotationNode)
    UpdateTextHaloBlur(annotationNode)
    UpdateTextHaloColor(annotationNode)
    UpdateTextHaloWidth(annotationNode)
    UpdateTextOcclusionOpacity(annotationNode)
    UpdateTextOpacity(annotationNode)
  }
}

// End of generated file