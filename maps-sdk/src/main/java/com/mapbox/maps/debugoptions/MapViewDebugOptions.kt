package com.mapbox.maps.debugoptions

import com.mapbox.maps.MapDebugOptions

/** Options for enabling debugging features in a map view. */
data class MapViewDebugOptions internal constructor(internal val name: String) {

  /**
   * Static methods and variables.
   */
  companion object {
    /**
     * Edges of tile boundaries are shown as thick, red lines to help diagnose
     * tile clipping issues.
     */
    @JvmField
    val TILE_BORDERS = MapViewDebugOptions("TILE_BORDERS")

    /** Each tile shows its tile coordinate (x/y/z) in the upper-left corner. */
    @JvmField
    val PARSE_STATUS = MapViewDebugOptions("PARSE_STATUS")

    /**
     * Each tile shows a timestamps with modified and expires dates or n/a if
     * timestamp is not available.
     */
    @JvmField
    val TIMESTAMPS = MapViewDebugOptions("TIMESTAMPS")

    /**
     * Edges of glyphs and symbols are shown as faint, green lines to help
     * diagnose collision and label placement issues.
     */
    @JvmField
    val COLLISION = MapViewDebugOptions("COLLISION")

    /**
     * Each drawing operation is replaced by a translucent fill. Overlapping
     * drawing operations appear more prominent to help diagnose overdrawing.
     */
    @JvmField
    val OVERDRAW = MapViewDebugOptions("OVERDRAW")

    /** The stencil buffer is shown instead of the color buffer. */
    @JvmField
    val STENCIL_CLIP = MapViewDebugOptions("STENCIL_CLIP")

    /** The depth buffer is shown instead of the color buffer. */
    @JvmField
    val DEPTH_BUFFER = MapViewDebugOptions("DEPTH_BUFFER")

    /** Show 3D model bounding boxes. */
    @JvmField
    val MODEL_BOUNDS = MapViewDebugOptions("MODEL_BOUNDS")

    /** Show a wireframe for terrain. Currently supported for GL backend only. */
    @JvmField
    val TERRAIN_WIREFRAME = MapViewDebugOptions("TERRAIN_WIREFRAME")

    /** Show a wireframe for 2d layers. Currently supported for GL backend only. */
    @JvmField
    val LAYERS2_DWIREFRAME = MapViewDebugOptions("LAYERS2_DWIREFRAME")

    /** Show a wireframe for 3d layers. Currently supported for GL backend only. */
    @JvmField
    val LAYERS3_DWIREFRAME = MapViewDebugOptions("LAYERS3_DWIREFRAME")

    /** Each tile shows its local lighting conditions in the upper-left corner. (If `lights` properties are used, otherwise they show zero.) */
    @JvmField
    val LIGHT = MapViewDebugOptions("LIGHT")

    /**
     * Show a debug view with information about the current camera state
     * including lat, lng, zoom, pitch and bearing.
     */
    @JvmField
    val CAMERA = MapViewDebugOptions("CAMERA")

    /**
     * Draws camera padding frame.
     */
    @JvmField
    val PADDING = MapViewDebugOptions("PADDING")
  }
}

internal val Set<MapViewDebugOptions>.nativeDebugOptions: Set<MapDebugOptions>
  get() = mapNotNull {
    try {
      MapDebugOptions.valueOf(it.name)
    } catch (e: Exception) {
      // skip map view options that don't have native counterparts
      null
    }
  }.toSet()