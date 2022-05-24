// This file is generated.

package com.mapbox.maps.extension.style.layers.properties.generated

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
enum class Visibility(override val value: String) : LayerProperty {
  /**
   * The layer is shown.
   */
  VISIBLE("visible"),
  /**
   * The layer is hidden.
   */
  NONE("none")
}

// LINE_CAP: The display of line endings.
/**
 * The display of line endings.
 *
 * @param value String value of this property
 */
enum class LineCap(override val value: String) : LayerProperty {
  /**
   * A cap with a squared-off end which is drawn to the exact endpoint of the line.
   */
  BUTT("butt"),
  /**
   * A cap with a rounded end which is drawn beyond the endpoint of the line at a radius of one-half of the line's width and centered on the endpoint of the line.
   */
  ROUND("round"),
  /**
   * A cap with a squared-off end which is drawn beyond the endpoint of the line at a distance of one-half of the line's width.
   */
  SQUARE("square"),
}
// LINE_JOIN: The display of lines when joining.
/**
 * The display of lines when joining.
 *
 * @param value String value of this property
 */
enum class LineJoin(override val value: String) : LayerProperty {
  /**
   * A join with a squared-off end which is drawn beyond the endpoint of the line at a distance of one-half of the line's width.
   */
  BEVEL("bevel"),
  /**
   * A join with a rounded end which is drawn beyond the endpoint of the line at a radius of one-half of the line's width and centered on the endpoint of the line.
   */
  ROUND("round"),
  /**
   * A join with a sharp, angled corner which is drawn with the outer sides beyond the endpoint of the path until they meet.
   */
  MITER("miter"),
}
// ICON_ANCHOR: Part of the icon placed closest to the anchor.
/**
 * Part of the icon placed closest to the anchor.
 *
 * @param value String value of this property
 */
enum class IconAnchor(override val value: String) : LayerProperty {
  /**
   * The center of the icon is placed closest to the anchor.
   */
  CENTER("center"),
  /**
   * The left side of the icon is placed closest to the anchor.
   */
  LEFT("left"),
  /**
   * The right side of the icon is placed closest to the anchor.
   */
  RIGHT("right"),
  /**
   * The top of the icon is placed closest to the anchor.
   */
  TOP("top"),
  /**
   * The bottom of the icon is placed closest to the anchor.
   */
  BOTTOM("bottom"),
  /**
   * The top left corner of the icon is placed closest to the anchor.
   */
  TOP_LEFT("top-left"),
  /**
   * The top right corner of the icon is placed closest to the anchor.
   */
  TOP_RIGHT("top-right"),
  /**
   * The bottom left corner of the icon is placed closest to the anchor.
   */
  BOTTOM_LEFT("bottom-left"),
  /**
   * The bottom right corner of the icon is placed closest to the anchor.
   */
  BOTTOM_RIGHT("bottom-right"),
}
// ICON_PITCH_ALIGNMENT: Orientation of icon when map is pitched.
/**
 * Orientation of icon when map is pitched.
 *
 * @param value String value of this property
 */
enum class IconPitchAlignment(override val value: String) : LayerProperty {
  /**
   * The icon is aligned to the plane of the map.
   */
  MAP("map"),
  /**
   * The icon is aligned to the plane of the viewport.
   */
  VIEWPORT("viewport"),
  /**
   * Automatically matches the value of {@link ICON_ROTATION_ALIGNMENT}.
   */
  AUTO("auto"),
}
// ICON_ROTATION_ALIGNMENT: In combination with `symbol-placement`, determines the rotation behavior of icons.
/**
 * In combination with `symbol-placement`, determines the rotation behavior of icons.
 *
 * @param value String value of this property
 */
enum class IconRotationAlignment(override val value: String) : LayerProperty {
  /**
   * When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_POINT}, aligns icons east-west. When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_LINE} or {@link Property#SYMBOL_PLACEMENT_LINE_CENTER}, aligns icon x-axes with the line.
   */
  MAP("map"),
  /**
   * Produces icons whose x-axes are aligned with the x-axis of the viewport, regardless of the value of {@link SYMBOL_PLACEMENT}.
   */
  VIEWPORT("viewport"),
  /**
   * When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_POINT}, this is equivalent to {@link Property#ICON_ROTATION_ALIGNMENT_VIEWPORT}. When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_LINE} or {@link Property#SYMBOL_PLACEMENT_LINE_CENTER}, this is equivalent to {@link Property#ICON_ROTATION_ALIGNMENT_MAP}.
   */
  AUTO("auto"),
}
// ICON_TEXT_FIT: Scales the icon to fit around the associated text.
/**
 * Scales the icon to fit around the associated text.
 *
 * @param value String value of this property
 */
enum class IconTextFit(override val value: String) : LayerProperty {
  /**
   * The icon is displayed at its intrinsic aspect ratio.
   */
  NONE("none"),
  /**
   * The icon is scaled in the x-dimension to fit the width of the text.
   */
  WIDTH("width"),
  /**
   * The icon is scaled in the y-dimension to fit the height of the text.
   */
  HEIGHT("height"),
  /**
   * The icon is scaled in both x- and y-dimensions.
   */
  BOTH("both"),
}
// SYMBOL_PLACEMENT: Label placement relative to its geometry.
/**
 * Label placement relative to its geometry.
 *
 * @param value String value of this property
 */
enum class SymbolPlacement(override val value: String) : LayerProperty {
  /**
   * The label is placed at the point where the geometry is located.
   */
  POINT("point"),
  /**
   * The label is placed along the line of the geometry. Can only be used on LineString and Polygon geometries.
   */
  LINE("line"),
  /**
   * The label is placed at the center of the line of the geometry. Can only be used on LineString and Polygon geometries. Note that a single feature in a vector tile may contain multiple line geometries.
   */
  LINE_CENTER("line-center"),
}
// SYMBOL_Z_ORDER: Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
/**
 * Determines whether overlapping symbols in the same layer are rendered in the order that they appear in the data source or by their y-position relative to the viewport. To control the order and prioritization of symbols otherwise, use `symbol-sort-key`.
 *
 * @param value String value of this property
 */
enum class SymbolZOrder(override val value: String) : LayerProperty {
  /**
   * Sorts symbols by symbol sort key if set. Otherwise, sorts symbols by their y-position relative to the viewport if {@link ICON_ALLOW_OVERLAP} or {@link TEXT_ALLOW_OVERLAP} is set to {@link TRUE} or {@link ICON_IGNORE_PLACEMENT} or {@link TEXT_IGNORE_PLACEMENT} is {@link FALSE}.
   */
  AUTO("auto"),
  /**
   * Sorts symbols by their y-position relative to the viewport if {@link ICON_ALLOW_OVERLAP} or {@link TEXT_ALLOW_OVERLAP} is set to {@link TRUE} or {@link ICON_IGNORE_PLACEMENT} or {@link TEXT_IGNORE_PLACEMENT} is {@link FALSE}.
   */
  VIEWPORT_Y("viewport-y"),
  /**
   * Sorts symbols by symbol sort key if set. Otherwise, no sorting is applied; symbols are rendered in the same order as the source data.
   */
  SOURCE("source"),
}
// TEXT_ANCHOR: Part of the text placed closest to the anchor.
/**
 * Part of the text placed closest to the anchor.
 *
 * @param value String value of this property
 */
enum class TextAnchor(override val value: String) : LayerProperty {
  /**
   * The center of the text is placed closest to the anchor.
   */
  CENTER("center"),
  /**
   * The left side of the text is placed closest to the anchor.
   */
  LEFT("left"),
  /**
   * The right side of the text is placed closest to the anchor.
   */
  RIGHT("right"),
  /**
   * The top of the text is placed closest to the anchor.
   */
  TOP("top"),
  /**
   * The bottom of the text is placed closest to the anchor.
   */
  BOTTOM("bottom"),
  /**
   * The top left corner of the text is placed closest to the anchor.
   */
  TOP_LEFT("top-left"),
  /**
   * The top right corner of the text is placed closest to the anchor.
   */
  TOP_RIGHT("top-right"),
  /**
   * The bottom left corner of the text is placed closest to the anchor.
   */
  BOTTOM_LEFT("bottom-left"),
  /**
   * The bottom right corner of the text is placed closest to the anchor.
   */
  BOTTOM_RIGHT("bottom-right"),
}
// TEXT_JUSTIFY: Text justification options.
/**
 * Text justification options.
 *
 * @param value String value of this property
 */
enum class TextJustify(override val value: String) : LayerProperty {
  /**
   * The text is aligned towards the anchor position.
   */
  AUTO("auto"),
  /**
   * The text is aligned to the left.
   */
  LEFT("left"),
  /**
   * The text is centered.
   */
  CENTER("center"),
  /**
   * The text is aligned to the right.
   */
  RIGHT("right"),
}
// TEXT_PITCH_ALIGNMENT: Orientation of text when map is pitched.
/**
 * Orientation of text when map is pitched.
 *
 * @param value String value of this property
 */
enum class TextPitchAlignment(override val value: String) : LayerProperty {
  /**
   * The text is aligned to the plane of the map.
   */
  MAP("map"),
  /**
   * The text is aligned to the plane of the viewport.
   */
  VIEWPORT("viewport"),
  /**
   * Automatically matches the value of {@link TEXT_ROTATION_ALIGNMENT}.
   */
  AUTO("auto"),
}
// TEXT_ROTATION_ALIGNMENT: In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
/**
 * In combination with `symbol-placement`, determines the rotation behavior of the individual glyphs forming the text.
 *
 * @param value String value of this property
 */
enum class TextRotationAlignment(override val value: String) : LayerProperty {
  /**
   * When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_POINT}, aligns text east-west. When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_LINE} or {@link Property#SYMBOL_PLACEMENT_LINE_CENTER}, aligns text x-axes with the line.
   */
  MAP("map"),
  /**
   * Produces glyphs whose x-axes are aligned with the x-axis of the viewport, regardless of the value of {@link SYMBOL_PLACEMENT}.
   */
  VIEWPORT("viewport"),
  /**
   * When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_POINT}, this is equivalent to {@link Property#TEXT_ROTATION_ALIGNMENT_VIEWPORT}. When {@link SYMBOL_PLACEMENT} is set to {@link Property#SYMBOL_PLACEMENT_LINE} or {@link Property#SYMBOL_PLACEMENT_LINE_CENTER}, this is equivalent to {@link Property#TEXT_ROTATION_ALIGNMENT_MAP}.
   */
  AUTO("auto"),
}
// TEXT_TRANSFORM: Specifies how to capitalize text, similar to the CSS `text-transform` property.
/**
 * Specifies how to capitalize text, similar to the CSS `text-transform` property.
 *
 * @param value String value of this property
 */
enum class TextTransform(override val value: String) : LayerProperty {
  /**
   * The text is not altered.
   */
  NONE("none"),
  /**
   * Forces all letters to be displayed in uppercase.
   */
  UPPERCASE("uppercase"),
  /**
   * Forces all letters to be displayed in lowercase.
   */
  LOWERCASE("lowercase"),
}
// FILL_TRANSLATE_ANCHOR: Controls the frame of reference for `fill-translate`.
/**
 * Controls the frame of reference for `fill-translate`.
 *
 * @param value String value of this property
 */
enum class FillTranslateAnchor(override val value: String) : LayerProperty {
  /**
   * The fill is translated relative to the map.
   */
  MAP("map"),
  /**
   * The fill is translated relative to the viewport.
   */
  VIEWPORT("viewport"),
}
// LINE_TRANSLATE_ANCHOR: Controls the frame of reference for `line-translate`.
/**
 * Controls the frame of reference for `line-translate`.
 *
 * @param value String value of this property
 */
enum class LineTranslateAnchor(override val value: String) : LayerProperty {
  /**
   * The line is translated relative to the map.
   */
  MAP("map"),
  /**
   * The line is translated relative to the viewport.
   */
  VIEWPORT("viewport"),
}
// ICON_TRANSLATE_ANCHOR: Controls the frame of reference for `icon-translate`.
/**
 * Controls the frame of reference for `icon-translate`.
 *
 * @param value String value of this property
 */
enum class IconTranslateAnchor(override val value: String) : LayerProperty {
  /**
   * Icons are translated relative to the map.
   */
  MAP("map"),
  /**
   * Icons are translated relative to the viewport.
   */
  VIEWPORT("viewport"),
}
// TEXT_TRANSLATE_ANCHOR: Controls the frame of reference for `text-translate`.
/**
 * Controls the frame of reference for `text-translate`.
 *
 * @param value String value of this property
 */
enum class TextTranslateAnchor(override val value: String) : LayerProperty {
  /**
   * The text is translated relative to the map.
   */
  MAP("map"),
  /**
   * The text is translated relative to the viewport.
   */
  VIEWPORT("viewport"),
}
// CIRCLE_PITCH_ALIGNMENT: Orientation of circle when map is pitched.
/**
 * Orientation of circle when map is pitched.
 *
 * @param value String value of this property
 */
enum class CirclePitchAlignment(override val value: String) : LayerProperty {
  /**
   * The circle is aligned to the plane of the map.
   */
  MAP("map"),
  /**
   * The circle is aligned to the plane of the viewport.
   */
  VIEWPORT("viewport"),
}
// CIRCLE_PITCH_SCALE: Controls the scaling behavior of the circle when the map is pitched.
/**
 * Controls the scaling behavior of the circle when the map is pitched.
 *
 * @param value String value of this property
 */
enum class CirclePitchScale(override val value: String) : LayerProperty {
  /**
   * Circles are scaled according to their apparent distance to the camera.
   */
  MAP("map"),
  /**
   * Circles are not scaled.
   */
  VIEWPORT("viewport"),
}
// CIRCLE_TRANSLATE_ANCHOR: Controls the frame of reference for `circle-translate`.
/**
 * Controls the frame of reference for `circle-translate`.
 *
 * @param value String value of this property
 */
enum class CircleTranslateAnchor(override val value: String) : LayerProperty {
  /**
   * The circle is translated relative to the map.
   */
  MAP("map"),
  /**
   * The circle is translated relative to the viewport.
   */
  VIEWPORT("viewport"),
}
// FILL_EXTRUSION_TRANSLATE_ANCHOR: Controls the frame of reference for `fill-extrusion-translate`.
/**
 * Controls the frame of reference for `fill-extrusion-translate`.
 *
 * @param value String value of this property
 */
enum class FillExtrusionTranslateAnchor(override val value: String) : LayerProperty {
  /**
   * The fill extrusion is translated relative to the map.
   */
  MAP("map"),
  /**
   * The fill extrusion is translated relative to the viewport.
   */
  VIEWPORT("viewport"),
}
// RASTER_RESAMPLING: The resampling/interpolation method to use for overscaling, also known as texture magnification filter
/**
 * The resampling/interpolation method to use for overscaling, also known as texture magnification filter
 *
 * @param value String value of this property
 */
enum class RasterResampling(override val value: String) : LayerProperty {
  /**
   * (Bi)linear filtering interpolates pixel values using the weighted average of the four closest original source pixels creating a smooth but blurry look when overscaled
   */
  LINEAR("linear"),
  /**
   * Nearest neighbor filtering interpolates pixel values using the nearest original source pixel creating a sharp but pixelated look when overscaled
   */
  NEAREST("nearest"),
}
// HILLSHADE_ILLUMINATION_ANCHOR: Direction of light source when map is rotated.
/**
 * Direction of light source when map is rotated.
 *
 * @param value String value of this property
 */
enum class HillshadeIlluminationAnchor(override val value: String) : LayerProperty {
  /**
   * The hillshade illumination is relative to the north direction.
   */
  MAP("map"),
  /**
   * The hillshade illumination is relative to the top of the viewport.
   */
  VIEWPORT("viewport"),
}
// SKY_TYPE: The type of the sky
/**
 * The type of the sky
 *
 * @param value String value of this property
 */
enum class SkyType(override val value: String) : LayerProperty {
  /**
   * Renders the sky with a gradient that can be configured with {@link SKY_GRADIENT_RADIUS} and {@link SKY_GRADIENT}.
   */
  GRADIENT("gradient"),
  /**
   * Renders the sky with a simulated atmospheric scattering algorithm, the sun direction can be attached to the light position or explicitly set through {@link SKY_ATMOSPHERE_SUN}.
   */
  ATMOSPHERE("atmosphere"),
}
// MODEL_TYPE: Defines rendering behavior of model in respect to other 3D scene objects.
/**
 * Defines rendering behavior of model in respect to other 3D scene objects.
 *
 * @param value String value of this property
 */
enum class ModelType(override val value: String) : LayerProperty {
  /**
   * Integrated to 3D scene, using depth testing, along with terrain, fill-extrusions and custom layer.
   */
  COMMON_3D("common-3d"),
  /**
   * Displayed over other 3D content, occluded by terrain.
   */
  LOCATION_INDICATOR("location-indicator"),
}
// ANCHOR: Whether extruded geometries are lit relative to the map or viewport.
/**
 * Whether extruded geometries are lit relative to the map or viewport.
 *
 * @param value String value of this property
 */
enum class Anchor(override val value: String) : LayerProperty {
  /**
   * The position of the light source is aligned to the rotation of the map.
   */
  MAP("map"),
  /**
   * The position of the light source is aligned to the rotation of the viewport.
   */
  VIEWPORT("viewport"),
}
// NAME: The name of the projection to be used for rendering the map.
/**
 * The name of the projection to be used for rendering the map.
 *
 * @param value String value of this property
 */
enum class ProjectionName(override val value: String) : LayerProperty {
  /**
   * The Mercator projection is the default projection.
   */
  MERCATOR("mercator"),
  /**
   * A globe projection.
   */
  GLOBE("globe"),
}
// TEXT_WRITING_MODE: The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. The order of elements in an array define priority order for the placement of an orientation variant.
/**
 * The property allows control over a symbol's orientation. Note that the property values act as a hint, so that a symbol whose language doesn’t support the provided orientation will be laid out in its natural orientation. Example: English point symbol will be rendered horizontally even if array value contains single 'vertical' enum value. The order of elements in an array define priority order for the placement of an orientation variant.
 *
 * @param value String value of this property
 */
enum class TextWritingMode(override val value: String) : LayerProperty {
  /**
   * If a text's language supports horizontal writing mode, symbols with point placement would be laid out horizontally.
   */
  HORIZONTAL("horizontal"),
  /**
   * If a text's language supports vertical writing mode, symbols with point placement would be laid out vertically.
   */
  VERTICAL("vertical"),
}