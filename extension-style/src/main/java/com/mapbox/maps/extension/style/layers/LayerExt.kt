@file:JvmName("LayerUtils")

package com.mapbox.maps.extension.style.layers

import com.mapbox.maps.CustomLayerHost
import com.mapbox.maps.CustomLayerRenderParameters
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.style.StyleContract
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
fun MapboxStyleManager.getLayer(layerId: String): Layer? {
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
    "custom" -> CustomLayer(
      layerId,
      // passing the dummy CustomLayerHost value here as an actual one is already applied in core
      object : CustomLayerHost {
        override fun initialize() {
        }

        override fun render(parameters: CustomLayerRenderParameters) {
        }

        override fun contextLost() {
        }

        override fun deinitialize() {
        }
      }
    )
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
inline fun <reified T : Layer> MapboxStyleManager.getLayerAs(layerId: String): T? {
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
fun MapboxStyleManager.addLayerBelow(layer: StyleContract.StyleLayerExtension, below: String?) {
  layer.bindTo(this, LayerPosition(null, below, null))
}

/**
 * Extension function to add a Layer provided by the Style Extension to the Style.
 *
 * @param layer The layer to be added
 * @param above the layer id that the current layer is added above
 */
fun MapboxStyleManager.addLayerAbove(layer: StyleContract.StyleLayerExtension, above: String?) {
  layer.bindTo(this, LayerPosition(above, null, null))
}

/**
 * Extension function to add a Layer provided by the Style Extension to the Style.
 *
 * @param layer The layer to be added
 * @param index the index that the current layer is added on
 */
fun MapboxStyleManager.addLayerAt(layer: StyleContract.StyleLayerExtension, index: Int?) {
  layer.bindTo(this, LayerPosition(null, null, index))
}

/**
 * Extension function to add a Layer provided by the Style Extension to the Style.
 *
 * @param layer The layer to be added
 */
fun MapboxStyleManager.addLayer(layer: StyleContract.StyleLayerExtension) {
  layer.bindTo(this)
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
fun MapboxStyleManager.addPersistentLayer(layer: Layer, position: LayerPosition? = null) {
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