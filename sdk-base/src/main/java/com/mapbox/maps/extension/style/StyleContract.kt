package com.mapbox.maps.extension.style

import com.mapbox.maps.ColorTheme
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.TransitionOptions

/**
 * Define the common interfaces for the Style component.
 */
interface StyleContract {
  /**
   * Define the interfaces for the Style Extension.
   */
  interface StyleExtension {
    /**
     * Style represented as JSON or URI.
     */
    val style: String

    /**
     * The sources of the style.
     */
    val sources: List<StyleSourceExtension>

    /**
     * The images of the style.
     */
    val images: List<StyleImageExtension>

    /**
     * The models of the style.
     */
    @MapboxExperimental
    val models: List<StyleModelExtension>

    /**
     * The layers of the style.
     */
    val layers: List<Pair<StyleLayerExtension, LayerPosition>>

    /**
     * The flat light of the style.
     */
    val flatLight: StyleLightExtension?

    /**
     * The dynamic (directional and ambient) light of the style.
     */
    val dynamicLight: StyleLightExtension?

    /**
     * The 3D terrain of the style.
     */
    val terrain: StyleTerrainExtension?

    /**
     * The atmosphere of the style.
     */
    val atmosphere: StyleAtmosphereExtension?

    /**
     * Map projection of the style.
     */
    val projection: StyleProjectionExtension?

    /**
     * Transition options applied when loading the style.
     */
    val transition: TransitionOptions?

    /**
     * The rain precipitation of the style.
     */
    @MapboxExperimental
    val rain: StyleRainExtension?

    /**
     * The snow precipitation of the style.
     */
    @MapboxExperimental
    val snow: StyleSnowExtension?

    /**
     * The color theme of the style.
     */
    @MapboxExperimental
    val colorTheme: ColorTheme?
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
    fun bindTo(delegate: MapboxStyleManager, position: LayerPosition? = null)
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
    fun bindTo(delegate: MapboxStyleManager)
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
    fun bindTo(delegate: MapboxStyleManager)
  }

  /**
   * Define the interfaces for the Atmosphere plugin.
   */
  fun interface StyleAtmosphereExtension {
    /**
     * Bind the atmosphere to the Style.
     *
     * @param delegate The style delegate
     */
    fun bindTo(delegate: MapboxStyleManager)
  }

  /**
   * Define the interfaces for the Projection plugin.
   */
  fun interface StyleProjectionExtension {
    /**
     * Bind the projection to the Style.
     *
     * @param delegate The style delegate
     */
    fun bindTo(delegate: MapboxStyleManager)
  }

  /**
   * Define the interfaces for the source plugin.
   */
  fun interface StyleSourceExtension {
    /**
     * Bind the source to the Style.
     *
     * @param delegate The style delegate
     */
    fun bindTo(delegate: MapboxStyleManager)
  }

  /**
   * Define the interfaces for the Image plugin.
   */
  fun interface StyleImageExtension {
    /**
     * Bind the image to the Style.
     *
     * @param delegate The style delegate
     */
    fun bindTo(delegate: MapboxStyleManager)
  }

  /**
   * Define the interfaces for the model extension.
   */
  @MapboxExperimental
  fun interface StyleModelExtension {
    /**
     * Bind the model to the Style.
     *
     * @param delegate The style delegate
     */
    fun bindTo(delegate: MapboxStyleManager)
  }

  /**
   * Define the interfaces for the Snow plugin.
   */
  @MapboxExperimental
  fun interface StyleSnowExtension {
    /**
     * Bind the snow to the Style.
     *
     * @param delegate The style delegate
     */
    fun bindTo(delegate: MapboxStyleManager)
  }

  /**
   * Define the interfaces for the Rain plugin.
   */
  @MapboxExperimental
  fun interface StyleRainExtension {
    /**
     * Bind the rain to the Style.
     *
     * @param delegate The style delegate
     */
    fun bindTo(delegate: MapboxStyleManager)
  }
}