@file:JvmName("LayerUtils")
package com.mapbox.maps.extension.style.layers

import com.mapbox.common.Logger
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.layers.generated.*
import com.mapbox.maps.extension.style.utils.silentUnwrap
import com.mapbox.maps.extension.style.utils.unwrap

/**
 * Extension function to get a Layer provided by the Style Extension by layer id.
 *
 * @param layerId the layer id
 * @return StyleLayerPlugin
 */
fun StyleManagerInterface.getLayer(layerId: String): Layer? {
  return this.getStyleLayerProperty(layerId, "type").silentUnwrap<String>()?.let { type ->
    when (type) {
      "background" -> BackgroundLayer(layerId).also { it.delegate = this }
      "location-indicator" -> LocationIndicatorLayer(layerId).also { it.delegate = this }
      "sky" -> SkyLayer(layerId).also { it.delegate = this }
      "circle" -> CircleLayer(layerId, this.getStyleLayerProperty(layerId, "source").unwrap()).also { it.delegate = this }
      "fill-extrusion" -> FillExtrusionLayer(layerId, this.getStyleLayerProperty(layerId, "source").unwrap()).also { it.delegate = this }
      "fill" -> FillLayer(layerId, this.getStyleLayerProperty(layerId, "source").unwrap()).also { it.delegate = this }
      "heatmap" -> HeatmapLayer(layerId, this.getStyleLayerProperty(layerId, "source").unwrap()).also { it.delegate = this }
      "hillshade" -> HillshadeLayer(layerId, this.getStyleLayerProperty(layerId, "source").unwrap()).also { it.delegate = this }
      "line" -> LineLayer(layerId, this.getStyleLayerProperty(layerId, "source").unwrap()).also { it.delegate = this }
      "raster" -> RasterLayer(layerId, this.getStyleLayerProperty(layerId, "source").unwrap()).also { it.delegate = this }
      "symbol" -> SymbolLayer(layerId, this.getStyleLayerProperty(layerId, "source").unwrap()).also { it.delegate = this }
      else -> {
        Logger.e("StyleLayerPlugin", "Layer type: $type unknown.")
        null
      }
    }
  }
}

/**
 * Tries to cast the Layer to T, throws ClassCastException if it's another type.
 *
 * @param layerId the layer id
 * @return T
 */
fun <T : Layer> StyleManagerInterface.getLayerAs(layerId: String): T {
  @Suppress("UNCHECKED_CAST")
  return getLayer(layerId) as T
}

/**
 * Extension function to add a Layer provided by the Style Extension to the Style.
 *
 * @param layer The layer to be added
 * @param below the layer id that the current layer is added below
 */
fun StyleInterface.addLayerBelow(layer: StyleContract.StyleLayerExtension, below: String?) {
  layer.bindTo(this, LayerPosition(null, below, null))
}

/**
 * Extension function to add a Layer provided by the Style Extension to the Style.
 *
 * @param layer The layer to be added
 * @param above the layer id that the current layer is added above
 */
fun StyleInterface.addLayerAbove(layer: StyleContract.StyleLayerExtension, above: String?) {
  layer.bindTo(this, LayerPosition(above, null, null))
}

/**
 * Extension function to add a Layer provided by the Style Extension to the Style.
 *
 * @param layer The layer to be added
 * @param index the index that the current layer is added on
 */
fun StyleInterface.addLayerAt(layer: StyleContract.StyleLayerExtension, index: Int?) {
  layer.bindTo(this, LayerPosition(null, null, index))
}

/**
 * Extension function to add a Layer provided by the Style Extension to the Style.
 *
 * @param layer The layer to be added
 */
fun StyleInterface.addLayer(layer: StyleContract.StyleLayerExtension) {
  layer.bindTo(this)
}

/**
 * Bind the layer to the map controller persistently.
 *
 * Note: This is an experimental API and is a subject to change.
 *
 * Whenever a new style is being parsed and currently used style has persistent layers,
 * an engine will try to do following:
 *   - keep the persistent layer at its relative position
 *   - keep the source used by a persistent layer
 *   - keep images added through `addStyleImage` method
 *
 * In cases when a new style has the same layer, source or image resource, style's resources would be
 * used instead and `MapLoadingError` event will be emitted.
 *
 * @param style The style
 * @param position the position that the current layer is added to
 */
@MapboxExperimental
internal fun Layer.bindPersistentlyTo(style: StyleInterface, position: LayerPosition? = null) {
  this.delegate = style
  val expected = style.addPersistentStyleLayer(getCachedLayerProperties(), position)
  expected.error?.let {
    throw RuntimeException("Add persistent layer failed: $it")
  }
}

/**
 * Add layer to the style persistently.
 *
 * Note: This is an experimental API and is a subject to change.
 *
 * Whenever a new style is being parsed and currently used style has persistent layers,
 * an engine will try to do following:
 *   - keep the persistent layer at its relative position
 *   - keep the source used by a persistent layer
 *   - keep images added through `addStyleImage` method
 *
 * In cases when a new style has the same layer, source or image resource, style's resources would be
 * used instead and `MapLoadingError` event will be emitted.
 *
 * @param layer The layer to be added to the style
 * @param position the position that the current layer is added to
 */
@MapboxExperimental
@JvmOverloads
fun StyleInterface.addPersistentLayer(layer: Layer, position: LayerPosition? = null) {
  layer.bindPersistentlyTo(this, position)
}

/**
 * Get the persistent property as Boolean.
 *
 * If true, the layer will be persisted across style changes.
 */
@MapboxExperimental
fun Layer.isPersistent(): Boolean? {
  return delegate?.isStyleLayerPersistent(layerId)?.value
}