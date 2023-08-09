// This file is generated.

package com.mapbox.maps.extension.style.layers.properties.generated

import com.mapbox.maps.MapboxExperimental

/**
 * Paint/Layout properties for Layer
 */
internal interface LayerProperty {
  val value: String
}
// VISIBILITY: Whether this layer is displayed.
/**
 * The visibility of a layer.
 *
 * @param value String value of this property
 */
class Visibility private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is Visibility &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "Visibility(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The layer is shown.
     */
    @JvmField
    val VISIBLE = Visibility("visible")

    /**
     * The layer is hidden.
     */
    @JvmField
    val NONE = Visibility("none")

    /**
     * Utility function to get [Visibility] instance from given [value]
     */
    @JvmStatic
    fun valueOf(value: String): Visibility {
      return when (value) {
        "VISIBLE" -> VISIBLE
        "NONE" -> NONE
        else -> throw RuntimeException("Visibility.valueOf does not support [$value]")
      }
    }
  }
}

// LINE_CAP: The display of line endings.
/**
 * The display of line endings.
 *
 * @param value String value of this property
 */
class LineCap private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is LineCap &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "LineCap(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * A cap with a squared-off end which is drawn to the exact endpoint of the line.
     */
    @JvmField
    val BUTT = LineCap("butt")
    /**
     * A cap with a rounded end which is drawn beyond the endpoint of the line at a radius of one-half of the line's width and centered on the endpoint of the line.
     */
    @JvmField
    val ROUND = LineCap("round")
    /**
     * A cap with a squared-off end which is drawn beyond the endpoint of the line at a distance of one-half of the line's width.
     */
    @JvmField
    val SQUARE = LineCap("square")

    /**
     * Utility function to get [LineCap] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): LineCap {
      return when (value) {
        "BUTT" -> BUTT
        "ROUND" -> ROUND
        "SQUARE" -> SQUARE
        else -> throw RuntimeException("LineCap.valueOf does not support [$value]")
      }
    }
  }
}
// LINE_JOIN: The display of lines when joining.
/**
 * The display of lines when joining.
 *
 * @param value String value of this property
 */
class LineJoin private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is LineJoin &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "LineJoin(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * A join with a squared-off end which is drawn beyond the endpoint of the line at a distance of one-half of the line's width.
     */
    @JvmField
    val BEVEL = LineJoin("bevel")
    /**
     * A join with a rounded end which is drawn beyond the endpoint of the line at a radius of one-half of the line's width and centered on the endpoint of the line.
     */
    @JvmField
    val ROUND = LineJoin("round")
    /**
     * A join with a sharp, angled corner which is drawn with the outer sides beyond the endpoint of the path until they meet.
     */
    @JvmField
    val MITER = LineJoin("miter")

    /**
     * Utility function to get [LineJoin] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): LineJoin {
      return when (value) {
        "BEVEL" -> BEVEL
        "ROUND" -> ROUND
        "MITER" -> MITER
        else -> throw RuntimeException("LineJoin.valueOf does not support [$value]")
      }
    }
  }
}
// ICON_ANCHOR: Part of the icon placed closest to the anchor.
/**
 * Part of the icon placed closest to the anchor.
 *
 * @param value String value of this property
 */
class IconAnchor private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is IconAnchor &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "IconAnchor(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The center of the icon is placed closest to the anchor.
     */
    @JvmField
    val CENTER = IconAnchor("center")
    /**
     * The left side of the icon is placed closest to the anchor.
     */
    @JvmField
    val LEFT = IconAnchor("left")
    /**
     * The right side of the icon is placed closest to the anchor.
     */
    @JvmField
    val RIGHT = IconAnchor("right")
    /**
     * The top of the icon is placed closest to the anchor.
     */
    @JvmField
    val TOP = IconAnchor("top")
    /**
     * The bottom of the icon is placed closest to the anchor.
     */
    @JvmField
    val BOTTOM = IconAnchor("bottom")
    /**
     * The top left corner of the icon is placed closest to the anchor.
     */
    @JvmField
    val TOP_LEFT = IconAnchor("top-left")
    /**
     * The top right corner of the icon is placed closest to the anchor.
     */
    @JvmField
    val TOP_RIGHT = IconAnchor("top-right")
    /**
     * The bottom left corner of the icon is placed closest to the anchor.
     */
    @JvmField
    val BOTTOM_LEFT = IconAnchor("bottom-left")
    /**
     * The bottom right corner of the icon is placed closest to the anchor.
     */
    @JvmField
    val BOTTOM_RIGHT = IconAnchor("bottom-right")

    /**
     * Utility function to get [IconAnchor] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): IconAnchor {
      return when (value) {
        "CENTER" -> CENTER
        "LEFT" -> LEFT
        "RIGHT" -> RIGHT
        "TOP" -> TOP
        "BOTTOM" -> BOTTOM
        "TOP_LEFT" -> TOP_LEFT
        "TOP_RIGHT" -> TOP_RIGHT
        "BOTTOM_LEFT" -> BOTTOM_LEFT
        "BOTTOM_RIGHT" -> BOTTOM_RIGHT
        else -> throw RuntimeException("IconAnchor.valueOf does not support [$value]")
      }
    }
  }
}
// ICON_PITCH_ALIGNMENT: Orientation of icon when map is pitched.
/**
 * Orientation of icon when map is pitched.
 *
 * @param value String value of this property
 */
class IconPitchAlignment private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is IconPitchAlignment &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "IconPitchAlignment(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The icon is aligned to the plane of the map.
     */
    @JvmField
    val MAP = IconPitchAlignment("map")
    /**
     * The icon is aligned to the plane of the viewport.
     */
    @JvmField
    val VIEWPORT = IconPitchAlignment("viewport")
    /**
     * Automatically matches the value of {@link ICON_ROTATION_ALIGNMENT}.
     */
    @JvmField
    val AUTO = IconPitchAlignment("auto")

    /**
     * Utility function to get [IconPitchAlignment] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): IconPitchAlignment {
      return when (value) {
        "MAP" -> MAP
        "VIEWPORT" -> VIEWPORT
        "AUTO" -> AUTO
        else -> throw RuntimeException("IconPitchAlignment.valueOf does not support [$value]")
      }
    }
  }
}
// ICON_ROTATION_ALIGNMENT: In combination with `symbol-placement`, determines the rotation behavior of icons.
/**
 * In combination with `symbol-placement`, determines the rotation behavior of icons.
 *
 * @param value String value of this property
 */
class IconRotationAlignment private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is IconRotationAlignment &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "IconRotationAlignment(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_POINT}, aligns icons east-west. When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_LINE} or {@link Property#SYMBOL_PLACEMENT_LINE_CENTER}, aligns icon x-axes with the line.
     */
    @JvmField
    val MAP = IconRotationAlignment("map")
    /**
     * Produces icons whose x-axes are aligned with the x-axis of the viewport, regardless of the value of {@link SYMBOL_PLACEMENT}.
     */
    @JvmField
    val VIEWPORT = IconRotationAlignment("viewport")
    /**
     * When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_POINT}, this is equivalent to {@link Property#ICON_ROTATION_ALIGNMENT_VIEWPORT}. When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_LINE} or {@link Property#SYMBOL_PLACEMENT_LINE_CENTER}, this is equivalent to {@link Property#ICON_ROTATION_ALIGNMENT_MAP}.
     */
    @JvmField
    val AUTO = IconRotationAlignment("auto")

    /**
     * Utility function to get [IconRotationAlignment] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): IconRotationAlignment {
      return when (value) {
        "MAP" -> MAP
        "VIEWPORT" -> VIEWPORT
        "AUTO" -> AUTO
        else -> throw RuntimeException("IconRotationAlignment.valueOf does not support [$value]")
      }
    }
  }
}
// ICON_TEXT_FIT: Scales the icon to fit around the associated text.
/**
 * Scales the icon to fit around the associated text.
 *
 * @param value String value of this property
 */
class IconTextFit private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is IconTextFit &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "IconTextFit(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The icon is displayed at its intrinsic aspect ratio.
     */
    @JvmField
    val NONE = IconTextFit("none")
    /**
     * The icon is scaled in the x-dimension to fit the width of the text.
     */
    @JvmField
    val WIDTH = IconTextFit("width")
    /**
     * The icon is scaled in the y-dimension to fit the height of the text.
     */
    @JvmField
    val HEIGHT = IconTextFit("height")
    /**
     * The icon is scaled in both x- and y-dimensions.
     */
    @JvmField
    val BOTH = IconTextFit("both")

    /**
     * Utility function to get [IconTextFit] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): IconTextFit {
      return when (value) {
        "NONE" -> NONE
        "WIDTH" -> WIDTH
        "HEIGHT" -> HEIGHT
        "BOTH" -> BOTH
        else -> throw RuntimeException("IconTextFit.valueOf does not support [$value]")
      }
    }
  }
}
// SYMBOL_PLACEMENT: Label placement relative to its geometry.
/**
 * Label placement relative to its geometry.
 *
 * @param value String value of this property
 */
class SymbolPlacement private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is SymbolPlacement &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "SymbolPlacement(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The label is placed at the point where the geometry is located.
     */
    @JvmField
    val POINT = SymbolPlacement("point")
    /**
     * The label is placed along the line of the geometry. Can only be used on LineString and Polygon geometries.
     */
    @JvmField
    val LINE = SymbolPlacement("line")
    /**
     * The label is placed at the center of the line of the geometry. Can only be used on LineString and Polygon geometries. Note that a single feature in a vector tile may contain multiple line geometries.
     */
    @JvmField
    val LINE_CENTER = SymbolPlacement("line-center")

    /**
     * Utility function to get [SymbolPlacement] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): SymbolPlacement {
      return when (value) {
        "POINT" -> POINT
        "LINE" -> LINE
        "LINE_CENTER" -> LINE_CENTER
        else -> throw RuntimeException("SymbolPlacement.valueOf does not support [$value]")
      }
    }
  }
}
// SYMBOL_Z_ORDER: Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
/**
 * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
 *
 * @param value String value of this property
 */
class SymbolZOrder private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is SymbolZOrder &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "SymbolZOrder(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * Sorts symbols by symbol sort key if set. Otherwise, sorts symbols by their y-position relative to the viewport if {@link ICON_ALLOW_OVERLAP} or {@link TEXT_ALLOW_OVERLAP} is set to {@link TRUE} or {@link ICON_IGNORE_PLACEMENT} or {@link TEXT_IGNORE_PLACEMENT} is {@link FALSE}.
     */
    @JvmField
    val AUTO = SymbolZOrder("auto")
    /**
     * Sorts symbols by their y-position relative to the viewport if {@link ICON_ALLOW_OVERLAP} or {@link TEXT_ALLOW_OVERLAP} is set to {@link TRUE} or {@link ICON_IGNORE_PLACEMENT} or {@link TEXT_IGNORE_PLACEMENT} is {@link FALSE}.
     */
    @JvmField
    val VIEWPORT_Y = SymbolZOrder("viewport-y")
    /**
     * Sorts symbols by symbol sort key if set. Otherwise, no sorting is applied; symbols are rendered in the same order as the source data.
     */
    @JvmField
    val SOURCE = SymbolZOrder("source")

    /**
     * Utility function to get [SymbolZOrder] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): SymbolZOrder {
      return when (value) {
        "AUTO" -> AUTO
        "VIEWPORT_Y" -> VIEWPORT_Y
        "SOURCE" -> SOURCE
        else -> throw RuntimeException("SymbolZOrder.valueOf does not support [$value]")
      }
    }
  }
}
// TEXT_ANCHOR: Part of the text placed closest to the anchor.
/**
 * Part of the text placed closest to the anchor.
 *
 * @param value String value of this property
 */
class TextAnchor private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is TextAnchor &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "TextAnchor(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The center of the text is placed closest to the anchor.
     */
    @JvmField
    val CENTER = TextAnchor("center")
    /**
     * The left side of the text is placed closest to the anchor.
     */
    @JvmField
    val LEFT = TextAnchor("left")
    /**
     * The right side of the text is placed closest to the anchor.
     */
    @JvmField
    val RIGHT = TextAnchor("right")
    /**
     * The top of the text is placed closest to the anchor.
     */
    @JvmField
    val TOP = TextAnchor("top")
    /**
     * The bottom of the text is placed closest to the anchor.
     */
    @JvmField
    val BOTTOM = TextAnchor("bottom")
    /**
     * The top left corner of the text is placed closest to the anchor.
     */
    @JvmField
    val TOP_LEFT = TextAnchor("top-left")
    /**
     * The top right corner of the text is placed closest to the anchor.
     */
    @JvmField
    val TOP_RIGHT = TextAnchor("top-right")
    /**
     * The bottom left corner of the text is placed closest to the anchor.
     */
    @JvmField
    val BOTTOM_LEFT = TextAnchor("bottom-left")
    /**
     * The bottom right corner of the text is placed closest to the anchor.
     */
    @JvmField
    val BOTTOM_RIGHT = TextAnchor("bottom-right")

    /**
     * Utility function to get [TextAnchor] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): TextAnchor {
      return when (value) {
        "CENTER" -> CENTER
        "LEFT" -> LEFT
        "RIGHT" -> RIGHT
        "TOP" -> TOP
        "BOTTOM" -> BOTTOM
        "TOP_LEFT" -> TOP_LEFT
        "TOP_RIGHT" -> TOP_RIGHT
        "BOTTOM_LEFT" -> BOTTOM_LEFT
        "BOTTOM_RIGHT" -> BOTTOM_RIGHT
        else -> throw RuntimeException("TextAnchor.valueOf does not support [$value]")
      }
    }
  }
}
// TEXT_JUSTIFY: Text justification options.
/**
 * Text justification options.
 *
 * @param value String value of this property
 */
class TextJustify private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is TextJustify &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "TextJustify(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The text is aligned towards the anchor position.
     */
    @JvmField
    val AUTO = TextJustify("auto")
    /**
     * The text is aligned to the left.
     */
    @JvmField
    val LEFT = TextJustify("left")
    /**
     * The text is centered.
     */
    @JvmField
    val CENTER = TextJustify("center")
    /**
     * The text is aligned to the right.
     */
    @JvmField
    val RIGHT = TextJustify("right")

    /**
     * Utility function to get [TextJustify] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): TextJustify {
      return when (value) {
        "AUTO" -> AUTO
        "LEFT" -> LEFT
        "CENTER" -> CENTER
        "RIGHT" -> RIGHT
        else -> throw RuntimeException("TextJustify.valueOf does not support [$value]")
      }
    }
  }
}
// TEXT_PITCH_ALIGNMENT: Orientation of text when map is pitched.
/**
 * Orientation of text when map is pitched.
 *
 * @param value String value of this property
 */
class TextPitchAlignment private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is TextPitchAlignment &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "TextPitchAlignment(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The text is aligned to the plane of the map.
     */
    @JvmField
    val MAP = TextPitchAlignment("map")
    /**
     * The text is aligned to the plane of the viewport.
     */
    @JvmField
    val VIEWPORT = TextPitchAlignment("viewport")
    /**
     * Automatically matches the value of {@link TEXT_ROTATION_ALIGNMENT}.
     */
    @JvmField
    val AUTO = TextPitchAlignment("auto")

    /**
     * Utility function to get [TextPitchAlignment] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): TextPitchAlignment {
      return when (value) {
        "MAP" -> MAP
        "VIEWPORT" -> VIEWPORT
        "AUTO" -> AUTO
        else -> throw RuntimeException("TextPitchAlignment.valueOf does not support [$value]")
      }
    }
  }
}
// TEXT_ROTATION_ALIGNMENT: In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
/**
 * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
 *
 * @param value String value of this property
 */
class TextRotationAlignment private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is TextRotationAlignment &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "TextRotationAlignment(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_POINT}, aligns text east-west. When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_LINE} or {@link Property#SYMBOL_PLACEMENT_LINE_CENTER}, aligns text x-axes with the line.
     */
    @JvmField
    val MAP = TextRotationAlignment("map")
    /**
     * Produces glyphs whose x-axes are aligned with the x-axis of the viewport, regardless of the value of {@link SYMBOL_PLACEMENT}.
     */
    @JvmField
    val VIEWPORT = TextRotationAlignment("viewport")
    /**
     * When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_POINT}, this is equivalent to {@link Property#TEXT_ROTATION_ALIGNMENT_VIEWPORT}. When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_LINE} or {@link Property#SYMBOL_PLACEMENT_LINE_CENTER}, this is equivalent to {@link Property#TEXT_ROTATION_ALIGNMENT_MAP}.
     */
    @JvmField
    val AUTO = TextRotationAlignment("auto")

    /**
     * Utility function to get [TextRotationAlignment] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): TextRotationAlignment {
      return when (value) {
        "MAP" -> MAP
        "VIEWPORT" -> VIEWPORT
        "AUTO" -> AUTO
        else -> throw RuntimeException("TextRotationAlignment.valueOf does not support [$value]")
      }
    }
  }
}
// TEXT_TRANSFORM: Specifies how to capitalize text, similar to the CSS `text-transform` property.
/**
 * Specifies how to capitalize text, similar to the CSS `text-transform` property.
 *
 * @param value String value of this property
 */
class TextTransform private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is TextTransform &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "TextTransform(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The text is not altered.
     */
    @JvmField
    val NONE = TextTransform("none")
    /**
     * Forces all letters to be displayed in uppercase.
     */
    @JvmField
    val UPPERCASE = TextTransform("uppercase")
    /**
     * Forces all letters to be displayed in lowercase.
     */
    @JvmField
    val LOWERCASE = TextTransform("lowercase")

    /**
     * Utility function to get [TextTransform] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): TextTransform {
      return when (value) {
        "NONE" -> NONE
        "UPPERCASE" -> UPPERCASE
        "LOWERCASE" -> LOWERCASE
        else -> throw RuntimeException("TextTransform.valueOf does not support [$value]")
      }
    }
  }
}
// FILL_TRANSLATE_ANCHOR: Controls the frame of reference for `fill-translate`.
/**
 * Controls the frame of reference for `fill-translate`.
 *
 * @param value String value of this property
 */
class FillTranslateAnchor private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is FillTranslateAnchor &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "FillTranslateAnchor(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The fill is translated relative to the map.
     */
    @JvmField
    val MAP = FillTranslateAnchor("map")
    /**
     * The fill is translated relative to the viewport.
     */
    @JvmField
    val VIEWPORT = FillTranslateAnchor("viewport")

    /**
     * Utility function to get [FillTranslateAnchor] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): FillTranslateAnchor {
      return when (value) {
        "MAP" -> MAP
        "VIEWPORT" -> VIEWPORT
        else -> throw RuntimeException("FillTranslateAnchor.valueOf does not support [$value]")
      }
    }
  }
}
// LINE_TRANSLATE_ANCHOR: Controls the frame of reference for `line-translate`.
/**
 * Controls the frame of reference for `line-translate`.
 *
 * @param value String value of this property
 */
class LineTranslateAnchor private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is LineTranslateAnchor &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "LineTranslateAnchor(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The line is translated relative to the map.
     */
    @JvmField
    val MAP = LineTranslateAnchor("map")
    /**
     * The line is translated relative to the viewport.
     */
    @JvmField
    val VIEWPORT = LineTranslateAnchor("viewport")

    /**
     * Utility function to get [LineTranslateAnchor] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): LineTranslateAnchor {
      return when (value) {
        "MAP" -> MAP
        "VIEWPORT" -> VIEWPORT
        else -> throw RuntimeException("LineTranslateAnchor.valueOf does not support [$value]")
      }
    }
  }
}
// ICON_TRANSLATE_ANCHOR: Controls the frame of reference for `icon-translate`.
/**
 * Controls the frame of reference for `icon-translate`.
 *
 * @param value String value of this property
 */
class IconTranslateAnchor private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is IconTranslateAnchor &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "IconTranslateAnchor(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * Icons are translated relative to the map.
     */
    @JvmField
    val MAP = IconTranslateAnchor("map")
    /**
     * Icons are translated relative to the viewport.
     */
    @JvmField
    val VIEWPORT = IconTranslateAnchor("viewport")

    /**
     * Utility function to get [IconTranslateAnchor] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): IconTranslateAnchor {
      return when (value) {
        "MAP" -> MAP
        "VIEWPORT" -> VIEWPORT
        else -> throw RuntimeException("IconTranslateAnchor.valueOf does not support [$value]")
      }
    }
  }
}
// TEXT_TRANSLATE_ANCHOR: Controls the frame of reference for `text-translate`.
/**
 * Controls the frame of reference for `text-translate`.
 *
 * @param value String value of this property
 */
class TextTranslateAnchor private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is TextTranslateAnchor &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "TextTranslateAnchor(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The text is translated relative to the map.
     */
    @JvmField
    val MAP = TextTranslateAnchor("map")
    /**
     * The text is translated relative to the viewport.
     */
    @JvmField
    val VIEWPORT = TextTranslateAnchor("viewport")

    /**
     * Utility function to get [TextTranslateAnchor] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): TextTranslateAnchor {
      return when (value) {
        "MAP" -> MAP
        "VIEWPORT" -> VIEWPORT
        else -> throw RuntimeException("TextTranslateAnchor.valueOf does not support [$value]")
      }
    }
  }
}
// CIRCLE_PITCH_ALIGNMENT: Orientation of circle when map is pitched.
/**
 * Orientation of circle when map is pitched.
 *
 * @param value String value of this property
 */
class CirclePitchAlignment private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is CirclePitchAlignment &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "CirclePitchAlignment(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The circle is aligned to the plane of the map.
     */
    @JvmField
    val MAP = CirclePitchAlignment("map")
    /**
     * The circle is aligned to the plane of the viewport.
     */
    @JvmField
    val VIEWPORT = CirclePitchAlignment("viewport")

    /**
     * Utility function to get [CirclePitchAlignment] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): CirclePitchAlignment {
      return when (value) {
        "MAP" -> MAP
        "VIEWPORT" -> VIEWPORT
        else -> throw RuntimeException("CirclePitchAlignment.valueOf does not support [$value]")
      }
    }
  }
}
// CIRCLE_PITCH_SCALE: Controls the scaling behavior of the circle when the map is pitched.
/**
 * Controls the scaling behavior of the circle when the map is pitched.
 *
 * @param value String value of this property
 */
class CirclePitchScale private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is CirclePitchScale &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "CirclePitchScale(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * Circles are scaled according to their apparent distance to the camera.
     */
    @JvmField
    val MAP = CirclePitchScale("map")
    /**
     * Circles are not scaled.
     */
    @JvmField
    val VIEWPORT = CirclePitchScale("viewport")

    /**
     * Utility function to get [CirclePitchScale] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): CirclePitchScale {
      return when (value) {
        "MAP" -> MAP
        "VIEWPORT" -> VIEWPORT
        else -> throw RuntimeException("CirclePitchScale.valueOf does not support [$value]")
      }
    }
  }
}
// CIRCLE_TRANSLATE_ANCHOR: Controls the frame of reference for `circle-translate`.
/**
 * Controls the frame of reference for `circle-translate`.
 *
 * @param value String value of this property
 */
class CircleTranslateAnchor private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is CircleTranslateAnchor &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "CircleTranslateAnchor(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The circle is translated relative to the map.
     */
    @JvmField
    val MAP = CircleTranslateAnchor("map")
    /**
     * The circle is translated relative to the viewport.
     */
    @JvmField
    val VIEWPORT = CircleTranslateAnchor("viewport")

    /**
     * Utility function to get [CircleTranslateAnchor] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): CircleTranslateAnchor {
      return when (value) {
        "MAP" -> MAP
        "VIEWPORT" -> VIEWPORT
        else -> throw RuntimeException("CircleTranslateAnchor.valueOf does not support [$value]")
      }
    }
  }
}
// FILL_EXTRUSION_TRANSLATE_ANCHOR: Controls the frame of reference for `fill-extrusion-translate`.
/**
 * Controls the frame of reference for `fill-extrusion-translate`.
 *
 * @param value String value of this property
 */
class FillExtrusionTranslateAnchor private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is FillExtrusionTranslateAnchor &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "FillExtrusionTranslateAnchor(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The fill extrusion is translated relative to the map.
     */
    @JvmField
    val MAP = FillExtrusionTranslateAnchor("map")
    /**
     * The fill extrusion is translated relative to the viewport.
     */
    @JvmField
    val VIEWPORT = FillExtrusionTranslateAnchor("viewport")

    /**
     * Utility function to get [FillExtrusionTranslateAnchor] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): FillExtrusionTranslateAnchor {
      return when (value) {
        "MAP" -> MAP
        "VIEWPORT" -> VIEWPORT
        else -> throw RuntimeException("FillExtrusionTranslateAnchor.valueOf does not support [$value]")
      }
    }
  }
}
// RASTER_RESAMPLING: The resampling/interpolation method to use for overscaling, also known as texture magnification filter
/**
 * The resampling/interpolation method to use for overscaling, also known as texture magnification filter
 *
 * @param value String value of this property
 */
class RasterResampling private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is RasterResampling &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "RasterResampling(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * (Bi)linear filtering interpolates pixel values using the weighted average of the four closest original source pixels creating a smooth but blurry look when overscaled
     */
    @JvmField
    val LINEAR = RasterResampling("linear")
    /**
     * Nearest neighbor filtering interpolates pixel values using the nearest original source pixel creating a sharp but pixelated look when overscaled
     */
    @JvmField
    val NEAREST = RasterResampling("nearest")

    /**
     * Utility function to get [RasterResampling] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): RasterResampling {
      return when (value) {
        "LINEAR" -> LINEAR
        "NEAREST" -> NEAREST
        else -> throw RuntimeException("RasterResampling.valueOf does not support [$value]")
      }
    }
  }
}
// HILLSHADE_ILLUMINATION_ANCHOR: Direction of light source when map is rotated.
/**
 * Direction of light source when map is rotated.
 *
 * @param value String value of this property
 */
class HillshadeIlluminationAnchor private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is HillshadeIlluminationAnchor &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "HillshadeIlluminationAnchor(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The hillshade illumination is relative to the north direction.
     */
    @JvmField
    val MAP = HillshadeIlluminationAnchor("map")
    /**
     * The hillshade illumination is relative to the top of the viewport.
     */
    @JvmField
    val VIEWPORT = HillshadeIlluminationAnchor("viewport")

    /**
     * Utility function to get [HillshadeIlluminationAnchor] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): HillshadeIlluminationAnchor {
      return when (value) {
        "MAP" -> MAP
        "VIEWPORT" -> VIEWPORT
        else -> throw RuntimeException("HillshadeIlluminationAnchor.valueOf does not support [$value]")
      }
    }
  }
}
// MODEL_SCALE_MODE: Defines scaling mode. Only applies to location-indicator type layers.
/**
 * Defines scaling mode. Only applies to location-indicator type layers.
 *
 * @param value String value of this property
 */
@MapboxExperimental
class ModelScaleMode private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is ModelScaleMode &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "ModelScaleMode(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * Model is scaled so that it's always the same size relative to other map features. The property model-scale specifies how many meters each unit in the model file should cover.
     */
    @JvmField
    val MAP = ModelScaleMode("map")
    /**
     * Model is scaled so that it's always the same size on the screen. The property model-scale specifies how many pixels each unit in model file should cover.
     */
    @JvmField
    val VIEWPORT = ModelScaleMode("viewport")

    /**
     * Utility function to get [ModelScaleMode] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): ModelScaleMode {
      return when (value) {
        "MAP" -> MAP
        "VIEWPORT" -> VIEWPORT
        else -> throw RuntimeException("ModelScaleMode.valueOf does not support [$value]")
      }
    }
  }
}
// MODEL_TYPE: Defines rendering behavior of model in respect to other 3D scene objects.
/**
 * Defines rendering behavior of model in respect to other 3D scene objects.
 *
 * @param value String value of this property
 */
@MapboxExperimental
class ModelType private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is ModelType &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "ModelType(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * Integrated to 3D scene, using depth testing, along with terrain, fill-extrusions and custom layer.
     */
    @JvmField
    val COMMON_3D = ModelType("common-3d")
    /**
     * Displayed over other 3D content, occluded by terrain.
     */
    @JvmField
    val LOCATION_INDICATOR = ModelType("location-indicator")

    /**
     * Utility function to get [ModelType] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): ModelType {
      return when (value) {
        "COMMON_3D" -> COMMON_3D
        "LOCATION_INDICATOR" -> LOCATION_INDICATOR
        else -> throw RuntimeException("ModelType.valueOf does not support [$value]")
      }
    }
  }
}
// SKY_TYPE: The type of the sky
/**
 * The type of the sky
 *
 * @param value String value of this property
 */
class SkyType private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is SkyType &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "SkyType(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * Renders the sky with a gradient that can be configured with {@link SKY_GRADIENT_RADIUS} and {@link SKY_GRADIENT}.
     */
    @JvmField
    val GRADIENT = SkyType("gradient")
    /**
     * Renders the sky with a simulated atmospheric scattering algorithm, the sun direction can be attached to the light position or explicitly set through {@link SKY_ATMOSPHERE_SUN}.
     */
    @JvmField
    val ATMOSPHERE = SkyType("atmosphere")

    /**
     * Utility function to get [SkyType] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): SkyType {
      return when (value) {
        "GRADIENT" -> GRADIENT
        "ATMOSPHERE" -> ATMOSPHERE
        else -> throw RuntimeException("SkyType.valueOf does not support [$value]")
      }
    }
  }
}
// ANCHOR: Whether extruded geometries are lit relative to the map or viewport.
/**
 * Whether extruded geometries are lit relative to the map or viewport.
 *
 * @param value String value of this property
 */
class Anchor private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is Anchor &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "Anchor(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The position of the light source is aligned to the rotation of the map.
     */
    @JvmField
    val MAP = Anchor("map")
    /**
     * The position of the light source is aligned to the rotation of the viewport.
     */
    @JvmField
    val VIEWPORT = Anchor("viewport")

    /**
     * Utility function to get [Anchor] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): Anchor {
      return when (value) {
        "MAP" -> MAP
        "VIEWPORT" -> VIEWPORT
        else -> throw RuntimeException("Anchor.valueOf does not support [$value]")
      }
    }
  }
}
// NAME: The name of the projection to be used for rendering the map.
/**
 * The name of the projection to be used for rendering the map.
 *
 * @param value String value of this property
 */
class ProjectionName private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is ProjectionName &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "ProjectionName(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * The Mercator projection is the default projection.
     */
    @JvmField
    val MERCATOR = ProjectionName("mercator")
    /**
     * A globe projection.
     */
    @JvmField
    val GLOBE = ProjectionName("globe")

    /**
     * Utility function to get [ProjectionName] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): ProjectionName {
      return when (value) {
        "MERCATOR" -> MERCATOR
        "GLOBE" -> GLOBE
        else -> throw RuntimeException("ProjectionName.valueOf does not support [$value]")
      }
    }
  }
}
// TEXT_WRITING_MODE: The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
/**
 * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. For symbol with point placement, the order of elements in an array define priority order for the placement of an orientation variant. For symbol with line placement, the default text writing mode is either ['horizontal', 'vertical'] or ['vertical', 'horizontal'], the order doesn't affect the placement.
 *
 * @param value String value of this property
 */
class TextWritingMode private constructor(override val value: String) : LayerProperty {

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is TextWritingMode &&
    value == other.value

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode() = value.hashCode()

  /**
   * Returns a String for the object.
   */
  override fun toString() = "TextWritingMode(value=$value)"

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * If a text's language supports horizontal writing mode, symbols would be laid out horizontally.
     */
    @JvmField
    val HORIZONTAL = TextWritingMode("horizontal")
    /**
     * If a text's language supports vertical writing mode, symbols would be laid out vertically.
     */
    @JvmField
    val VERTICAL = TextWritingMode("vertical")

    /**
     * Utility function to get [TextWritingMode] instance from given [value].
     */
    @JvmStatic
    fun valueOf(value: String): TextWritingMode {
      return when (value) {
        "HORIZONTAL" -> HORIZONTAL
        "VERTICAL" -> VERTICAL
        else -> throw RuntimeException("TextWritingMode.valueOf does not support [$value]")
      }
    }
  }
}