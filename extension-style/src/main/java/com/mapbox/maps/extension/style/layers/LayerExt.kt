@file:JvmName("LayerUtils")

package com.mapbox.maps.extension.style.layers

import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.layers.generated.*
import com.mapbox.maps.extension.style.utils.silentUnwrap
import com.mapbox.maps.extension.style.utils.unwrap
import com.mapbox.maps.logE

/**
 * Extension function to get a Layer provided by the Style Extension by layer id.
 *
 * @param layerId the layer id
 * @return StyleLayerPlugin
 */
fun StyleManagerInterface.getLayer(layerId: String): Layer? {
  val source by lazy { getStyleLayerProperty(layerId, "source").unwrap<String>() }
  return when (val type = getStyleLayerProperty(layerId, "type").silentUnwrap<String>()) {
    "background" -> BackgroundLayer(layerId)
    "location-indicator" -> LocationIndicatorLayer(layerId)
    "sky" -> SkyLayer(layerId)
    "circle" -> CircleLayer(layerId, source)
    "fill-extrusion" -> FillExtrusionLayer(layerId, source)
    "fill" -> FillLayer(layerId, source)
    "heatmap" -> HeatmapLayer(layerId, source)
    "hillshade" -> HillshadeLayer(layerId, source)
    "line" -> LineLayer(layerId, source)
    "raster" -> RasterLayer(layerId, source)
    "symbol" -> SymbolLayer(layerId, source)
    "model" -> ModelLayer(layerId, source)
    else -> {
      logE(TAG, "Layer type: $type unknown.")
      null
    }
  }?.also { result ->
    result.delegate = this
    result.appliedLayerPropertiesValue = getStyleLayerProperties(layerId).value
  }
}

/**
 * Tries to cast the Layer to T.
 *
 * @param layerId the layer id
 * @return T if layer is T, otherwise null
 */
@SuppressWarnings("ChangedType")
inline fun <reified T : Layer> StyleManagerInterface.getLayerAs(layerId: String): T? {
  val layer = getLayer(layerId) as? T
  if (layer == null) {
    logE(TAG, "Given layerId = $layerId is not requested type in Layer")
    return null
  }
  return layer
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
internal fun Layer.bindPersistentlyTo(style: StyleInterface, position: LayerPosition? = null) {
  this.delegate = style
  val expected = style.addPersistentStyleLayer(getCachedLayerProperties(), position)
  expected.error?.let {
    throw MapboxStyleException("Add persistent layer failed: $it")
  }
}

/**
 * Add layer to the style persistently.
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
@JvmOverloads
fun StyleInterface.addPersistentLayer(layer: Layer, position: LayerPosition? = null) {
  layer.bindPersistentlyTo(this, position)
}

/**
 * Get the persistent property as Boolean.
 *
 * If true, the layer will be persisted across style changes.
 */
fun Layer.isPersistent(): Boolean? {
  return delegate?.isStyleLayerPersistent(layerId)?.value
}

@PublishedApi
internal const val TAG = "Mbgl-LayerUtils"