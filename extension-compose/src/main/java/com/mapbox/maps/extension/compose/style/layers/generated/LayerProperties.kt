// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Immutable
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.internal.ComposeTypeUtils
import com.mapbox.maps.extension.style.expressions.generated.Expression

/**
 * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillSortKey(public val value: Value) {
  /**
   * Construct the FillSortKey with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillSortKey with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-sort-key"

    /**
     * Default value for [FillSortKey], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillSortKey = FillSortKey(Value.nullValue())
  }
}

/**
 * Whether or not the fill should be antialiased.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillAntialias(public val value: Value) {
  /**
   * Construct the FillAntialias with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillAntialias with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-antialias"

    /**
     * Default value for [FillAntialias], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillAntialias = FillAntialias(Value.nullValue())
  }
}

/**
 * The color of the filled part of this layer. This color can be specified as `rgba` with an alpha component and the color's opacity will not affect the opacity of the 1px stroke, if it is used.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillColor(public val value: Value) {
  /**
   * Construct the FillColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillColor = FillColor(Value.nullValue())
  }
}

/**
 * Controls the intensity of light emitted on the source features.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillEmissiveStrength(public val value: Value) {
  /**
   * Construct the FillEmissiveStrength with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillEmissiveStrength with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-emissive-strength"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillEmissiveStrength], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillEmissiveStrength = FillEmissiveStrength(Value.nullValue())
  }
}

/**
 * The opacity of the entire fill layer. In contrast to the `fill-color`, this value will also affect the 1px stroke around the fill, if the stroke is used.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillOpacity(public val value: Value) {
  /**
   * Construct the FillOpacity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillOpacity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-opacity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillOpacity], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillOpacity = FillOpacity(Value.nullValue())
  }
}

/**
 * The outline color of the fill. Matches the value of `fill-color` if unspecified.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillOutlineColor(public val value: Value) {
  /**
   * Construct the FillOutlineColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillOutlineColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-outline-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillOutlineColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillOutlineColor = FillOutlineColor(Value.nullValue())
  }
}

/**
 * Name of image in sprite to use for drawing image fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillPattern(public val value: Value) {
  /**
   * Construct the FillPattern with [String].
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillPattern with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-pattern"

    /**
     * Default value for [FillPattern], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillPattern = FillPattern(Value.nullValue())
  }
}

/**
 * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillTranslate(public val value: Value) {
  /**
   * Construct the FillTranslate with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillTranslate with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-translate"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillTranslate], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillTranslate = FillTranslate(Value.nullValue())
  }
}

/**
 * Controls the frame of reference for `fill-translate`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillTranslateAnchor(public val value: Value) {
  /**
   * Construct the FillTranslateAnchor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-translate-anchor"

    /**
     * Default value for [FillTranslateAnchor], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillTranslateAnchor = FillTranslateAnchor(Value.nullValue())

    /**
     * The fill is translated relative to the map.
     */
    @JvmField
    public val MAP: FillTranslateAnchor = FillTranslateAnchor(Value("map"))

    /**
     * The fill is translated relative to the viewport.
     */
    @JvmField
    public val VIEWPORT: FillTranslateAnchor = FillTranslateAnchor(Value("viewport"))
  }
}

/**
 * Whether this layer is displayed.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Visibility(public val value: Value) {
  /**
   * Construct the Visibility with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "visibility"

    /**
     * Default value for [Visibility], setting default will result in restoring the property value defined in the style.
     */
    public val default: Visibility = Visibility(Value.nullValue())

    /**
     * The layer is shown.
     */
    @JvmField
    public val VISIBLE: Visibility = Visibility(Value("visible"))

    /**
     * The layer is not shown.
     */
    @JvmField
    public val NONE: Visibility = Visibility(Value("none"))
  }
}

/**
 * The minimum zoom level for the layer. At zoom levels less than the minzoom, the layer will be hidden.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class MinZoom(public val value: Value) {
  /**
   * Construct the MinZoom with [Long].
   */
  public constructor(value: Long) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the MinZoom with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "min-zoom"

    /**
     * Default value for [MinZoom], setting default will result in restoring the property value defined in the style.
     */
    public val default: MinZoom = MinZoom(Value.nullValue())
  }
}

/**
 * The maximum zoom level for the layer. At zoom levels equal to or greater than the maxzoom, the layer will be hidden.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class MaxZoom(public val value: Value) {
  /**
   * Construct the MaxZoom with [Long].
   */
  public constructor(value: Long) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the MaxZoom with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "max-zoom"

    /**
     * Default value for [MaxZoom], setting default will result in restoring the property value defined in the style.
     */
    public val default: MaxZoom = MaxZoom(Value.nullValue())
  }
}

/**
 * Layer to use from a vector tile source. Required for vector tile sources; prohibited for all other source types, including GeoJSON sources.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SourceLayer(public val value: Value) {
  /**
   * Construct the SourceLayer with [String].
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the SourceLayer with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "source-layer"

    /**
     * Default value for [SourceLayer], setting default will result in restoring the property value defined in the style.
     */
    public val default: SourceLayer = SourceLayer(Value.nullValue())
  }
}

/**
 * An expression specifying conditions on source features. Only features that match the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom levels. The `["feature-state", ...]` expression is not supported in filter expressions. The `["pitch"]` and `["distance-from-center"]` expressions are supported only for filter expressions on the symbol layer.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Filter(public val value: Value) {
  /**
   * Construct the Filter with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "filter"

    /**
     * Default value for [Filter], setting default will result in restoring the property value defined in the style.
     */
    public val default: Filter = Filter(Value.nullValue())
  }
}

/**
 * The display of line endings.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineCap(public val value: Value) {
  /**
   * Construct the LineCap with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-cap"

    /**
     * Default value for [LineCap], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineCap = LineCap(Value.nullValue())

    /**
     * A cap with a squared-off end which is drawn to the exact endpoint of the line.
     */
    @JvmField
    public val BUTT: LineCap = LineCap(Value("butt"))

    /**
     * A cap with a rounded end which is drawn beyond the endpoint of the line at a radius of one-half of the line's width and centered on the endpoint of the line.
     */
    @JvmField
    public val ROUND: LineCap = LineCap(Value("round"))

    /**
     * A cap with a squared-off end which is drawn beyond the endpoint of the line at a distance of one-half of the line's width.
     */
    @JvmField
    public val SQUARE: LineCap = LineCap(Value("square"))
  }
}

/**
 * The display of lines when joining.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineJoin(public val value: Value) {
  /**
   * Construct the LineJoin with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-join"

    /**
     * Default value for [LineJoin], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineJoin = LineJoin(Value.nullValue())

    /**
     * A join with a squared-off end which is drawn beyond the endpoint of the line at a distance of one-half of the line's width.
     */
    @JvmField
    public val BEVEL: LineJoin = LineJoin(Value("bevel"))

    /**
     * A join with a rounded end which is drawn beyond the endpoint of the line at a radius of one-half of the line's width and centered on the endpoint of the line.
     */
    @JvmField
    public val ROUND: LineJoin = LineJoin(Value("round"))

    /**
     * A join with a sharp, angled corner which is drawn with the outer sides beyond the endpoint of the path until they meet.
     */
    @JvmField
    public val MITER: LineJoin = LineJoin(Value("miter"))
  }
}

/**
 * Used to automatically convert miter joins to bevel joins for sharp angles.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineMiterLimit(public val value: Value) {
  /**
   * Construct the LineMiterLimit with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineMiterLimit with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-miter-limit"

    /**
     * Default value for [LineMiterLimit], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineMiterLimit = LineMiterLimit(Value.nullValue())
  }
}

/**
 * Used to automatically convert round joins to miter joins for shallow angles.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineRoundLimit(public val value: Value) {
  /**
   * Construct the LineRoundLimit with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineRoundLimit with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-round-limit"

    /**
     * Default value for [LineRoundLimit], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineRoundLimit = LineRoundLimit(Value.nullValue())
  }
}

/**
 * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineSortKey(public val value: Value) {
  /**
   * Construct the LineSortKey with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineSortKey with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-sort-key"

    /**
     * Default value for [LineSortKey], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineSortKey = LineSortKey(Value.nullValue())
  }
}

/**
 * Blur applied to the line, in pixels.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineBlur(public val value: Value) {
  /**
   * Construct the LineBlur with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineBlur with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-blur"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [LineBlur], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineBlur = LineBlur(Value.nullValue())
  }
}

/**
 * The color of the line border. If line-border-width is greater than zero and the alpha value of this color is 0 (default), the color for the border will be selected automatically based on the line color.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineBorderColor(public val value: Value) {
  /**
   * Construct the LineBorderColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineBorderColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-border-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [LineBorderColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineBorderColor = LineBorderColor(Value.nullValue())
  }
}

/**
 * The width of the line border. A value of zero means no border.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineBorderWidth(public val value: Value) {
  /**
   * Construct the LineBorderWidth with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineBorderWidth with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-border-width"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [LineBorderWidth], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineBorderWidth = LineBorderWidth(Value.nullValue())
  }
}

/**
 * The color with which the line will be drawn.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineColor(public val value: Value) {
  /**
   * Construct the LineColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [LineColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineColor = LineColor(Value.nullValue())
  }
}

/**
 * Specifies the lengths of the alternating dashes and gaps that form the dash pattern. The lengths are later scaled by the line width. To convert a dash length to pixels, multiply the length by the current line width. Note that GeoJSON sources with `lineMetrics: true` specified won't render dashed lines to the expected scale. Also note that zoom-dependent expressions will be evaluated only at integer zoom levels.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineDasharray(public val value: Value) {
  /**
   * Construct the LineDasharray with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineDasharray with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-dasharray"

    /**
     * Default value for [LineDasharray], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineDasharray = LineDasharray(Value.nullValue())
  }
}

/**
 * Decrease line layer opacity based on occlusion from 3D objects. Value 0 disables occlusion, value 1 means fully occluded.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineDepthOcclusionFactor(public val value: Value) {
  /**
   * Construct the LineDepthOcclusionFactor with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineDepthOcclusionFactor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-depth-occlusion-factor"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [LineDepthOcclusionFactor], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineDepthOcclusionFactor = LineDepthOcclusionFactor(Value.nullValue())
  }
}

/**
 * Controls the intensity of light emitted on the source features.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineEmissiveStrength(public val value: Value) {
  /**
   * Construct the LineEmissiveStrength with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineEmissiveStrength with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-emissive-strength"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [LineEmissiveStrength], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineEmissiveStrength = LineEmissiveStrength(Value.nullValue())
  }
}

/**
 * Draws a line casing outside of a line's actual path. Value indicates the width of the inner gap.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineGapWidth(public val value: Value) {
  /**
   * Construct the LineGapWidth with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineGapWidth with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-gap-width"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [LineGapWidth], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineGapWidth = LineGapWidth(Value.nullValue())
  }
}

/**
 * A gradient used to color a line feature at various distances along its length. Defined using a `step` or `interpolate` expression which outputs a color for each corresponding `line-progress` input value. `line-progress` is a percentage of the line feature's total length as measured on the webmercator projected coordinate plane (a `number` between `0` and `1`). Can only be used with GeoJSON sources that specify `"lineMetrics": true`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineGradient(public val value: Value) {
  /**
   * Construct the LineGradient with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-gradient"

    /**
     * Default value for [LineGradient], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineGradient = LineGradient(Value.nullValue())
  }
}

/**
 * The line's offset. For linear features, a positive value offsets the line to the right, relative to the direction of the line, and a negative value to the left. For polygon features, a positive value results in an inset, and a negative value results in an outset.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineOffset(public val value: Value) {
  /**
   * Construct the LineOffset with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineOffset with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-offset"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [LineOffset], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineOffset = LineOffset(Value.nullValue())
  }
}

/**
 * The opacity at which the line will be drawn.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineOpacity(public val value: Value) {
  /**
   * Construct the LineOpacity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineOpacity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-opacity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [LineOpacity], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineOpacity = LineOpacity(Value.nullValue())
  }
}

/**
 * Name of image in sprite to use for drawing image lines. For seamless patterns, image width must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LinePattern(public val value: Value) {
  /**
   * Construct the LinePattern with [String].
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LinePattern with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-pattern"

    /**
     * Default value for [LinePattern], setting default will result in restoring the property value defined in the style.
     */
    public val default: LinePattern = LinePattern(Value.nullValue())
  }
}

/**
 * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineTranslate(public val value: Value) {
  /**
   * Construct the LineTranslate with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineTranslate with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-translate"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [LineTranslate], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineTranslate = LineTranslate(Value.nullValue())
  }
}

/**
 * Controls the frame of reference for `line-translate`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineTranslateAnchor(public val value: Value) {
  /**
   * Construct the LineTranslateAnchor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-translate-anchor"

    /**
     * Default value for [LineTranslateAnchor], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineTranslateAnchor = LineTranslateAnchor(Value.nullValue())

    /**
     * The line is translated relative to the map.
     */
    @JvmField
    public val MAP: LineTranslateAnchor = LineTranslateAnchor(Value("map"))

    /**
     * The line is translated relative to the viewport.
     */
    @JvmField
    public val VIEWPORT: LineTranslateAnchor = LineTranslateAnchor(Value("viewport"))
  }
}

/**
 * The line part between [trim-start, trim-end] will be marked as transparent to make a route vanishing effect. The line trim-off offset is based on the whole line range [0.0, 1.0].
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineTrimOffset(public val value: Value) {
  /**
   * Construct the LineTrimOffset with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineTrimOffset with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-trim-offset"

    /**
     * Default value for [LineTrimOffset], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineTrimOffset = LineTrimOffset(Value.nullValue())
  }
}

/**
 * Stroke thickness.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineWidth(public val value: Value) {
  /**
   * Construct the LineWidth with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LineWidth with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "line-width"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [LineWidth], setting default will result in restoring the property value defined in the style.
     */
    public val default: LineWidth = LineWidth(Value.nullValue())
  }
}

/**
 * If true, the icon will be visible even if it collides with other previously drawn symbols.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconAllowOverlap(public val value: Value) {
  /**
   * Construct the IconAllowOverlap with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconAllowOverlap with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-allow-overlap"

    /**
     * Default value for [IconAllowOverlap], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconAllowOverlap = IconAllowOverlap(Value.nullValue())
  }
}

/**
 * Part of the icon placed closest to the anchor.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconAnchor(public val value: Value) {
  /**
   * Construct the IconAnchor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-anchor"

    /**
     * Default value for [IconAnchor], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconAnchor = IconAnchor(Value.nullValue())

    /**
     * The center of the icon is placed closest to the anchor.
     */
    @JvmField
    public val CENTER: IconAnchor = IconAnchor(Value("center"))

    /**
     * The left side of the icon is placed closest to the anchor.
     */
    @JvmField
    public val LEFT: IconAnchor = IconAnchor(Value("left"))

    /**
     * The right side of the icon is placed closest to the anchor.
     */
    @JvmField
    public val RIGHT: IconAnchor = IconAnchor(Value("right"))

    /**
     * The top of the icon is placed closest to the anchor.
     */
    @JvmField
    public val TOP: IconAnchor = IconAnchor(Value("top"))

    /**
     * The bottom of the icon is placed closest to the anchor.
     */
    @JvmField
    public val BOTTOM: IconAnchor = IconAnchor(Value("bottom"))

    /**
     * The top left corner of the icon is placed closest to the anchor.
     */
    @JvmField
    public val TOP_LEFT: IconAnchor = IconAnchor(Value("top-left"))

    /**
     * The top right corner of the icon is placed closest to the anchor.
     */
    @JvmField
    public val TOP_RIGHT: IconAnchor = IconAnchor(Value("top-right"))

    /**
     * The bottom left corner of the icon is placed closest to the anchor.
     */
    @JvmField
    public val BOTTOM_LEFT: IconAnchor = IconAnchor(Value("bottom-left"))

    /**
     * The bottom right corner of the icon is placed closest to the anchor.
     */
    @JvmField
    public val BOTTOM_RIGHT: IconAnchor = IconAnchor(Value("bottom-right"))
  }
}

/**
 * If true, other symbols can be visible even if they collide with the icon.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconIgnorePlacement(public val value: Value) {
  /**
   * Construct the IconIgnorePlacement with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconIgnorePlacement with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-ignore-placement"

    /**
     * Default value for [IconIgnorePlacement], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconIgnorePlacement = IconIgnorePlacement(Value.nullValue())
  }
}

/**
 * Name of image in sprite to use for drawing an image background.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconImage(public val value: Value) {
  /**
   * Construct the IconImage with [String].
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconImage with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-image"

    /**
     * Default value for [IconImage], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconImage = IconImage(Value.nullValue())
  }
}

/**
 * If true, the icon may be flipped to prevent it from being rendered upside-down.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconKeepUpright(public val value: Value) {
  /**
   * Construct the IconKeepUpright with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconKeepUpright with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-keep-upright"

    /**
     * Default value for [IconKeepUpright], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconKeepUpright = IconKeepUpright(Value.nullValue())
  }
}

/**
 * Offset distance of icon from its anchor. Positive values indicate right and down, while negative values indicate left and up. Each component is multiplied by the value of `icon-size` to obtain the final offset in pixels. When combined with `icon-rotate` the offset will be as if the rotated direction was up.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconOffset(public val value: Value) {
  /**
   * Construct the IconOffset with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconOffset with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-offset"

    /**
     * Default value for [IconOffset], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconOffset = IconOffset(Value.nullValue())
  }
}

/**
 * If true, text will display without their corresponding icons when the icon collides with other symbols and the text does not.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconOptional(public val value: Value) {
  /**
   * Construct the IconOptional with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconOptional with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-optional"

    /**
     * Default value for [IconOptional], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconOptional = IconOptional(Value.nullValue())
  }
}

/**
 * Size of the additional area around the icon bounding box used for detecting symbol collisions.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconPadding(public val value: Value) {
  /**
   * Construct the IconPadding with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconPadding with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-padding"

    /**
     * Default value for [IconPadding], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconPadding = IconPadding(Value.nullValue())
  }
}

/**
 * Orientation of icon when map is pitched.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconPitchAlignment(public val value: Value) {
  /**
   * Construct the IconPitchAlignment with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-pitch-alignment"

    /**
     * Default value for [IconPitchAlignment], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconPitchAlignment = IconPitchAlignment(Value.nullValue())

    /**
     * The icon is aligned to the plane of the map.
     */
    @JvmField
    public val MAP: IconPitchAlignment = IconPitchAlignment(Value("map"))

    /**
     * The icon is aligned to the plane of the viewport.
     */
    @JvmField
    public val VIEWPORT: IconPitchAlignment = IconPitchAlignment(Value("viewport"))

    /**
     * Automatically matches the value of {@link ICON_ROTATION_ALIGNMENT}.
     */
    @JvmField
    public val AUTO: IconPitchAlignment = IconPitchAlignment(Value("auto"))
  }
}

/**
 * Rotates the icon clockwise.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconRotate(public val value: Value) {
  /**
   * Construct the IconRotate with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconRotate with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-rotate"

    /**
     * Default value for [IconRotate], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconRotate = IconRotate(Value.nullValue())
  }
}

/**
 * In combination with `symbol-placement`, determines the rotation behavior of icons.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconRotationAlignment(public val value: Value) {
  /**
   * Construct the IconRotationAlignment with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-rotation-alignment"

    /**
     * Default value for [IconRotationAlignment], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconRotationAlignment = IconRotationAlignment(Value.nullValue())

    /**
     * When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_POINT}, aligns icons east-west. When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_LINE} or {@link Property#SYMBOL_PLACEMENT_LINE_CENTER}, aligns icon x-axes with the line.
     */
    @JvmField
    public val MAP: IconRotationAlignment = IconRotationAlignment(Value("map"))

    /**
     * Produces icons whose x-axes are aligned with the x-axis of the viewport, regardless of the value of {@link SYMBOL_PLACEMENT}.
     */
    @JvmField
    public val VIEWPORT: IconRotationAlignment = IconRotationAlignment(Value("viewport"))

    /**
     * When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_POINT}, this is equivalent to {@link Property#ICON_ROTATION_ALIGNMENT_VIEWPORT}. When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_LINE} or {@link Property#SYMBOL_PLACEMENT_LINE_CENTER}, this is equivalent to {@link Property#ICON_ROTATION_ALIGNMENT_MAP}.
     */
    @JvmField
    public val AUTO: IconRotationAlignment = IconRotationAlignment(Value("auto"))
  }
}

/**
 * Scales the original size of the icon by the provided factor. The new pixel size of the image will be the original pixel size multiplied by `icon-size`. 1 is the original size; 3 triples the size of the image.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconSize(public val value: Value) {
  /**
   * Construct the IconSize with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconSize with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-size"

    /**
     * Default value for [IconSize], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconSize = IconSize(Value.nullValue())
  }
}

/**
 * Scales the icon to fit around the associated text.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconTextFit(public val value: Value) {
  /**
   * Construct the IconTextFit with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-text-fit"

    /**
     * Default value for [IconTextFit], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconTextFit = IconTextFit(Value.nullValue())

    /**
     * The icon is displayed at its intrinsic aspect ratio.
     */
    @JvmField
    public val NONE: IconTextFit = IconTextFit(Value("none"))

    /**
     * The icon is scaled in the x-dimension to fit the width of the text.
     */
    @JvmField
    public val WIDTH: IconTextFit = IconTextFit(Value("width"))

    /**
     * The icon is scaled in the y-dimension to fit the height of the text.
     */
    @JvmField
    public val HEIGHT: IconTextFit = IconTextFit(Value("height"))

    /**
     * The icon is scaled in both x- and y-dimensions.
     */
    @JvmField
    public val BOTH: IconTextFit = IconTextFit(Value("both"))
  }
}

/**
 * Size of the additional area added to dimensions determined by `icon-text-fit`, in clockwise order: top, right, bottom, left.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconTextFitPadding(public val value: Value) {
  /**
   * Construct the IconTextFitPadding with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconTextFitPadding with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-text-fit-padding"

    /**
     * Default value for [IconTextFitPadding], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconTextFitPadding = IconTextFitPadding(Value.nullValue())
  }
}

/**
 * If true, the symbols will not cross tile edges to avoid mutual collisions. Recommended in layers that don't have enough padding in the vector tile to prevent collisions, or if it is a point symbol layer placed after a line symbol layer. When using a client that supports global collision detection, like Mapbox GL JS version 0.42.0 or greater, enabling this property is not needed to prevent clipped labels at tile boundaries.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SymbolAvoidEdges(public val value: Value) {
  /**
   * Construct the SymbolAvoidEdges with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the SymbolAvoidEdges with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "symbol-avoid-edges"

    /**
     * Default value for [SymbolAvoidEdges], setting default will result in restoring the property value defined in the style.
     */
    public val default: SymbolAvoidEdges = SymbolAvoidEdges(Value.nullValue())
  }
}

/**
 * Label placement relative to its geometry.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SymbolPlacement(public val value: Value) {
  /**
   * Construct the SymbolPlacement with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "symbol-placement"

    /**
     * Default value for [SymbolPlacement], setting default will result in restoring the property value defined in the style.
     */
    public val default: SymbolPlacement = SymbolPlacement(Value.nullValue())

    /**
     * The label is placed at the point where the geometry is located.
     */
    @JvmField
    public val POINT: SymbolPlacement = SymbolPlacement(Value("point"))

    /**
     * The label is placed along the line of the geometry. Can only be used on LineString and Polygon geometries.
     */
    @JvmField
    public val LINE: SymbolPlacement = SymbolPlacement(Value("line"))

    /**
     * The label is placed at the center of the line of the geometry. Can only be used on LineString and Polygon geometries. Note that a single feature in a vector tile may contain multiple line geometries.
     */
    @JvmField
    public val LINE_CENTER: SymbolPlacement = SymbolPlacement(Value("line-center"))
  }
}

/**
 * Sorts features in ascending order based on this value. Features with lower sort keys are drawn and placed first. When `icon-allow-overlap` or `text-allow-overlap` is `false`, features with a lower sort key will have priority during placement. When `icon-allow-overlap` or `text-allow-overlap` is set to `true`, features with a higher sort key will overlap over features with a lower sort key.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SymbolSortKey(public val value: Value) {
  /**
   * Construct the SymbolSortKey with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the SymbolSortKey with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "symbol-sort-key"

    /**
     * Default value for [SymbolSortKey], setting default will result in restoring the property value defined in the style.
     */
    public val default: SymbolSortKey = SymbolSortKey(Value.nullValue())
  }
}

/**
 * Distance between two symbol anchors.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SymbolSpacing(public val value: Value) {
  /**
   * Construct the SymbolSpacing with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the SymbolSpacing with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "symbol-spacing"

    /**
     * Default value for [SymbolSpacing], setting default will result in restoring the property value defined in the style.
     */
    public val default: SymbolSpacing = SymbolSpacing(Value.nullValue())
  }
}

/**
 * Position symbol on buildings (both fill extrusions and models) rooftops. In order to have minimal impact on performance, this is supported only when `fill-extrusion-height` is not zoom-dependent and remains unchanged. For fading in buildings when zooming in, fill-extrusion-vertical-scale should be used and symbols would raise with building rooftops. Symbols are sorted by elevation, except in cases when `viewport-y` sorting or `symbol-sort-key` are applied.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SymbolZElevate(public val value: Value) {
  /**
   * Construct the SymbolZElevate with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the SymbolZElevate with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "symbol-z-elevate"

    /**
     * Default value for [SymbolZElevate], setting default will result in restoring the property value defined in the style.
     */
    public val default: SymbolZElevate = SymbolZElevate(Value.nullValue())
  }
}

/**
 * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SymbolZOrder(public val value: Value) {
  /**
   * Construct the SymbolZOrder with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "symbol-z-order"

    /**
     * Default value for [SymbolZOrder], setting default will result in restoring the property value defined in the style.
     */
    public val default: SymbolZOrder = SymbolZOrder(Value.nullValue())

    /**
     * Sorts symbols by symbol sort key if set. Otherwise, sorts symbols by their y-position relative to the viewport if {@link ICON_ALLOW_OVERLAP} or {@link TEXT_ALLOW_OVERLAP} is set to {@link TRUE} or {@link ICON_IGNORE_PLACEMENT} or {@link TEXT_IGNORE_PLACEMENT} is {@link FALSE}.
     */
    @JvmField
    public val AUTO: SymbolZOrder = SymbolZOrder(Value("auto"))

    /**
     * Sorts symbols by their y-position relative to the viewport if {@link ICON_ALLOW_OVERLAP} or {@link TEXT_ALLOW_OVERLAP} is set to {@link TRUE} or {@link ICON_IGNORE_PLACEMENT} or {@link TEXT_IGNORE_PLACEMENT} is {@link FALSE}.
     */
    @JvmField
    public val VIEWPORT_Y: SymbolZOrder = SymbolZOrder(Value("viewport-y"))

    /**
     * Sorts symbols by symbol sort key if set. Otherwise, no sorting is applied; symbols are rendered in the same order as the source data.
     */
    @JvmField
    public val SOURCE: SymbolZOrder = SymbolZOrder(Value("source"))
  }
}

/**
 * If true, the text will be visible even if it collides with other previously drawn symbols.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextAllowOverlap(public val value: Value) {
  /**
   * Construct the TextAllowOverlap with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextAllowOverlap with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-allow-overlap"

    /**
     * Default value for [TextAllowOverlap], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextAllowOverlap = TextAllowOverlap(Value.nullValue())
  }
}

/**
 * Part of the text placed closest to the anchor.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextAnchor(public val value: Value) {
  /**
   * Construct the TextAnchor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-anchor"

    /**
     * Default value for [TextAnchor], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextAnchor = TextAnchor(Value.nullValue())

    /**
     * The center of the text is placed closest to the anchor.
     */
    @JvmField
    public val CENTER: TextAnchor = TextAnchor(Value("center"))

    /**
     * The left side of the text is placed closest to the anchor.
     */
    @JvmField
    public val LEFT: TextAnchor = TextAnchor(Value("left"))

    /**
     * The right side of the text is placed closest to the anchor.
     */
    @JvmField
    public val RIGHT: TextAnchor = TextAnchor(Value("right"))

    /**
     * The top of the text is placed closest to the anchor.
     */
    @JvmField
    public val TOP: TextAnchor = TextAnchor(Value("top"))

    /**
     * The bottom of the text is placed closest to the anchor.
     */
    @JvmField
    public val BOTTOM: TextAnchor = TextAnchor(Value("bottom"))

    /**
     * The top left corner of the text is placed closest to the anchor.
     */
    @JvmField
    public val TOP_LEFT: TextAnchor = TextAnchor(Value("top-left"))

    /**
     * The top right corner of the text is placed closest to the anchor.
     */
    @JvmField
    public val TOP_RIGHT: TextAnchor = TextAnchor(Value("top-right"))

    /**
     * The bottom left corner of the text is placed closest to the anchor.
     */
    @JvmField
    public val BOTTOM_LEFT: TextAnchor = TextAnchor(Value("bottom-left"))

    /**
     * The bottom right corner of the text is placed closest to the anchor.
     */
    @JvmField
    public val BOTTOM_RIGHT: TextAnchor = TextAnchor(Value("bottom-right"))
  }
}

/**
 * Value to use for a text label. If a plain `string` is provided, it will be treated as a `formatted` with default/inherited formatting options. SDF images are not supported in formatted text and will be ignored.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextField(public val value: Value) {
  /**
   * Construct the TextField with [String].
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextField with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-field"

    /**
     * Default value for [TextField], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextField = TextField(Value.nullValue())
  }
}

/**
 * Font stack to use for displaying text.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextFont(public val value: Value) {
  /**
   * Construct the TextFont with [List<String>].
   */
  public constructor(value: List<String>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextFont with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-font"

    /**
     * Default value for [TextFont], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextFont = TextFont(Value.nullValue())
  }
}

/**
 * If true, other symbols can be visible even if they collide with the text.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextIgnorePlacement(public val value: Value) {
  /**
   * Construct the TextIgnorePlacement with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextIgnorePlacement with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-ignore-placement"

    /**
     * Default value for [TextIgnorePlacement], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextIgnorePlacement = TextIgnorePlacement(Value.nullValue())
  }
}

/**
 * Text justification options.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextJustify(public val value: Value) {
  /**
   * Construct the TextJustify with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-justify"

    /**
     * Default value for [TextJustify], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextJustify = TextJustify(Value.nullValue())

    /**
     * The text is aligned towards the anchor position.
     */
    @JvmField
    public val AUTO: TextJustify = TextJustify(Value("auto"))

    /**
     * The text is aligned to the left.
     */
    @JvmField
    public val LEFT: TextJustify = TextJustify(Value("left"))

    /**
     * The text is centered.
     */
    @JvmField
    public val CENTER: TextJustify = TextJustify(Value("center"))

    /**
     * The text is aligned to the right.
     */
    @JvmField
    public val RIGHT: TextJustify = TextJustify(Value("right"))
  }
}

/**
 * If true, the text may be flipped vertically to prevent it from being rendered upside-down.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextKeepUpright(public val value: Value) {
  /**
   * Construct the TextKeepUpright with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextKeepUpright with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-keep-upright"

    /**
     * Default value for [TextKeepUpright], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextKeepUpright = TextKeepUpright(Value.nullValue())
  }
}

/**
 * Text tracking amount.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextLetterSpacing(public val value: Value) {
  /**
   * Construct the TextLetterSpacing with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextLetterSpacing with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-letter-spacing"

    /**
     * Default value for [TextLetterSpacing], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextLetterSpacing = TextLetterSpacing(Value.nullValue())
  }
}

/**
 * Text leading value for multi-line text.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextLineHeight(public val value: Value) {
  /**
   * Construct the TextLineHeight with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextLineHeight with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-line-height"

    /**
     * Default value for [TextLineHeight], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextLineHeight = TextLineHeight(Value.nullValue())
  }
}

/**
 * Maximum angle change between adjacent characters.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextMaxAngle(public val value: Value) {
  /**
   * Construct the TextMaxAngle with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextMaxAngle with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-max-angle"

    /**
     * Default value for [TextMaxAngle], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextMaxAngle = TextMaxAngle(Value.nullValue())
  }
}

/**
 * The maximum line width for text wrapping.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextMaxWidth(public val value: Value) {
  /**
   * Construct the TextMaxWidth with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextMaxWidth with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-max-width"

    /**
     * Default value for [TextMaxWidth], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextMaxWidth = TextMaxWidth(Value.nullValue())
  }
}

/**
 * Offset distance of text from its anchor. Positive values indicate right and down, while negative values indicate left and up. If used with text-variable-anchor, input values will be taken as absolute values. Offsets along the x- and y-axis will be applied automatically based on the anchor position.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextOffset(public val value: Value) {
  /**
   * Construct the TextOffset with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextOffset with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-offset"

    /**
     * Default value for [TextOffset], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextOffset = TextOffset(Value.nullValue())
  }
}

/**
 * If true, icons will display without their corresponding text when the text collides with other symbols and the icon does not.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextOptional(public val value: Value) {
  /**
   * Construct the TextOptional with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextOptional with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-optional"

    /**
     * Default value for [TextOptional], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextOptional = TextOptional(Value.nullValue())
  }
}

/**
 * Size of the additional area around the text bounding box used for detecting symbol collisions.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextPadding(public val value: Value) {
  /**
   * Construct the TextPadding with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextPadding with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-padding"

    /**
     * Default value for [TextPadding], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextPadding = TextPadding(Value.nullValue())
  }
}

/**
 * Orientation of text when map is pitched.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextPitchAlignment(public val value: Value) {
  /**
   * Construct the TextPitchAlignment with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-pitch-alignment"

    /**
     * Default value for [TextPitchAlignment], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextPitchAlignment = TextPitchAlignment(Value.nullValue())

    /**
     * The text is aligned to the plane of the map.
     */
    @JvmField
    public val MAP: TextPitchAlignment = TextPitchAlignment(Value("map"))

    /**
     * The text is aligned to the plane of the viewport.
     */
    @JvmField
    public val VIEWPORT: TextPitchAlignment = TextPitchAlignment(Value("viewport"))

    /**
     * Automatically matches the value of {@link TEXT_ROTATION_ALIGNMENT}.
     */
    @JvmField
    public val AUTO: TextPitchAlignment = TextPitchAlignment(Value("auto"))
  }
}

/**
 * Radial offset of text, in the direction of the symbol's anchor. Useful in combination with `text-variable-anchor`, which defaults to using the two-dimensional `text-offset` if present.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextRadialOffset(public val value: Value) {
  /**
   * Construct the TextRadialOffset with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextRadialOffset with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-radial-offset"

    /**
     * Default value for [TextRadialOffset], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextRadialOffset = TextRadialOffset(Value.nullValue())
  }
}

/**
 * Rotates the text clockwise.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextRotate(public val value: Value) {
  /**
   * Construct the TextRotate with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextRotate with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-rotate"

    /**
     * Default value for [TextRotate], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextRotate = TextRotate(Value.nullValue())
  }
}

/**
 * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextRotationAlignment(public val value: Value) {
  /**
   * Construct the TextRotationAlignment with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-rotation-alignment"

    /**
     * Default value for [TextRotationAlignment], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextRotationAlignment = TextRotationAlignment(Value.nullValue())

    /**
     * When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_POINT}, aligns text east-west. When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_LINE} or {@link Property#SYMBOL_PLACEMENT_LINE_CENTER}, aligns text x-axes with the line.
     */
    @JvmField
    public val MAP: TextRotationAlignment = TextRotationAlignment(Value("map"))

    /**
     * Produces glyphs whose x-axes are aligned with the x-axis of the viewport, regardless of the value of {@link SYMBOL_PLACEMENT}.
     */
    @JvmField
    public val VIEWPORT: TextRotationAlignment = TextRotationAlignment(Value("viewport"))

    /**
     * When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_POINT}, this is equivalent to {@link Property#TEXT_ROTATION_ALIGNMENT_VIEWPORT}. When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_LINE} or {@link Property#SYMBOL_PLACEMENT_LINE_CENTER}, this is equivalent to {@link Property#TEXT_ROTATION_ALIGNMENT_MAP}.
     */
    @JvmField
    public val AUTO: TextRotationAlignment = TextRotationAlignment(Value("auto"))
  }
}

/**
 * Font size.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextSize(public val value: Value) {
  /**
   * Construct the TextSize with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextSize with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-size"

    /**
     * Default value for [TextSize], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextSize = TextSize(Value.nullValue())
  }
}

/**
 * Specifies how to capitalize text, similar to the CSS `text-transform` property.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextTransform(public val value: Value) {
  /**
   * Construct the TextTransform with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-transform"

    /**
     * Default value for [TextTransform], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextTransform = TextTransform(Value.nullValue())

    /**
     * The text is not altered.
     */
    @JvmField
    public val NONE: TextTransform = TextTransform(Value("none"))

    /**
     * Forces all letters to be displayed in uppercase.
     */
    @JvmField
    public val UPPERCASE: TextTransform = TextTransform(Value("uppercase"))

    /**
     * Forces all letters to be displayed in lowercase.
     */
    @JvmField
    public val LOWERCASE: TextTransform = TextTransform(Value("lowercase"))
  }
}

/**
 * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextVariableAnchor(public val value: Value) {
  /**
   * Construct the TextVariableAnchor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-variable-anchor"

    /**
     * Default value for [TextVariableAnchor], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextVariableAnchor = TextVariableAnchor(Value.nullValue())

    /**
     * The center of the text is placed closest to the anchor.
     */
    @JvmField
    public val CENTER: TextVariableAnchor = TextVariableAnchor(Value("center"))

    /**
     * The left side of the text is placed closest to the anchor.
     */
    @JvmField
    public val LEFT: TextVariableAnchor = TextVariableAnchor(Value("left"))

    /**
     * The right side of the text is placed closest to the anchor.
     */
    @JvmField
    public val RIGHT: TextVariableAnchor = TextVariableAnchor(Value("right"))

    /**
     * The top of the text is placed closest to the anchor.
     */
    @JvmField
    public val TOP: TextVariableAnchor = TextVariableAnchor(Value("top"))

    /**
     * The bottom of the text is placed closest to the anchor.
     */
    @JvmField
    public val BOTTOM: TextVariableAnchor = TextVariableAnchor(Value("bottom"))

    /**
     * The top left corner of the text is placed closest to the anchor.
     */
    @JvmField
    public val TOP_LEFT: TextVariableAnchor = TextVariableAnchor(Value("top-left"))

    /**
     * The top right corner of the text is placed closest to the anchor.
     */
    @JvmField
    public val TOP_RIGHT: TextVariableAnchor = TextVariableAnchor(Value("top-right"))

    /**
     * The bottom left corner of the text is placed closest to the anchor.
     */
    @JvmField
    public val BOTTOM_LEFT: TextVariableAnchor = TextVariableAnchor(Value("bottom-left"))

    /**
     * The bottom right corner of the text is placed closest to the anchor.
     */
    @JvmField
    public val BOTTOM_RIGHT: TextVariableAnchor = TextVariableAnchor(Value("bottom-right"))
  }
}

/**
 * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextWritingMode(public val value: Value) {
  /**
   * Construct the TextWritingMode with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-writing-mode"

    /**
     * Default value for [TextWritingMode], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextWritingMode = TextWritingMode(Value.nullValue())

    /**
     * If a text's language supports horizontal writing mode, symbols would be laid out horizontally.
     */
    @JvmField
    public val HORIZONTAL: TextWritingMode = TextWritingMode(Value("horizontal"))

    /**
     * If a text's language supports vertical writing mode, symbols would be laid out vertically.
     */
    @JvmField
    public val VERTICAL: TextWritingMode = TextWritingMode(Value("vertical"))
  }
}

/**
 * The color of the icon. This can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconColor(public val value: Value) {
  /**
   * Construct the IconColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [IconColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconColor = IconColor(Value.nullValue())
  }
}

/**
 * Controls saturation level of the symbol icon. With the default value of 1 the icon color is preserved while with a value of 0 it is fully desaturated and looks black and white.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconColorSaturation(public val value: Value) {
  /**
   * Construct the IconColorSaturation with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconColorSaturation with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-color-saturation"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [IconColorSaturation], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconColorSaturation = IconColorSaturation(Value.nullValue())
  }
}

/**
 * Controls the intensity of light emitted on the source features.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconEmissiveStrength(public val value: Value) {
  /**
   * Construct the IconEmissiveStrength with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconEmissiveStrength with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-emissive-strength"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [IconEmissiveStrength], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconEmissiveStrength = IconEmissiveStrength(Value.nullValue())
  }
}

/**
 * Fade out the halo towards the outside.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconHaloBlur(public val value: Value) {
  /**
   * Construct the IconHaloBlur with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconHaloBlur with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-halo-blur"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [IconHaloBlur], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconHaloBlur = IconHaloBlur(Value.nullValue())
  }
}

/**
 * The color of the icon's halo. Icon halos can only be used with [SDF icons](/help/troubleshooting/using-recolorable-images-in-mapbox-maps/).
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconHaloColor(public val value: Value) {
  /**
   * Construct the IconHaloColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconHaloColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-halo-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [IconHaloColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconHaloColor = IconHaloColor(Value.nullValue())
  }
}

/**
 * Distance of halo to the icon outline.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconHaloWidth(public val value: Value) {
  /**
   * Construct the IconHaloWidth with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconHaloWidth with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-halo-width"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [IconHaloWidth], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconHaloWidth = IconHaloWidth(Value.nullValue())
  }
}

/**
 * Controls the transition progress between the image variants of icon-image. Zero means the first variant is used, one is the second, and in between they are blended together.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconImageCrossFade(public val value: Value) {
  /**
   * Construct the IconImageCrossFade with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconImageCrossFade with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-image-cross-fade"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [IconImageCrossFade], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconImageCrossFade = IconImageCrossFade(Value.nullValue())
  }
}

/**
 * The opacity at which the icon will be drawn.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconOpacity(public val value: Value) {
  /**
   * Construct the IconOpacity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconOpacity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-opacity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [IconOpacity], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconOpacity = IconOpacity(Value.nullValue())
  }
}

/**
 * Distance that the icon's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconTranslate(public val value: Value) {
  /**
   * Construct the IconTranslate with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the IconTranslate with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-translate"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [IconTranslate], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconTranslate = IconTranslate(Value.nullValue())
  }
}

/**
 * Controls the frame of reference for `icon-translate`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconTranslateAnchor(public val value: Value) {
  /**
   * Construct the IconTranslateAnchor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "icon-translate-anchor"

    /**
     * Default value for [IconTranslateAnchor], setting default will result in restoring the property value defined in the style.
     */
    public val default: IconTranslateAnchor = IconTranslateAnchor(Value.nullValue())

    /**
     * Icons are translated relative to the map.
     */
    @JvmField
    public val MAP: IconTranslateAnchor = IconTranslateAnchor(Value("map"))

    /**
     * Icons are translated relative to the viewport.
     */
    @JvmField
    public val VIEWPORT: IconTranslateAnchor = IconTranslateAnchor(Value("viewport"))
  }
}

/**
 * The color with which the text will be drawn.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextColor(public val value: Value) {
  /**
   * Construct the TextColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [TextColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextColor = TextColor(Value.nullValue())
  }
}

/**
 * Controls the intensity of light emitted on the source features.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextEmissiveStrength(public val value: Value) {
  /**
   * Construct the TextEmissiveStrength with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextEmissiveStrength with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-emissive-strength"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [TextEmissiveStrength], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextEmissiveStrength = TextEmissiveStrength(Value.nullValue())
  }
}

/**
 * The halo's fadeout distance towards the outside.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextHaloBlur(public val value: Value) {
  /**
   * Construct the TextHaloBlur with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextHaloBlur with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-halo-blur"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [TextHaloBlur], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextHaloBlur = TextHaloBlur(Value.nullValue())
  }
}

/**
 * The color of the text's halo, which helps it stand out from backgrounds.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextHaloColor(public val value: Value) {
  /**
   * Construct the TextHaloColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextHaloColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-halo-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [TextHaloColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextHaloColor = TextHaloColor(Value.nullValue())
  }
}

/**
 * Distance of halo to the font outline. Max text halo width is 1/4 of the font-size.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextHaloWidth(public val value: Value) {
  /**
   * Construct the TextHaloWidth with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextHaloWidth with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-halo-width"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [TextHaloWidth], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextHaloWidth = TextHaloWidth(Value.nullValue())
  }
}

/**
 * The opacity at which the text will be drawn.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextOpacity(public val value: Value) {
  /**
   * Construct the TextOpacity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextOpacity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-opacity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [TextOpacity], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextOpacity = TextOpacity(Value.nullValue())
  }
}

/**
 * Distance that the text's anchor is moved from its original placement. Positive values indicate right and down, while negative values indicate left and up.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextTranslate(public val value: Value) {
  /**
   * Construct the TextTranslate with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TextTranslate with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-translate"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [TextTranslate], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextTranslate = TextTranslate(Value.nullValue())
  }
}

/**
 * Controls the frame of reference for `text-translate`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextTranslateAnchor(public val value: Value) {
  /**
   * Construct the TextTranslateAnchor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "text-translate-anchor"

    /**
     * Default value for [TextTranslateAnchor], setting default will result in restoring the property value defined in the style.
     */
    public val default: TextTranslateAnchor = TextTranslateAnchor(Value.nullValue())

    /**
     * The text is translated relative to the map.
     */
    @JvmField
    public val MAP: TextTranslateAnchor = TextTranslateAnchor(Value("map"))

    /**
     * The text is translated relative to the viewport.
     */
    @JvmField
    public val VIEWPORT: TextTranslateAnchor = TextTranslateAnchor(Value("viewport"))
  }
}

/**
 * Sorts features in ascending order based on this value. Features with a higher sort key will appear above features with a lower sort key.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class CircleSortKey(public val value: Value) {
  /**
   * Construct the CircleSortKey with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the CircleSortKey with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "circle-sort-key"

    /**
     * Default value for [CircleSortKey], setting default will result in restoring the property value defined in the style.
     */
    public val default: CircleSortKey = CircleSortKey(Value.nullValue())
  }
}

/**
 * Amount to blur the circle. 1 blurs the circle such that only the centerpoint is full opacity.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class CircleBlur(public val value: Value) {
  /**
   * Construct the CircleBlur with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the CircleBlur with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "circle-blur"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [CircleBlur], setting default will result in restoring the property value defined in the style.
     */
    public val default: CircleBlur = CircleBlur(Value.nullValue())
  }
}

/**
 * The fill color of the circle.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class CircleColor(public val value: Value) {
  /**
   * Construct the CircleColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the CircleColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "circle-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [CircleColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: CircleColor = CircleColor(Value.nullValue())
  }
}

/**
 * Controls the intensity of light emitted on the source features.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class CircleEmissiveStrength(public val value: Value) {
  /**
   * Construct the CircleEmissiveStrength with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the CircleEmissiveStrength with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "circle-emissive-strength"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [CircleEmissiveStrength], setting default will result in restoring the property value defined in the style.
     */
    public val default: CircleEmissiveStrength = CircleEmissiveStrength(Value.nullValue())
  }
}

/**
 * The opacity at which the circle will be drawn.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class CircleOpacity(public val value: Value) {
  /**
   * Construct the CircleOpacity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the CircleOpacity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "circle-opacity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [CircleOpacity], setting default will result in restoring the property value defined in the style.
     */
    public val default: CircleOpacity = CircleOpacity(Value.nullValue())
  }
}

/**
 * Orientation of circle when map is pitched.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class CirclePitchAlignment(public val value: Value) {
  /**
   * Construct the CirclePitchAlignment with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "circle-pitch-alignment"

    /**
     * Default value for [CirclePitchAlignment], setting default will result in restoring the property value defined in the style.
     */
    public val default: CirclePitchAlignment = CirclePitchAlignment(Value.nullValue())

    /**
     * The circle is aligned to the plane of the map.
     */
    @JvmField
    public val MAP: CirclePitchAlignment = CirclePitchAlignment(Value("map"))

    /**
     * The circle is aligned to the plane of the viewport.
     */
    @JvmField
    public val VIEWPORT: CirclePitchAlignment = CirclePitchAlignment(Value("viewport"))
  }
}

/**
 * Controls the scaling behavior of the circle when the map is pitched.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class CirclePitchScale(public val value: Value) {
  /**
   * Construct the CirclePitchScale with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "circle-pitch-scale"

    /**
     * Default value for [CirclePitchScale], setting default will result in restoring the property value defined in the style.
     */
    public val default: CirclePitchScale = CirclePitchScale(Value.nullValue())

    /**
     * Circles are scaled according to their apparent distance to the camera.
     */
    @JvmField
    public val MAP: CirclePitchScale = CirclePitchScale(Value("map"))

    /**
     * Circles are not scaled.
     */
    @JvmField
    public val VIEWPORT: CirclePitchScale = CirclePitchScale(Value("viewport"))
  }
}

/**
 * Circle radius.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class CircleRadius(public val value: Value) {
  /**
   * Construct the CircleRadius with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the CircleRadius with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "circle-radius"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [CircleRadius], setting default will result in restoring the property value defined in the style.
     */
    public val default: CircleRadius = CircleRadius(Value.nullValue())
  }
}

/**
 * The stroke color of the circle.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class CircleStrokeColor(public val value: Value) {
  /**
   * Construct the CircleStrokeColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the CircleStrokeColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "circle-stroke-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [CircleStrokeColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: CircleStrokeColor = CircleStrokeColor(Value.nullValue())
  }
}

/**
 * The opacity of the circle's stroke.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class CircleStrokeOpacity(public val value: Value) {
  /**
   * Construct the CircleStrokeOpacity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the CircleStrokeOpacity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "circle-stroke-opacity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [CircleStrokeOpacity], setting default will result in restoring the property value defined in the style.
     */
    public val default: CircleStrokeOpacity = CircleStrokeOpacity(Value.nullValue())
  }
}

/**
 * The width of the circle's stroke. Strokes are placed outside of the `circle-radius`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class CircleStrokeWidth(public val value: Value) {
  /**
   * Construct the CircleStrokeWidth with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the CircleStrokeWidth with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "circle-stroke-width"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [CircleStrokeWidth], setting default will result in restoring the property value defined in the style.
     */
    public val default: CircleStrokeWidth = CircleStrokeWidth(Value.nullValue())
  }
}

/**
 * The geometry's offset. Values are [x, y] where negatives indicate left and up, respectively.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class CircleTranslate(public val value: Value) {
  /**
   * Construct the CircleTranslate with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the CircleTranslate with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "circle-translate"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [CircleTranslate], setting default will result in restoring the property value defined in the style.
     */
    public val default: CircleTranslate = CircleTranslate(Value.nullValue())
  }
}

/**
 * Controls the frame of reference for `circle-translate`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class CircleTranslateAnchor(public val value: Value) {
  /**
   * Construct the CircleTranslateAnchor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "circle-translate-anchor"

    /**
     * Default value for [CircleTranslateAnchor], setting default will result in restoring the property value defined in the style.
     */
    public val default: CircleTranslateAnchor = CircleTranslateAnchor(Value.nullValue())

    /**
     * The circle is translated relative to the map.
     */
    @JvmField
    public val MAP: CircleTranslateAnchor = CircleTranslateAnchor(Value("map"))

    /**
     * The circle is translated relative to the viewport.
     */
    @JvmField
    public val VIEWPORT: CircleTranslateAnchor = CircleTranslateAnchor(Value("viewport"))
  }
}

/**
 * Defines the color of each pixel based on its density value in a heatmap. Should be an expression that uses `["heatmap-density"]` as input.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class HeatmapColor(public val value: Value) {
  /**
   * Construct the HeatmapColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "heatmap-color"

    /**
     * Default value for [HeatmapColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: HeatmapColor = HeatmapColor(Value.nullValue())
  }
}

/**
 * Similar to `heatmap-weight` but controls the intensity of the heatmap globally. Primarily used for adjusting the heatmap based on zoom level.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class HeatmapIntensity(public val value: Value) {
  /**
   * Construct the HeatmapIntensity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the HeatmapIntensity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "heatmap-intensity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [HeatmapIntensity], setting default will result in restoring the property value defined in the style.
     */
    public val default: HeatmapIntensity = HeatmapIntensity(Value.nullValue())
  }
}

/**
 * The global opacity at which the heatmap layer will be drawn.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class HeatmapOpacity(public val value: Value) {
  /**
   * Construct the HeatmapOpacity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the HeatmapOpacity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "heatmap-opacity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [HeatmapOpacity], setting default will result in restoring the property value defined in the style.
     */
    public val default: HeatmapOpacity = HeatmapOpacity(Value.nullValue())
  }
}

/**
 * Radius of influence of one heatmap point in pixels. Increasing the value makes the heatmap smoother, but less detailed. `queryRenderedFeatures` on heatmap layers will return points within this radius.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class HeatmapRadius(public val value: Value) {
  /**
   * Construct the HeatmapRadius with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the HeatmapRadius with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "heatmap-radius"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [HeatmapRadius], setting default will result in restoring the property value defined in the style.
     */
    public val default: HeatmapRadius = HeatmapRadius(Value.nullValue())
  }
}

/**
 * A measure of how much an individual point contributes to the heatmap. A value of 10 would be equivalent to having 10 points of weight 1 in the same spot. Especially useful when combined with clustering.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class HeatmapWeight(public val value: Value) {
  /**
   * Construct the HeatmapWeight with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the HeatmapWeight with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "heatmap-weight"

    /**
     * Default value for [HeatmapWeight], setting default will result in restoring the property value defined in the style.
     */
    public val default: HeatmapWeight = HeatmapWeight(Value.nullValue())
  }
}

/**
 * Radius of a fill extrusion edge in meters. If not zero, rounds extrusion edges for a smoother appearance.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionEdgeRadius(public val value: Value) {
  /**
   * Construct the FillExtrusionEdgeRadius with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionEdgeRadius with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-edge-radius"

    /**
     * Default value for [FillExtrusionEdgeRadius], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionEdgeRadius = FillExtrusionEdgeRadius(Value.nullValue())
  }
}

/**
 * Provides a control to futher fine-tune the look of the ambient occlusion on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionAmbientOcclusionGroundAttenuation(public val value: Value) {
  /**
   * Construct the FillExtrusionAmbientOcclusionGroundAttenuation with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionAmbientOcclusionGroundAttenuation with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-ambient-occlusion-ground-attenuation"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionAmbientOcclusionGroundAttenuation], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionAmbientOcclusionGroundAttenuation = FillExtrusionAmbientOcclusionGroundAttenuation(Value.nullValue())
  }
}

/**
 * The extent of the ambient occlusion effect on the ground beneath the extruded buildings in meters.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionAmbientOcclusionGroundRadius(public val value: Value) {
  /**
   * Construct the FillExtrusionAmbientOcclusionGroundRadius with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionAmbientOcclusionGroundRadius with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-ambient-occlusion-ground-radius"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionAmbientOcclusionGroundRadius], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionAmbientOcclusionGroundRadius = FillExtrusionAmbientOcclusionGroundRadius(Value.nullValue())
  }
}

/**
 * Controls the intensity of shading near ground and concave angles between walls. Default value 0.0 disables ambient occlusion and values around 0.3 provide the most plausible results for buildings.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionAmbientOcclusionIntensity(public val value: Value) {
  /**
   * Construct the FillExtrusionAmbientOcclusionIntensity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionAmbientOcclusionIntensity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-ambient-occlusion-intensity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionAmbientOcclusionIntensity], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionAmbientOcclusionIntensity = FillExtrusionAmbientOcclusionIntensity(Value.nullValue())
  }
}

/**
 * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings. This property works only with legacy light. When 3D lights are enabled `fill-extrusion-ambient-occlusion-wall-radius` and `fill-extrusion-ambient-occlusion-ground-radius` are used instead.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionAmbientOcclusionRadius(public val value: Value) {
  /**
   * Construct the FillExtrusionAmbientOcclusionRadius with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionAmbientOcclusionRadius with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-ambient-occlusion-radius"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionAmbientOcclusionRadius], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionAmbientOcclusionRadius = FillExtrusionAmbientOcclusionRadius(Value.nullValue())
  }
}

/**
 * Shades area near ground and concave angles between walls where the radius defines only vertical impact. Default value 3.0 corresponds to height of one floor and brings the most plausible results for buildings.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionAmbientOcclusionWallRadius(public val value: Value) {
  /**
   * Construct the FillExtrusionAmbientOcclusionWallRadius with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionAmbientOcclusionWallRadius with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-ambient-occlusion-wall-radius"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionAmbientOcclusionWallRadius], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionAmbientOcclusionWallRadius = FillExtrusionAmbientOcclusionWallRadius(Value.nullValue())
  }
}

/**
 * The height with which to extrude the base of this layer. Must be less than or equal to `fill-extrusion-height`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionBase(public val value: Value) {
  /**
   * Construct the FillExtrusionBase with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionBase with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-base"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionBase], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionBase = FillExtrusionBase(Value.nullValue())
  }
}

/**
 * The base color of the extruded fill. The extrusion's surfaces will be shaded differently based on this color in combination with the root `light` settings. If this color is specified as `rgba` with an alpha component, the alpha component will be ignored; use `fill-extrusion-opacity` to set layer opacity.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionColor(public val value: Value) {
  /**
   * Construct the FillExtrusionColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionColor = FillExtrusionColor(Value.nullValue())
  }
}

/**
 * This parameter defines the range for the fade-out effect before an automatic content cutoff on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionCutoffFadeRange(public val value: Value) {
  /**
   * Construct the FillExtrusionCutoffFadeRange with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionCutoffFadeRange with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-cutoff-fade-range"

    /**
     * Default value for [FillExtrusionCutoffFadeRange], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionCutoffFadeRange = FillExtrusionCutoffFadeRange(Value.nullValue())
  }
}

/**
 * Controls the intensity of light emitted on the source features.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionEmissiveStrength(public val value: Value) {
  /**
   * Construct the FillExtrusionEmissiveStrength with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionEmissiveStrength with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-emissive-strength"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionEmissiveStrength], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionEmissiveStrength = FillExtrusionEmissiveStrength(Value.nullValue())
  }
}

/**
 * The color of the flood light effect on the walls of the extruded buildings.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionFloodLightColor(public val value: Value) {
  /**
   * Construct the FillExtrusionFloodLightColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionFloodLightColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-flood-light-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionFloodLightColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionFloodLightColor = FillExtrusionFloodLightColor(Value.nullValue())
  }
}

/**
 * Provides a control to futher fine-tune the look of the flood light on the ground beneath the extruded buildings. Lower values give the effect a more solid look while higher values make it smoother.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionFloodLightGroundAttenuation(public val value: Value) {
  /**
   * Construct the FillExtrusionFloodLightGroundAttenuation with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionFloodLightGroundAttenuation with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-flood-light-ground-attenuation"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionFloodLightGroundAttenuation], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionFloodLightGroundAttenuation = FillExtrusionFloodLightGroundAttenuation(Value.nullValue())
  }
}

/**
 * The extent of the flood light effect on the ground beneath the extruded buildings in meters.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionFloodLightGroundRadius(public val value: Value) {
  /**
   * Construct the FillExtrusionFloodLightGroundRadius with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionFloodLightGroundRadius with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-flood-light-ground-radius"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionFloodLightGroundRadius], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionFloodLightGroundRadius = FillExtrusionFloodLightGroundRadius(Value.nullValue())
  }
}

/**
 * The intensity of the flood light color.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionFloodLightIntensity(public val value: Value) {
  /**
   * Construct the FillExtrusionFloodLightIntensity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionFloodLightIntensity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-flood-light-intensity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionFloodLightIntensity], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionFloodLightIntensity = FillExtrusionFloodLightIntensity(Value.nullValue())
  }
}

/**
 * The extent of the flood light effect on the walls of the extruded buildings in meters.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionFloodLightWallRadius(public val value: Value) {
  /**
   * Construct the FillExtrusionFloodLightWallRadius with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionFloodLightWallRadius with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-flood-light-wall-radius"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionFloodLightWallRadius], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionFloodLightWallRadius = FillExtrusionFloodLightWallRadius(Value.nullValue())
  }
}

/**
 * The height with which to extrude this layer.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionHeight(public val value: Value) {
  /**
   * Construct the FillExtrusionHeight with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionHeight with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-height"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionHeight], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionHeight = FillExtrusionHeight(Value.nullValue())
  }
}

/**
 * The opacity of the entire fill extrusion layer. This is rendered on a per-layer, not per-feature, basis, and data-driven styling is not available.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionOpacity(public val value: Value) {
  /**
   * Construct the FillExtrusionOpacity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionOpacity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-opacity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionOpacity], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionOpacity = FillExtrusionOpacity(Value.nullValue())
  }
}

/**
 * Name of image in sprite to use for drawing images on extruded fills. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionPattern(public val value: Value) {
  /**
   * Construct the FillExtrusionPattern with [String].
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionPattern with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-pattern"

    /**
     * Default value for [FillExtrusionPattern], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionPattern = FillExtrusionPattern(Value.nullValue())
  }
}

/**
 * Indicates whether top edges should be rounded when fill-extrusion-edge-radius has a value greater than 0. If false, rounded edges are only applied to the sides. Default is true.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionRoundedRoof(public val value: Value) {
  /**
   * Construct the FillExtrusionRoundedRoof with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionRoundedRoof with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-rounded-roof"

    /**
     * Default value for [FillExtrusionRoundedRoof], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionRoundedRoof = FillExtrusionRoundedRoof(Value.nullValue())
  }
}

/**
 * The geometry's offset. Values are [x, y] where negatives indicate left and up (on the flat plane), respectively.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionTranslate(public val value: Value) {
  /**
   * Construct the FillExtrusionTranslate with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionTranslate with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-translate"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionTranslate], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionTranslate = FillExtrusionTranslate(Value.nullValue())
  }
}

/**
 * Controls the frame of reference for `fill-extrusion-translate`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionTranslateAnchor(public val value: Value) {
  /**
   * Construct the FillExtrusionTranslateAnchor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-translate-anchor"

    /**
     * Default value for [FillExtrusionTranslateAnchor], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionTranslateAnchor = FillExtrusionTranslateAnchor(Value.nullValue())

    /**
     * The fill extrusion is translated relative to the map.
     */
    @JvmField
    public val MAP: FillExtrusionTranslateAnchor = FillExtrusionTranslateAnchor(Value("map"))

    /**
     * The fill extrusion is translated relative to the viewport.
     */
    @JvmField
    public val VIEWPORT: FillExtrusionTranslateAnchor = FillExtrusionTranslateAnchor(Value("viewport"))
  }
}

/**
 * Whether to apply a vertical gradient to the sides of a fill-extrusion layer. If true, sides will be shaded slightly darker farther down.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionVerticalGradient(public val value: Value) {
  /**
   * Construct the FillExtrusionVerticalGradient with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionVerticalGradient with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-vertical-gradient"

    /**
     * Default value for [FillExtrusionVerticalGradient], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionVerticalGradient = FillExtrusionVerticalGradient(Value.nullValue())
  }
}

/**
 * A global multiplier that can be used to scale base, height, AO, and flood light of the fill extrusions.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionVerticalScale(public val value: Value) {
  /**
   * Construct the FillExtrusionVerticalScale with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the FillExtrusionVerticalScale with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "fill-extrusion-vertical-scale"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [FillExtrusionVerticalScale], setting default will result in restoring the property value defined in the style.
     */
    public val default: FillExtrusionVerticalScale = FillExtrusionVerticalScale(Value.nullValue())
  }
}

/**
 * Displayed band of raster array source layer
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class RasterArrayBand(public val value: Value) {
  /**
   * Construct the RasterArrayBand with [String].
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the RasterArrayBand with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "raster-array-band"

    /**
     * Default value for [RasterArrayBand], setting default will result in restoring the property value defined in the style.
     */
    public val default: RasterArrayBand = RasterArrayBand(Value.nullValue())
  }
}

/**
 * Increase or reduce the brightness of the image. The value is the maximum brightness.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class RasterBrightnessMax(public val value: Value) {
  /**
   * Construct the RasterBrightnessMax with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the RasterBrightnessMax with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "raster-brightness-max"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [RasterBrightnessMax], setting default will result in restoring the property value defined in the style.
     */
    public val default: RasterBrightnessMax = RasterBrightnessMax(Value.nullValue())
  }
}

/**
 * Increase or reduce the brightness of the image. The value is the minimum brightness.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class RasterBrightnessMin(public val value: Value) {
  /**
   * Construct the RasterBrightnessMin with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the RasterBrightnessMin with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "raster-brightness-min"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [RasterBrightnessMin], setting default will result in restoring the property value defined in the style.
     */
    public val default: RasterBrightnessMin = RasterBrightnessMin(Value.nullValue())
  }
}

/**
 * Defines a color map by which to colorize a raster layer, parameterized by the `["raster-value"]` expression and evaluated at 256 uniformly spaced steps over the range specified by `raster-color-range`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class RasterColor(public val value: Value) {
  /**
   * Construct the RasterColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "raster-color"

    /**
     * Default value for [RasterColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: RasterColor = RasterColor(Value.nullValue())
  }
}

/**
 * When `raster-color` is active, specifies the combination of source RGB channels used to compute the raster value. Computed using the equation `mix.r * src.r + mix.g * src.g + mix.b * src.b + mix.a`. The first three components specify the mix of source red, green, and blue channels, respectively. The fourth component serves as a constant offset and is *not* multipled by source alpha. Source alpha is instead carried through and applied as opacity to the colorized result. Default value corresponds to RGB luminosity.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class RasterColorMix(public val value: Value) {
  /**
   * Construct the RasterColorMix with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the RasterColorMix with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "raster-color-mix"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [RasterColorMix], setting default will result in restoring the property value defined in the style.
     */
    public val default: RasterColorMix = RasterColorMix(Value.nullValue())
  }
}

/**
 * When `raster-color` is active, specifies the range over which `raster-color` is tabulated. Units correspond to the computed raster value via `raster-color-mix`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class RasterColorRange(public val value: Value) {
  /**
   * Construct the RasterColorRange with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the RasterColorRange with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "raster-color-range"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [RasterColorRange], setting default will result in restoring the property value defined in the style.
     */
    public val default: RasterColorRange = RasterColorRange(Value.nullValue())
  }
}

/**
 * Increase or reduce the contrast of the image.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class RasterContrast(public val value: Value) {
  /**
   * Construct the RasterContrast with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the RasterContrast with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "raster-contrast"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [RasterContrast], setting default will result in restoring the property value defined in the style.
     */
    public val default: RasterContrast = RasterContrast(Value.nullValue())
  }
}

/**
 * Specifies an uniform elevation from the ground, in meters. Only supported with image sources.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class RasterElevation(public val value: Value) {
  /**
   * Construct the RasterElevation with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the RasterElevation with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "raster-elevation"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [RasterElevation], setting default will result in restoring the property value defined in the style.
     */
    public val default: RasterElevation = RasterElevation(Value.nullValue())
  }
}

/**
 * Controls the intensity of light emitted on the source features.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class RasterEmissiveStrength(public val value: Value) {
  /**
   * Construct the RasterEmissiveStrength with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the RasterEmissiveStrength with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "raster-emissive-strength"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [RasterEmissiveStrength], setting default will result in restoring the property value defined in the style.
     */
    public val default: RasterEmissiveStrength = RasterEmissiveStrength(Value.nullValue())
  }
}

/**
 * Fade duration when a new tile is added.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class RasterFadeDuration(public val value: Value) {
  /**
   * Construct the RasterFadeDuration with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the RasterFadeDuration with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "raster-fade-duration"

    /**
     * Default value for [RasterFadeDuration], setting default will result in restoring the property value defined in the style.
     */
    public val default: RasterFadeDuration = RasterFadeDuration(Value.nullValue())
  }
}

/**
 * Rotates hues around the color wheel.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class RasterHueRotate(public val value: Value) {
  /**
   * Construct the RasterHueRotate with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the RasterHueRotate with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "raster-hue-rotate"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [RasterHueRotate], setting default will result in restoring the property value defined in the style.
     */
    public val default: RasterHueRotate = RasterHueRotate(Value.nullValue())
  }
}

/**
 * The opacity at which the image will be drawn.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class RasterOpacity(public val value: Value) {
  /**
   * Construct the RasterOpacity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the RasterOpacity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "raster-opacity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [RasterOpacity], setting default will result in restoring the property value defined in the style.
     */
    public val default: RasterOpacity = RasterOpacity(Value.nullValue())
  }
}

/**
 * The resampling/interpolation method to use for overscaling, also known as texture magnification filter
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class RasterResampling(public val value: Value) {
  /**
   * Construct the RasterResampling with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "raster-resampling"

    /**
     * Default value for [RasterResampling], setting default will result in restoring the property value defined in the style.
     */
    public val default: RasterResampling = RasterResampling(Value.nullValue())

    /**
     * (Bi)linear filtering interpolates pixel values using the weighted average of the four closest original source pixels creating a smooth but blurry look when overscaled
     */
    @JvmField
    public val LINEAR: RasterResampling = RasterResampling(Value("linear"))

    /**
     * Nearest neighbor filtering interpolates pixel values using the nearest original source pixel creating a sharp but pixelated look when overscaled
     */
    @JvmField
    public val NEAREST: RasterResampling = RasterResampling(Value("nearest"))
  }
}

/**
 * Increase or reduce the saturation of the image.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class RasterSaturation(public val value: Value) {
  /**
   * Construct the RasterSaturation with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the RasterSaturation with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "raster-saturation"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [RasterSaturation], setting default will result in restoring the property value defined in the style.
     */
    public val default: RasterSaturation = RasterSaturation(Value.nullValue())
  }
}

/**
 * The shading color used to accentuate rugged terrain like sharp cliffs and gorges.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class HillshadeAccentColor(public val value: Value) {
  /**
   * Construct the HillshadeAccentColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the HillshadeAccentColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "hillshade-accent-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [HillshadeAccentColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: HillshadeAccentColor = HillshadeAccentColor(Value.nullValue())
  }
}

/**
 * Controls the intensity of light emitted on the source features.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class HillshadeEmissiveStrength(public val value: Value) {
  /**
   * Construct the HillshadeEmissiveStrength with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the HillshadeEmissiveStrength with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "hillshade-emissive-strength"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [HillshadeEmissiveStrength], setting default will result in restoring the property value defined in the style.
     */
    public val default: HillshadeEmissiveStrength = HillshadeEmissiveStrength(Value.nullValue())
  }
}

/**
 * Intensity of the hillshade
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class HillshadeExaggeration(public val value: Value) {
  /**
   * Construct the HillshadeExaggeration with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the HillshadeExaggeration with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "hillshade-exaggeration"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [HillshadeExaggeration], setting default will result in restoring the property value defined in the style.
     */
    public val default: HillshadeExaggeration = HillshadeExaggeration(Value.nullValue())
  }
}

/**
 * The shading color of areas that faces towards the light source.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class HillshadeHighlightColor(public val value: Value) {
  /**
   * Construct the HillshadeHighlightColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the HillshadeHighlightColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "hillshade-highlight-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [HillshadeHighlightColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: HillshadeHighlightColor = HillshadeHighlightColor(Value.nullValue())
  }
}

/**
 * Direction of light source when map is rotated.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class HillshadeIlluminationAnchor(public val value: Value) {
  /**
   * Construct the HillshadeIlluminationAnchor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "hillshade-illumination-anchor"

    /**
     * Default value for [HillshadeIlluminationAnchor], setting default will result in restoring the property value defined in the style.
     */
    public val default: HillshadeIlluminationAnchor = HillshadeIlluminationAnchor(Value.nullValue())

    /**
     * The hillshade illumination is relative to the north direction.
     */
    @JvmField
    public val MAP: HillshadeIlluminationAnchor = HillshadeIlluminationAnchor(Value("map"))

    /**
     * The hillshade illumination is relative to the top of the viewport.
     */
    @JvmField
    public val VIEWPORT: HillshadeIlluminationAnchor = HillshadeIlluminationAnchor(Value("viewport"))
  }
}

/**
 * The direction of the light source used to generate the hillshading with 0 as the top of the viewport if `hillshade-illumination-anchor` is set to `viewport` and due north if `hillshade-illumination-anchor` is set to `map` and no 3d lights enabled. If `hillshade-illumination-anchor` is set to `map` and 3d lights enabled, the direction from 3d lights is used instead.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class HillshadeIlluminationDirection(public val value: Value) {
  /**
   * Construct the HillshadeIlluminationDirection with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the HillshadeIlluminationDirection with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "hillshade-illumination-direction"

    /**
     * Default value for [HillshadeIlluminationDirection], setting default will result in restoring the property value defined in the style.
     */
    public val default: HillshadeIlluminationDirection = HillshadeIlluminationDirection(Value.nullValue())
  }
}

/**
 * The shading color of areas that face away from the light source.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class HillshadeShadowColor(public val value: Value) {
  /**
   * Construct the HillshadeShadowColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the HillshadeShadowColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "hillshade-shadow-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [HillshadeShadowColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: HillshadeShadowColor = HillshadeShadowColor(Value.nullValue())
  }
}

/**
 * Model to render.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelId(public val value: Value) {
  /**
   * Construct the ModelId with [String].
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ModelId with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "model-id"

    /**
     * Default value for [ModelId], setting default will result in restoring the property value defined in the style.
     */
    public val default: ModelId = ModelId(Value.nullValue())
  }
}

/**
 * Intensity of the ambient occlusion if present in the 3D model.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelAmbientOcclusionIntensity(public val value: Value) {
  /**
   * Construct the ModelAmbientOcclusionIntensity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ModelAmbientOcclusionIntensity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "model-ambient-occlusion-intensity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [ModelAmbientOcclusionIntensity], setting default will result in restoring the property value defined in the style.
     */
    public val default: ModelAmbientOcclusionIntensity = ModelAmbientOcclusionIntensity(Value.nullValue())
  }
}

/**
 * Enable/Disable shadow casting for this layer
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelCastShadows(public val value: Value) {
  /**
   * Construct the ModelCastShadows with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ModelCastShadows with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "model-cast-shadows"

    /**
     * Default value for [ModelCastShadows], setting default will result in restoring the property value defined in the style.
     */
    public val default: ModelCastShadows = ModelCastShadows(Value.nullValue())
  }
}

/**
 * The tint color of the model layer. model-color-mix-intensity (defaults to 0) defines tint(mix) intensity - this means that, this color is not used unless model-color-mix-intensity gets value greater than 0.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelColor(public val value: Value) {
  /**
   * Construct the ModelColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ModelColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "model-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [ModelColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: ModelColor = ModelColor(Value.nullValue())
  }
}

/**
 * Intensity of model-color (on a scale from 0 to 1) in color mix with original 3D model's colors. Higher number will present a higher model-color contribution in mix.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelColorMixIntensity(public val value: Value) {
  /**
   * Construct the ModelColorMixIntensity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ModelColorMixIntensity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "model-color-mix-intensity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [ModelColorMixIntensity], setting default will result in restoring the property value defined in the style.
     */
    public val default: ModelColorMixIntensity = ModelColorMixIntensity(Value.nullValue())
  }
}

/**
 * This parameter defines the range for the fade-out effect before an automatic content cutoff  on pitched map views. The automatic cutoff range is calculated according to the minimum required zoom level of the source and layer. The fade range is expressed in relation to the height of the map view. A value of 1.0 indicates that the content is faded to the same extent as the map's height in pixels, while a value close to zero represents a sharp cutoff. When the value is set to 0.0, the cutoff is completely disabled. Note: The property has no effect on the map if terrain is enabled.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelCutoffFadeRange(public val value: Value) {
  /**
   * Construct the ModelCutoffFadeRange with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ModelCutoffFadeRange with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "model-cutoff-fade-range"

    /**
     * Default value for [ModelCutoffFadeRange], setting default will result in restoring the property value defined in the style.
     */
    public val default: ModelCutoffFadeRange = ModelCutoffFadeRange(Value.nullValue())
  }
}

/**
 * Strength of the emission. There is no emission for value 0. For value 1.0, only emissive component (no shading) is displayed and values above 1.0 produce light contribution to surrounding area, for some of the parts (e.g. doors). Expressions that depend on measure-light are not supported when using GeoJSON or vector tile as the model layer source.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelEmissiveStrength(public val value: Value) {
  /**
   * Construct the ModelEmissiveStrength with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ModelEmissiveStrength with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "model-emissive-strength"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [ModelEmissiveStrength], setting default will result in restoring the property value defined in the style.
     */
    public val default: ModelEmissiveStrength = ModelEmissiveStrength(Value.nullValue())
  }
}

/**
 * Emissive strength multiplier along model height (gradient begin, gradient end, value at begin, value at end, gradient curve power (logarithmic scale, curve power = pow(10, val)).
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelHeightBasedEmissiveStrengthMultiplier(public val value: Value) {
  /**
   * Construct the ModelHeightBasedEmissiveStrengthMultiplier with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ModelHeightBasedEmissiveStrengthMultiplier with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "model-height-based-emissive-strength-multiplier"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [ModelHeightBasedEmissiveStrengthMultiplier], setting default will result in restoring the property value defined in the style.
     */
    public val default: ModelHeightBasedEmissiveStrengthMultiplier = ModelHeightBasedEmissiveStrengthMultiplier(Value.nullValue())
  }
}

/**
 * The opacity of the model layer.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelOpacity(public val value: Value) {
  /**
   * Construct the ModelOpacity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ModelOpacity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "model-opacity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [ModelOpacity], setting default will result in restoring the property value defined in the style.
     */
    public val default: ModelOpacity = ModelOpacity(Value.nullValue())
  }
}

/**
 * Enable/Disable shadow receiving for this layer
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelReceiveShadows(public val value: Value) {
  /**
   * Construct the ModelReceiveShadows with [Boolean].
   */
  public constructor(value: Boolean) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ModelReceiveShadows with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "model-receive-shadows"

    /**
     * Default value for [ModelReceiveShadows], setting default will result in restoring the property value defined in the style.
     */
    public val default: ModelReceiveShadows = ModelReceiveShadows(Value.nullValue())
  }
}

/**
 * The rotation of the model in euler angles [lon, lat, z].
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelRotation(public val value: Value) {
  /**
   * Construct the ModelRotation with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ModelRotation with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "model-rotation"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [ModelRotation], setting default will result in restoring the property value defined in the style.
     */
    public val default: ModelRotation = ModelRotation(Value.nullValue())
  }
}

/**
 * Material roughness. Material is fully smooth for value 0, and fully rough for value 1. Affects only layers using batched-model source.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelRoughness(public val value: Value) {
  /**
   * Construct the ModelRoughness with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ModelRoughness with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "model-roughness"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [ModelRoughness], setting default will result in restoring the property value defined in the style.
     */
    public val default: ModelRoughness = ModelRoughness(Value.nullValue())
  }
}

/**
 * The scale of the model.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelScale(public val value: Value) {
  /**
   * Construct the ModelScale with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ModelScale with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "model-scale"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [ModelScale], setting default will result in restoring the property value defined in the style.
     */
    public val default: ModelScale = ModelScale(Value.nullValue())
  }
}

/**
 * Defines scaling mode. Only applies to location-indicator type layers.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelScaleMode(public val value: Value) {
  /**
   * Construct the ModelScaleMode with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "model-scale-mode"

    /**
     * Default value for [ModelScaleMode], setting default will result in restoring the property value defined in the style.
     */
    public val default: ModelScaleMode = ModelScaleMode(Value.nullValue())

    /**
     * Model is scaled so that it's always the same size relative to other map features. The property model-scale specifies how many meters each unit in the model file should cover.
     */
    @JvmField
    public val MAP: ModelScaleMode = ModelScaleMode(Value("map"))

    /**
     * Model is scaled so that it's always the same size on the screen. The property model-scale specifies how many pixels each unit in model file should cover.
     */
    @JvmField
    public val VIEWPORT: ModelScaleMode = ModelScaleMode(Value("viewport"))
  }
}

/**
 * The translation of the model in meters in form of [longitudal, latitudal, altitude] offsets.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelTranslation(public val value: Value) {
  /**
   * Construct the ModelTranslation with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ModelTranslation with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "model-translation"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [ModelTranslation], setting default will result in restoring the property value defined in the style.
     */
    public val default: ModelTranslation = ModelTranslation(Value.nullValue())
  }
}

/**
 * Defines rendering behavior of model in respect to other 3D scene objects.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelType(public val value: Value) {
  /**
   * Construct the ModelType with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "model-type"

    /**
     * Default value for [ModelType], setting default will result in restoring the property value defined in the style.
     */
    public val default: ModelType = ModelType(Value.nullValue())

    /**
     * Integrated to 3D scene, using depth testing, along with terrain, fill-extrusions and custom layer.
     */
    @JvmField
    public val COMMON_3D: ModelType = ModelType(Value("common-3d"))

    /**
     * Displayed over other 3D content, occluded by terrain.
     */
    @JvmField
    public val LOCATION_INDICATOR: ModelType = ModelType(Value("location-indicator"))
  }
}

/**
 * The color with which the background will be drawn.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class BackgroundColor(public val value: Value) {
  /**
   * Construct the BackgroundColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the BackgroundColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "background-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [BackgroundColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: BackgroundColor = BackgroundColor(Value.nullValue())
  }
}

/**
 * Controls the intensity of light emitted on the source features.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class BackgroundEmissiveStrength(public val value: Value) {
  /**
   * Construct the BackgroundEmissiveStrength with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the BackgroundEmissiveStrength with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "background-emissive-strength"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [BackgroundEmissiveStrength], setting default will result in restoring the property value defined in the style.
     */
    public val default: BackgroundEmissiveStrength = BackgroundEmissiveStrength(Value.nullValue())
  }
}

/**
 * The opacity at which the background will be drawn.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class BackgroundOpacity(public val value: Value) {
  /**
   * Construct the BackgroundOpacity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the BackgroundOpacity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "background-opacity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [BackgroundOpacity], setting default will result in restoring the property value defined in the style.
     */
    public val default: BackgroundOpacity = BackgroundOpacity(Value.nullValue())
  }
}

/**
 * Name of image in sprite to use for drawing an image background. For seamless patterns, image width and height must be a factor of two (2, 4, 8, ..., 512). Note that zoom-dependent expressions will be evaluated only at integer zoom levels.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class BackgroundPattern(public val value: Value) {
  /**
   * Construct the BackgroundPattern with [String].
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the BackgroundPattern with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "background-pattern"

    /**
     * Default value for [BackgroundPattern], setting default will result in restoring the property value defined in the style.
     */
    public val default: BackgroundPattern = BackgroundPattern(Value.nullValue())
  }
}

/**
 * A color used to tweak the main atmospheric scattering coefficients. Using white applies the default coefficients giving the natural blue color to the atmosphere. This color affects how heavily the corresponding wavelength is represented during scattering. The alpha channel describes the density of the atmosphere, with 1 maximum density and 0 no density.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SkyAtmosphereColor(public val value: Value) {
  /**
   * Construct the SkyAtmosphereColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the SkyAtmosphereColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "sky-atmosphere-color"

    /**
     * Default value for [SkyAtmosphereColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: SkyAtmosphereColor = SkyAtmosphereColor(Value.nullValue())
  }
}

/**
 * A color applied to the atmosphere sun halo. The alpha channel describes how strongly the sun halo is represented in an atmosphere sky layer.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SkyAtmosphereHaloColor(public val value: Value) {
  /**
   * Construct the SkyAtmosphereHaloColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the SkyAtmosphereHaloColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "sky-atmosphere-halo-color"

    /**
     * Default value for [SkyAtmosphereHaloColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: SkyAtmosphereHaloColor = SkyAtmosphereHaloColor(Value.nullValue())
  }
}

/**
 * Position of the sun center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the sun relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the sun, where 0 degree is directly above, at zenith, and 90 degree at the horizon. When this property is ommitted, the sun center is directly inherited from the light position.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SkyAtmosphereSun(public val value: Value) {
  /**
   * Construct the SkyAtmosphereSun with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the SkyAtmosphereSun with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "sky-atmosphere-sun"

    /**
     * Default value for [SkyAtmosphereSun], setting default will result in restoring the property value defined in the style.
     */
    public val default: SkyAtmosphereSun = SkyAtmosphereSun(Value.nullValue())
  }
}

/**
 * Intensity of the sun as a light source in the atmosphere (on a scale from 0 to a 100). Setting higher values will brighten up the sky.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SkyAtmosphereSunIntensity(public val value: Value) {
  /**
   * Construct the SkyAtmosphereSunIntensity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the SkyAtmosphereSunIntensity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "sky-atmosphere-sun-intensity"

    /**
     * Default value for [SkyAtmosphereSunIntensity], setting default will result in restoring the property value defined in the style.
     */
    public val default: SkyAtmosphereSunIntensity = SkyAtmosphereSunIntensity(Value.nullValue())
  }
}

/**
 * Defines a radial color gradient with which to color the sky. The color values can be interpolated with an expression using `sky-radial-progress`. The range [0, 1] for the interpolant covers a radial distance (in degrees) of [0, `sky-gradient-radius`] centered at the position specified by `sky-gradient-center`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SkyGradient(public val value: Value) {
  /**
   * Construct the SkyGradient with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "sky-gradient"

    /**
     * Default value for [SkyGradient], setting default will result in restoring the property value defined in the style.
     */
    public val default: SkyGradient = SkyGradient(Value.nullValue())
  }
}

/**
 * Position of the gradient center [a azimuthal angle, p polar angle]. The azimuthal angle indicates the position of the gradient center relative to 0 degree north, where degrees proceed clockwise. The polar angle indicates the height of the gradient center, where 0 degree is directly above, at zenith, and 90 degree at the horizon.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SkyGradientCenter(public val value: Value) {
  /**
   * Construct the SkyGradientCenter with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the SkyGradientCenter with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "sky-gradient-center"

    /**
     * Default value for [SkyGradientCenter], setting default will result in restoring the property value defined in the style.
     */
    public val default: SkyGradientCenter = SkyGradientCenter(Value.nullValue())
  }
}

/**
 * The angular distance (measured in degrees) from `sky-gradient-center` up to which the gradient extends. A value of 180 causes the gradient to wrap around to the opposite direction from `sky-gradient-center`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SkyGradientRadius(public val value: Value) {
  /**
   * Construct the SkyGradientRadius with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the SkyGradientRadius with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "sky-gradient-radius"

    /**
     * Default value for [SkyGradientRadius], setting default will result in restoring the property value defined in the style.
     */
    public val default: SkyGradientRadius = SkyGradientRadius(Value.nullValue())
  }
}

/**
 * The opacity of the entire sky layer.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SkyOpacity(public val value: Value) {
  /**
   * Construct the SkyOpacity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the SkyOpacity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "sky-opacity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [SkyOpacity], setting default will result in restoring the property value defined in the style.
     */
    public val default: SkyOpacity = SkyOpacity(Value.nullValue())
  }
}

/**
 * The type of the sky
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SkyType(public val value: Value) {
  /**
   * Construct the SkyType with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "sky-type"

    /**
     * Default value for [SkyType], setting default will result in restoring the property value defined in the style.
     */
    public val default: SkyType = SkyType(Value.nullValue())

    /**
     * Renders the sky with a gradient that can be configured with {@link SKY_GRADIENT_RADIUS} and {@link SKY_GRADIENT}.
     */
    @JvmField
    public val GRADIENT: SkyType = SkyType(Value("gradient"))

    /**
     * Renders the sky with a simulated atmospheric scattering algorithm, the sun direction can be attached to the light position or explicitly set through {@link SKY_ATMOSPHERE_SUN}.
     */
    @JvmField
    public val ATMOSPHERE: SkyType = SkyType(Value("atmosphere"))
  }
}

/**
 * Name of image in sprite to use as the middle of the location indicator.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class BearingImage(public val value: Value) {
  /**
   * Construct the BearingImage with [String].
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the BearingImage with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "bearing-image"

    /**
     * Default value for [BearingImage], setting default will result in restoring the property value defined in the style.
     */
    public val default: BearingImage = BearingImage(Value.nullValue())
  }
}

/**
 * Name of image in sprite to use as the background of the location indicator.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ShadowImage(public val value: Value) {
  /**
   * Construct the ShadowImage with [String].
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ShadowImage with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "shadow-image"

    /**
     * Default value for [ShadowImage], setting default will result in restoring the property value defined in the style.
     */
    public val default: ShadowImage = ShadowImage(Value.nullValue())
  }
}

/**
 * Name of image in sprite to use as the top of the location indicator.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TopImage(public val value: Value) {
  /**
   * Construct the TopImage with [String].
   */
  public constructor(value: String) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TopImage with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "top-image"

    /**
     * Default value for [TopImage], setting default will result in restoring the property value defined in the style.
     */
    public val default: TopImage = TopImage(Value.nullValue())
  }
}

/**
 * The accuracy, in meters, of the position source used to retrieve the position of the location indicator.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class AccuracyRadius(public val value: Value) {
  /**
   * Construct the AccuracyRadius with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the AccuracyRadius with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "accuracy-radius"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [AccuracyRadius], setting default will result in restoring the property value defined in the style.
     */
    public val default: AccuracyRadius = AccuracyRadius(Value.nullValue())
  }
}

/**
 * The color for drawing the accuracy radius border. To adjust transparency, set the alpha component of the color accordingly.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class AccuracyRadiusBorderColor(public val value: Value) {
  /**
   * Construct the AccuracyRadiusBorderColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the AccuracyRadiusBorderColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "accuracy-radius-border-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [AccuracyRadiusBorderColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: AccuracyRadiusBorderColor = AccuracyRadiusBorderColor(Value.nullValue())
  }
}

/**
 * The color for drawing the accuracy radius, as a circle. To adjust transparency, set the alpha component of the color accordingly.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class AccuracyRadiusColor(public val value: Value) {
  /**
   * Construct the AccuracyRadiusColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the AccuracyRadiusColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "accuracy-radius-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [AccuracyRadiusColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: AccuracyRadiusColor = AccuracyRadiusColor(Value.nullValue())
  }
}

/**
 * The bearing of the location indicator.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Bearing(public val value: Value) {
  /**
   * Construct the Bearing with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the Bearing with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "bearing"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [Bearing], setting default will result in restoring the property value defined in the style.
     */
    public val default: Bearing = Bearing(Value.nullValue())
  }
}

/**
 * The size of the bearing image, as a scale factor applied to the size of the specified image.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class BearingImageSize(public val value: Value) {
  /**
   * Construct the BearingImageSize with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the BearingImageSize with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "bearing-image-size"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [BearingImageSize], setting default will result in restoring the property value defined in the style.
     */
    public val default: BearingImageSize = BearingImageSize(Value.nullValue())
  }
}

/**
 * The color of the circle emphasizing the indicator. To adjust transparency, set the alpha component of the color accordingly.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class EmphasisCircleColor(public val value: Value) {
  /**
   * Construct the EmphasisCircleColor with [androidx.compose.ui.graphics.Color].
   */
  public constructor(value: androidx.compose.ui.graphics.Color) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the EmphasisCircleColor with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "emphasis-circle-color"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [EmphasisCircleColor], setting default will result in restoring the property value defined in the style.
     */
    public val default: EmphasisCircleColor = EmphasisCircleColor(Value.nullValue())
  }
}

/**
 * The radius, in pixel, of the circle emphasizing the indicator, drawn between the accuracy radius and the indicator shadow.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class EmphasisCircleRadius(public val value: Value) {
  /**
   * Construct the EmphasisCircleRadius with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the EmphasisCircleRadius with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "emphasis-circle-radius"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [EmphasisCircleRadius], setting default will result in restoring the property value defined in the style.
     */
    public val default: EmphasisCircleRadius = EmphasisCircleRadius(Value.nullValue())
  }
}

/**
 * The displacement off the center of the top image and the shadow image when the pitch of the map is greater than 0. This helps producing a three-dimensional appearence.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ImagePitchDisplacement(public val value: Value) {
  /**
   * Construct the ImagePitchDisplacement with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ImagePitchDisplacement with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "image-pitch-displacement"

    /**
     * Default value for [ImagePitchDisplacement], setting default will result in restoring the property value defined in the style.
     */
    public val default: ImagePitchDisplacement = ImagePitchDisplacement(Value.nullValue())
  }
}

/**
 * An array of [latitude, longitude, altitude] position of the location indicator.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class Location(public val value: Value) {
  /**
   * Construct the Location with [List<Double>].
   */
  public constructor(value: List<Double>) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the Location with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "location"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [Location], setting default will result in restoring the property value defined in the style.
     */
    public val default: Location = Location(Value.nullValue())
  }
}

/**
 * The opacity of the entire location indicator layer.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LocationIndicatorOpacity(public val value: Value) {
  /**
   * Construct the LocationIndicatorOpacity with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the LocationIndicatorOpacity with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "location-indicator-opacity"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [LocationIndicatorOpacity], setting default will result in restoring the property value defined in the style.
     */
    public val default: LocationIndicatorOpacity = LocationIndicatorOpacity(Value.nullValue())
  }
}

/**
 * The amount of the perspective compensation, between 0 and 1. A value of 1 produces a location indicator of constant width across the screen. A value of 0 makes it scale naturally according to the viewing projection.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class PerspectiveCompensation(public val value: Value) {
  /**
   * Construct the PerspectiveCompensation with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the PerspectiveCompensation with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "perspective-compensation"

    /**
     * Default value for [PerspectiveCompensation], setting default will result in restoring the property value defined in the style.
     */
    public val default: PerspectiveCompensation = PerspectiveCompensation(Value.nullValue())
  }
}

/**
 * The size of the shadow image, as a scale factor applied to the size of the specified image.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ShadowImageSize(public val value: Value) {
  /**
   * Construct the ShadowImageSize with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the ShadowImageSize with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "shadow-image-size"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [ShadowImageSize], setting default will result in restoring the property value defined in the style.
     */
    public val default: ShadowImageSize = ShadowImageSize(Value.nullValue())
  }
}

/**
 * The size of the top image, as a scale factor applied to the size of the specified image.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TopImageSize(public val value: Value) {
  /**
   * Construct the TopImageSize with [Double].
   */
  public constructor(value: Double) : this(ComposeTypeUtils.wrapToValue(value))
  /**
   * Construct the TopImageSize with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * Public companion object.
   */
  public companion object {
    internal const val NAME: String = "top-image-size"
    internal const val TRANSITION_NAME = "$NAME-transition"

    /**
     * Default value for [TopImageSize], setting default will result in restoring the property value defined in the style.
     */
    public val default: TopImageSize = TopImageSize(Value.nullValue())
  }
}

/**
 * Defines the timing for the interpolation between a transitionable style layer property's previous value and new value.
 *
 * @param value the transition wrapped in [Value] to be used with native renderer.
 */
public data class Transition private constructor(public val value: Value) {

  /**
   * Construct the [Transition] with duration and delay.
   */
  public constructor(duration: Long = 0L, delay: Long = 0L) : this(
    Value(
      hashMapOf(
        "delay" to Value(delay),
        "duration" to Value(duration)
      )
    )
  )

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Default value for [Transition], setting default will result in restoring the transition defined in the style.
     */
    public val default: Transition = Transition(Value.nullValue())
  }
}
// End of generated file.