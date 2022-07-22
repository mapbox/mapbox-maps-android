package com.mapbox.maps.extension.style

import android.opengl.GLES20
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.extension.style.StyleExtensionImpl.*
import com.mapbox.maps.extension.style.atmosphere.generated.Atmosphere
import com.mapbox.maps.extension.style.image.ImageExtensionImpl
import com.mapbox.maps.extension.style.image.ImageNinePatchExtensionImpl
import com.mapbox.maps.extension.style.layers.*
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.light.generated.Light
import com.mapbox.maps.extension.style.model.ModelExtensionImpl
import com.mapbox.maps.extension.style.projection.generated.Projection
import com.mapbox.maps.extension.style.sources.*
import com.mapbox.maps.extension.style.terrain.generated.Terrain

/**
 * The concrete implementation of style extension.
 */
class StyleExtensionImpl private constructor(
  builder: Builder
) : StyleContract.StyleExtension {

  /**
   * The style's Uri. Will load an empty json `{}` if the styleUri is empty.
   */
  override val styleUri: String = builder.styleUri

  /**
   * The sources of the style.
   */
  override val sources = builder.sources.toList()

  /**
   * The images of the style.
   */
  override val images = builder.images.toList()

  /**
   * The models of the style.
   */
  override val models: List<StyleContract.StyleModelExtension> = builder.models.toList()

  /**
   * The layers of the style.
   */
  override val layers = builder.layers.toList()

  /**
   * The light of the style.
   */
  override val light: Light? = builder.light

  /**
   * The terrain of the style.
   */
  override val terrain: Terrain? = builder.terrain

  /**
   * The map projection of the style.
   */
  override val projection: Projection? = builder.projection

  /**
   * The atmosphere of the style.
   */
  override val atmosphere: Atmosphere? = builder.atmosphere

  /**
   * The builder for style extension.
   */
  class Builder(
    /**
     * The Uri of the style to load.
     */
    val styleUri: String
  ) {
    internal val layers = mutableListOf<Pair<Layer, LayerPosition>>()
    internal val sources = mutableListOf<Source>()
    internal val images = mutableListOf<StyleContract.StyleImageExtension>()
    internal val models = mutableListOf<StyleContract.StyleModelExtension>()
    internal var light: Light? = null
    internal var terrain: Terrain? = null
    internal var atmosphere: Atmosphere? = null
    internal var projection: Projection? = null

    /**
     * Extension function for [Layer] to overload Unary operations.
     *
     * Apply +[Layer] will add the layer to the [StyleExtensionImpl].
     */
    @JvmName("addLayer")
    operator fun Layer.unaryPlus() {
      layers.add(Pair(this, LayerPosition(null, null, null)))
    }

    /**
     * Extension function for [Pair]<[Layer], [LayerPosition]> to overload Unary operations.
     *
     * Apply +[Pair]<[Layer], [LayerPosition]> will add the layer to the [StyleExtensionImpl] at given [LayerPosition].
     */
    @JvmName("addLayerAtPosition")
    operator fun Pair<Layer, LayerPosition>.unaryPlus() {
      layers.add(this)
    }

    /**
     * Extension function for [Source] to overload Unary operations.
     *
     * Apply +[Source] will add the source to the [StyleExtensionImpl].
     */
    @JvmName("addSource")
    operator fun Source.unaryPlus() {
      sources.add(this)
    }

    /**
     * Extension function for [Light] to overload Unary operations.
     *
     * Apply +[Light] will add the light to the [StyleExtensionImpl].
     */
    @JvmName("setLight")
    operator fun Light.unaryPlus() {
      light = this
    }

    /**
     * Extension function for [Terrain] to overload Unary operations.
     *
     * Apply +[Terrain] will add the terrain to the [StyleExtensionImpl].
     */
    @JvmName("setTerrain")
    operator fun Terrain.unaryPlus() {
      terrain = this
    }

    /**
     * Extension function for [Atmosphere] to overload Unary operations.
     *
     * Apply +[Atmosphere] will add the atmosphere to the [StyleExtensionImpl].
     */
    @JvmName("setAtmosphere")
    operator fun Atmosphere.unaryPlus() {
      atmosphere = this
    }

    /**
     * Extension function for [Projection] to overload Unary operations.
     *
     * Using [ProjectionName.GLOBE] requires OpenGL [GLES20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS] be more than zero.
     * If [GLES20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS] is zero (which may happen for low-end devices and old Android versions) -
     * [ProjectionName.GLOBE] will fallback to [ProjectionName.MERCATOR] with the log warning.
     *
     * Apply +[Projection] will add specific map projection to the [StyleExtensionImpl].
     */
    @JvmName("setProjection")
    operator fun Projection.unaryPlus() {
      projection = this
    }

    /**
     * Extension function for [ImageExtensionImpl] to overload Unary operations.
     *
     * Apply +[ImageExtensionImpl] will add the source to the [StyleExtensionImpl].
     */
    @JvmName("addImage")
    operator fun ImageExtensionImpl.unaryPlus() {
      images.add(this)
    }

    /**
     * Extension function for [ModelExtensionImpl] to overload Unary operations.
     *
     * Apply +[ModelExtensionImpl] will add the source to the [StyleExtensionImpl].
     */
    @JvmName("addModel")
    operator fun ModelExtensionImpl.unaryPlus() {
      models.add(this)
    }

    /**
     * Extension function for [ImageNinePatchExtensionImpl] to overload Unary operations.
     *
     * Apply +[ImageNinePatchExtensionImpl] will add the source to the [StyleExtensionImpl].
     */
    @JvmName("addImage9Patch")
    operator fun ImageNinePatchExtensionImpl.unaryPlus() {
      images.add(this)
    }

    /**
     * Convenient function to combine [Layer] and [LayerPosition] into [Pair]<[Layer], [LayerPosition]>.
     *
     * @param layer the layer to add
     * @param above the layer id to be added above to
     * @param below the layer id to be added below to
     * @param at the index to be added at
     *
     * @return [Pair]<[Layer], [LayerPosition]>
     */
    @JvmOverloads
    fun layerAtPosition(
      layer: Layer,
      above: String? = null,
      below: String? = null,
      at: Int? = null
    ): Pair<Layer, LayerPosition> {
      return Pair(layer, LayerPosition(above, below, at))
    }

    /**
     * Build an [StyleContract.StyleExtension] instance from this builder.
     *
     * @return an [StyleContract.StyleExtension] instance.
     */
    fun build(): StyleContract.StyleExtension {
      return StyleExtensionImpl(this)
    }
  }
}

/**
 * DSL function to construct a style extension.
 */
fun style(styleUri: String = "", block: Builder.() -> Unit) =
  Builder(styleUri).apply(block).build()