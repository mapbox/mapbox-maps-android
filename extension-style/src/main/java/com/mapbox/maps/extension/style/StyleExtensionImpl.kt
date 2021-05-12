package com.mapbox.maps.extension.style

import com.mapbox.maps.LayerPosition
import com.mapbox.maps.extension.style.StyleExtensionImpl.*
import com.mapbox.maps.extension.style.image.ImageExtensionImpl
import com.mapbox.maps.extension.style.layers.*
import com.mapbox.maps.extension.style.light.generated.Light
import com.mapbox.maps.extension.style.sources.*
import com.mapbox.maps.extension.style.terrain.generated.Terrain

/**
 * The concrete implementation of style extension.
 */
class StyleExtensionImpl private constructor(
  builder: Builder,
  override var resourceCount: Int
) : StyleContract.StyleExtension {

  /**
   * The style's Uri.
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
   * The builder for style extension.
   */
  class Builder(
    /**
     * The Uri of the style to load.
     */
    val styleUri: String
  ) {
    private var resourceCount = 0
    internal val layers = mutableListOf<Pair<Layer, LayerPosition>>()
    internal val sources = mutableListOf<Source>()
    internal val images = mutableListOf<ImageExtensionImpl>()
    internal var light: Light? = null
    internal var terrain: Terrain? = null

    /**
     * Extension function for [Layer] to overload Unary operations.
     *
     * Apply +[Layer] will add the layer to the [StyleExtensionImpl].
     */
    operator fun Layer.unaryPlus() {
      layers.add(Pair(this, LayerPosition(null, null, null)))
      resourceCount++
    }

    /**
     * Extension function for [Pair]<[Layer], [LayerPosition]> to overload Unary operations.
     *
     * Apply +[Pair]<[Layer], [LayerPosition]> will add the layer to the [StyleExtensionImpl] at given [LayerPosition].
     */
    operator fun Pair<Layer, LayerPosition>.unaryPlus() {
      layers.add(this)
      resourceCount++
    }

    /**
     * Extension function for [Source] to overload Unary operations.
     *
     * Apply +[Source] will add the source to the [StyleExtensionImpl].
     */
    operator fun Source.unaryPlus() {
      sources.add(this)
      resourceCount++
    }

    /**
     * Extension function for [Light] to overload Unary operations.
     *
     * Apply +[Light] will add the light to the [StyleExtensionImpl].
     */
    operator fun Light.unaryPlus() {
      light = this
    }

    /**
     * Extension function for [Terrain] to overload Unary operations.
     *
     * Apply +[Terrain] will add the terrain to the [StyleExtensionImpl].
     */
    operator fun Terrain.unaryPlus() {
      terrain = this
    }

    /**
     * Extension function for [ImageExtensionImpl] to overload Unary operations.
     *
     * Apply +[ImageExtensionImpl] will add the source to the [StyleExtensionImpl].
     */
    operator fun ImageExtensionImpl.unaryPlus() {
      images.add(this)
      resourceCount++
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
    fun layerAtPosition(
      layer: Layer,
      above: String? = null,
      below: String? = null,
      at: Int? = null
    ): Pair<Layer, LayerPosition> {
      return Pair(layer, LayerPosition(above, below, at))
    }

    internal fun build(): StyleContract.StyleExtension {
      return StyleExtensionImpl(this, resourceCount)
    }
  }
}

/**
 * DSL function to construct a style extension.
 */
fun style(styleUri: String, block: Builder.() -> Unit) =
  Builder(styleUri).apply(block).build()