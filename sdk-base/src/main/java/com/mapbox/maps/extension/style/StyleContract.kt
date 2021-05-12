package com.mapbox.maps.extension.style

import com.mapbox.maps.LayerPosition

/**
 * Define the common interfaces for the Style component.
 */
interface StyleContract {
  /**
   * Define the interfaces for the Style Extension.
   */
  interface StyleExtension {

    /**
     * Total resource count including all sources, layers and images.
     */
    var resourceCount: Int
    /**
     * The style's Uri.
     */
    val styleUri: String

    /**
     * The sources of the style.
     */
    val sources: List<StyleSourceExtension>

    /**
     * The images of the style.
     */
    val images: List<StyleImageExtension>

    /**
     * The layers of the style.
     */
    val layers: List<Pair<StyleLayerExtension, LayerPosition>>

    /**
     * The light of the style.
     */
    val light: StyleLightExtension?

    /**
     * The 3D terrain of the style.
     */
    val terrain: StyleTerrainExtension?
  }

  /**
   * Define the interfaces for the Layer plugin.
   */
  interface StyleLayerExtension {
    /**
     * Bind the layer to the Style.
     *
     * @param delegate The style delegate
     * @param position the layer position
     */
    fun bindTo(delegate: StyleInterface, position: LayerPosition? = null)
  }

  /**
   * Define the interfaces for the Light plugin.
   */
  fun interface StyleLightExtension {
    /**
     * Bind the light to the Style.
     *
     * @param delegate The style delegate
     */
    fun bindTo(delegate: StyleInterface)
  }

  /**
   * Define the interfaces for the Terrain plugin.
   */
  fun interface StyleTerrainExtension {
    /**
     * Bind the terrain to the Style.
     *
     * @param delegate The style delegate
     */
    fun bindTo(delegate: StyleInterface)
  }

  /**
   * Define the interfaces for the Light plugin.
   */
  fun interface StyleSourceExtension {
    /**
     * Add the source to the Style.
     *
     * @param delegate The style delegate
     */
    fun bindTo(delegate: StyleInterface)
  }

  /**
   * Define the interfaces for the Image plugin.
   */
  fun interface StyleImageExtension {
    /**
     * Add the image to the Style.
     *
     * @param delegate The style delegate
     */
    fun bindTo(delegate: StyleInterface)
  }
}