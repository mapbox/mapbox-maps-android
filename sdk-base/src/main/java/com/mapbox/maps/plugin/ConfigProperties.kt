// This file is generated.

package com.mapbox.maps.plugin

import android.graphics.drawable.Drawable

/**
 * The map rendering mode. By default, it is set to Continuous so the map will render as data arrives from the network and react immediately to state changes.
 *
 * @param value
 */
enum class MapMode(val value: String) {
  /**
   * Continuously updating map.
   */
  CONTINUOUS("continuous"),
  /**
   * A once-off still image of an arbitrary viewport.
   */
  STATIC("static"),
  /**
   * A once-off still image of a single tile.
   */
  TILE("tile"),
}

/**
 * The map context mode. This can be used to optimizations if we know that the drawing context is not shared with other code.
 *
 * @param value
 */
enum class ContextMode(val value: String) {
  /**
   * Unique context mode: in OpenGL, the GL context is not shared, thus we can retain knowledge about the GL state from a previous render pass. It also enables clearing the screen using glClear for the bottommost background layer when no pattern is applied to that layer.
   */
  UNIQUE("unique"),
  /**
   * Shared context mode: in OpenGL, the GL context is shared with other renderers, thus we cannot rely on the GL state set from a previous render pass.
   */
  SHARED("shared"),
  /**
   * A once-off still image of a single tile.
   */
  TILE("tile"),
}

/**
 * The map constrain mode. This can be used to limit the map to wrap around the globe horizontally. By default, it is set to HeightOnly.
 *
 * @param value
 */
enum class ConstrainMode(val value: String) {
  /**
   * No constraints.
   */
  NONE("none"),
  /**
   * Constrain to height only.
   */
  HEIGHT_ONLY("height-only"),
  /**
   * Constrain both width and height axes.
   */
  WIDTH_AND_HEIGHT("width-and-height"),
}

/**
 * The viewport mode. This can be used to flip the vertical orientation of the map as some devices may use inverted orientation.
 *
 * @param value
 */
enum class ViewportMode(val value: String) {
  /**
   * Default viewport.
   */
  DEFAULT("default"),
  /**
   * Viewport flipped on the y-axis.
   */
  FLIPPED_Y("flipped-y"),
}

/**
 * The orientation of the Map. By default, it is set to upwards.
 *
 * @param value
 */
enum class Orientation(val value: String) {
  /**
   * Default, map oriented upwards.
   */
  UPWARDS("upwards"),
  /**
   * Map oriented rightwards.
   */
  RIGHTWARDS("rightwards"),
  /**
   * Map oriented downwards.
   */
  DOWNWARDS("downwards"),
  /**
   * Map oriented lefttwards.
   */
  LEFTTWARDS("lefttwards"),
}

/**
 * Describes glyphs rasterization modes.
 *
 * @param value
 */
enum class GlyphRasterizationMode(val value: String) {
  /**
   * No glyphs are rasterized locally. All glyphs are loaded from the server.
   */
  NO_GLYPHS_RASTERIZED_LOCALLY("no-glyphs-rasterized-locally"),
  /**
   * Ideographs are rasterized locally, and they are not loaded from the server.
   */
  IDEOGRAPHS_RASTERIZED_LOCALLY("ideographs-rasterized-locally"),
  /**
   * All glyphs are rasterized locally. No glyphs are loaded from the server.
   */
  ALL_GLYPHS_RASTERIZED_LOCALLY("all-glyphs-rasterized-locally"),
}

/**
 * The view type used to render.
 *
 * @param value
 */
enum class Surface(val value: String) {
  /**
   * Default, map rendering on a GLSurfaceView.
   */
  SURFACE_VIEW("surface-view"),
  /**
   * Map rendering on a TextureView.
   */
  TEXTURE_VIEW("texture-view"),
}

/**
 * Sealed class representing a location-puck.
 */
sealed class LocationPuck

/**
 * Definition of a location_puck_2_d.
 */
data class LocationPuck2D(
  /**
   * Name of image in sprite to use as the top of the location indicator.
   */
  var topImage: Drawable? = null,
  /**
   * Name of image in sprite to use as the middle of the location indicator.
   */
  var bearingImage: Drawable? = null,
  /**
   * Name of image in sprite to use as the background of the location indicator.
   */
  var shadowImage: Drawable? = null,
  /**
   * The scale expression of the images. If defined, it will be applied to all the three images.
   */
  var scaleExpression: String? = null,
) : LocationPuck()

/**
 * Definition of a location_puck_3_d.
 */
data class LocationPuck3D(
  /**
   * An URL for the model file in gltf format.
   */
  var modelUri: String,
  /**
   * The scale of the model.
   */
  var position: List<Float> = listOf(0f, 0f),
  /**
   * The opacity of the model.
   */
  var modelOpacity: Float = 1f,
  /**
   * The scale of the model.
   */
  var modelScale: List<Float> = listOf(1f, 1f, 1f),
  /**
   * The scale expression of the model, which will overwrite the default scale expression that keeps the model size constant during zoom.
   */
  var modelScaleExpression: String? = null,
  /**
   * The rotation of the model.
   */
  var modelRotation: List<Float> = listOf(0f, 0f, 90f),
) : LocationPuck()

/**
 * Defines where the scale bar is positioned on the map
 *
 * @param value
 */
enum class Position(val value: String) {
  /**
   * The top left position of the map.
   */
  TOP_LEFT("top-left"),
  /**
   * The top center position of the map.
   */
  TOP_CENTER("top-center"),
  /**
   * The top right position of the map.
   */
  TOP_RIGHT("top-right"),
  /**
   * The center right position of the map.
   */
  CENTER_RIGHT("center-right"),
  /**
   * The bottom right position of the map.
   */
  BOTTOM_RIGHT("bottom-right"),
  /**
   * The bottom center position of the map.
   */
  BOTTOM_CENTER("bottom-center"),
  /**
   * The bottom left position of the map.
   */
  BOTTOM_LEFT("bottom-left"),
  /**
   * The center left position of the map.
   */
  CENTER_LEFT("center-left"),
}

// End of generated file.