// This file is generated.

package com.mapbox.maps.extension.compose.style.layers.generated

import androidx.compose.runtime.Immutable
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.internal.ComposeTypeUtils
import com.mapbox.maps.extension.style.expressions.generated.Expression

/**
 * Controls the frame of reference for `fill-translate`. Default value: "map".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillTranslateAnchorValue(public val value: Value) {
  /**
   * Construct the [FillTranslateAnchorValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: FillTranslateAnchorValue = FillTranslateAnchorValue(Value.valueOf("FillTranslateAnchorValue.INITIAL"))

    /**
     * Default value for [FillTranslateAnchorValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: FillTranslateAnchorValue = FillTranslateAnchorValue(Value.nullValue())

    /**
     * The fill is translated relative to the map.
     */
    @JvmField
    public val MAP: FillTranslateAnchorValue = FillTranslateAnchorValue(Value("map"))

    /**
     * The fill is translated relative to the viewport.
     */
    @JvmField
    public val VIEWPORT: FillTranslateAnchorValue = FillTranslateAnchorValue(Value("viewport"))
  }
}

/**
 * Whether this layer is displayed. Default value: "visible".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class VisibilityValue(public val value: Value) {
  /**
   * Construct the [VisibilityValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: VisibilityValue = VisibilityValue(Value.valueOf("VisibilityValue.INITIAL"))

    /**
     * Default value for [VisibilityValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: VisibilityValue = VisibilityValue(Value.nullValue())

    /**
     * The layer is shown.
     */
    @JvmField
    public val VISIBLE: VisibilityValue = VisibilityValue(Value("visible"))

    /**
     * The layer is not shown.
     */
    @JvmField
    public val NONE: VisibilityValue = VisibilityValue(Value("none"))
  }
}

/**
 * The display of line endings. Default value: "butt".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineCapValue(public val value: Value) {
  /**
   * Construct the [LineCapValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: LineCapValue = LineCapValue(Value.valueOf("LineCapValue.INITIAL"))

    /**
     * Default value for [LineCapValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: LineCapValue = LineCapValue(Value.nullValue())

    /**
     * A cap with a squared-off end which is drawn to the exact endpoint of the line.
     */
    @JvmField
    public val BUTT: LineCapValue = LineCapValue(Value("butt"))

    /**
     * A cap with a rounded end which is drawn beyond the endpoint of the line at a radius of one-half of the line's width and centered on the endpoint of the line.
     */
    @JvmField
    public val ROUND: LineCapValue = LineCapValue(Value("round"))

    /**
     * A cap with a squared-off end which is drawn beyond the endpoint of the line at a distance of one-half of the line's width.
     */
    @JvmField
    public val SQUARE: LineCapValue = LineCapValue(Value("square"))
  }
}

/**
 * The display of lines when joining. Default value: "miter".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineJoinValue(public val value: Value) {
  /**
   * Construct the [LineJoinValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: LineJoinValue = LineJoinValue(Value.valueOf("LineJoinValue.INITIAL"))

    /**
     * Default value for [LineJoinValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: LineJoinValue = LineJoinValue(Value.nullValue())

    /**
     * A join with a squared-off end which is drawn beyond the endpoint of the line at a distance of one-half of the line's width.
     */
    @JvmField
    public val BEVEL: LineJoinValue = LineJoinValue(Value("bevel"))

    /**
     * A join with a rounded end which is drawn beyond the endpoint of the line at a radius of one-half of the line's width and centered on the endpoint of the line.
     */
    @JvmField
    public val ROUND: LineJoinValue = LineJoinValue(Value("round"))

    /**
     * A join with a sharp, angled corner which is drawn with the outer sides beyond the endpoint of the path until they meet.
     */
    @JvmField
    public val MITER: LineJoinValue = LineJoinValue(Value("miter"))
  }
}

/**
 * Controls the frame of reference for `line-translate`. Default value: "map".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class LineTranslateAnchorValue(public val value: Value) {
  /**
   * Construct the [LineTranslateAnchorValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: LineTranslateAnchorValue = LineTranslateAnchorValue(Value.valueOf("LineTranslateAnchorValue.INITIAL"))

    /**
     * Default value for [LineTranslateAnchorValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: LineTranslateAnchorValue = LineTranslateAnchorValue(Value.nullValue())

    /**
     * The line is translated relative to the map.
     */
    @JvmField
    public val MAP: LineTranslateAnchorValue = LineTranslateAnchorValue(Value("map"))

    /**
     * The line is translated relative to the viewport.
     */
    @JvmField
    public val VIEWPORT: LineTranslateAnchorValue = LineTranslateAnchorValue(Value("viewport"))
  }
}

/**
 * Part of the icon placed closest to the anchor. Default value: "center".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconAnchorValue(public val value: Value) {
  /**
   * Construct the [IconAnchorValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: IconAnchorValue = IconAnchorValue(Value.valueOf("IconAnchorValue.INITIAL"))

    /**
     * Default value for [IconAnchorValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: IconAnchorValue = IconAnchorValue(Value.nullValue())

    /**
     * The center of the icon is placed closest to the anchor.
     */
    @JvmField
    public val CENTER: IconAnchorValue = IconAnchorValue(Value("center"))

    /**
     * The left side of the icon is placed closest to the anchor.
     */
    @JvmField
    public val LEFT: IconAnchorValue = IconAnchorValue(Value("left"))

    /**
     * The right side of the icon is placed closest to the anchor.
     */
    @JvmField
    public val RIGHT: IconAnchorValue = IconAnchorValue(Value("right"))

    /**
     * The top of the icon is placed closest to the anchor.
     */
    @JvmField
    public val TOP: IconAnchorValue = IconAnchorValue(Value("top"))

    /**
     * The bottom of the icon is placed closest to the anchor.
     */
    @JvmField
    public val BOTTOM: IconAnchorValue = IconAnchorValue(Value("bottom"))

    /**
     * The top left corner of the icon is placed closest to the anchor.
     */
    @JvmField
    public val TOP_LEFT: IconAnchorValue = IconAnchorValue(Value("top-left"))

    /**
     * The top right corner of the icon is placed closest to the anchor.
     */
    @JvmField
    public val TOP_RIGHT: IconAnchorValue = IconAnchorValue(Value("top-right"))

    /**
     * The bottom left corner of the icon is placed closest to the anchor.
     */
    @JvmField
    public val BOTTOM_LEFT: IconAnchorValue = IconAnchorValue(Value("bottom-left"))

    /**
     * The bottom right corner of the icon is placed closest to the anchor.
     */
    @JvmField
    public val BOTTOM_RIGHT: IconAnchorValue = IconAnchorValue(Value("bottom-right"))
  }
}

/**
 * Orientation of icon when map is pitched. Default value: "auto".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconPitchAlignmentValue(public val value: Value) {
  /**
   * Construct the [IconPitchAlignmentValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: IconPitchAlignmentValue = IconPitchAlignmentValue(Value.valueOf("IconPitchAlignmentValue.INITIAL"))

    /**
     * Default value for [IconPitchAlignmentValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: IconPitchAlignmentValue = IconPitchAlignmentValue(Value.nullValue())

    /**
     * The icon is aligned to the plane of the map.
     */
    @JvmField
    public val MAP: IconPitchAlignmentValue = IconPitchAlignmentValue(Value("map"))

    /**
     * The icon is aligned to the plane of the viewport.
     */
    @JvmField
    public val VIEWPORT: IconPitchAlignmentValue = IconPitchAlignmentValue(Value("viewport"))

    /**
     * Automatically matches the value of {@link ICON_ROTATION_ALIGNMENT}.
     */
    @JvmField
    public val AUTO: IconPitchAlignmentValue = IconPitchAlignmentValue(Value("auto"))
  }
}

/**
 * In combination with `symbol-placement`, determines the rotation behavior of icons. Default value: "auto".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconRotationAlignmentValue(public val value: Value) {
  /**
   * Construct the [IconRotationAlignmentValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: IconRotationAlignmentValue = IconRotationAlignmentValue(Value.valueOf("IconRotationAlignmentValue.INITIAL"))

    /**
     * Default value for [IconRotationAlignmentValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: IconRotationAlignmentValue = IconRotationAlignmentValue(Value.nullValue())

    /**
     * When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_POINT}, aligns icons east-west. When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_LINE} or {@link Property#SYMBOL_PLACEMENT_LINE_CENTER}, aligns icon x-axes with the line.
     */
    @JvmField
    public val MAP: IconRotationAlignmentValue = IconRotationAlignmentValue(Value("map"))

    /**
     * Produces icons whose x-axes are aligned with the x-axis of the viewport, regardless of the value of {@link SYMBOL_PLACEMENT}.
     */
    @JvmField
    public val VIEWPORT: IconRotationAlignmentValue = IconRotationAlignmentValue(Value("viewport"))

    /**
     * When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_POINT}, this is equivalent to {@link Property#ICON_ROTATION_ALIGNMENT_VIEWPORT}. When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_LINE} or {@link Property#SYMBOL_PLACEMENT_LINE_CENTER}, this is equivalent to {@link Property#ICON_ROTATION_ALIGNMENT_MAP}.
     */
    @JvmField
    public val AUTO: IconRotationAlignmentValue = IconRotationAlignmentValue(Value("auto"))
  }
}

/**
 * Scales the icon to fit around the associated text. Default value: "none".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconTextFitValue(public val value: Value) {
  /**
   * Construct the [IconTextFitValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: IconTextFitValue = IconTextFitValue(Value.valueOf("IconTextFitValue.INITIAL"))

    /**
     * Default value for [IconTextFitValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: IconTextFitValue = IconTextFitValue(Value.nullValue())

    /**
     * The icon is displayed at its intrinsic aspect ratio.
     */
    @JvmField
    public val NONE: IconTextFitValue = IconTextFitValue(Value("none"))

    /**
     * The icon is scaled in the x-dimension to fit the width of the text.
     */
    @JvmField
    public val WIDTH: IconTextFitValue = IconTextFitValue(Value("width"))

    /**
     * The icon is scaled in the y-dimension to fit the height of the text.
     */
    @JvmField
    public val HEIGHT: IconTextFitValue = IconTextFitValue(Value("height"))

    /**
     * The icon is scaled in both x- and y-dimensions.
     */
    @JvmField
    public val BOTH: IconTextFitValue = IconTextFitValue(Value("both"))
  }
}

/**
 * Label placement relative to its geometry. Default value: "point".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SymbolPlacementValue(public val value: Value) {
  /**
   * Construct the [SymbolPlacementValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: SymbolPlacementValue = SymbolPlacementValue(Value.valueOf("SymbolPlacementValue.INITIAL"))

    /**
     * Default value for [SymbolPlacementValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: SymbolPlacementValue = SymbolPlacementValue(Value.nullValue())

    /**
     * The label is placed at the point where the geometry is located.
     */
    @JvmField
    public val POINT: SymbolPlacementValue = SymbolPlacementValue(Value("point"))

    /**
     * The label is placed along the line of the geometry. Can only be used on LineString and Polygon geometries.
     */
    @JvmField
    public val LINE: SymbolPlacementValue = SymbolPlacementValue(Value("line"))

    /**
     * The label is placed at the center of the line of the geometry. Can only be used on LineString and Polygon geometries. Note that a single feature in a vector tile may contain multiple line geometries.
     */
    @JvmField
    public val LINE_CENTER: SymbolPlacementValue = SymbolPlacementValue(Value("line-center"))
  }
}

/**
 * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`. Default value: "auto".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SymbolZOrderValue(public val value: Value) {
  /**
   * Construct the [SymbolZOrderValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: SymbolZOrderValue = SymbolZOrderValue(Value.valueOf("SymbolZOrderValue.INITIAL"))

    /**
     * Default value for [SymbolZOrderValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: SymbolZOrderValue = SymbolZOrderValue(Value.nullValue())

    /**
     * Sorts symbols by symbol sort key if set. Otherwise, sorts symbols by their y-position relative to the viewport if {@link ICON_ALLOW_OVERLAP} or {@link TEXT_ALLOW_OVERLAP} is set to {@link TRUE} or {@link ICON_IGNORE_PLACEMENT} or {@link TEXT_IGNORE_PLACEMENT} is {@link FALSE}.
     */
    @JvmField
    public val AUTO: SymbolZOrderValue = SymbolZOrderValue(Value("auto"))

    /**
     * Sorts symbols by their y-position relative to the viewport if {@link ICON_ALLOW_OVERLAP} or {@link TEXT_ALLOW_OVERLAP} is set to {@link TRUE} or {@link ICON_IGNORE_PLACEMENT} or {@link TEXT_IGNORE_PLACEMENT} is {@link FALSE}.
     */
    @JvmField
    public val VIEWPORT_Y: SymbolZOrderValue = SymbolZOrderValue(Value("viewport-y"))

    /**
     * Sorts symbols by symbol sort key if set. Otherwise, no sorting is applied; symbols are rendered in the same order as the source data.
     */
    @JvmField
    public val SOURCE: SymbolZOrderValue = SymbolZOrderValue(Value("source"))
  }
}

/**
 * Part of the text placed closest to the anchor. Default value: "center".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextAnchorValue(public val value: Value) {
  /**
   * Construct the [TextAnchorValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: TextAnchorValue = TextAnchorValue(Value.valueOf("TextAnchorValue.INITIAL"))

    /**
     * Default value for [TextAnchorValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: TextAnchorValue = TextAnchorValue(Value.nullValue())

    /**
     * The center of the text is placed closest to the anchor.
     */
    @JvmField
    public val CENTER: TextAnchorValue = TextAnchorValue(Value("center"))

    /**
     * The left side of the text is placed closest to the anchor.
     */
    @JvmField
    public val LEFT: TextAnchorValue = TextAnchorValue(Value("left"))

    /**
     * The right side of the text is placed closest to the anchor.
     */
    @JvmField
    public val RIGHT: TextAnchorValue = TextAnchorValue(Value("right"))

    /**
     * The top of the text is placed closest to the anchor.
     */
    @JvmField
    public val TOP: TextAnchorValue = TextAnchorValue(Value("top"))

    /**
     * The bottom of the text is placed closest to the anchor.
     */
    @JvmField
    public val BOTTOM: TextAnchorValue = TextAnchorValue(Value("bottom"))

    /**
     * The top left corner of the text is placed closest to the anchor.
     */
    @JvmField
    public val TOP_LEFT: TextAnchorValue = TextAnchorValue(Value("top-left"))

    /**
     * The top right corner of the text is placed closest to the anchor.
     */
    @JvmField
    public val TOP_RIGHT: TextAnchorValue = TextAnchorValue(Value("top-right"))

    /**
     * The bottom left corner of the text is placed closest to the anchor.
     */
    @JvmField
    public val BOTTOM_LEFT: TextAnchorValue = TextAnchorValue(Value("bottom-left"))

    /**
     * The bottom right corner of the text is placed closest to the anchor.
     */
    @JvmField
    public val BOTTOM_RIGHT: TextAnchorValue = TextAnchorValue(Value("bottom-right"))
  }
}

/**
 * Text justification options. Default value: "center".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextJustifyValue(public val value: Value) {
  /**
   * Construct the [TextJustifyValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: TextJustifyValue = TextJustifyValue(Value.valueOf("TextJustifyValue.INITIAL"))

    /**
     * Default value for [TextJustifyValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: TextJustifyValue = TextJustifyValue(Value.nullValue())

    /**
     * The text is aligned towards the anchor position.
     */
    @JvmField
    public val AUTO: TextJustifyValue = TextJustifyValue(Value("auto"))

    /**
     * The text is aligned to the left.
     */
    @JvmField
    public val LEFT: TextJustifyValue = TextJustifyValue(Value("left"))

    /**
     * The text is centered.
     */
    @JvmField
    public val CENTER: TextJustifyValue = TextJustifyValue(Value("center"))

    /**
     * The text is aligned to the right.
     */
    @JvmField
    public val RIGHT: TextJustifyValue = TextJustifyValue(Value("right"))
  }
}

/**
 * Orientation of text when map is pitched. Default value: "auto".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextPitchAlignmentValue(public val value: Value) {
  /**
   * Construct the [TextPitchAlignmentValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: TextPitchAlignmentValue = TextPitchAlignmentValue(Value.valueOf("TextPitchAlignmentValue.INITIAL"))

    /**
     * Default value for [TextPitchAlignmentValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: TextPitchAlignmentValue = TextPitchAlignmentValue(Value.nullValue())

    /**
     * The text is aligned to the plane of the map.
     */
    @JvmField
    public val MAP: TextPitchAlignmentValue = TextPitchAlignmentValue(Value("map"))

    /**
     * The text is aligned to the plane of the viewport.
     */
    @JvmField
    public val VIEWPORT: TextPitchAlignmentValue = TextPitchAlignmentValue(Value("viewport"))

    /**
     * Automatically matches the value of {@link TEXT_ROTATION_ALIGNMENT}.
     */
    @JvmField
    public val AUTO: TextPitchAlignmentValue = TextPitchAlignmentValue(Value("auto"))
  }
}

/**
 * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text. Default value: "auto".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextRotationAlignmentValue(public val value: Value) {
  /**
   * Construct the [TextRotationAlignmentValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: TextRotationAlignmentValue = TextRotationAlignmentValue(Value.valueOf("TextRotationAlignmentValue.INITIAL"))

    /**
     * Default value for [TextRotationAlignmentValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: TextRotationAlignmentValue = TextRotationAlignmentValue(Value.nullValue())

    /**
     * When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_POINT}, aligns text east-west. When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_LINE} or {@link Property#SYMBOL_PLACEMENT_LINE_CENTER}, aligns text x-axes with the line.
     */
    @JvmField
    public val MAP: TextRotationAlignmentValue = TextRotationAlignmentValue(Value("map"))

    /**
     * Produces glyphs whose x-axes are aligned with the x-axis of the viewport, regardless of the value of {@link SYMBOL_PLACEMENT}.
     */
    @JvmField
    public val VIEWPORT: TextRotationAlignmentValue = TextRotationAlignmentValue(Value("viewport"))

    /**
     * When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_POINT}, this is equivalent to {@link Property#TEXT_ROTATION_ALIGNMENT_VIEWPORT}. When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_LINE} or {@link Property#SYMBOL_PLACEMENT_LINE_CENTER}, this is equivalent to {@link Property#TEXT_ROTATION_ALIGNMENT_MAP}.
     */
    @JvmField
    public val AUTO: TextRotationAlignmentValue = TextRotationAlignmentValue(Value("auto"))
  }
}

/**
 * Specifies how to capitalize text, similar to the CSS `text-transform` property. Default value: "none".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextTransformValue(public val value: Value) {
  /**
   * Construct the [TextTransformValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: TextTransformValue = TextTransformValue(Value.valueOf("TextTransformValue.INITIAL"))

    /**
     * Default value for [TextTransformValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: TextTransformValue = TextTransformValue(Value.nullValue())

    /**
     * The text is not altered.
     */
    @JvmField
    public val NONE: TextTransformValue = TextTransformValue(Value("none"))

    /**
     * Forces all letters to be displayed in uppercase.
     */
    @JvmField
    public val UPPERCASE: TextTransformValue = TextTransformValue(Value("uppercase"))

    /**
     * Forces all letters to be displayed in lowercase.
     */
    @JvmField
    public val LOWERCASE: TextTransformValue = TextTransformValue(Value("lowercase"))
  }
}

/**
 * To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextVariableAnchorListValue(public val value: Value) {
  /**
   * Construct the [TextVariableAnchorListValue] with [TextVariableAnchor].
   */
  public constructor(value: List<TextVariableAnchor>) : this(ComposeTypeUtils.wrapToValue(value))

  /**
   * Construct the [TextVariableAnchorListValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: TextVariableAnchorListValue = TextVariableAnchorListValue(Value.valueOf("TextVariableAnchorListValue.INITIAL"))

    /**
     * Default value for [TextVariableAnchorListValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: TextVariableAnchorListValue = TextVariableAnchorListValue(Value.nullValue())
  }
}

/**
 *  To increase the chance of placing high-priority labels on the map, you can provide an array of `text-anchor` locations: the renderer will attempt to place the label at each location, in order, before moving onto the next label. Use `text-justify: auto` to choose justification based on anchor position. To apply an offset, use the `text-radial-offset` or the two-dimensional `text-offset`.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextVariableAnchor internal constructor(public val value: Value) {
  /**
   * Public companion object.
   */
  public companion object {

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
public data class TextWritingModeListValue(public val value: Value) {
  /**
   * Construct the [TextWritingModeListValue] with [TextWritingMode].
   */
  public constructor(value: List<TextWritingMode>) : this(ComposeTypeUtils.wrapToValue(value))

  /**
   * Construct the [TextWritingModeListValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: TextWritingModeListValue = TextWritingModeListValue(Value.valueOf("TextWritingModeListValue.INITIAL"))

    /**
     * Default value for [TextWritingModeListValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: TextWritingModeListValue = TextWritingModeListValue(Value.nullValue())
  }
}

/**
 *  The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextWritingMode internal constructor(public val value: Value) {
  /**
   * Public companion object.
   */
  public companion object {

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
 * Controls the frame of reference for `icon-translate`. Default value: "map".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class IconTranslateAnchorValue(public val value: Value) {
  /**
   * Construct the [IconTranslateAnchorValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: IconTranslateAnchorValue = IconTranslateAnchorValue(Value.valueOf("IconTranslateAnchorValue.INITIAL"))

    /**
     * Default value for [IconTranslateAnchorValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: IconTranslateAnchorValue = IconTranslateAnchorValue(Value.nullValue())

    /**
     * Icons are translated relative to the map.
     */
    @JvmField
    public val MAP: IconTranslateAnchorValue = IconTranslateAnchorValue(Value("map"))

    /**
     * Icons are translated relative to the viewport.
     */
    @JvmField
    public val VIEWPORT: IconTranslateAnchorValue = IconTranslateAnchorValue(Value("viewport"))
  }
}

/**
 * Controls the frame of reference for `text-translate`. Default value: "map".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class TextTranslateAnchorValue(public val value: Value) {
  /**
   * Construct the [TextTranslateAnchorValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: TextTranslateAnchorValue = TextTranslateAnchorValue(Value.valueOf("TextTranslateAnchorValue.INITIAL"))

    /**
     * Default value for [TextTranslateAnchorValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: TextTranslateAnchorValue = TextTranslateAnchorValue(Value.nullValue())

    /**
     * The text is translated relative to the map.
     */
    @JvmField
    public val MAP: TextTranslateAnchorValue = TextTranslateAnchorValue(Value("map"))

    /**
     * The text is translated relative to the viewport.
     */
    @JvmField
    public val VIEWPORT: TextTranslateAnchorValue = TextTranslateAnchorValue(Value("viewport"))
  }
}

/**
 * Orientation of circle when map is pitched. Default value: "viewport".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class CirclePitchAlignmentValue(public val value: Value) {
  /**
   * Construct the [CirclePitchAlignmentValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: CirclePitchAlignmentValue = CirclePitchAlignmentValue(Value.valueOf("CirclePitchAlignmentValue.INITIAL"))

    /**
     * Default value for [CirclePitchAlignmentValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: CirclePitchAlignmentValue = CirclePitchAlignmentValue(Value.nullValue())

    /**
     * The circle is aligned to the plane of the map.
     */
    @JvmField
    public val MAP: CirclePitchAlignmentValue = CirclePitchAlignmentValue(Value("map"))

    /**
     * The circle is aligned to the plane of the viewport.
     */
    @JvmField
    public val VIEWPORT: CirclePitchAlignmentValue = CirclePitchAlignmentValue(Value("viewport"))
  }
}

/**
 * Controls the scaling behavior of the circle when the map is pitched. Default value: "map".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class CirclePitchScaleValue(public val value: Value) {
  /**
   * Construct the [CirclePitchScaleValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: CirclePitchScaleValue = CirclePitchScaleValue(Value.valueOf("CirclePitchScaleValue.INITIAL"))

    /**
     * Default value for [CirclePitchScaleValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: CirclePitchScaleValue = CirclePitchScaleValue(Value.nullValue())

    /**
     * Circles are scaled according to their apparent distance to the camera.
     */
    @JvmField
    public val MAP: CirclePitchScaleValue = CirclePitchScaleValue(Value("map"))

    /**
     * Circles are not scaled.
     */
    @JvmField
    public val VIEWPORT: CirclePitchScaleValue = CirclePitchScaleValue(Value("viewport"))
  }
}

/**
 * Controls the frame of reference for `circle-translate`. Default value: "map".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class CircleTranslateAnchorValue(public val value: Value) {
  /**
   * Construct the [CircleTranslateAnchorValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: CircleTranslateAnchorValue = CircleTranslateAnchorValue(Value.valueOf("CircleTranslateAnchorValue.INITIAL"))

    /**
     * Default value for [CircleTranslateAnchorValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: CircleTranslateAnchorValue = CircleTranslateAnchorValue(Value.nullValue())

    /**
     * The circle is translated relative to the map.
     */
    @JvmField
    public val MAP: CircleTranslateAnchorValue = CircleTranslateAnchorValue(Value("map"))

    /**
     * The circle is translated relative to the viewport.
     */
    @JvmField
    public val VIEWPORT: CircleTranslateAnchorValue = CircleTranslateAnchorValue(Value("viewport"))
  }
}

/**
 * Controls the frame of reference for `fill-extrusion-translate`. Default value: "map".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class FillExtrusionTranslateAnchorValue(public val value: Value) {
  /**
   * Construct the [FillExtrusionTranslateAnchorValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: FillExtrusionTranslateAnchorValue = FillExtrusionTranslateAnchorValue(Value.valueOf("FillExtrusionTranslateAnchorValue.INITIAL"))

    /**
     * Default value for [FillExtrusionTranslateAnchorValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: FillExtrusionTranslateAnchorValue = FillExtrusionTranslateAnchorValue(Value.nullValue())

    /**
     * The fill extrusion is translated relative to the map.
     */
    @JvmField
    public val MAP: FillExtrusionTranslateAnchorValue = FillExtrusionTranslateAnchorValue(Value("map"))

    /**
     * The fill extrusion is translated relative to the viewport.
     */
    @JvmField
    public val VIEWPORT: FillExtrusionTranslateAnchorValue = FillExtrusionTranslateAnchorValue(Value("viewport"))
  }
}

/**
 * The resampling/interpolation method to use for overscaling, also known as texture magnification filter Default value: "linear".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class RasterResamplingValue(public val value: Value) {
  /**
   * Construct the [RasterResamplingValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: RasterResamplingValue = RasterResamplingValue(Value.valueOf("RasterResamplingValue.INITIAL"))

    /**
     * Default value for [RasterResamplingValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: RasterResamplingValue = RasterResamplingValue(Value.nullValue())

    /**
     * (Bi)linear filtering interpolates pixel values using the weighted average of the four closest original source pixels creating a smooth but blurry look when overscaled
     */
    @JvmField
    public val LINEAR: RasterResamplingValue = RasterResamplingValue(Value("linear"))

    /**
     * Nearest neighbor filtering interpolates pixel values using the nearest original source pixel creating a sharp but pixelated look when overscaled
     */
    @JvmField
    public val NEAREST: RasterResamplingValue = RasterResamplingValue(Value("nearest"))
  }
}

/**
 * Direction of light source when map is rotated. Default value: "viewport".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class HillshadeIlluminationAnchorValue(public val value: Value) {
  /**
   * Construct the [HillshadeIlluminationAnchorValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: HillshadeIlluminationAnchorValue = HillshadeIlluminationAnchorValue(Value.valueOf("HillshadeIlluminationAnchorValue.INITIAL"))

    /**
     * Default value for [HillshadeIlluminationAnchorValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: HillshadeIlluminationAnchorValue = HillshadeIlluminationAnchorValue(Value.nullValue())

    /**
     * The hillshade illumination is relative to the north direction.
     */
    @JvmField
    public val MAP: HillshadeIlluminationAnchorValue = HillshadeIlluminationAnchorValue(Value("map"))

    /**
     * The hillshade illumination is relative to the top of the viewport.
     */
    @JvmField
    public val VIEWPORT: HillshadeIlluminationAnchorValue = HillshadeIlluminationAnchorValue(Value("viewport"))
  }
}

/**
 * Defines scaling mode. Only applies to location-indicator type layers. Default value: "map".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelScaleModeValue(public val value: Value) {
  /**
   * Construct the [ModelScaleModeValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: ModelScaleModeValue = ModelScaleModeValue(Value.valueOf("ModelScaleModeValue.INITIAL"))

    /**
     * Default value for [ModelScaleModeValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: ModelScaleModeValue = ModelScaleModeValue(Value.nullValue())

    /**
     * Model is scaled so that it's always the same size relative to other map features. The property model-scale specifies how many meters each unit in the model file should cover.
     */
    @JvmField
    public val MAP: ModelScaleModeValue = ModelScaleModeValue(Value("map"))

    /**
     * Model is scaled so that it's always the same size on the screen. The property model-scale specifies how many pixels each unit in model file should cover.
     */
    @JvmField
    public val VIEWPORT: ModelScaleModeValue = ModelScaleModeValue(Value("viewport"))
  }
}

/**
 * Defines rendering behavior of model in respect to other 3D scene objects. Default value: "common-3d".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class ModelTypeValue(public val value: Value) {
  /**
   * Construct the [ModelTypeValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: ModelTypeValue = ModelTypeValue(Value.valueOf("ModelTypeValue.INITIAL"))

    /**
     * Default value for [ModelTypeValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: ModelTypeValue = ModelTypeValue(Value.nullValue())

    /**
     * Integrated to 3D scene, using depth testing, along with terrain, fill-extrusions and custom layer.
     */
    @JvmField
    public val COMMON_3D: ModelTypeValue = ModelTypeValue(Value("common-3d"))

    /**
     * Displayed over other 3D content, occluded by terrain.
     */
    @JvmField
    public val LOCATION_INDICATOR: ModelTypeValue = ModelTypeValue(Value("location-indicator"))
  }
}

/**
 * The type of the sky Default value: "atmosphere".
 *
 * @param value the property wrapped in [Value] to be used with native renderer.
 */
@Immutable
@MapboxExperimental
public data class SkyTypeValue(public val value: Value) {
  /**
   * Construct the [SkyTypeValue] with [Mapbox Expression](https://docs.mapbox.com/style-spec/reference/expressions/).
   */
  public constructor(expression: Expression) : this(expression as Value)

  /**
   * True if the this value is not [INITIAL]
   */
  internal val notInitial: Boolean
    get() = this !== INITIAL

  /**
   * Public companion object.
   */
  public companion object {
    /**
     * Use this constant to signal that no property should be set to the Maps engine.
     * This is needed because sending nullValue resets the value of the property to the default one
     * defined by the Maps engine, which results in overriding the value from the loaded style.
     * Moreover, we set a custom String to differentiate it from [DEFAULT], otherwise things
     * like [kotlinx.coroutines.flow.Flow] or [androidx.compose.runtime.MutableState] won't be able
     * to differentiate them because they use [equals].
     */
    @JvmField
    internal val INITIAL: SkyTypeValue = SkyTypeValue(Value.valueOf("SkyTypeValue.INITIAL"))

    /**
     * Default value for [SkyTypeValue], setting default will result in restoring the property value defined in the style.
     */
    @JvmField
    public val DEFAULT: SkyTypeValue = SkyTypeValue(Value.nullValue())

    /**
     * Renders the sky with a gradient that can be configured with {@link SKY_GRADIENT_RADIUS} and {@link SKY_GRADIENT}.
     */
    @JvmField
    public val GRADIENT: SkyTypeValue = SkyTypeValue(Value("gradient"))

    /**
     * Renders the sky with a simulated atmospheric scattering algorithm, the sun direction can be attached to the light position or explicitly set through {@link SKY_ATMOSPHERE_SUN}.
     */
    @JvmField
    public val ATMOSPHERE: SkyTypeValue = SkyTypeValue(Value("atmosphere"))
  }
}
// End of generated file.